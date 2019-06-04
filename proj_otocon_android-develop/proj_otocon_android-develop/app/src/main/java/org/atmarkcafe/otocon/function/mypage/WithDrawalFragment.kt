package org.atmarkcafe.otocon.function.mypage

import android.content.Context
import android.view.View
import org.atmarkcafe.otocon.ExtensionActivity
import org.atmarkcafe.otocon.MainFragment
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentWithDrawalBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.mypage.rematch.RequestRematchFragment
import org.atmarkcafe.otocon.function.party.RegisteredPartyListFragment
import org.atmarkcafe.otocon.model.Account
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.parameter.WidDrawParams
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension
import org.atmarkcafe.otocon.utils.FragmentUtils

class WithDrawalFragment : OtoconBindingFragment<FragmentWithDrawalBinding>(), MVPExtension.View<ResponseExtension<String>> {
    val presenter = WithDrawalPresenter(this)
    var param = WidDrawParams()
    override fun layout(): Int {
        return R.layout.fragment_with_drawal
    }

    override fun onCreateView(viewDataBinding: FragmentWithDrawalBinding?) {
        viewDataBinding?.let {
            it.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

            it.buttonAppliedParty.setOnClickListener {
                val fragment = RegisteredPartyListFragment()
                FragmentUtils.replace(activity!!, fragment, true)
            }

            it.buttonWithDrawal.setOnClickListener { view ->
                ExtensionActivity.setEnableBack(activity, false)
                it.inputPasswordLayout.error = ""
                presenter.onExecute(context, 0, param)
            }
        }

        viewDataBinding!!.param = param
    }

    override fun success(response: ResponseExtension<String>?) {
        // TODO notify with drawal is successful
        ExtensionActivity.setEnableBack(activity, true)
        DBManager.save(activity!!, Account())

        val fragment = MainFragment()
        FragmentUtils.replace(activity!!, fragment, false)
    }

    override fun showProgress(show: Boolean) {
        viewDataBinding?.loadingLayout?.root?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(response: ResponseExtension<String>?) {
        ExtensionActivity.setEnableBack(activity, true)
        viewDataBinding?.inputPasswordLayout?.error = response?.getErrorMessage("password")
    }

    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(context, title, message, null)
    }
}

class WithDrawalPresenter(val view: MVPExtension.View<ResponseExtension<String>>) : MVPExtension.Presenter<WidDrawParams> {
    override fun onExecute(context: Context?, action: Int, params: WidDrawParams?) {
        view.showProgress(true)
        var listener = object : MVPPresenter.ExecuteListener<ResponseExtension<String>> {
            override fun onNext(response: ResponseExtension<String>?) {
                view.showProgress(false)
                if (response != null && response!!.isSuccess) {
                    view.success(response)
                } else {
                    view.showMessage(response)
                }
            }

            override fun onError(e: Throwable?) {
                view.showProgress(false)
                val messages = OnResponse.getMessage(context, e, null)
                view.showPopup(messages[0], messages[1])
            }
        }
        MVPPresenter.executeApi(InteractorManager.getApiInterface(context!!).outGroup(params!!.password), listener)
    }

}