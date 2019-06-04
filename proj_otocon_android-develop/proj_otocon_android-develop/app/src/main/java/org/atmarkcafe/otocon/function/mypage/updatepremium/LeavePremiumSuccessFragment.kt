package org.atmarkcafe.otocon.function.mypage.updatepremium

import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentLeavePremiumSuccessBinding
import org.atmarkcafe.otocon.function.mypage.profile.ProfileSettingFragment
import org.atmarkcafe.otocon.utils.FragmentUtils

class LeavePremiumSuccessFragment : OtoconBindingFragment<FragmentLeavePremiumSuccessBinding>() {
    override fun layout(): Int {
        return R.layout.fragment_leave_premium_success
    }

    override fun onCreateView(viewDataBinding: FragmentLeavePremiumSuccessBinding?) {
        viewDataBinding?.submit?.setOnClickListener {
            setEnableBack(true)
            FragmentUtils.backFragment(activity, ProfileSettingFragment::class.java)
        }
    }

}