package org.atmarkcafe.otocon.function.mypage.profile

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View

import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentLoginInfoBinding
import org.atmarkcafe.otocon.model.Account
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.parameter.Params
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserRespose
import org.atmarkcafe.otocon.utils.FragmentUtils

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.atmarkcafe.otocon.model.LoginInfoModel

open class LoginInfoFragment : OtoconBindingFragment<FragmentLoginInfoBinding>(), MVPExtension.View<OnResponse> {
    protected var presenter = LoginInfoPresenter(this)
    var user = Account()
    open lateinit var textSubmit:String
    override fun layout(): Int {
        return R.layout.fragment_login_info
    }

    override fun onCreateView(viewDataBinding: FragmentLoginInfoBinding) {
        textSubmit=getString(R.string.edit_introduce)
        viewDataBinding.fragment = this
        viewDataBinding.toolbar.setNavigationOnClickListener { v -> activity!!.onBackPressed() }
        viewDataBinding.passworld.infoKey.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        user = DBManager.getMyAccount(context)
        viewDataBinding.user = user
        presenter.loadInfor(activity, object : MVPPresenter.ExecuteListener<UserRespose> {
            override fun onNext(userRespose: UserRespose) {
                if (context != null)
                    viewDataBinding.user = user
            }

            override fun onError(e: Throwable) {

            }
        })

    }

   open fun onSubmit() {
        val infoEditFragment = LoginInfoEditFragment()
        infoEditFragment.setOtoconFragmentListener { status, extras ->
            activity!!.onBackPressed()
            otoconFragmentListener.onHandlerReult(status, extras)
        }
        FragmentUtils.replace(activity!!, infoEditFragment, true)
    }

    override fun showPopup(title: String, message: String) {

    }

    override fun success(userRespose: OnResponse) {

    }

    override fun showProgress(show: Boolean) {
        viewDataBinding.loadingLayout.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(userRespose: OnResponse) {

    }
}

open class LoginInfoPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<LoginInfoModel, OnResponse>(view) {

    override fun onExecute(context: Context, action: Int, params: LoginInfoModel) {

    }
}

