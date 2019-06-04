package org.atmarkcafe.otocon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.function.beacon.GpsManager;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;
import org.atmarkcafe.otocon.utils.DialogUtils;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;

public class ExtensionActivity extends AppCompatActivity implements KeyExtensionUtils, FragmentManager.OnBackStackChangedListener {
    public static final int RESULT_CODE = 1000;

    private ExtensionActivityPresenter presenter = new ExtensionActivityPresenter(null);

    public enum Action {
        none,
        party_list,
        web,
        mypage,
        coupon,
        myparty,
        main;

        public static String ACTION = "org.atmarkcafe.otocon.ExtensionActivity";
    }

    public static void setEnableBack(Activity activity, boolean enable) {
        if (activity instanceof ExtensionActivity) {
            ((ExtensionActivity) activity).setEnableBack(enable);
        }
    }

    private boolean enableBack = true;

    public void setEnableBack(boolean enableBack) {
        this.enableBack = enableBack;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_party_list);

        // check deeplink rematch
        skipIntro(getIntent());

        SplashFragment f = new SplashFragment();
        FragmentUtils.replace(this, f, false);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private boolean skipIntro(Intent intent){
        Uri data = intent.getData();
        String deepLinkUrl = data != null ? data.toString() : "";

        // have deeplink
        if (deepLinkUrl.startsWith(BuildConfig.DEEP_LINK_URL)) {
            // chua vao app
            if (SearchDefault.getInstance().init(this).isSaveDefault()) {
                // dieu huong toi login
                // chi can save da qua man hinh intro
                String action = data.getQueryParameter(KEY_ACTION);
                if (action != null && (action.equals(KEY_ACTION_REMATCH)
                        || action.startsWith(KEY_ACTION_REMATCH_DETAIL)
                        || action.startsWith(KEY_ACTION_REMINDER))) {
                    SearchDefault.getInstance().setFirst(false);
                    SearchDefault.getInstance().save();
                    return true;
                }
            }
        }
        return false;
    }


    private int backStackCount = 0;

    @Override
    public void onBackStackChanged() {
        int newCount = getSupportFragmentManager().getBackStackEntryCount();
        if (newCount < backStackCount) {
            // remove fragment in stack
            ((OtoconFragment) FragmentUtils.getFragment(ExtensionActivity.this)).onReload();
        }
        backStackCount = newCount;
    }

    private long timeBackPressed = 0;

    @Override
    public void onBackPressed() {
        if (enableBack) {
            OtoconBindingFragment fragment = (OtoconBindingFragment) FragmentUtils.getFragment(this);
            if (fragment instanceof MainFragment) {
                if (System.currentTimeMillis() - timeBackPressed > 1500) {
                    Toast.makeText(this, R.string.message_press_back_to_exit, Toast.LENGTH_LONG).show();
                    timeBackPressed = System.currentTimeMillis();
                    return;
                }
            } else if (fragment.getStoreChildFrgementManager() != null && fragment.getStoreChildFrgementManager().getBackStackEntryCount() > 0) {
                fragment.getStoreChildFrgementManager().popBackStack();
                return;
            }

            super.onBackPressed();
        }
    }

    /*
     * Only here when the application is active or
     * in the background used to receive push notification
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Fragment currentFragment = FragmentUtils.getFragment(this);

        if (skipIntro(intent) && (currentFragment instanceof SplashFragment || currentFragment instanceof IntroFragment)){
            getIntent().setData(intent.getData());
            MainFragment fragment = new MainFragment();
            FragmentUtils.replace(this, fragment, false);
            return;
        }

        if (intent.getData() == null && !(intent.getExtras() != null && intent.getExtras().containsKey(KEY_TYPE))) {
            // no push & no deeplink
            return;
        }

        // back to Main
        FragmentUtils.backToTop(this);

        currentFragment = FragmentUtils.getFragment(this);
        if (currentFragment instanceof MainFragment) {
            ((MainFragment) currentFragment).redirect(intent);
        }
    }

    public static void openAppSettings(Context context) {
        Uri packageUri = Uri.fromParts("package", context.getPackageName(), null);

        Intent applicationDetailsSettingsIntent = new Intent();

        applicationDetailsSettingsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        applicationDetailsSettingsIntent.setData(packageUri);
        applicationDetailsSettingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(applicationDetailsSettingsIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AuthenticationUtils.Companion.setActivity(this);

        // Call api start time online
        presenter.onExecute(this, ExtensionActivityPresenter.UPDATE_TIME_START_ONLINE, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Call api end time online
        presenter.onExecute(this, ExtensionActivityPresenter.UPDATE_TIME_END_ONLINE, null);
    }

    private void showDialogPushNotification(Intent intent) {
        if (intent.getExtras() != null && intent.getStringExtra(KEY_TITLE) != null) {
            DialogUtils.showDialog(this, intent.getStringExtra(KEY_TITLE), intent.getStringExtra(KEY_CONTENT));
        }
    }

    class ExtensionActivityPresenter extends MVPPresenter<String, OnResponse> {
        public static final int UPDATE_TIME_START_ONLINE = 0;
        public static final int UPDATE_TIME_END_ONLINE = 1;

        public ExtensionActivityPresenter(MVPExtension.View<OnResponse> view) {
            super(view);
        }

        @Override
        public void onExecute(Context context, int action, String s) {
            if (DBManager.isLogin(context)) {
                if (action == UPDATE_TIME_START_ONLINE) {
                    execute(InteractorManager.getApiInterface(context).updateTimeStartOnline(), null);
                }
                if (action == UPDATE_TIME_END_ONLINE) {
                    execute(InteractorManager.getApiInterface(context).updateTimeEndOnline(), null);
                }
            }
        }
    }


    GpsManager.GpsManagerListener  listener = null;
    public void setGpsManagerListener(GpsManager.GpsManagerListener  listener){
        this.listener = listener;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GpsManager.REQUEST_CHECK_LOCATION_SETTINGS && listener != null){
            listener.onOpenGps(resultCode == RESULT_OK);
        }
    }
}
