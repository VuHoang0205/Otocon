package org.atmarkcafe.otocon.function.mypage.profile

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import org.atmarkcafe.otocon.ExtensionActivity
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentLoginInfoBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.mypage.item.UserProfileParams
import org.atmarkcafe.otocon.function.mypage.profile.LoginInfoConfirmPrensenter.Companion.CHANGE_PASS_WORD
import org.atmarkcafe.otocon.model.Account
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.LoginInfoModel
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension
import org.atmarkcafe.otocon.model.response.UserRespose
import org.atmarkcafe.otocon.model.response.ValidatePasswordRespone
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.KeyboardUtils

class LoginInfoConfirmFragment : LoginInfoFragment() {

    lateinit var param: LoginInfoModel

    override fun onCreateView(viewDataBinding: FragmentLoginInfoBinding) {
        super.onCreateView(viewDataBinding)
        var bundle = arguments
        if (bundle != null) {
            param = Gson().fromJson(bundle.getString("data"), LoginInfoModel::class.java)
            user.email = param.email
            viewDataBinding!!.user = user
        }

        presenter = LoginInfoConfirmPrensenter(this)
        textSubmit = getString(R.string.save_completed)
        viewDataBinding.titleConfirm.text = getString(R.string.title_confirm_change_info)
        viewDataBinding.fragment = this
    }

    override fun success(userRespose: OnResponse) {

        DBManager.save(context, Account())
        val infoEditFragment = LoginInfoCompletedFragment()
        infoEditFragment.setOtoconFragmentListener { status, extras ->
            activity!!.onBackPressed()
            otoconFragmentListener.onHandlerReult(status, extras)
        }
        FragmentUtils.replace(activity!!, infoEditFragment, true)

    }

    override fun onSubmit() {
        ExtensionActivity.setEnableBack(activity, false)
        context?.let { presenter.onExecute(it, CHANGE_PASS_WORD, param) }
    }

    override fun showPopup(title: String, message: String) {
        ExtensionActivity.setEnableBack(activity, true)
        PopupMessageErrorDialog.show(activity, title, message, null)
    }
}

class LoginInfoConfirmPrensenter(view: MVPExtension.View<OnResponse>) : LoginInfoPresenter(view) {
    companion object {
        var CHANGE_PASS_WORD = 0
    }

    override fun onExecute(context: Context, action: Int, param: LoginInfoModel) {
        if (action == CHANGE_PASS_WORD) {
            execute(InteractorManager.getApiInterface(context).changePassword(param), object : ExecuteListener<OnResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])
                }

                override fun onNext(response: OnResponse?) {
                    if (response != null && response!!.isSuccess) {
                        view.success(response)
                    } else {
                        val messages = OnResponse.getMessage(context, null, response)
                        view.showPopup(messages[0], messages[1])
                    }
                }
            })

        }
    }

    override fun loadInfor(context: Context?, listener: ExecuteListener<UserRespose>?) {
        return
    }
}