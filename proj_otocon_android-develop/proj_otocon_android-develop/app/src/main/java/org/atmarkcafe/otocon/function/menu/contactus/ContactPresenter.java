package org.atmarkcafe.otocon.function.menu.contactus;

import android.app.Activity;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.dialog.PrefectureSelectionDialog;
import org.atmarkcafe.otocon.dialog.SelectionDialog;
import org.atmarkcafe.otocon.function.register.RegisterActitvityContract;
import org.atmarkcafe.otocon.function.register.RulesDialog;
import org.atmarkcafe.otocon.model.Account;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.parameter.ContactUsParams;
import org.atmarkcafe.otocon.model.parameter.RegisterParams;
import org.atmarkcafe.otocon.model.response.ContactUsResponse;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.Prefecture;
import org.atmarkcafe.otocon.model.response.PrefectureResponse;
import org.atmarkcafe.otocon.model.response.RegisterResponse;
import org.atmarkcafe.otocon.model.response.UserRespose;
import org.atmarkcafe.otocon.pref.BaseShareReferences;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;
import org.atmarkcafe.otocon.utils.CityByAreaUtils;
import org.atmarkcafe.otocon.utils.DateUtils;
import org.atmarkcafe.otocon.utils.Gender;
import org.atmarkcafe.otocon.utils.KeyboardUtils;
import org.atmarkcafe.otocon.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactPresenter implements ContactActivityContract.Presenter {

    private Context context;
    private ContactActivityContract.View view;

    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> content = new ObservableField<>("");
    public final ObservableField<String> sei = new ObservableField<>("");
    public final ObservableField<String> mei = new ObservableField<>("");
    public final ObservableField<String> kanaSei = new ObservableField<>("");
    public final ObservableField<String> kanaMei = new ObservableField<>("");
    public final ObservableField<String> gender = new ObservableField<>(""); // 1, 2
    public final ObservableField<String> year = new ObservableField<>("");
    public final ObservableField<String> month = new ObservableField<>("");
    public final ObservableField<String> day = new ObservableField<>("");
    public final ObservableField<String> tel1 = new ObservableField<>("");
    public final ObservableField<String> tel2 = new ObservableField<>("");
    public final ObservableField<String> tel3 = new ObservableField<>("");
    public final ObservableField<String> address = new ObservableField<>("");
    public final ObservableField<String> addressId = new ObservableField<>("");
    public final ObservableBoolean errorEmail = new ObservableBoolean(false);

    public final ObservableBoolean errorContent = new ObservableBoolean(false);
    public final ObservableBoolean errorSei = new ObservableBoolean(false);
    public final ObservableBoolean errorMei = new ObservableBoolean(false);
    public final ObservableBoolean errorKanaSei = new ObservableBoolean(false);
    public final ObservableBoolean errorKanaMei = new ObservableBoolean(false);
    public final ObservableBoolean errorGender = new ObservableBoolean(false);
    public final ObservableBoolean errorYear = new ObservableBoolean(false);
    public final ObservableBoolean errorMonth = new ObservableBoolean(false);
    public final ObservableBoolean errorDay = new ObservableBoolean(false);
    public final ObservableBoolean errorTel1 = new ObservableBoolean(false);
    public final ObservableBoolean errorAdrress = new ObservableBoolean(false);
    public final ObservableBoolean errorTerm = new ObservableBoolean(false);

    public final ObservableBoolean isError = new ObservableBoolean(false);
    public final ObservableBoolean isReadRules = new ObservableBoolean(false);

    public final ObservableField<String> messageEmail = new ObservableField<>("");
    public final ObservableField<String> messageContent = new ObservableField<>("");
    public final ObservableField<String> messageSei = new ObservableField<>("");
    public final ObservableField<String> messageMei = new ObservableField<>("");
    public final ObservableField<String> messageKanaSei = new ObservableField<>("");
    public final ObservableField<String> messageKanaMei = new ObservableField<>("");
    public final ObservableField<String> messageGender = new ObservableField<>("");
    public final ObservableField<String> messageDate = new ObservableField<>("");
    public final ObservableField<String> messageTel = new ObservableField<>("");
    public final ObservableField<String> messageAdrress = new ObservableField<>("");
    public final ObservableField<String> messageTerm = new ObservableField<>("");

    public final ObservableField<String> messageError = new ObservableField<>("");
    public final ObservableBoolean completed = new ObservableBoolean(false);

    public final ObservableBoolean isLoading = new ObservableBoolean(false);


    public ContactPresenter(Context context, ContactActivityContract.View view) {
        this.context = context;
        this.view = view;

        this.year.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateBirthDay();
            }
        });

        this.month.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateBirthDay();
            }
        });

        // Set defalut birth day
        int year = Calendar.getInstance().get(Calendar.YEAR);
        this.month.set("1");
        this.day.set("1");
        this.year.set(year - 30 + "");
    }

    private void initInfo() {
        Account account = DBManager.getMyAccount(context);

        email.set(account.getEmail());
        sei.set(account.getName_sei());
        mei.set(account.getName_mei());
        kanaSei.set(account.getName_kanasei());
        kanaMei.set(account.getName_kanamei());
        gender.set(Gender.getGenderName(context, account.getGender()));
        year.set(account.getBirthday().substring(0, 4));
        month.set(Integer.parseInt(account.getBirthday().substring(5, 7)) + "");
        day.set(Integer.parseInt(account.getBirthday().substring(8)) + "");

        addressId.set(account.getPrefecture() + "");
        address.set(CityByAreaUtils.getPrefectureName(context, account.getPrefecture() + ""));

        if (account.getTel() != null && !account.getTel().isEmpty()) {
            tel1.set(account.getTel().substring(0, 3));
            tel2.set(account.getTel().substring(4, 8));
            tel3.set(account.getTel().substring(9));
        }
    }

    public void loadInfo() {
        if (!DBManager.isLogin(context)) return;
        isLoading.set(true);
        view.showProgess(true);
        InteractorManager.getApiInterface(context).getUserInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserRespose>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserRespose response) {
                        isLoading.set(false);
                        view.showProgess(false);
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if (response.isSuccess()) {
                            // save data base
                            if (!response.getAcoount().hasToken()) {
                                response.getAcoount().setToken(DBManager.getToken(context));
                            }
                            DBManager.save(context, response.getAcoount());
                        }

                        // update by local
                        initInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgess(false);
                        isLoading.set(false);

                        // update by local
                        initInfo();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void callApi() {

        setDefault();
        isLoading.set(true);
        view.showProgess(true);
        ContactUsParams params = new ContactUsParams();
        params.setEmail(email.get());

        String temp = this.gender.get();

        params.setTerm(isReadRules.get() ? "1" : "0");

        params.setContent(content.get());
        params.setTerm(params.getTerm());
        if ((context.getString(R.string.confirm_information_male)).equals(temp)) {
            params.setGender("1");
        } else if (context.getString(R.string.confirm_information_Female).equals(temp)) {
            params.setGender("2");
        } else {
            params.setGender("");
        }
        params.setGenderName(this.gender.get());
        params.setNameSei(sei.get());
        params.setNameMei(mei.get());
        params.setNameKanasei(kanaSei.get());
        params.setNameKanamei(kanaMei.get());
        params.setBirthday(params.getBirthDayParam(context,year.get(),month.get(),day.get()));
        if (tel1.get().length() > 0 || tel2.get().length() > 0 || tel3.get().length() > 0) {
            params.setTel(context.getString(R.string.fomat_date,tel1.get(),tel2.get(),tel3.get()));
        } else {
            params.setTel("");
        }
        params.setPrefecture(addressId.get());
        params.setPrefectureName(address.get());
        InteractorManager.getApiInterface(context).contactValidate(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContactUsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ContactUsResponse response) {
                        isLoading.set(false);
                        view.showProgess(false);
                        if (response.isSuccess()) {
                            completed.set(true);
                            view.success(params);
                        } else if (response == null) {
                            view.showOtherErorrMessage(false);
                        } else {
                            updateUi(response);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgess(false);
                        setDefault();
                        isLoading.set(false);
                        view.showOtherErorrMessage(true);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void onExecuteLoadPreficture(final Context mContext) {
        isLoading.set(true);
        view.showProgess(true);
        InteractorManager.getApiInterface(mContext).listPefectures()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrefectureResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PrefectureResponse response) {
                        isLoading.set(false);
                        view.showProgess(false);
                        if (response.isSuccess()) {
                            List<Prefecture> data = response.getList();
                            // save list to db
                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            new BaseShareReferences(mContext).set(BaseShareReferences.KEY_PREFECTURE, json);
                            loadInfo();
                        } else {
                            view.prefectureError(OnResponse.getMessage(context, null, response));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        view.showProgess(false);

                        if (loadPrefecture().size() == 0) {
                            String[] messages = OnResponse.getMessage(mContext, e, null);
                            view.prefectureError(messages);
                        } else {
                            loadInfo();
                        }
                    }

                    private List<Prefecture> loadPrefecture() {
                        // get from DB
                        String json = new BaseShareReferences(mContext).get(BaseShareReferences.KEY_PREFECTURE);

                        // if first
                        if (json.isEmpty()) {
                            json = "[]";
                        }

                        Gson gson = new Gson();
                        List<Prefecture> list = gson.fromJson(json, Prefecture.typeList);
                        return list;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void updateUi(ContactUsResponse response) {
        if (response.getErrorRegisterResponse() != null) {
            List<String> emailList = response.getErrorRegisterResponse().getEmails();
            List<String> contentList = response.getErrorRegisterResponse().getContents();
            List<String> nameKanaseiList = response.getErrorRegisterResponse().getNameKanaseis();
            List<String> nameKanameiList = response.getErrorRegisterResponse().getNameKanameis();
            List<String> nameSeiList = response.getErrorRegisterResponse().getNameSeis();
            List<String> nameMeiList = response.getErrorRegisterResponse().getNameMeis();
            List<String> genderList = response.getErrorRegisterResponse().getGender();
            List<String> birthdayList = response.getErrorRegisterResponse().getBirthdays();
            List<String> telList = response.getErrorRegisterResponse().getTels();
            List<String> prefectureList = response.getErrorRegisterResponse().getPrefectures();
            List<String> termList = response.getErrorRegisterResponse().getTermsOfService();

            if (termList != null && termList.size() > 0) {
                errorTerm.set(true);
                messageTerm.set(termList.get(0));
            }
            if (emailList != null && emailList.size() > 0) {
                errorEmail.set(true);
                messageEmail.set(emailList.get(0));
            }

            if (nameSeiList != null && nameSeiList.size() > 0) {
                errorSei.set(true);
                messageSei.set(nameSeiList.get(0));
            }

            if (contentList != null && contentList.size() > 0) {
                errorContent.set(true);
                messageContent.set(contentList.get(0));
            }

            if (nameKanaseiList != null && nameKanaseiList.size() > 0) {
                errorKanaSei.set(true);
                messageKanaSei.set(nameKanaseiList.get(0));
            }

            if (nameKanameiList != null && nameKanameiList.size() > 0) {
                errorKanaMei.set(true);
                messageKanaMei.set(nameKanameiList.get(0));
            }

            if (nameMeiList != null && nameMeiList.size() > 0) {
                errorMei.set(true);
                messageMei.set(nameMeiList.get(0));
            }

            if (birthdayList != null && birthdayList.size() > 0) {
                errorYear.set(true);
                errorMonth.set(true);
                errorDay.set(true);
                messageDate.set(birthdayList.get(0));
            }

            if (genderList != null && genderList.size() > 0) {
                errorGender.set(true);
                messageGender.set(genderList.get(0));
            }

            if (telList != null && telList.size() > 0) {
                errorTel1.set(true);
                messageTel.set(telList.get(0));
            }

            if (prefectureList != null && prefectureList.size() > 0) {
                errorAdrress.set(true);
                messageAdrress.set(prefectureList.get(0));
            }

            isError.set(true);
            completed.set(false);
            messageError.set(response.getMessage());
        }
    }

    private void setDefault() {
        errorEmail.set(false);
        errorContent.set(false);
        errorSei.set(false);
        errorMei.set(false);
        errorKanaSei.set(false);
        errorKanaMei.set(false);
        errorYear.set(false);
        errorMonth.set(false);
        errorGender.set(false);
        errorDay.set(false);
        errorTel1.set(false);
        errorAdrress.set(false);
        errorTerm.set(false);
        isError.set(false);
        messageError.set("");
    }

    public void hideKeyBoard() {
        KeyboardUtils.hiddenKeyBoard((Activity) context);
    }

    public void selectGender() {
        List<String> genders = new ArrayList<>();
        genders.add(context.getString(R.string.confirm_information_male));
        genders.add(context.getString(R.string.confirm_information_Female));

        SelectionDialog selectionDialog = new SelectionDialog(context, genders);
        selectionDialog.setObversableResult(this.gender);
        selectionDialog.show();
    }

    public void selectAdrress() {
        PrefectureSelectionDialog prefectureSelectionDialog = new PrefectureSelectionDialog(context);
        prefectureSelectionDialog.setObversablePrefecture(this.address);
        prefectureSelectionDialog.setObversablePrefectureId(this.addressId);
        prefectureSelectionDialog.show();

    }

    public void openTerm() {
        new RulesContactDialog(context).show();
    }

    public void showBirthdayDialog(int type) {
        SelectionDialog selectionDialog = null;
        List<String> list = new ArrayList<>();
        int year;
        switch (type) {
            case 0: // year
                year = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = 20; i <= 100; i++) {
                    list.add(String.valueOf(year - (100 - i) - 20));
                }

                selectionDialog = new SelectionDialog(context, list);
                selectionDialog.setObversableResult(this.year);
                break;

            case 1: // month
                for (int i = 1; i <= 12; i++) {
                    list.add(String.valueOf(i));
                }

                selectionDialog = new SelectionDialog(context, list);
                selectionDialog.setObversableResult(this.month);
                break;

            case 2: // day
                String strYear = this.year.get();
                String strMonth = this.month.get();
                for (int i = 1; i <= DateUtils.GetMaxDay(strYear, strMonth); i++) {
                    list.add(String.valueOf(i));
                }

                selectionDialog = new SelectionDialog(context, list);
                selectionDialog.setObversableResult(this.day);
                break;
        }

        selectionDialog.show();
    }

    private void updateBirthDay() {
        String strYear = this.year.get();
        String strMonth = this.month.get();
        String strDay = this.day.get();
        String day = String.valueOf(DateUtils.updateDay(strYear, strMonth, strDay));
        if (!this.day.get().isEmpty()) {
            this.day.set(day);
        }
    }

}