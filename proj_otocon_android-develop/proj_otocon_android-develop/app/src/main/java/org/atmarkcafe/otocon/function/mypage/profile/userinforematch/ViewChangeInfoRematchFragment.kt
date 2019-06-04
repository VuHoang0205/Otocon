package org.atmarkcafe.otocon.function.mypage.profile.userinforematch

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import org.atmarkcafe.otocon.BuildConfig
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentConfirmChangeInfoRematchBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.dialog.RematchFlowStatusDialog
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.InfoReMatch
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.ModelUserInfoRematch
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchParams
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchResponse
import org.atmarkcafe.otocon.function.register.RulesDialog
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserProfileResponse
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.KeyboardUtils
import org.atmarkcafe.otocon.utils.StringUtils

open class ViewChangeInfoRematchFragment : OtoconBindingFragment<FragmentConfirmChangeInfoRematchBinding>(), MVPExtension.View<OnResponse>, OtoconFragment.OtoconFragmentListener {

    var presenter: ViewChangeInfoRematchPresenter = ViewChangeInfoRematchPresenter(this)

    var repons: InfoReMatch = InfoReMatch()
    var model: ModelUserInfoRematch = ModelUserInfoRematch()


    override fun layout(): Int {
        return R.layout.fragment_confirm_change_info_rematch
    }

    override fun onCreateView(viewDataBinding: FragmentConfirmChangeInfoRematchBinding?) {
        viewDataBinding?.toolbar?.setNavigationOnClickListener { v -> activity!!.onBackPressed() }
        viewDataBinding?.tvLink?.setOnClickListener {
            RematchFlowStatusDialog(activity, getString(R.string.title_rematch_flow), BuildConfig.LINK_REMATCH_FLOW).show()
        }
        viewDataBinding?.fragment = this

        viewDataBinding?.model = model
        presenter.onExecute(activity, 0, null)

    }

    open fun submit() {
        val bundle = Bundle()
        val gson = Gson()
        val paramtext: String = gson.toJson(repons)
        val modelText: String = gson.toJson(model)
        bundle.putString("repons_text", paramtext)
        bundle.putString("model_text", modelText)
        val fragment = ChangeInfoOnRematchFragment()

        fragment.otoconFragmentListener = this
        fragment.arguments = bundle
        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
    }

    //view
    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity, title, message, null)
    }

    override fun success(response: OnResponse?) {
        repons = (response as UserInfoRematchResponse).model
        model.setRepons(repons)
        viewDataBinding?.model = model

        if (StringUtils.parseStringToInteger(repons.complete_rematch, 0) == 0) {
            // switch
            val bundle = Bundle()
            val gson = Gson()
            val paramtext: String = gson.toJson(repons)
            val modelText: String = gson.toJson(model)
            bundle.putString("repons_text", paramtext)
            bundle.putString("model_text", modelText)

            val fragment = ChangeInfoOnRematchFragment()

            fragment.arguments = bundle
            FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, false)
        }
    }

    override fun showProgress(show: Boolean) {
        setEnableBack(!show)
        if (show) viewDataBinding.loading.root.visibility = View.VISIBLE else viewDataBinding.loading.root.visibility = View.GONE
    }

    override fun showMessage(response: OnResponse?) {

    }

    // OtoconFragmentListener
    override fun onHandlerReult(status: Int, extras: Bundle?) {
        Log.e("tuyen","calll back")
        activity!!.onBackPressed()
        otoconFragmentListener.onHandlerReult(status, extras)
    }
}

open class ViewChangeInfoRematchPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<UserInfoRematchParams, OnResponse>(view) {

    override fun onExecute(context: Context?, action: Int, params: UserInfoRematchParams?) {

        if (action == 0) {
            execute(InteractorManager.getApiInterface(context).userInfoRematch, object : ExecuteListener<UserInfoRematchResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])

                }

                override fun onNext(response: UserInfoRematchResponse?) {
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