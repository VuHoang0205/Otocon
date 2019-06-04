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
import org.atmarkcafe.otocon.databinding.FragmentIntroductionConfirmBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.mypage.item.ProfileNameModel
import org.atmarkcafe.otocon.function.mypage.item.UserProfileModel
import org.atmarkcafe.otocon.function.mypage.item.UserProfileParams
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserProfileResponse
import org.atmarkcafe.otocon.model.response.UserPropertiesRespone
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.StringUtils
import kotlin.math.log

// Step 1
open class IntroductionUserConfirmFragment : OtoconBindingFragment<FragmentIntroductionConfirmBinding>(), MVPExtension.View<OnResponse> , OtoconFragment.OtoconFragmentListener{

    var presenter = IntroductionUserConfirmPresenter(this)
    var model: ProfileNameModel = ProfileNameModel()
     var userProfle :UserProfileModel =UserProfileModel();


    override fun onCreateView(viewDataBinding: FragmentIntroductionConfirmBinding?) {
        viewDataBinding?.toolbar?.setNavigationOnClickListener { v -> activity!!.onBackPressed() }
        presenter.onExecute(activity!!, 0, null)

        viewDataBinding?.btnEdit?.setOnClickListener {
            var fragment: SelfIntroductionCardInfoFragment = SelfIntroductionCardInfoFragment()
            fragment.otoconFragmentListener = this
            var bundle: Bundle = Bundle()
            bundle.putString("data", Gson().toJson(model))
            bundle.putString("profile",Gson().toJson(userProfle))
            fragment.arguments = bundle
            FragmentUtils.replace(activity, fragment, true)
        }

    }

    override fun layout(): Int {
        return R.layout.fragment_introduction_confirm
    }

    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity!!, title, message, null)
    }

    override fun success(response: OnResponse?) {
        if (response is UserProfileResponse) {
            userProfle =response.model
            model.setDataName(context,model, userProfle)
            viewDataBinding.model = model

//            if (StringUtils.parseStringToInteger(response.model.complete_card, 0) == 0) {
//                // goto created screen
//                var fragment: SelfIntroductionCardInfoFragment = SelfIntroductionCardInfoFragment()
//                fragment.otoconFragmentListener = this.otoconFragmentListener
//                var bundle: Bundle = Bundle()
//                bundle.putString("data", Gson().toJson(model))
//                bundle.putString("profile",Gson().toJson(userProfle))
//                fragment.arguments = bundle
//                FragmentUtils.replace(activity, fragment, false)
//            }
        }else{
            presenter.onExecute(activity!!, 0, null)
        }

    }

    override fun showProgress(show: Boolean) {
        setEnableBack(!show)
        if(show){
            viewDataBinding.loading.root.visibility = View.VISIBLE
        }else{
            viewDataBinding.loading.root.visibility = View.GONE
        }
    }

    override fun showMessage(response: OnResponse?) {

    }

    // TODO OtoconFragmentListener
    override fun onHandlerReult(status: Int, extras: Bundle?) {
        activity?.onBackPressed()
    }
}

open class IntroductionUserConfirmPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<String, OnResponse>(view) {

    open override fun onExecute(context: Context?, action: Int, params: String?) {

        if (action == 0) {
            execute(InteractorManager.getApiInterface(context).userProfile, object : ExecuteListener<UserProfileResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])
                }

                override fun onNext(response: UserProfileResponse?) {
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
}