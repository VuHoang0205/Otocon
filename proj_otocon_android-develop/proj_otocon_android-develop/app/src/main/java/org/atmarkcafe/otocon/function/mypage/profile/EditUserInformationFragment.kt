package org.atmarkcafe.otocon.function.mypage.profile

import android.content.Context
import android.databinding.Observable
import android.databinding.ObservableField
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
import org.atmarkcafe.otocon.databinding.FragmentChangeBasicInformationBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.dialog.PrefectureSelectionDialog
import org.atmarkcafe.otocon.dialog.SelectionDialog
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.UserInfo
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserPropertiesRespone
import org.atmarkcafe.otocon.model.response.ValidateUserInfoResponse
import org.atmarkcafe.otocon.utils.DateUtils
import org.atmarkcafe.otocon.utils.FragmentUtils
import java.util.*

class EditUserInformationFragment : OtoconBindingFragment<FragmentChangeBasicInformationBinding>() , MVPExtension.View<OnResponse>,  OtoconFragment.OtoconFragmentListener{
    override fun onHandlerReult(status: Int, extras: Bundle?) {
        activity!!.onBackPressed()
        if(otoconFragmentListener == null){
            ( parentFragment as OtoconFragment).otoconFragmentListener.onHandlerReult(0, extras)
        }else{
            otoconFragmentListener.onHandlerReult(0, extras)
        }
    }

    var presenter = EditUserInformationPresenter(this)
    var model = UserInfo()

    override fun onCreateView(viewDataBinding: FragmentChangeBasicInformationBinding) {
        val model_text = arguments!!.getString("model_text")
        val gson = Gson()
        model = gson.fromJson<UserInfo>(model_text, UserInfo::class.java)
//        model.setDefaul(activity)
        viewDataBinding.toolbar.setNavigationOnClickListener { v -> activity!!.onBackPressed() }
        viewDataBinding.fragment = this
        viewDataBinding.model = model
    }

    override fun layout(): Int {
        return R.layout.fragment_change_basic_information
    }

    fun submit(){
        model.setLate()
        presenter.onExecute(activity!!, 0, model)
    }

    fun selectGender() {
        val genders = ArrayList<String>()
        genders.add(getString(R.string.confirm_information_male))
        genders.add(getString(R.string.confirm_information_Female))

        val selectionDialog = SelectionDialog(activity!!, genders)
        selectionDialog.setCurrentPosition(model.gender - 1)
        selectionDialog.setListener(object : SelectionDialog.SelectionDialogListener{
            override fun onResult(it: String?) {
                model.nameGender = it
                if (getString(R.string.confirm_information_male) == it) {
                    model.gender = 1
                } else if (getString(R.string.confirm_information_Female) == it) {
                    model.gender = 2
                }
            }

            override fun onDismis() {
            }
        })

        selectionDialog.show()
    }

    fun selectAdrress() {
        val prefectureSelectionDialog = PrefectureSelectionDialog(activity!!)
        val address = ObservableField<String>(model.namePrefecture)
        val addressId = ObservableField<String>(model.prefecture.toString())
        prefectureSelectionDialog.setObversablePrefecture(address)
        prefectureSelectionDialog.setObversablePrefectureId(addressId)
        prefectureSelectionDialog.show()

        addressId.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                // TODO nhung gi chu can lam
                model.prefecture = addressId.get()!!.toInt()
            }
        })

        address.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                // TODO nhung gi chu can lam
                model.namePrefecture = address.get()
            }
        })

    }

    fun showBirthdayDialog(type: Int) {
        val yearStr = ObservableField(model.year)
        val monthStr = ObservableField(model.month)
        val dayStr = ObservableField(model.day)
        val selectionDialog: SelectionDialog
        val list = ArrayList<String>()
        val year: Int
        when (type) {
            0 // year
            -> {
                year = Calendar.getInstance().get(Calendar.YEAR)
                for (i in 20..100) {
                    list.add((year - (100 - i) - 20).toString())
                }
                selectionDialog = SelectionDialog(activity!!, list)
                selectionDialog.setObversableResult(yearStr)
                yearStr.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                        model.year = yearStr.get()
                    }
                })
                selectionDialog.show()
            }

            1 // month
            -> {
                for (i in 1..12) {
                        list.add(i.toString())
                }

                selectionDialog = SelectionDialog(activity!!, list)
                selectionDialog.setObversableResult(monthStr)
                monthStr.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                        model.month = monthStr.get()
                    }
                })
                selectionDialog.show()
            }

            2 // day
            -> {
                val strYear = yearStr.get()
                val strMonth = monthStr.get()
                for (i in 1..DateUtils.GetMaxDay(strYear!!, strMonth)) {
                        list.add(i.toString())
                }

                selectionDialog = SelectionDialog(activity!!, list)
                selectionDialog.setObversableResult(dayStr)
                dayStr.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                        model.day = dayStr.get()
                    }
                })
                selectionDialog.show()
            }
        }
    }

    // View
    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity, title, message, null)
    }

    override fun success(response: OnResponse?) {
        val fragment = ConfirmEditUserInfoFragment()
        val bundle = Bundle()
        val model_text: String = Gson().toJson(model)
        bundle.putString("model_text",model_text)
        fragment.arguments = bundle
        fragment.otoconFragmentListener = this
        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
    }

    override fun showProgress(show: Boolean) {
        onHideKeyboard()
        setEnableBack(!show)
        resetTextError()
        if (show) viewDataBinding.loading.root.visibility = View.VISIBLE else viewDataBinding.loading.root.visibility = View.GONE
    }

    override fun showMessage(response: OnResponse?) {
        viewDataBinding?.tvError?.text = response?.message
        viewDataBinding?.tvError?.visibility = View.VISIBLE
        viewDataBinding?.layoutEdtSei?.error = (response as ValidateUserInfoResponse).error.nameSei
        viewDataBinding?.layoutEdtMei?.error = response.error.nameMei
        viewDataBinding?.layoutEdtKanaSei?.error = response.error.namekanasei
        viewDataBinding?.layoutEdtKanaMei?.error = response.error.name_kanamei
        viewDataBinding?.layoutEdtGender?.error = response.error.gender
        viewDataBinding?.tvAddress?.error = response.error.prefecture

        if (response.error.birthday != ""){
            viewDataBinding?.inputBirthday?.error = response.error.birthday
            viewDataBinding?.birthdayYear?.setBackgroundResource(R.drawable.xml_background_input_register_error)
            viewDataBinding?.birthdayMonth?.setBackgroundResource(R.drawable.xml_background_input_register_error)
            viewDataBinding?.birthdayDay?.setBackgroundResource(R.drawable.xml_background_input_register_error)
        }
        if (response.error.tel != ""){
            viewDataBinding?.inputPhoneNumber?.error = response.error.tel
            viewDataBinding?.tel1?.setBackgroundResource(R.drawable.xml_background_input_register_error)
            viewDataBinding?.tel2?.setBackgroundResource(R.drawable.xml_background_input_register_error)
            viewDataBinding?.tel3?.setBackgroundResource(R.drawable.xml_background_input_register_error)
        }
    }
    private fun resetTextError(){
        viewDataBinding?.tvError?.visibility = View.GONE
        viewDataBinding?.layoutEdtSei?.error = ""
        viewDataBinding?.layoutEdtMei?.error = ""
        viewDataBinding?.layoutEdtKanaSei?.error = ""
        viewDataBinding?.layoutEdtKanaMei?.error = ""
        viewDataBinding?.layoutEdtGender?.error = ""
        viewDataBinding?.tvAddress?.error = ""
        viewDataBinding?.inputBirthday?.error = ""
        viewDataBinding?.inputPhoneNumber?.error = ""
        viewDataBinding?.birthdayYear?.setBackgroundResource(R.drawable.xml_background_input_register)
        viewDataBinding?.birthdayMonth?.setBackgroundResource(R.drawable.xml_background_input_register)
        viewDataBinding?.birthdayDay?.setBackgroundResource(R.drawable.xml_background_input_register)
        viewDataBinding?.tel1?.setBackgroundResource(R.drawable.xml_background_input_register)
        viewDataBinding?.tel2?.setBackgroundResource(R.drawable.xml_background_input_register)
        viewDataBinding?.tel3?.setBackgroundResource(R.drawable.xml_background_input_register)
    }
}

class EditUserInformationPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<UserInfo, OnResponse>(view) {

    override fun onExecute(context: Context?, action: Int, params: UserInfo?) {

        execute(InteractorManager.getApiInterface(context).validateUserInfo(params), object : ExecuteListener<ValidateUserInfoResponse> {
            override fun onError(e: Throwable?) {
                val messages = OnResponse.getMessage(context, e, null)
                view.showPopup(messages[0], messages[1])

            }

            override fun onNext(response: ValidateUserInfoResponse) {
                if (response.isSuccess) {
                    view.success(response)
                } else {
                    view.showMessage(response)
                }
            }
        })

    }
}