package org.atmarkcafe.otocon.function.mypage.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.google.gson.Gson
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentSelfIntroductionCardBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.dialog.SelectionDialog
import org.atmarkcafe.otocon.dialog.multiple.MultipleChooseDialog
import org.atmarkcafe.otocon.dialog.multiple.MultipleModel
import org.atmarkcafe.otocon.function.mypage.item.ProfileNameModel
import org.atmarkcafe.otocon.function.mypage.item.SelfIntroductionModel
import org.atmarkcafe.otocon.function.mypage.item.UserProfileModel
import org.atmarkcafe.otocon.function.mypage.item.UserProfileParams
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.response.ListData
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserPropertiesRespone
import org.atmarkcafe.otocon.model.response.UserRespose
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.KeyboardUtils
import java.util.ArrayList

// Step 2
class SelfIntroductionCardInfoFragment : OtoconBindingFragment<FragmentSelfIntroductionCardBinding>(), MVPExtension.View<OnResponse> {

    var model: UserProfileParams = UserProfileParams()
    var data: ProfileNameModel = ProfileNameModel()
    val presenter = SelfIntroductionCardInfoPrsenter(this)
    var userProflie: UserProfileModel = UserProfileModel();
    override fun layout(): Int {
        return R.layout.fragment_self_introduction_card
    }

    override fun onCreateView(viewDataBinding: FragmentSelfIntroductionCardBinding) {
        viewDataBinding.toolbar.setNavigationOnClickListener { v -> activity!!.onBackPressed() }
        viewDataBinding.btnContinue.setOnClickListener { v -> FragmentUtils.replace(activity, IntroductionUserConfirmFragment(), true) }
        viewDataBinding.constraintLayout.setOnClickListener { KeyboardUtils.hiddenKeyBoard(activity) }

        //get data
        val bundle = arguments
        val s: String? = bundle?.getString("data")
        val proflie: String? = bundle?.getString("profile")
        data =   if(s!=null) Gson().fromJson(s, ProfileNameModel::class.java) else ProfileNameModel ()
        userProflie =   if(s!=null) Gson().fromJson(proflie, UserProfileModel::class.java) else UserProfileModel ()

        //Set data default
        model.setDefalutParam(model, userProflie)
        viewDataBinding.view = this
        viewDataBinding.data = data
        presenter.onExecute(activity!!, 0, null)

        if (data.isInCountry) {
            viewDataBinding.radioUse.isChecked = true
        } else {
            viewDataBinding.radioNotUse.isChecked = true
        }
        viewDataBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            data.isInCountry = checkedId == R.id.radio_use
        }
    }

    override fun showProgress(show: Boolean) {
        setEnableBack(!show)

        if (show) {
            viewDataBinding.loading.root.visibility = View.VISIBLE
        } else {
            viewDataBinding.loading.root.visibility = View.GONE
        }
    }

    override fun success(response: OnResponse?) {
        if (response is UserPropertiesRespone) {

            //Todo
        } else {
            activity!!.onBackPressed()
            otoconFragmentListener.onHandlerReult(0, null)
        }
    }

    override fun showMessage(response: OnResponse?) {
        if (response is UserRespose) {

        }
    }

    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity!!, title, message, null)
    }

    fun showAcitonSheet(key: ListData) {
        var respone: UserPropertiesRespone = DBManager.getCardInfo(activity)
        var multipleModelList = respone.getListDataByKey(respone, key)
        var listName: ArrayList<String> = ArrayList()
        for (item in multipleModelList) {
            listName.add(item.title)
        }
        val selectionDialog = SelectionDialog(context!!, listName)

        selectionDialog.setListener(object : SelectionDialog.SelectionDialogListener{
            override fun onResult(name: String?) {
                data.setField(name, key)
                for (item in multipleModelList) {
                    if (name.equals(item.title)) {
                        model.setField(item.id.toString(), key)
                    }
                }
            }

            override fun onDismis() {
            }
        })

        var nameInput: String? = data.getIdsField(key)
        for (i in 0 until listName.size) {
            if (listName[i] == (nameInput)) {
                selectionDialog.setCurrentPosition(i)
                break
            }
        }
        selectionDialog.show()
    }

    fun changeScreen() {

        // Set data params
        model.setDataUserParams(model, data)
        data.lifestyle_holiday = data.getHolidaySelection(context, data)
        data.family_composition_inroduce = data.getNameFamilySelection(context, data)
        data.job_location = data.getNameContry(context, data)
        var data: Bundle = Bundle();
        data.putString("param", Gson().toJson(model))
        data.putString("model", Gson().toJson(this.data))

        var fragment = Step3IntroductionUserConfirmFragment()
        fragment.arguments = data
        fragment.otoconFragmentListener = object : OtoconFragmentListener {
            override fun onHandlerReult(status: Int, extras: Bundle?) {
                activity?.onBackPressed()
                if (otoconFragmentListener!=null) {
                    otoconFragmentListener.onHandlerReult(0, data)
                }
            }
        }
        FragmentUtils.replace(activity!!, fragment, true)
    }
}


class SelfIntroductionCardInfoPrsenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<String, OnResponse>(view) {

    override fun onExecute(context: Context?, action: Int, params: String?) {

        if (action == 0) {
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
}







