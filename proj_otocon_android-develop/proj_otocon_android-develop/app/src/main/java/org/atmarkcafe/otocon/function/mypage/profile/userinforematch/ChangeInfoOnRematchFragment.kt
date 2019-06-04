package org.atmarkcafe.otocon.function.mypage.profile.userinforematch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_confirm_change_info_rematch.*
import org.atmarkcafe.otocon.BuildConfig
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentChangeInfoRematchBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.dialog.RematchFlowStatusDialog
import org.atmarkcafe.otocon.dialog.SelectionDialog
import org.atmarkcafe.otocon.dialog.multiple.MultipleChooseDialog
import org.atmarkcafe.otocon.function.mypage.item.UserProfileParams
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.InfoReMatch
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.ModelUserInfoRematch
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchParams
import org.atmarkcafe.otocon.function.register.RulesDialog
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.response.ListData
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserPropertiesRespone
import org.atmarkcafe.otocon.utils.FragmentUtils
import java.util.ArrayList

class ChangeInfoOnRematchFragment : OtoconBindingFragment<FragmentChangeInfoRematchBinding>(), MVPExtension.View<OnResponse> , OtoconFragment.OtoconFragmentListener{

    var presenter: ChangeInfoOnRematchPrsenter = ChangeInfoOnRematchPrsenter(this)
    lateinit var mResponeUser: InfoReMatch
    var params: UserInfoRematchParams = UserInfoRematchParams()
    var model: ModelUserInfoRematch = ModelUserInfoRematch()

    override fun layout(): Int {
        return R.layout.fragment_change_info_rematch
    }

    override fun onCreateView(viewDataBinding: FragmentChangeInfoRematchBinding?) {
        viewDataBinding?.toolbar?.setNavigationOnClickListener { v ->
            activity!!.onBackPressed()
            onHideKeyboard()
        }
        viewDataBinding?.tvLink?.setOnClickListener {
            RematchFlowStatusDialog(activity, getString(R.string.title_rematch_flow), BuildConfig.LINK_REMATCH_FLOW).show()
        }

        if (arguments!= null){
            val modelText = arguments!!.getString("model_text")
            val repons_text = arguments!!.getString("repons_text")
            val gson = Gson()
            mResponeUser = gson.fromJson<InfoReMatch>(repons_text, InfoReMatch::class.java)
            model = gson.fromJson<ModelUserInfoRematch>(modelText, ModelUserInfoRematch::class.java)
            params.setRepons(mResponeUser)
        }

        viewDataBinding?.fragment = this
        viewDataBinding?.model = model
        viewDataBinding?.params = params
        presenter.onExecute(activity, 0, null);
    }

    fun submit() {
        onHideKeyboard()
        val bundle = Bundle()
        val gson = Gson()
        val paramtext : String = gson.toJson(params)
        val modelText : String = gson.toJson(model)
        bundle.putString("param_text", paramtext)
        bundle.putString("model_text",modelText)
        val fragment = ConfirmUserInfoRematchFragment()
        fragment.arguments = bundle
        fragment.otoconFragmentListener = this
        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
    }

    // View
    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity, title, message, null)
    }

    override fun success(response: OnResponse?) {

    }

    override fun showProgress(show: Boolean) {
        setEnableBack(!show)
        if (show) viewDataBinding.loading.root.visibility = View.VISIBLE else viewDataBinding.loading.root.visibility = View.GONE
    }

    override fun showMessage(response: OnResponse?) {

    }

    fun showDialog(key: ListData, title: String) {
        val respone: UserPropertiesRespone = DBManager.getCardInfo(activity)
        val idsInput : String? = params.getIdsField(key)
        val dialog = MultipleChooseDialog(context!!, title, respone.getListDataByKey(respone, key), idsInput, MultipleChooseDialog.MultipleChooseDialogListener { lis, ids, names ->
            params.setField(ids, key)
            model.setField(names, key)
        })
        dialog.show()
    }

    fun showAcitonSheet(key: ListData) {
        val respone: UserPropertiesRespone = DBManager.getCardInfo(activity)
        val multipleModelList = respone.getListDataByKey(respone, key)
        val listName: ArrayList<String> = ArrayList()
        for (item in multipleModelList) {
            listName.add(item.title)
        }

        val selectionDialog = SelectionDialog(activity!!, listName)

        selectionDialog.setListener(object : SelectionDialog.SelectionDialogListener{
            override fun onResult(name: String?) {
                model.setField(name, key)
                for (item in multipleModelList) {
                    if (name.equals(item.title)) {
                        params.setField(item.id.toString(), key)
                    }
                }
            }

            override fun onDismis() {
            }
        })

        val nameInput = model.getIdsField(key)
        for (i in 0..listName.size-1){
            if (listName.get(i).equals(nameInput)){
                selectionDialog.setCurrentPosition(i)
                break
            }
        }
        selectionDialog.show()
    }

    // OtoconFragmentListener
    override fun onHandlerReult(status: Int, extras: Bundle?) {
        activity!!.onBackPressed()
        otoconFragmentListener.onHandlerReult(0, extras)
    }
}

class ChangeInfoOnRematchPrsenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<String, OnResponse>(view) {

    override fun onExecute(context: Context?, action: Int, params: String?) {

        // load infor of card
        execute(InteractorManager.getApiInterface(context).properties, object : ExecuteListener<UserPropertiesRespone> {
            override fun onError(e: Throwable?) {
                val messages = OnResponse.getMessage(context, e, null)
                view.showPopup(messages[0], messages[1])

            }

            override fun onNext(response: UserPropertiesRespone?) {
                if (response!!.isSuccess) {
                    DBManager.saveCardInfo(context, response as UserPropertiesRespone?)
                    view.success(response)
                } else {
                    view.showMessage(response)
                }
            }
        })

    }
}







