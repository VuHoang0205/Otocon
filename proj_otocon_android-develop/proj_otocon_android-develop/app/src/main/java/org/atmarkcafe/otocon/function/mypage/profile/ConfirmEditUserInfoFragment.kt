package org.atmarkcafe.otocon.function.mypage.profile

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentConfirmEditUserInformationBinding
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchParams
import org.atmarkcafe.otocon.model.UserInfo
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserInfoResponse

class ConfirmEditUserInfoFragment : ViewUserInfoFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ConfirmEditUserInfoPresenter(this)
    }
    override fun onCreateView(viewDataBinding: FragmentConfirmEditUserInformationBinding) {
        super.onCreateView(viewDataBinding)
        val model_text = arguments!!.getString("model_text")
        model = Gson().fromJson<UserInfo>(model_text, UserInfo::class.java)
        viewDataBinding.model = model
        viewDataBinding.btnSubmit.text = getString(R.string.save)
    }

    override fun onSubmit() {
        presenter.onExecute(activity,1,model)
    }

    override fun success(response: OnResponse?) {
        finishParent(0, null)
    }
}

class ConfirmEditUserInfoPresenter(view: MVPExtension.View<OnResponse>) : ViewUserInfoFragmentPresenter(view) {
    override fun onExecute(context: Context?, action: Int, params: UserInfo?) {
        if (action == 0) {
            return
        } else if (action == 1) {
            execute(InteractorManager.getApiInterface(context).updateUserInfo(params), object : ExecuteListener<OnResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])

                }

                override fun onNext(response: OnResponse?) {
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
