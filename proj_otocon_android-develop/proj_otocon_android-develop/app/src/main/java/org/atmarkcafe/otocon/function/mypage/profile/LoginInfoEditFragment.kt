package org.atmarkcafe.otocon.function.mypage.profile

import android.app.Activity
import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import org.atmarkcafe.otocon.ExtensionActivity
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentLoginInfoConfirmBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.model.Account
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.ErrorValidatePasswordModel
import org.atmarkcafe.otocon.model.LoginInfoModel
import org.atmarkcafe.otocon.model.response.*
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.KeyboardUtils

class LoginInfoEditFragment : OtoconBindingFragment<FragmentLoginInfoConfirmBinding>(), MVPExtension.View<OnResponse> {

    val presenter = LoginInfoEditPresenter(this)
    val params = LoginInfoModel()
    val errorMsg = ObservableField("")
    var error = ErrorValidatePasswordModel()

    override fun layout(): Int {
        return R.layout.fragment_login_info_confirm
    }

    override fun onCreateView(viewDataBinding: FragmentLoginInfoConfirmBinding?) {
        params.email = DBManager.getMyAccount(context).email
        viewDataBinding?.let {
            it.toolbar.setNavigationOnClickListener {
                KeyboardUtils.hiddenKeyBoard(activity)
                activity?.onBackPressed()
            }

            it.fragment = this
            it.error = error
            it.model = params
        }

    }

    fun hideKeyBoard() {
        KeyboardUtils.hiddenKeyBoard(activity)
    }

    override fun showPopup(title: String?, message: String?) {
        ExtensionActivity.setEnableBack(activity, true)
        PopupMessageErrorDialog.show(context, title, message, null)
    }

    override fun success(response: OnResponse?) {
        if (response is ValidatePasswordRespone) {
            if (response!!.isSuccess) {
                var data  = Bundle()
                val infoEditFragment = LoginInfoConfirmFragment()
                infoEditFragment.setOtoconFragmentListener { status, extras ->
                    activity!!.onBackPressed()
                    otoconFragmentListener.onHandlerReult(status, extras)
                }
                data.putString("data", Gson().toJson(params))
                infoEditFragment.arguments = data
                FragmentUtils.replace(activity!!, infoEditFragment, true)
            }
        }
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            viewDataBinding?.loadingLayout?.root?.visibility = View.VISIBLE
        } else {
            viewDataBinding?.loadingLayout?.root?.visibility = View.GONE
        }
    }

    override fun showMessage(response: OnResponse?) {
        if (response is ValidatePasswordRespone) {
            errorMsg.set(response.message)
            viewDataBinding.error = response.errors
        }
    }

    fun onSubmit() {
        KeyboardUtils.hiddenKeyBoard(activity)
        viewDataBinding?.let {
            viewDataBinding.error = ErrorValidatePasswordModel()
            presenter.onExecute(context, 0, params)
        }
    }
}

class LoginInfoEditPresenter(view: LoginInfoEditFragment) : MVPPresenter<LoginInfoModel, OnResponse>(view) {

    override fun onExecute(context: Context?, action: Int, params: LoginInfoModel?) {

        if (action == 0) {
            execute(InteractorManager.getApiInterface(context).validateChangePassword(params), object : ExecuteListener<ValidatePasswordRespone> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])
                }

                override fun onNext(response: ValidatePasswordRespone?) {
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