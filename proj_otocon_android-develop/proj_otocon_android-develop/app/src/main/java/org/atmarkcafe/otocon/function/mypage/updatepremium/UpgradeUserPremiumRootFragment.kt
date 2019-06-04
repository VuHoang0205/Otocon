package org.atmarkcafe.otocon.function.mypage.updatepremium

import android.os.Bundle
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentMypageUpdatePremiumRootBinding
import org.atmarkcafe.otocon.utils.FragmentUtils

class UpgradeUserPremiumRootFragment  : OtoconBindingFragment<FragmentMypageUpdatePremiumRootBinding> (), OtoconFragment.OtoconFragmentListener{
    override fun layout(): Int {
        return R.layout.fragment_mypage_update_premium_root
    }

    override fun onCreateView(viewDataBinding: FragmentMypageUpdatePremiumRootBinding?) {
        storeChildFrgementManager = childFragmentManager

        var fragment = UpgradeUserPremiumPravicyFragment()
        fragment.otoconFragmentListener = this
        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame,fragment, false )
    }

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        activity!!.onBackPressed()
        otoconFragmentListener.onHandlerReult(status, extras)
    }
}