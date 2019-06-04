package org.atmarkcafe.otocon.function.mypage.profile

import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentMypageUpdatePremiumRootBinding
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.ViewChangeInfoRematchFragment
import org.atmarkcafe.otocon.utils.FragmentUtils

class EditInfoUserRootFragment : OtoconBindingFragment<FragmentMypageUpdatePremiumRootBinding>() {

    override fun layout(): Int {
        return R.layout.fragment_mypage_update_premium_root
    }

    override fun onCreateView(viewDataBinding: FragmentMypageUpdatePremiumRootBinding) {
        setStoreChildFrgementManager(getChildFragmentManager())

        var fragment = ViewUserInfoFragment()
        
        FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, fragment, false)
    }
}