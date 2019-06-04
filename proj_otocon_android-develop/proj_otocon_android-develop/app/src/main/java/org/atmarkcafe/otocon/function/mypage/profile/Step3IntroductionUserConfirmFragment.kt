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
class Step3IntroductionUserConfirmFragment : IntroductionUserConfirmFragment() {

    lateinit var modelParams: UserProfileParams
    lateinit var dataView: ProfileNameModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = Step3IntroductionUserConfirmPresenter(this)

        // lay data argume
        //Todo data
        val param: String = arguments?.get("param") as String
        val proflie: String = arguments?.get("model") as String

        modelParams = Gson().fromJson(param, UserProfileParams::class.java)
        dataView = Gson().fromJson(proflie, ProfileNameModel::class.java)

    }

    override fun onCreateView(viewDataBinding: FragmentIntroductionConfirmBinding?) {
        super.onCreateView(viewDataBinding)
        viewDataBinding?.btnEdit?.setOnClickListener {
            (presenter as Step3IntroductionUserConfirmPresenter).onSumit(activity!!, modelParams)
        }


        viewDataBinding?.btnEdit?.text = activity!!.getString(R.string.update)
        viewDataBinding?.model = dataView

    }

    override fun layout(): Int {
        return R.layout.fragment_introduction_confirm
    }

    override fun success(response: OnResponse?) {
        activity!!.onBackPressed()
        if (otoconFragmentListener!=null) {
            otoconFragmentListener.onHandlerReult(0, null)
        }
    }
}

class Step3IntroductionUserConfirmPresenter(view: MVPExtension.View<OnResponse>) : IntroductionUserConfirmPresenter(view) {
    override fun onExecute(context: Context?, action: Int, params: String?) {

    }

    fun onSumit(context: Context?, params: UserProfileParams?) {
        execute(InteractorManager.getApiInterface(context).updateUserProfile(params), object : ExecuteListener<OnResponse> {
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