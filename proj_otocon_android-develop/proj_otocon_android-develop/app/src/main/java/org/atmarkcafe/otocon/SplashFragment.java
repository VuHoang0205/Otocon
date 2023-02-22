package org.atmarkcafe.otocon;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.login.LoginFragment;
import org.atmarkcafe.otocon.function.notification.PushType;
import org.atmarkcafe.otocon.model.DBManager;

import org.atmarkcafe.otocon.databinding.ActivitySplashBinding;

import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.function.beacon.GpsManager;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;
import org.atmarkcafe.otocon.utils.LogUtils;

import java.util.Set;

public class SplashFragment extends OtoconBindingFragment<ActivitySplashBinding> implements Runnable {
    GpsManager gpsManager = new GpsManager(new GpsManager.GpsManagerListener() {
        @Override
        public void onRequestPermissionSuccess(boolean isGraned) {
            nextStep();
        }

        @Override
        public void onOpenGps(boolean open) {

        }

        @Override
        public void onDenied() {
        }
    });

    @Override
    public int layout() {
        return R.layout.activity_splash;
    }

    @Override
    public void onCreateView(ActivitySplashBinding viewDataBinding) {
        new Handler().postDelayed(this, 500);
    }

    private void nextStep() {
        if (getActivity() != null) {
            if (SearchDefault.getInstance().init(getActivity()).isSaveDefault()) {
                IntroFragment fragment = new IntroFragment();
                FragmentUtils.replace(getActivity(), fragment, false);
            } else {
                MainFragment fragment = new MainFragment();
                FragmentUtils.replace(getActivity(), fragment, false);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        gpsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void run() {
        if (getActivity() != null) {
            if (gpsManager.isCalled(getActivity())) {
//                nextStep();
            } else {
                gpsManager.request(this);
            }
        }
    }
}
