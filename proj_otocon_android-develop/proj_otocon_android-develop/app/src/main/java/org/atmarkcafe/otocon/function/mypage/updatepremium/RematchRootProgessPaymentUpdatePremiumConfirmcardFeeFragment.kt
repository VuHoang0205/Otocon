package org.atmarkcafe.otocon.function.mypage.updatepremium

import android.os.Bundle
import android.view.View
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.*
import org.atmarkcafe.otocon.function.mypage.profile.ProfileSettingFragment
import org.atmarkcafe.otocon.function.rematch.RematchStatus
import org.atmarkcafe.otocon.utils.FragmentUtils


class RematchRootProgessPaymentUpdatePremiumConfirmcardFeeFragment : OtoconBindingFragment<FragmentRematchUserProgressPaymentUpdatePremiumConfirmCardFeeBinding>() {

    var transaction_id: String? = null
    var isPremium: Boolean? = true

    override fun layout(): Int {
        return R.layout.fragment_rematch_user_progress_payment_update_premium_confirm_card_fee
    }

    override fun onCreateView(viewDataBinding: FragmentRematchUserProgressPaymentUpdatePremiumConfirmCardFeeBinding?) {
        setEnableBack(false)

//        Trường hợp upgrade từ Profile setting thì chữ trên button là プロフィール設定画面へ và điều hướng về My page
//        Trường hợp upgrade từ follow rematch thì chữ trên button là 連絡先情報を送り続けます

        if (isPremium!!){
            viewDataBinding!!.btn.setText(if (transaction_id != null) R.string.rematch_btn_update_premium else R.string.goto_my_page)
        }

        viewDataBinding!!.btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                setEnableBack(true)
                //TODO
                var extras: Bundle? = null
                if (isPremium!!) {
                    if ( transaction_id != null) {
                        // goto My Page
                        extras = Bundle()
                        extras.putSerializable("status", RematchStatus.updatePremium)
                        finishParent(0, extras)
                    } else {
                        FragmentUtils.backFragment(activity, ProfileSettingFragment::class.java)
                    }
                } else {
                    // send request rematch
                    finish(0, extras)
                }
            }
        })
    }
}