package org.atmarkcafe.otocon.function.mypage.updatepremium

import android.content.Context
import android.view.View
import org.atmarkcafe.otocon.ExtensionActivity
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentLeavePremiumBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.model.parameter.WidDrawParams
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.KeyboardUtils
import java.lang.Exception
import java.text.SimpleDateFormat

class LeavePremiumFragment : OtoconBindingFragment<FragmentLeavePremiumBinding>(), MVPExtension.View<ResponseExtension<String>> {
    val presenter = LeavePremiumPresenter(this)
    var param = WidDrawParams()
    override fun layout(): Int {
        return R.layout.fragment_leave_premium
    }

    override fun onCreateView(viewDataBinding: FragmentLeavePremiumBinding?) {
        viewDataBinding?.let { it ->
            it.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

            it.buttonLeavePremium.setOnClickListener { view ->
                ExtensionActivity.setEnableBack(activity, false)
                it.inputPasswordLayout.error = ""
                presenter.onExecute(context, 0, param)
            }
            it.content.setOnClickListener { KeyboardUtils.hiddenKeyBoard(activity) }


            try {
                val datetime = arguments?.getString("date_expired")
                val fmFrom = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val format = SimpleDateFormat("yyyy/M/d")
                viewDataBinding.leavePremiumMessage.text = String.format(getString(R.string.leave_premium_message), format.format(fmFrom.parse(datetime)))
            } catch(e: Exception){
                viewDataBinding.leavePremiumMessage.text = String.format(getString(R.string.leave_premium_message), "XXX")
            }
        }

        viewDataBinding!!.param = param
    }

    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(context, title, message, null)
    }

    override fun success(response: ResponseExtension<String>?) {
        // downgrade is successful
        val fragment = LeavePremiumSuccessFragment()
        fragment.setOtoconFragmentListener { status, extras -> finish(status, extras) }
        FragmentUtils.replace(activity, fragment, true)
    }

    override fun showProgress(show: Boolean) {
        viewDataBinding?.loadingLayout?.root?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(response: ResponseExtension<String>?) {
        ExtensionActivity.setEnableBack(activity, true)
        viewDataBinding?.inputPasswordLayout?.error = response?.getErrorMessage("password")
    }
}

class LeavePremiumPresenter(val view: MVPExtension.View<ResponseExtension<String>>) : MVPExtension.Presenter<WidDrawParams> {
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
        MVPPresenter.executeApi(InteractorManager.getApiInterface(context!!).leavePremium(params!!.password), listener)
    }
}
