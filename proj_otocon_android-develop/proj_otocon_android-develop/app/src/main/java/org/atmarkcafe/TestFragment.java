package org.atmarkcafe;

import android.os.Handler;
import android.support.annotation.NonNull;

import org.atmarkcafe.otocon.IntroFragment;
import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ActivitySplashBinding;
import org.atmarkcafe.otocon.databinding.FragmentTestBinding;
import org.atmarkcafe.otocon.function.beacon.GpsManager;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.FragmentUtils;

public class TestFragment extends OtoconBindingFragment<FragmentTestBinding> {

    @Override
    public int layout() {
        return R.layout.fragment_test;
    }

    @Override
    public void onCreateView(FragmentTestBinding viewDataBinding) {
      setStoreChildFrgementManager(getChildFragmentManager());
      FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, new Test2Fragment(), false);
    }

    @Override
    public void onResume() {
        super.onResume();

        setStoreChildFrgementManager(getChildFragmentManager());
    }

    @Override
    public void onPause() {
        super.onPause();

        setStoreChildFrgementManager(null);
    }
}
