package org.atmarkcafe.otocon.function.mypage.card;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentMypageUpdatePremiumRootBinding;
import org.atmarkcafe.otocon.utils.FragmentUtils;

public class SettingCardRootFragment extends OtoconBindingFragment<FragmentMypageUpdatePremiumRootBinding> {

    @Override
    public int layout() {
        return R.layout.fragment_mypage_update_premium_root;
    }

    @Override
    public void onCreateView(FragmentMypageUpdatePremiumRootBinding viewDataBinding) {
        setStoreChildFrgementManager(getChildFragmentManager());


        FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, new SettingCardChooseFragment(), false);
    }
}