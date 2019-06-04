package org.atmarkcafe.otocon.function.party.register;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.dialog.PrefectureSelectionDialog;
import org.atmarkcafe.otocon.dialog.SelectionDialog;
import org.atmarkcafe.otocon.model.Account;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.Date;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.PartyRegister;
import org.atmarkcafe.otocon.model.RegisterPartyModel;
import org.atmarkcafe.otocon.model.parameter.RegisterPartyParams;
import org.atmarkcafe.otocon.model.response.EventRespone;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.PartyDetailData;
import org.atmarkcafe.otocon.model.response.PartyDetailRespone;
import org.atmarkcafe.otocon.model.response.PartyRegisterInfomationRespone;
import org.atmarkcafe.otocon.model.response.Prefecture;
import org.atmarkcafe.otocon.model.response.PrefectureResponse;
import org.atmarkcafe.otocon.model.response.RegisterPartyResponse;
import org.atmarkcafe.otocon.model.response.RegisterResponse;
import org.atmarkcafe.otocon.model.response.UserRespose;
import org.atmarkcafe.otocon.pref.BaseShareReferences;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.DateUtils;
import org.atmarkcafe.otocon.utils.Gender;
import org.atmarkcafe.otocon.utils.KeyboardUtils;
import org.atmarkcafe.otocon.utils.StringUtils;
import org.atmarkcafe.otocon.utils.UserRole;
import org.atmarkcafe.otocon.utils.ValidateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.atmarkcafe.otocon.utils.StringUtils.fomatDate;

public class RegisterPartyPresenter implements RegisterPartyContact.Presenter {

    ErrorRegisterModel errorRegisterModel;
    Context context;
    RegisterPartyModel model;
    RegisterPartyContact.View view;
    String idParty;


    public ObservableInt mGender = new ObservableInt(0);

    public void setIdParty(String idParty) {
        this.idParty = idParty;
        model.setEventID(idParty);
    }

    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableBoolean hasCoupon = new ObservableBoolean(false);
    public final ObservableField<String> error = new ObservableField<>("");

    public RegisterPartyPresenter(Context context, ErrorRegisterModel errorRegisterModel, RegisterPartyModel model, RegisterPartyContact.View view) {
        this.context = context;
        this.errorRegisterModel = errorRegisterModel;
        this.model = model;
        this.view = view;

        if (DBManager.isLogin(context)) {
            model.setLogin(true);
            mGender.set(DBManager.getMyAccount(context).getGender());
        } else {
            model.setLogin(false);
            mGender.set(SearchDefault.getInstance().init(context).getGender());
        }
    }

    @Override
    public void getEventDetail(final boolean isFirst) {
        isLoading.set(true);
        if (isFirst){
            // if first then load prefecture list before
            InteractorManager.getApiInterface(context).listPefectures().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PrefectureResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PrefectureResponse response) {
                            if (response != null && response.isSuccess()) {
                                // save list to db
                                String json = new Gson().toJson(response.getList());
                                new BaseShareReferences(context).set( BaseShareReferences.KEY_PREFECTURE, json);
                            }
                            executeGetEventDetail(isFirst);
                        }

                        @Override
                        public void onError(Throwable e) {
                            executeGetEventDetail(isFirst);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            executeGetEventDetail(isFirst);
        }
    }

    private void executeGetEventDetail(final boolean isFirst){
        InteractorManager.getApiInterface(context).getEventDetail(idParty)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PartyRegisterInfomationRespone>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PartyRegisterInfomationRespone respone) {
                        isLoading.set(false);
                        if (respone == null) {
                            view.showMessageDefail(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail));
                        } else if (respone.isSuccess()) {
                            if (respone.getAccount() != null && DBManager.isLogin(context)) {
                                respone.getAccount().setToken(DBManager.getToken(context));
                                DBManager.save(context, respone.getAccount());
                            }
                            if (isFirst) {
                                if (DBManager.isLogin(context)) {
                                    setDataFirstLogin(respone.getAccount());
                                } else {
                                    setDataFirst();
                                }
                            }

                            view.updateUI(respone.getData().get(0));

                            // update usable coupon
                            hasCoupon.set(respone.usableCoupon(mGender.get()));
                            model.setUse(hasCoupon.get());

                        } else if (respone.isFaileShowDialog()) {
                            if (isFirst) {
                                view.showMessageDefail(null, respone.getMessage());
                            }else{
                                view.onShowPopup(null, respone.getMessage());
                            }
                        }else {
                            view.showMessageDefail(null, respone.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);

                        if (ValidateUtils.isRetrofitErrorNetwork(e)) {
                            view.showMessageDefail(null, null);
                        } else {
                            view.showMessageDefail(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void setDataFirstLogin(Account account) {
        if (account!=null) {
            model.setFriend("1");
            model.setEmail(account.getEmail());
            model.setNameMei(account.getName_mei());
            model.setNameSei(account.getName_sei());
            model.setNameKanamei(account.getName_kanamei());
            model.setNameKanasei(account.getName_kanasei());
            model.setGenderName(Gender.getGenderName(context, account.getGender()));
            model.setPrefectureName(model.getPrefectureUser(context, account.getPrefecture()));
            model.setYear(model.getYearBirthdayUser(account.getBirthday()));
            model.setPrefecture(account.getPrefecture()+"");
            model.setMonth(model.getMonthBirthdayUser(account.getBirthday()));
            model.setDay(model.getDayBirthdayUser(account.getBirthday()));
            model.setTel1(model.getTel1User(account.getTel()));
            model.setTel2(model.getTel2User(account.getTel()));
            model.setTel3(model.getTel3User(account.getTel()));
            if (account.getCouponCode() != null&&!account.getCouponCode().isEmpty()) {
                hasCoupon.set(true);
                model.setUse(hasCoupon.get());
                model.setCoupon(account.getCouponId());
                model.setCouponCode(account.getCouponCode());
            }
            model.setPayment(1);
            model.setCard(true);
            model.setPontaID(account.getPontaId());
        }
    }

    public void setDataFirst() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        model.setUse(hasCoupon.get());
        model.setFriend("1");
        model.setYear(year - 30 + "");
        model.setMonth("1");
        model.setDay("1");
        model.setPayment(1);
        model.setCard(true);
    }


    @Override
    public void validation() {
        isLoading.set(true);

        setDefault();
        model.updateModel(model,context);

        //Call Api
        io.reactivex.Observable<RegisterPartyResponse> response = DBManager.isLogin(context) ? InteractorManager.getApiInterface(context).validatePartyLogin(model) : InteractorManager.getApiInterface(context).validateParty(model);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterPartyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterPartyResponse respone) {
                        isLoading.set(false);
                        if (respone == null) {
                            view.showOtherErorrMessage(false);
                        } else if (respone.isSuccess()) {
//                             not login and check button login
                            view.onSuccess(respone.getTotalPrice());

                        } else if(respone.isFaileShowDialog()) {
                            view.onShowPopup(null, respone.getMessage());
                        }else {
                            if (respone.getErrorRegisterResponse() == null){
                                view.onShowPopup(null, respone.getMessage());
                            } else {
                                updateUi(respone);
                            }
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        view.showOtherErorrMessage(true);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void selectGender() {
        ObservableField<String> gender = new ObservableField<>("");
        gender.set(model.getGenderName());
        List<String> genders = new ArrayList<>();
        genders.add(context.getString(R.string.confirm_information_male));
        genders.add(context.getString(R.string.confirm_information_Female));

        SelectionDialog selectionDialog = new SelectionDialog(context, genders);
        selectionDialog.setObversableResult(gender);
        selectionDialog.show();
        gender.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.setGenderName(gender.get());
                if ((context.getString(R.string.confirm_information_male)).equals(gender.get())) {
                    mGender.set(1);
                } else if (context.getString(R.string.confirm_information_Female).equals(gender.get())) {
                    mGender.set(2);
                }

                // call api
                getEventDetail(false);
            }
        });

    }

    public void selectAdrress() {
        ObservableField<String> address = new ObservableField<>(model.getPrefectureName());
        ObservableField<String> addressId = new ObservableField<>("");
        PrefectureSelectionDialog prefectureSelectionDialog = new PrefectureSelectionDialog(context);
        prefectureSelectionDialog.setObversablePrefecture(address);
        prefectureSelectionDialog.setObversablePrefectureId(addressId);
        prefectureSelectionDialog.show();
        address.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.setPrefectureName(address.get());
            }
        });

        addressId.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.setPrefecture(Integer.parseInt(addressId.get())+"");
            }
        });

    }

    private void updateBirthDay() {
        String strYear = model.getYear();
        String strMonth = model.getMonth();
        String strDay = model.getDay();
        String day = String.valueOf(DateUtils.updateDay(strYear, strMonth, strDay));
        if (!this.model.getDay().isEmpty()) {
            this.model.setDay(day);
        }
    }

    public void showBirthdayDialog(int type) {
        ObservableField<String> yearStr = new ObservableField<>(model.getYear());
        ObservableField<String> monthStr = new ObservableField<>(model.getMonth());
        ObservableField<String> dayStr = new ObservableField<>(model.getDay());
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
                selectionDialog.setObversableResult(yearStr);
                break;

            case 1: // month
                for (int i = 1; i <= 12; i++) {
                    list.add(String.valueOf(i));
                }

                selectionDialog = new SelectionDialog(context, list);
                selectionDialog.setObversableResult(monthStr);

                break;

            case 2: // day
                String strYear = yearStr.get();
                String strMonth = monthStr.get();
                for (int i = 1; i <= DateUtils.GetMaxDay(strYear, strMonth); i++) {
                    list.add(String.valueOf(i));
                }

                selectionDialog = new SelectionDialog(context, list);
                selectionDialog.setObversableResult(dayStr);
                break;
        }

        selectionDialog.show();

        monthStr.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.setMonth(monthStr.get());
                updateBirthDay();
            }
        });

        dayStr.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.setDay(dayStr.get());
            }
        });


        yearStr.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.setYear(yearStr.get());
            }
        });
    }

    public void showPeopleDialog() {
        ObservableField<String> people = new ObservableField<>(model.getFriend());
        List<String> peopleList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            peopleList.add(String.valueOf(i + 1));
        }
        SelectionDialog peopleDialog = new SelectionDialog(context, peopleList);
        peopleDialog.setObversableResult(people);
        peopleDialog.show();
        people.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.setFriend(people.get());
            }
        });
        errorRegisterModel.setFriendList1(null);
        errorRegisterModel.setFriendList2(null);
        view.updateUI(errorRegisterModel);
    }

    public void hideKeyBoard() {
        KeyboardUtils.hiddenKeyBoard((Activity) context);
    }

    private void updateUi(RegisterPartyResponse response) {
        error.set(response.getMessage());
        view.updateUI(response.getErrorRegisterResponse());
    }

    public void setTextError(List<String> errorString, ObservableBoolean error, ObservableField<String> message) {
        if (errorString != null && errorString.size() > 0) {
            error.set(true);
            message.set(errorString.get(0));
        }
    }

    private void setDefault() {
        view.updateUI(new ErrorRegisterModel());
        error.set("");
    }
}