package org.atmarkcafe.otocon.function.mypage.updatepremium

import android.app.Activity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.*
import org.atmarkcafe.otocon.function.party.register.card.ConfirmCardFragment
import org.atmarkcafe.otocon.function.party.register.card.CreditCardContract
import org.atmarkcafe.otocon.function.party.register.card.CreditCardFragment
import org.atmarkcafe.otocon.function.party.register.card.CreditCardPresenter
import org.atmarkcafe.otocon.model.CreditCard
import org.atmarkcafe.otocon.model.parameter.ConfirmCreditCardParams
import org.atmarkcafe.otocon.model.response.ConfirmCreditCardResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension
import org.atmarkcafe.otocon.utils.FragmentUtils



open class RematchRootProgessPaymentUpdatePremiumChooseCardFragment : CreditCardFragment() , OtoconFragment.OtoconFragmentListener{

    var transaction_id:String? = null
    var isPremium : Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments == null){
            arguments = Bundle()
        }
        if(isPremium!!){
            arguments!!.putInt("cost_price", 1620)
            arguments!!.putInt("total_price", 1620)
        }else if(transaction_id != null){
            arguments!!.putInt("cost_price", 540)
            arguments!!.putInt("total_price", 540)
        }
        mPresenter = UpdatePremiumCreditCardPresenter(activity!!, this)
        mPresenter.hasMessageHeader.set(false)
    }

    override fun onCreateView(binding: ActivityCreditCardBinding?) {
        super.onCreateView(binding)
        binding!!.step!!.root.visibility = View.GONE
        binding!!.textEnterFollowingItem!!.visibility = View.GONE

        (mPresenter as UpdatePremiumCreditCardPresenter).transaction_id = transaction_id
        (mPresenter as UpdatePremiumCreditCardPresenter).isPremium = isPremium
    }

    override fun goStep(session: Int, item: CreditCard?) {

        val fragment = RematchRootProgessPaymentUpdatePremiumConfirimCardFragment()
        val bundle = Bundle()

        bundle.putString("card", Gson().toJson(item))

        bundle.putInt("cost_price", listedPrice)
        bundle.putInt("total_price", paymentPrice)
        bundle.putInt("transaction_id", session)

        fragment.arguments = bundle

        fragment.isPremium = isPremium
        fragment.transaction_id = transaction_id

        fragment.otoconFragmentListener = this
        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
    }

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        // OtoconFragment.OtoconFragmentListener
        activity!!.onBackPressed()
        otoconFragmentListener.onHandlerReult(status, extras)
    }
}

class UpdatePremiumCreditCardPresenter (activity: Activity, view : CreditCardContract.View) : CreditCardPresenter(activity,view){
    var isPremium : Boolean? = true
    var transaction_id:String? = null

    override fun confirmCard(card: CreditCard?) {
        resetError()

        if (!validate(card)) return

        isLoading.set(true)
        view.showProgessBar(true)
        val params = ConfirmCreditCardParams()

        if (card == null || card.id.isNullOrEmpty()){
            params.secure = card?.securityCode
            params.number = card?.number
            params.expired = card?.expired
        } else {
            params.cardId = card.id
        }


        if(transaction_id != null){
            params.transaction_id = transaction_id
        }

        params.device_id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        params.session = session

        if(isPremium!!){
            if(this.transaction_id != null){
                MVPPresenter.executeApi(InteractorManager.getApiInterface(context).confirmCreditCard(params), object :MVPPresenter.ExecuteListener<ConfirmCreditCardResponse>{
                    override fun onNext(response: ConfirmCreditCardResponse?) {
                        view.showProgessBar(false)
                        if (response == null) {
                            val msg = ResponseExtension.getMessage(context, null, response)
                            view.showError(msg[0], msg[1])
                        } else if (response.isSuccess) {
                            view.goStep(response.sessionUserId, card)
                        } else {
                            val msg: String
                            if (response.errors.number != null && response.errors.number.size > 0) {
                                msg = response.errors.number[0]
                            } else {
                                msg = response.message
                            }
                            errorMessage.set(msg)
                        }

                        isLoading.set(false)
                    }

                    override fun onError(e: Throwable?) {
                        view.showProgessBar(false)
                        isLoading.set(false)
                        val msg = ResponseExtension.getMessage(context, e, null)
                        view.showError(msg[0], msg[1])
                    }
                })
            }else{
                MVPPresenter.executeApi(InteractorManager.getApiInterface(context).userConfirmCardUpdatePremium(params), object :MVPPresenter.ExecuteListener<ConfirmCreditCardResponse>{
                    override fun onNext(response: ConfirmCreditCardResponse?) {
                        view.showProgessBar(false)
                        if (response == null) {
                            val msg = ResponseExtension.getMessage(context, null, response)
                            view.showError(msg[0], msg[1])
                        } else if (response.isSuccess) {
                            view.goStep(response.sessionUserId, card)
                        } else {
                            val msg: String
                            if (response.errors.number != null && response.errors.number.size > 0) {
                                msg = response.errors.number[0]
                            } else {
                                msg = response.message
                            }
                            errorMessage.set(msg)
                        }

                        isLoading.set(false)
                    }

                    override fun onError(e: Throwable?) {
                        view.showProgessBar(false)
                        isLoading.set(false)
                        val msg = ResponseExtension.getMessage(context, e, null)
                        view.showError(msg[0], msg[1])
                    }
                })
            }

        }else{
            MVPPresenter.executeApi(InteractorManager.getApiInterface(context).confirmCreditCard(params), object :MVPPresenter.ExecuteListener<ConfirmCreditCardResponse>{
                override fun onNext(response: ConfirmCreditCardResponse?) {
                    view.showProgessBar(false)
                    if (response == null) {
                        val msg = ResponseExtension.getMessage(context, null, response)
                        view.showError(msg[0], msg[1])
                    } else if (response.isSuccess) {
                        view.goStep(response.sessionUserId, card)
                    } else {
                        val msg: String
                        if (response.errors.number != null && response.errors.number.size > 0) {
                            msg = response.errors.number[0]
                        } else {
                            msg = response.message
                        }
                        errorMessage.set(msg)
                    }

                    isLoading.set(false)
                }

                override fun onError(e: Throwable?) {
                    view.showProgessBar(false)
                    isLoading.set(false)
                    val msg = ResponseExtension.getMessage(context, e, null)
                    view.showError(msg[0], msg[1])
                }
            })
        }

    }
}