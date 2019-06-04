package org.atmarkcafe.otocon.function.rematch.process.payment.card

import android.view.View
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.*
import org.atmarkcafe.otocon.utils.FragmentUtils



class RematchRootProgessPaymentNormalCardConfirmFeeFragment : OtoconBindingFragment<FragmentRematchUserProgressChoosePaymentNormalConfirmCardFeeBinding>(){

    override fun layout(): Int {
        return R.layout.fragment_rematch_user_progress_choose_payment_normal_confirm_card_fee
    }

    override fun onCreateView(viewDataBinding: FragmentRematchUserProgressChoosePaymentNormalConfirmCardFeeBinding?) {

        viewDataBinding!!.btn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                //FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, RematchRootProgessSuccessFragment(), true)
                parentFragment!!.activity!!.supportFragmentManager.popBackStack()
            }
        })

    }
}