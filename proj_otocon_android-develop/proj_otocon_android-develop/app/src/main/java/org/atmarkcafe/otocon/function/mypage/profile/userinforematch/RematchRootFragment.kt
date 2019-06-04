package org.atmarkcafe.otocon.function.mypage.profile.userinforematch

import android.os.Bundle
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentMypageUpdatePremiumRootBinding
import org.atmarkcafe.otocon.utils.FragmentUtils

class RematchRootFragment : OtoconBindingFragment<FragmentMypageUpdatePremiumRootBinding>(), OtoconFragment.OtoconFragmentListener {

    override fun layout(): Int {
        return R.layout.fragment_mypage_update_premium_root
    }

    override fun onCreateView(viewDataBinding: FragmentMypageUpdatePremiumRootBinding) {
        setStoreChildFrgementManager(getChildFragmentManager())
        val complete_rematch = arguments!!.getInt("complete_rematch",0)
        if (complete_rematch > 0) {
            val viewChangeInfoRematchFragment = ViewChangeInfoRematchFragment()
            viewChangeInfoRematchFragment.otoconFragmentListener = this@RematchRootFragment
            FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, viewChangeInfoRematchFragment, false)
        } else {
            val viewChangeInfoRematchFragment = ChangeInfoOnRematchFragment()
            viewChangeInfoRematchFragment.otoconFragmentListener = this@RematchRootFragment
            FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, viewChangeInfoRematchFragment, false)
        }

    }

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        activity!!.onBackPressed()
    }
}