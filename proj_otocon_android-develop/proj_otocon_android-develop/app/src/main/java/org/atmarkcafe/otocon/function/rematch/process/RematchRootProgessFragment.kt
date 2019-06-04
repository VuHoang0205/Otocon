package org.atmarkcafe.otocon.function.rematch.process

import android.os.Bundle
import android.support.v4.app.Fragment
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentRematchRootProgessBinding
import org.atmarkcafe.otocon.utils.FragmentUtils

class RematchRootProgessFragment : OtoconBindingFragment<FragmentRematchRootProgessBinding>(), OtoconFragment.OtoconFragmentListener{

    override fun layout(): Int {
        return R.layout.fragment_rematch_root_progess
    }

    override fun onCreateView(viewDataBinding: FragmentRematchRootProgessBinding?) {
        storeChildFrgementManager = childFragmentManager

        var fragment = RematchRootProgessInputInforFragment()
        fragment.arguments = arguments
        fragment.otoconFragmentListener = this
        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, false)

        //
    }

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        activity!!.onBackPressed()
        otoconFragmentListener.onHandlerReult(status, extras)
    }
}