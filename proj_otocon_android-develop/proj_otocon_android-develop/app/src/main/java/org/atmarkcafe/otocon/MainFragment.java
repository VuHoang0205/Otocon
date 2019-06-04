package org.atmarkcafe.otocon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.databinding.ActivityMainBinding;
import org.atmarkcafe.otocon.dialog.RematchFlowStatusDialog;
import org.atmarkcafe.otocon.function.beacon.GPSBeaconFragment;
import org.atmarkcafe.otocon.function.home.HomeFragment;
import org.atmarkcafe.otocon.function.login.LoginFragment;
import org.atmarkcafe.otocon.function.menu.MenuFragment;
import org.atmarkcafe.otocon.function.menu.MenuNewFragment;
import org.atmarkcafe.otocon.function.mypage.MyPageMenuFragment;
import org.atmarkcafe.otocon.function.notification.PushType;
import org.atmarkcafe.otocon.function.beacon.GpsManager;
import org.atmarkcafe.otocon.function.party.PartyDetailFragment;
import org.atmarkcafe.otocon.function.party.SpecialDiscountPartyListFragment;
import org.atmarkcafe.otocon.function.rematch.RematchPairingFragment;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.TotalNotiRematch;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import onactivityresult.ActivityResult;
import onactivityresult.OnActivityResult;


public class MainFragment extends OtoconBindingFragment<ActivityMainBinding> implements KeyExtensionUtils, OtoconApplication.OtoconBluetoothManager.BluetoohServiceListener, TabLayout.OnTabSelectedListener, MVPExtension.View<TotalNotiRematch> {


    Fragment tabs[] = new Fragment[]{
            HomeFragment.newInstance(this),//
//            new SpecialDiscountPartyListFragment(),
            SpecialDiscountPartyListFragment.newInstance(false),
            new GPSBeaconFragment(),//
            new RematchPairingFragment(),
            new MenuFragment()//
    };

    private BroadcastReceiver receiver = null;

    MainFragmentPresenter presenter = new MainFragmentPresenter(this);

    @Override
    public int layout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateView(ActivityMainBinding binding) {

        TabLayout tab_layout = binding.tabLayout;

        tab_layout.addTab(tab_layout.newTab().setCustomView(R.layout.tab_indivicator));
        tab_layout.addTab(tab_layout.newTab().setCustomView(R.layout.tab_indivicator));
        tab_layout.addTab(tab_layout.newTab().setCustomView(R.layout.tab_indivicator));
        tab_layout.addTab(tab_layout.newTab().setCustomView(R.layout.tab_indivicator));
        tab_layout.addTab(tab_layout.newTab().setCustomView(R.layout.tab_indivicator));

        config(tab_layout, 0, getString(R.string.tab1), R.drawable.tab1_icon);
        config(tab_layout, 1, getString(R.string.tab2), R.drawable.tab2_icon);
        config(tab_layout, 2, getString(R.string.tab3), R.drawable.tab3_icon);
        config(tab_layout, 3, getString(R.string.tab4), R.drawable.tab4_icon);
        config(tab_layout, 4, getString(R.string.tab5), R.drawable.tab5_icon);

        tab_layout.addOnTabSelectedListener(this);

        redirect(getActivity().getIntent());

        IntentFilter filter = new IntentFilter();
        // Register action intentfilter
        filter.addAction(ACTION_NOTI_MY_REMATCH);
        filter.addAction(ACTION_HIDE_NOTI_MY_REMATCH);

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(ACTION_NOTI_MY_REMATCH)){
                    if (DBManager.isLogin(getActivity())){
                        presenter.onExecute(getActivity(),presenter.ACTION_GET_NOTI_TOTAL,null);
                    }
                } else if (action.equals(ACTION_HIDE_NOTI_MY_REMATCH)){
                    setNoticeBuget(viewDataBinding.tabLayout,3, 0);
                }
            }
        };
        getContext().registerReceiver(receiver, filter);

        if (current==-1){
            gotoTab(0);
        }
    }

    private void redirectByDeepLink(Uri deepLink) {
        String action = deepLink.getQueryParameter(KEY_ACTION);
        if (action == null) return;
        if (!DBManager.isLogin(getActivity())) {
            gotoTab(0);
            if (action.equals(KEY_ACTION_LOGIN)) {
                LoginFragment fragment = LoginFragment.create((status, extras) -> {
                    // refresh when logged in
                    ((HomeFragment) tabs[0]).refresh();
                });

                FragmentUtils.replace(getActivity(), fragment, true);
            } else if (action.equals(KEY_ACTION_EXPIRED_REGISTRATION)) {
                Bundle extras = new Bundle();
                extras.putBoolean("EXPIRED", true);
                LoginFragment fragment = LoginFragment.create((status, extras1) -> {
                    // refresh when logged in
                    ((HomeFragment) tabs[0]).refresh();
                });

                fragment.setArguments(extras);

                FragmentUtils.replace(getActivity(), fragment, true);
            } else if (action.equals(KEY_ACTION_REMATCH)
                    || action.startsWith(KEY_ACTION_REMATCH_DETAIL)
                    || action.startsWith(KEY_ACTION_REMINDER)) {
                // TODO Login
                LoginFragment fragment = LoginFragment.create((status, extras) -> {
                    // refresh when logged in
                    ((HomeFragment) tabs[0]).refresh();
                });
                FragmentUtils.replace(getActivity(), fragment, true);
            }
        } else {
            if (action.equals(KEY_ACTION_REMATCH)) {
                gotoTab(3);
            } else if (action.startsWith(KEY_ACTION_REMATCH_DETAIL)) {
                Bundle args = new Bundle();
                args.putString(KEY_TYPE, String.valueOf(PushType.rematchDetail.ordinal()));
                args.putString(KEY_USER_ID, deepLink.getQueryParameter(KEY_USER_ID));
                args.putString(KEY_EVENT_ID, deepLink.getQueryParameter(KEY_EVENT_ID));
                redirectMyPage(args);
            } else if (action.startsWith(KEY_ACTION_REMINDER)) {
                Bundle args = new Bundle();
                args.putString(KEY_TYPE, String.valueOf(PushType.rematchListRequest.ordinal()));
                redirectMyPage(args);
            } else {
                gotoTab(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    public void redirectMyPage(Bundle arguments){
        gotoTab(0);
        MyPageMenuFragment myPageFragment = new MyPageMenuFragment();
        arguments.putBoolean("redirect", true);
        myPageFragment.setArguments(arguments);
        myPageFragment.setOtoconFragmentListener(new OtoconFragmentListener() {
            @Override
            public void onHandlerReult(int status, Bundle extras) {
                // TODO tab 4
                gotoTab(3);
            }
        });
        FragmentUtils.replace(getActivity(), myPageFragment, true);
    }

    public void redirect(Intent intent) {
        if (intent != null) {
            RematchFlowStatusDialog.forceDismiss();
            if (intent.getExtras() != null && intent.getExtras().containsKey(KeyExtensionUtils.KEY_TYPE)) {
                String type = intent.getExtras().getString(KeyExtensionUtils.KEY_TYPE);

                switch (PushType.factory(type)) {
                    case newlist:
                        gotoTab(4);
                        FragmentUtils.replace(getActivity(), new MenuNewFragment(), true);
                        break;
                    case couponList:
                        redirectMyPage(intent.getExtras());
                        break;
                    case rematchDetail:
                        redirectMyPage(intent.getExtras());
                        break;
                    case rematchListRequest:
                        // call api click push
                        String event_id = intent.getExtras().getString(KeyExtensionUtils.KEY_EVENT_ID);
                        String rematch_type = intent.getExtras().getString(KeyExtensionUtils.KEY_REMATCH_TYPE);
                        List<String> listParam = new ArrayList<>();
                        listParam.add(event_id);
                        listParam.add(rematch_type);
                        presenter.onExecute(getContext(),presenter.ACTION_READ_PARTY_TIME,listParam);

                        redirectMyPage(intent.getExtras());
                        break;
                    case rematchTopPage:
                        gotoTab(3);
                        break;
                    case partyDetail:
                        gotoTab(0);
                        String partId = intent.getExtras().getString(KeyExtensionUtils.KEY_EVENT_ID);
                        PartyDetailFragment f = new PartyDetailFragment();
                        Bundle extras = new Bundle();
                        extras.putString(PartyDetailFragment.PARTY_ID, partId);
                        f.setArguments(extras);

                        FragmentUtils.replace(getActivity(), f, true);
                        break;
                    case notice:
                        String url = intent.getExtras().containsKey(KeyExtensionUtils.KEY_URL) ? intent.getExtras().getString(KeyExtensionUtils.KEY_URL): "";
                        if (url != null && !url.isEmpty()){
                            try {
                                Intent browerIntent = new Intent(Intent.ACTION_VIEW);
                                browerIntent.setData(Uri.parse(url));
                                browerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(browerIntent);
                            } catch (Exception e){

                            }
                        }
                }

            }
            Uri deepLink = intent.getData();
            if (deepLink != null && deepLink.toString().startsWith(BuildConfig.DEEP_LINK_URL)) {
                redirectByDeepLink(deepLink);
            }

        }
    }

    //====================================================================================
    // Tab selected
    private int current = -1;
    private boolean enable = true;

    public void disable() {
        enable = false;
        viewDataBinding.tabLayout.getTabAt(current).select();
    }

    public void gotoLocationTab() {
        gotoTab(2);
    }

    public void handelRegisterSuccess(int status, Bundle extras){
        gotoTab(0);
        ((HomeFragment)tabs[0]).handelRegisterSuccess(status, extras);
    }

    public void requestReLogin(){
        gotoTab(0);
        ((HomeFragment)tabs[0]).requestReLogin();
    }

    public void gotoTab(int index) {
        if (index != current) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, tabs[index]).runOnCommit(() -> {
                        if (tabs[index] instanceof HomeFragment){
                            ((HomeFragment) tabs[index]).refresh();
                        } else {
                            ((OtoconFragment) tabs[index]).onReload();
                        }
                    }).commit();
                    viewDataBinding.tabLayout.getTabAt(index).select();
            current = index;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        KeyboardUtils.hiddenKeyBoard(getActivity());

        if (!enable) {
            enable = true;
            return;
        }


        switch (tab.getPosition()) {
            case 0:
            case 4:
            case 3:
            case 1:
                gotoTab(tab.getPosition());
                break;

            case 2:
                // TODO: Location
                final GpsManager gpsManager = new GpsManager(new GpsManager.GpsManagerListener() {
                    @Override
                    public void onRequestPermissionSuccess(boolean isGraned) {

                    }

                    @Override
                    public void onDenied() {
                        disable();
                    }

                    @Override
                    public void onOpenGps(boolean open) {
                        if (open) {
                            // start check bluetooth
//                            if (OtoconApplication.getBluetoothManager(getActivity()).isSupportBluetooth(getActivity())) {
//                                if (OtoconApplication.getBluetoothManager(getActivity()).isEnabled(getActivity())) {
//                                    // enable
//                                    gotoLocationTab();
//                                } else {
//                                    disable();
//
//                                    OtoconApplication.getBluetoothManager(getActivity()).startBluetoothSetting(getActivity());
//                                }
//                            } else {

                                // don't support bluetooth
                                gotoLocationTab();
//                            }
                        } else {
                            disable();
                        }
                    }
                });
                gpsManager.requestFollow(getActivity());

                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //====================================================================================
    // Bluetooth
    @Override
    public void onBluettooth(OtoconApplication.OtoconBluetoothManager.BluetoothCommand command) {
        switch (command) {
            case start:
                break;
            case pause:
                break;
            case end:
                break;
            case no_support:
            case bluetooth_enable:
                // TODO show no support bluettooth
                gotoLocationTab();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        new AddDeviceNoLoginPresenter(null).onExecute(getActivity(), 0, null);

        Fragment f = tabs[viewDataBinding.tabLayout.getSelectedTabPosition()];

        if (f instanceof SpecialDiscountPartyListFragment || f instanceof GPSBeaconFragment
                || f instanceof MenuFragment || f instanceof RematchPairingFragment){
            ((OtoconFragment)f).onReload();
        }

        // call api get total noti remtach
        if (DBManager.isLogin(getActivity())){
            presenter.onExecute(getActivity(),presenter.ACTION_GET_NOTI_TOTAL,null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        OtoconApplication.getBluetoothManager(getActivity()).removeBluetoohServiceListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResult.onResult(requestCode, resultCode, data).into(this);
    }

//    //TODO 
//    public void onDetailParty(Intent data) {
//        //Todo DetailParty example
//        if (data.getExtras().containsKey("redirect-mypage") && !data.getExtras().getBoolean("redirect-mypage")) {
//            new PartyRegisterSuccessDialog(getActivity()).show();
//        }
//    }

    @OnActivityResult(requestCode = OtoconApplication.OtoconBluetoothManager.REQUEST)
    public void onBluetoothChange(Intent data) {
        // if (OtoconApplication.getBluetoothManager(this).isEnabled(this)) {
        //onTabSelectedListener.setEnable(false);
        //onTabSelectedListener.gotoLocationTab();
        viewDataBinding.tabLayout.getTabAt(2).select();
        // onTabSelectedListener.setEnable(true);
        //}
    }

    private void config(TabLayout tab_layout, int i, String text, int logo) {
        ImageView icon = ((ImageView) tab_layout.getTabAt(i).getCustomView().findViewById(R.id.icon));
        TextView textView = ((TextView) tab_layout.getTabAt(i).getCustomView().findViewById(R.id.text));
        if (logo > 0) {
            icon.setImageResource(logo);
            textView.setText(text);
        }
    }

    private void setNoticeBuget(TabLayout tab_layout, int i, int count) {
        TextView noti = ((TextView) tab_layout.getTabAt(i).getCustomView().findViewById(R.id.tvNoti));
        noti.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        noti.setText(String.valueOf(count));
    }

    @Override
    public void showPopup(String title, String message) {

    }

    @Override
    public void success(TotalNotiRematch totalNotiRematch) {
        setNoticeBuget(viewDataBinding.tabLayout,3,totalNotiRematch.getTotal());
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(TotalNotiRematch totalNotiRematch) {

    }
}

class MainFragmentPresenter extends MVPPresenter<List<String>, TotalNotiRematch> {
    public final int ACTION_GET_NOTI_TOTAL = 0;
    public final int ACTION_READ_PARTY_TIME = 1;

    public MainFragmentPresenter(MVPExtension.View<TotalNotiRematch> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, List<String> s) {
        if (action == ACTION_GET_NOTI_TOTAL){
            execute(InteractorManager.getApiInterface(context).getTotalNotificationRematch(), new ExecuteListener<TotalNotiRematch>(){

                @Override
                public void onNext(TotalNotiRematch totalNotiRematch) {
                    if (totalNotiRematch != null && totalNotiRematch.isSuccess()){
                        view.success(totalNotiRematch);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
        if (action == ACTION_READ_PARTY_TIME){
            execute(InteractorManager.getApiInterface(context).updateReadPartyTime(s.get(0), s.get(1)), new ExecuteListener<TotalNotiRematch>(){

                @Override
                public void onNext(TotalNotiRematch totalNotiRematch) {

                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }
}

