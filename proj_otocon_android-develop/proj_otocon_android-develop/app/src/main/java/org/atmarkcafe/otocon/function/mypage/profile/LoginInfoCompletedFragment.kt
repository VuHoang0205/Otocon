package org.atmarkcafe.otocon.function.mypage.profile

import org.atmarkcafe.otocon.ExtensionActivity
import org.atmarkcafe.otocon.MainFragment
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentLoginInfoCompletedBinding
import org.atmarkcafe.otocon.model.Account
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.utils.FragmentUtils

class LoginInfoCompletedFragment : OtoconBindingFragment<FragmentLoginInfoCompletedBinding>() {
    override fun layout(): Int {
        return R.layout.fragment_login_info_completed
    }

    override fun onCreateView(viewDataBinding: FragmentLoginInfoCompletedBinding?) {

        viewDataBinding?.fragment = this
    }

    fun onComplete() {
        ExtensionActivity.setEnableBack(activity, true)

        FragmentUtils.backToTop(activity)
        val currentFragment = FragmentUtils.getFragment(activity)
        (currentFragment as? MainFragment)?.requestReLogin()


    }
}