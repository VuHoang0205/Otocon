package org.atmarkcafe.otocon.function.register;

import android.app.Activity;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.dialog.PrefectureSelectionDialog;
import org.atmarkcafe.otocon.dialog.SelectionDialog;
import org.atmarkcafe.otocon.model.parameter.RegisterParams;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.Prefecture;
import org.atmarkcafe.otocon.model.response.PrefectureResponse;
import org.atmarkcafe.otocon.model.response.RegisterResponse;
import org.atmarkcafe.otocon.model.response.ResponseExtension;
import org.atmarkcafe.otocon.pref.BaseShareReferences;
import org.atmarkcafe.otocon.utils.DateUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;
import org.atmarkcafe.otocon.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenterImpl implements RegisterActitvityContract.Presenter {

    private Context context;
    private RegisterActitvityContract.View view;

    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableField<String> passConfirm = new ObservableField<>("");
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

    public final ObservableBoolean errorPassword = new ObservableBoolean(false);
    public final ObservableBoolean errorPassConfirm = new ObservableBoolean(false);
    public final ObservableBoolean errorSei = new ObservableBoolean(false);
    public final ObservableBoolean errorMei = new ObservableBoolean(false);
    public final ObservableBoolean errorKanaSei = new ObservableBoolean(false);
    public final ObservableBoolean errorKanaMei = new ObservableBoolean(false);
    public final ObservableBoolean errorGender = new ObservableBoolean(false);
    public final ObservableBoolean errorYear = new ObservableBoolean(false);
    public final ObservableBoolean errorMonth = new ObservableBoolean(false);
    public final ObservableBoolean errorDay = new ObservableBoolean(false);
    public final ObservableBoolean errorTel1 = new ObservableBoolean(false);
    public final ObservableBoolean errorTerm = new ObservableBoolean(false);

    public final ObservableBoolean errorAdrress = new ObservableBoolean(false);
    public final ObservableBoolean isError = new ObservableBoolean(false);
    public final ObservableBoolean isReadRules = new ObservableBoolean(false);

    public final ObservableField<String> messageEmail = new ObservableField<>("");
    public final ObservableField<String> messagePassword = new ObservableField<>("");
    public final ObservableField<String> messagePassConfirm = new ObservableField<>("");
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


    public RegisterPresenterImpl(Context context, RegisterActitvityContract.View view) {
        this.context = context;
        this.view = view;

        this.year.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
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
        this.year.set(year - 30 + "");
        this.month.set("1");
        this.day.set("1");
    }


    public void loadPrefecture() {
        isLoading.set(true);
        InteractorManager.getApiInterface(context).listPefectures()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrefectureResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PrefectureResponse response) {
                        isLoading.set(false);
                        isLoading.set(false);
                        if (response.isSuccess()) {
                            List<Prefecture> data = response.getList();
                            // save list to db
                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            new BaseShareReferences(context).set(BaseShareReferences.KEY_PREFECTURE, json);
                        } else {
                            view.prefectureError(OnResponse.getMessage(context, null, response));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        isLoading.set(false);

                        if (!hasPrefectureList()) {
                            String[] messages = OnResponse.getMessage(context, e, null);
                            view.prefectureError(messages);
                        }
                    }

                    private boolean hasPrefectureList() {
                        // get from DB
                        String json = new BaseShareReferences(context).get(BaseShareReferences.KEY_PREFECTURE);

                        // if first
                        if (json.isEmpty()) {
                            return false;
                        }

                        List<Prefecture> list = new Gson().fromJson(json, Prefecture.typeList);
                        return list.size()>0;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void callApi() {
        view.setEnableBack(false);

        setDefault();
        isLoading.set(true);

        int genderTemp = 0;
        RegisterParams registerParams = new RegisterParams();
        registerParams.setEmail(email.get());
        registerParams.setPassword(password.get());
        registerParams.setPasswordConfirmation(passConfirm.get());

        String temp = this.gender.get();
        if ((context.getString(R.string.confirm_information_male)).equals(temp)) {
            genderTemp = 1;
        } else if (context.getString(R.string.confirm_information_Female).equals(temp)) {
            genderTemp = 2;
        }

        if (isReadRules.get()) {
            registerParams.setTerm("1");
        } else {
            registerParams.setTerm("0");
        }
        registerParams.setTerm(registerParams.getTerm());

        if (genderTemp == 0) {
            registerParams.setGender("");
        } else {
            registerParams.setGender(genderTemp + "");
        }

        registerParams.setGenderName(this.gender.get());
        registerParams.setNameSei(sei.get());
        registerParams.setNameMei(mei.get());
        registerParams.setNameKanasei(kanaSei.get());
        registerParams.setNameKanamei(kanaMei.get());
        registerParams.setBirthday(registerParams.getBirthDayParam(context,year.get(),month.get(),day.get()));
        if ((tel1.get().equals("") && tel2.get().equals("") && tel3.get().equals(""))) {
            registerParams.setTel("");
        } else {
            registerParams.setTel(context.getString(R.string.fomat_date,tel1.get(),tel2.get(),tel3.get()));
        }

        registerParams.setPrefecture(addressId.get());
        registerParams.setPrefectureName(address.get());

        InteractorManager.getApiInterface(context).registerValidate(registerParams)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterResponse registerResponse) {
                        isLoading.set(false);
                        if (registerResponse == null) {
                            String[] msg = ResponseExtension.getMessage(context, null, registerResponse);
                            view.showErorrMessage(msg[0], msg[1]);
                        } else if (registerResponse.isSuccess()) {
                            completed.set(true);
                            view.success(registerParams);
                        } else {
                            updateUi(registerResponse);
                            view.setEnableBack(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        setDefault();
                        isLoading.set(false);
                        String[] msg = ResponseExtension.getMessage(context, e, null);
                        view.showErorrMessage(msg[0], msg[1]);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void updateUi(RegisterResponse response) {
        String errors = response.getMessage();
        if (response.getErrorRegisterResponse() != null) {
            List<String> emailList = response.getErrorRegisterResponse().getEmails();
            List<String> passwordList = response.getErrorRegisterResponse().getPasswords();
            List<String> passwordConfirmationList = response.getErrorRegisterResponse().getPasswordConfirmations();
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

            if (passwordList != null && passwordList.size() > 0) {
                errorPassword.set(true);
                messagePassword.set(passwordList.get(0));
            }

            if (passwordConfirmationList != null && passwordConfirmationList.size() > 0) {
                errorPassConfirm.set(true);
                messagePassConfirm.set(passwordConfirmationList.get(0));
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
            messageError.set(response.getMessage());
            completed.set(false);
            messageError.set(errors);
        }
    }

    private void setDefault() {
        errorEmail.set(false);
        errorPassword.set(false);
        errorPassConfirm.set(false);
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
        isError.set(false);
        errorTerm.set(false);
        messageError.set("");
        messageTerm.set("");
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
        new RulesDialog(context).show();
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