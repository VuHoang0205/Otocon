package org.atmarkcafe.otocon.function.mypage.updatepremium

import android.content.Context
import android.os.Bundle
import android.view.View
import org.atmarkcafe.otocon.BuildConfig
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentUpgradeUserPremiumBinding
import org.atmarkcafe.otocon.dialog.DialogRematchMessage
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.dialog.RematchFlowStatusDialog
import org.atmarkcafe.otocon.function.rematch.RematchStatus
import org.atmarkcafe.otocon.ktextension.setDefaultClient
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserPropertiesRespone
import org.atmarkcafe.otocon.model.response.UserRespose
import org.atmarkcafe.otocon.utils.FragmentUtils

open class UpgradeUserPremiumPravicyFragment : OtoconBindingFragment<FragmentUpgradeUserPremiumBinding>(), OtoconFragment.OtoconFragmentListener, MVPExtension.View<UserRespose> {
    var transaction_id: String? = null
    var isPremium: Boolean? = true
    val presenter = UpgradeUserPremiumPravicyPresenter(this)

    override fun onCreateView(viewDataBinding: FragmentUpgradeUserPremiumBinding?) {
        viewDataBinding?.webView?.setDefaultClient(viewDataBinding.loadingLayout!!.root)
        viewDataBinding?.webView?.loadUrl(BuildConfig.LINK_UPGRADE_PREMIUM)
        viewDataBinding?.toolbar?.setNavigationOnClickListener({ v -> activity!!.onBackPressed() })
        viewDataBinding?.fragment = this
    }

    override fun layout(): Int {
        return R.layout.fragment_upgrade_user_premium;
    }

    open fun submit() {
        presenter.onExecute(context, 0, null)
    }

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        // OtoconFragment.OtoconFragmentListener
        activity!!.onBackPressed()
        otoconFragmentListener.onHandlerReult(status, extras)
    }

    // MVPExtension.View
    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity, title, message, null)
    }

    // MVPExtension.View
    override fun success(response: UserRespose?) {
        var fragment = RematchRootProgessPaymentUpdatePremiumChooseCardFragment()
        fragment.isPremium = isPremium
        fragment.transaction_id = transaction_id

        fragment.otoconFragmentListener = this

        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
    }

    // MVPExtension.View
    override fun showProgress(show: Boolean) {
        viewDataBinding.loadingLayout.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    // MVPExtension.View
    override fun showMessage(response: UserRespose?) {
        DialogRematchMessage(activity!!, "あなたは既にプレミアム会員になっています。", DialogRematchMessage.DialogRematchListener { isOke, isChecked ->
            if (isOke) {
                val extras = Bundle()
                extras.putSerializable("status", RematchStatus.updatePremium)
                finishParent(0, extras)
            }
        }).setTextButtonGreen(getString(R.string.yes))
                .show()
    }

}

class UpgradeUserPremiumPravicyPresenter(view: MVPExtension.View<UserRespose>) : MVPPresenter<Int, UserRespose>(view) {
    override fun onExecute(context: Context?, action: Int, params: Int?) {
        execute(InteractorManager.getApiInterface(context).userInfo, object : ExecuteListener<UserRespose> {
            override fun onError(e: Throwable?) {
                val messages = OnResponse.getMessage(context, e, null)
                view.showPopup(messages[0], messages[1])
            }

            override fun onNext(response: UserRespose?) {
                if (response != null && response.isSuccess) {
                    if (!response.acoount!!.hasToken()) {
                        response.acoount!!.token = DBManager.getToken(context)
                    }
                    DBManager.save(context, response.acoount)
                    if (response.acoount.isPremium) {
                        view.showMessage(response)
                    } else {
                        view.success(response)
                    }
                } else {
                    val messages = OnResponse.getMessage(context, null, response)
                    view.showPopup(messages[0], messages[1])
                }
            }
        })
    }
}