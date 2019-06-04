package org.atmarkcafe.otocon.function.mypage.profile.userinforematch

import android.content.Context
import android.os.Bundle
import com.google.gson.Gson
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentConfirmChangeInfoRematchBinding
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.ModelUserInfoRematch
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchParams
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchResponse
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension

class ConfirmUserInfoRematchFragment : ViewChangeInfoRematchFragment(){

    var params : UserInfoRematchParams = UserInfoRematchParams()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ConfirmInfoRematchPresenter(this)
    }
    override fun onCreateView(viewDataBinding: FragmentConfirmChangeInfoRematchBinding?) {
        super.onCreateView(viewDataBinding)

        val modelText = arguments!!.getString("model_text")
        val param_text = arguments!!.getString("param_text")
        val gson = Gson()
        params = gson.fromJson<UserInfoRematchParams>(param_text, UserInfoRematchParams::class.java)
        model = gson.fromJson<ModelUserInfoRematch>(modelText, ModelUserInfoRematch::class.java)
        model.setParams(params)
        viewDataBinding?.model = model
        viewDataBinding?.btnSubmit?.text = activity!!.getString(R.string.save)
    }

    override fun submit() {
        params.setSubmit()
        presenter.onExecute(activity,1,params)
    }

    override fun success(response: OnResponse?) {
        // finis fragment

        //activity!!.onBackPressed()
        //otoconFragmentListener.onHandlerReult(0, null)
        //clearStoreChildFrgementManager()
        activity!!.supportFragmentManager.popBackStack()
    }
}

class ConfirmInfoRematchPresenter(view: MVPExtension.View<OnResponse>) : ViewChangeInfoRematchPresenter(view) {

    override fun onExecute(context: Context?, action: Int, params: UserInfoRematchParams?) {

        if (action == 0) {
            return
        }
        else if (action == 1){
            execute(InteractorManager.getApiInterface(context).userUpdateInfoRematch(params), object : ExecuteListener<ResponseExtension<String>> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])

                }

                override fun onNext(response: ResponseExtension<String>?) {
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

