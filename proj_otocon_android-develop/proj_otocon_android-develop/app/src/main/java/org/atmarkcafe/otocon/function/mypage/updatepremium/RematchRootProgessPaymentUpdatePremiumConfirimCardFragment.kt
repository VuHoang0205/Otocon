package org.atmarkcafe.otocon.function.mypage.updatepremium

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.view.View
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.*
import org.atmarkcafe.otocon.function.party.register.SuccessRegisterPartyFragment
import org.atmarkcafe.otocon.function.party.register.card.ConfirmCardContract
import org.atmarkcafe.otocon.function.party.register.card.ConfirmCardFragment
import org.atmarkcafe.otocon.function.party.register.card.ConfirmCardPresenter
import org.atmarkcafe.otocon.function.rematch.RematchStatus
import org.atmarkcafe.otocon.model.CreditCard
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.parameter.PaymentParams
import org.atmarkcafe.otocon.model.response.ConfirmCreditCardResponse
import org.atmarkcafe.otocon.model.response.PaymentResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.ValidateUtils


class RematchRootProgessPaymentUpdatePremiumConfirimCardFragment : ConfirmCardFragment() , OtoconFragment.OtoconFragmentListener{

    var transaction_id:String? = null
    var isPremium : Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ConfirmUpdatePremiumCardPresenter(this)
    }

    override fun onCreateView(binding: AcitivityChooseConfirmCardBinding?) {
        super.onCreateView(binding)

        binding!!.step!!.root.visibility = View.GONE

        (presenter as ConfirmUpdatePremiumCardPresenter).transaction_id = transaction_id
        (presenter as ConfirmUpdatePremiumCardPresenter).isPremium = isPremium
    }

    override fun success(success_html: String?) {

        if(!this.isPremium!!){
            //TODO show dialog
            var extras = Bundle()
            extras.putSerializable("status", RematchStatus.card)

            finishParent(0, extras)
//            activity!!.onBackPressed()
//            otoconFragmentListener.onHandlerReult(0, extras)
        }else{
            val fragment = RematchRootProgessPaymentUpdatePremiumConfirmcardFeeFragment()//.create(success_html, object : OtoconFragmentListener {

            fragment.isPremium = isPremium
            fragment.transaction_id = transaction_id
            fragment.otoconFragmentListener = this
            FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
        }
    }

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        // OtoconFragment.OtoconFragmentListener
        activity!!.onBackPressed()
        otoconFragmentListener.onHandlerReult(status, extras)
    }
}

class ConfirmUpdatePremiumCardPresenter(view : ConfirmCardContract.View) : ConfirmCardPresenter(view){

    var transaction_id:String? = null
    var isPremium : Boolean? = true

    override fun onExcute(context: Context?, transaction_id: String?, card: CreditCard) {
        view.showProgess(true)
        messageError.set("")

        val params = PaymentParams()
        params.setData(context!!, "$transaction_id", card)

        if(isPremium!!){
            if(this.transaction_id != null){
                // rematch update premium
                // /api/share-contact-by-upgrade-to-premium
                MVPPresenter.executeApi(InteractorManager.getApiInterface(context).rematchUpToUpdatePremium(params), object : MVPPresenter.ExecuteListener<PaymentResponse>{
                    override fun onNext(response: PaymentResponse?) {
                        view.showProgess(false)
                        if (response == null) {
                            view.showError(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail))
                        } else if (response.isSuccess) {
                            view.success(response.html_success)
                        } else if (response.isFaileShowDialog) {
                            view.onShowPopup(null, response.message)
                        } else {
                            messageError.set(response.message)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        view.showProgess(false)
                        if (ValidateUtils.isRetrofitErrorNetwork(e)) {
                            view.showError(null, null)
                        } else {
                            view.showError(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail))
                        }
                    }
                })
            }else{
                // my page update premium
                MVPPresenter.executeApi(InteractorManager.getApiInterface(context).userUpToUpdatePremium(params), object : MVPPresenter.ExecuteListener<PaymentResponse>{
                    override fun onNext(response: PaymentResponse?) {
                        view.showProgess(false)
                        if (response == null) {
                            view.showError(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail))
                        } else if (response.isSuccess) {
                            if (response.account.token != null && !response.account.token.isEmpty()) {
                                DBManager.save(context, response.account)
                            }
                            view.success(response.html_success)
                        } else if (response.isFaileShowDialog) {
                            view.onShowPopup(null, response.message)
                        } else {
                            messageError.set(response.message)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        view.showProgess(false)
                        if (ValidateUtils.isRetrofitErrorNetwork(e)) {
                            view.showError(null, null)
                        } else {
                            view.showError(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail))
                        }
                    }
                })
            }
        }else{
            // Payment rematch with normal account
            ///api/share-contact-normal
            MVPPresenter.executeApi(InteractorManager.getApiInterface(context).rematchNormalPayment(params), object : MVPPresenter.ExecuteListener<PaymentResponse>{
                override fun onNext(response: PaymentResponse?) {
                    view.showProgess(false)
                    if (response == null) {
                        view.showError(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail))
                    } else if (response.isSuccess) {
                        view.success(response.html_success)
                    } else if (response.isFaileShowDialog) {
                        view.onShowPopup(null, response.message)
                    } else {
                        messageError.set(response.message)
                    }
                }

                override fun onError(e: Throwable?) {
                    view.showProgess(false)
                    if (ValidateUtils.isRetrofitErrorNetwork(e)) {
                        view.showError(null, null)
                    } else {
                        view.showError(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail))
                    }
                }
            })
        }

    }

}