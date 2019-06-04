package org.atmarkcafe.otocon.function.rematch.process.payment.card

import android.view.View
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.*
import org.atmarkcafe.otocon.utils.FragmentUtils



class RematchRootProgessPaymentNormalCardListFragment : OtoconBindingFragment<FragmentRematchUserProgressChoosePaymentNormalChooseCardBinding>(){

    override fun layout(): Int {
        return R.layout.fragment_rematch_user_progress_choose_payment_normal_choose_card
    }

    override fun onCreateView(viewDataBinding: FragmentRematchUserProgressChoosePaymentNormalChooseCardBinding?) {




        viewDataBinding!!.btn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, RematchRootProgessPaymentNormalCardConfirmFragment(), true)
            }
        })

    }
}