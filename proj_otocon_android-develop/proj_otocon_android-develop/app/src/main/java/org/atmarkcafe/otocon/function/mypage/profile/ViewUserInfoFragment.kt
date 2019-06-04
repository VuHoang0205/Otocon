package org.atmarkcafe.otocon.function.mypage.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentConfirmEditUserInformationBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.ChangeInfoOnRematchFragment
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchParams
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchResponse
import org.atmarkcafe.otocon.model.UserInfo
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.PrefectureResponse
import org.atmarkcafe.otocon.model.response.UserInfoResponse
import org.atmarkcafe.otocon.pref.BaseShareReferences
import org.atmarkcafe.otocon.utils.FragmentUtils

open class ViewUserInfoFragment : OtoconBindingFragment<FragmentConfirmEditUserInformationBinding>(),  MVPExtension.View<OnResponse> ,  OtoconFragment.OtoconFragmentListener{
    override fun onHandlerReult(status: Int, extras: Bundle?) {
       // activity!!.onBackPressed()

        presenter.onExecute(activity, 0, null)
    }

    var presenter: ViewUserInfoFragmentPresenter = ViewUserInfoFragmentPresenter(this)
    var model: UserInfo = UserInfo()

    override fun layout(): Int {
        return R.layout.fragment_confirm_edit_user_information
    }

    override fun onCreateView(viewDataBinding: FragmentConfirmEditUserInformationBinding) {
        viewDataBinding.toolbar.setNavigationOnClickListener { v -> activity!!.onBackPressed() }
        viewDataBinding.fragment = this
        presenter.onExecute(activity,0,null)
    }

    open fun onSubmit(){
        val fragment = EditUserInformationFragment()

        val bundle = Bundle()
        val gson = Gson()
        val modelText: String = gson.toJson(model)
        bundle.putString("model_text", modelText)

        fragment.arguments = bundle
        fragment.otoconFragmentListener = this
        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
    }

    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity, title, message, null)
    }

    open override fun success(response: OnResponse?) {
        model = (response as UserInfoResponse).userInfor
        model.setDefaul(activity)
        viewDataBinding.model = model
    }

    override fun showProgress(show: Boolean) {
        setEnableBack(!show)
        if (show) viewDataBinding.loading.root.visibility = View.VISIBLE else viewDataBinding.loading.root.visibility = View.GONE
    }

    override fun showMessage(response: OnResponse?) {

    }
}

open class ViewUserInfoFragmentPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<UserInfo, OnResponse>(view) {

    open override fun onExecute(context: Context?, action: Int, params: UserInfo?) {

        if (action == 0) {
            if (BaseShareReferences(context).get(BaseShareReferences.KEY_PREFECTURE).isNullOrEmpty()){
                execute(InteractorManager.getApiInterface(context).listPefectures(), object : ExecuteListener<PrefectureResponse> {
                    override fun onError(e: Throwable?) {

                    }

                    override fun onNext(response: PrefectureResponse?) {
                        val data = response?.getList()
                        // save list to db
                        val gson = Gson()
                        val json = gson.toJson(data)
                        BaseShareReferences(context).set(BaseShareReferences.KEY_PREFECTURE, json)
                    }
                })
            }

            execute(InteractorManager.getApiInterface(context).userInfoBasic, object : ExecuteListener<UserInfoResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])

                }

                override fun onNext(response: UserInfoResponse?) {
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
