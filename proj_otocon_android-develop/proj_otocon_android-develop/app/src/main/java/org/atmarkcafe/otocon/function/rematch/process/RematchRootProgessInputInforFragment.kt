package org.atmarkcafe.otocon.function.rematch.process

import android.content.Context
import android.os.Bundle
import android.view.View
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentRematchUserProgressInputInforBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.rematch.RematchStatus
import org.atmarkcafe.otocon.model.parameter.RequestRematchParams
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.ShareContactError
import org.atmarkcafe.otocon.model.response.ShareContactInfoResponse
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.KeyExtensionUtils


class RematchRootProgessInputInforFragment : OtoconBindingFragment<FragmentRematchUserProgressInputInforBinding>(), MVPExtension.View<OnResponse>, OtoconFragment.OtoconFragmentListener {
    override fun onHandlerReult(status: Int, extras: Bundle?) {
        onBackPressed()
        otoconFragmentListener?.onHandlerReult(0, extras)
    }

    var params: RequestRematchParams = RequestRematchParams()
    var presenter: VProgessInputInforPresenter = VProgessInputInforPresenter(this)
    var transaction_id: String? = ""

    override fun layout(): Int {
        return R.layout.fragment_rematch_user_progress_input_infor
    }

    override fun onCreateView(viewDataBinding: FragmentRematchUserProgressInputInforBinding?) {

        if (arguments != null) {
            // Data Input
            params.rematch_user_receive_id = arguments!!.getString("rematch_user_receive_id")
            params.event_id = arguments!!.getString("event_id")
            transaction_id = arguments!!.getString("transaction_id")

        }
        viewDataBinding?.fragment = this
        viewDataBinding?.params = params
        viewDataBinding?.ivClose?.setOnClickListener() {
            activity!!.onBackPressed()
        }
    }

    fun onSubmit() {
        viewDataBinding.error=null
        presenter.onExecute(activity, 0, params)
    }

    //View
    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity, title, message, null)
    }

    override fun success(response: OnResponse?) {
        if ((response as ShareContactInfoResponse).model.is_premium == 1) {
            val bundle = Bundle()
            bundle.putString("transaction_id", response .model.transaction_id.toString())
            bundle.putString(KeyExtensionUtils.KEY_USER_ID,params.rematch_user_receive_id)
            bundle.putString(KeyExtensionUtils.KEY_EVENT_ID, params.event_id)
            var fragment = RematchRootProgessChoosePaymentFragment()
            fragment.arguments = bundle
            fragment.otoconFragmentListener = this
            fragment.transaction_id = response .model.transaction_id.toString()
            viewDataBinding.error=null
            FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
        } else if (response.model.is_premium == 2) {
            // TODO chuyen den man hinh cuoi
            var extras = Bundle()
            extras.putSerializable("status", RematchStatus.isPremium);
            finishParent(0, extras)
//            otoconFragmentListener.onHandlerReult(0, extras)
        }
    }

    override fun showProgress(show: Boolean) {
        viewDataBinding.tvError.visibility = View.GONE
        setEnableBack(!show)
        if (show) viewDataBinding.loading.root.visibility = View.VISIBLE else viewDataBinding.loading.root.visibility = View.GONE
    }

    override fun showMessage(response: OnResponse?) {
        var error :ShareContactError =(response as ShareContactInfoResponse).error
        error.messageError=response.message
        viewDataBinding.error=error
    }
}

class VProgessInputInforPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<RequestRematchParams, OnResponse>(view) {
    override fun onExecute(context: Context?, action: Int, params: RequestRematchParams?) {
        if (action == 0) {
            execute(InteractorManager.getApiInterface(context).saveShareContactInfo(params), object : ExecuteListener<ShareContactInfoResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])
                }

                override fun onNext(response: ShareContactInfoResponse?) {
                    if (response!!.isSuccess) {
                        view.success(response)
                    } else {
                        view.showMessage(response)
                    }
                }
            })

        }
    }
}