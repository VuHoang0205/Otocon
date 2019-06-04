package org.atmarkcafe.otocon.function.home;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.provider.Settings;
import android.util.Log;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;

import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.PartyRespone;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;
import org.atmarkcafe.otocon.utils.CityByAreaUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;
import org.atmarkcafe.otocon.utils.ValidateUtils;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import org.atmarkcafe.otocon.model.RecommendSlider;
import org.atmarkcafe.otocon.model.response.SettingsResponse;

import java.util.ArrayList;


public class HomePresenter implements HomeContract.Presenter {
    public final ObservableBoolean loggedin = new ObservableBoolean(false);
    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableInt resultSearch = new ObservableInt(0);

    public final List<RecommendSlider> mRecommendList = new ArrayList<>();
    public final List<RecommendSlider> mSliderList = new ArrayList<>();

    private HomeContract.View view;
    private Context context;

    private int mCurrentPage = 0;

    public HomePresenter(Context context, HomeContract.View view) {
        this.context = context;
        this.view = view;

    }

    @Override
    public void likeParty(Party party) {
        Observable<OnResponse> response = party.getLike() == 0 ? InteractorManager.getApiInterface(context).likeEvent(party.getId()) : InteractorManager.getApiInterface(context).unlikeEvent(party.getId());
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<OnResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OnResponse response) {
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if (response != null && response.isSuccess()) {
                            // TODO success
                            party.setLike(party.getLike() == 0 ? 1 : 0);
                            view.onChangedLike();
                        } else {
                            view.showError(false);
                        }
                        isLoading.set(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        view.showError(ValidateUtils.isRetrofitErrorNetwork(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void clickBanner(String setting_id) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        InteractorManager.getApiInterface(context).updateClickBanner(setting_id, deviceId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<OnResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OnResponse response) {
                        //
                    }

                    @Override
                    public void onError(Throwable e) {
                        //
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void callApi(int limit, int page) {
        isLoading.set(true);
        SearchDefault aDefault = SearchDefault.getInstance().init(context);
        PartyParams params = new PartyParams();
        params.setLimit(limit);
        params.setPage(page);

        mCurrentPage = page;

        if (aDefault.getCitys() != null && !aDefault.getCitys().isEmpty())
            params.setCity(CityByAreaUtils.getParamsNameCity(context, aDefault.getCitys()));

        if (aDefault.getEventDate() != null && !aDefault.getEventDate().isEmpty())
            params.setEventDate(aDefault.getEventDate());

        if (aDefault.getDayOfWeek() != null && !aDefault.getDayOfWeek().isEmpty())
            params.setDayofWeek(aDefault.getDayOfWeek());

        int gender = aDefault.getGender();
        int status = aDefault.isOnlyParty() ? 1 : 0;

        if (gender == KeyExtensionUtils.VALUE_GENDER_MALE) {
            // Age and Round Age is json {'option':'1,2' , 'age': 23}
            //Age
            // Round Age
            String json = createJsonAge(aDefault.getAge(), aDefault.getAgeOfOpponent(), 1);

            if (!json.isEmpty()) {
                // addd
                params.setCheckSlot(createJsonCheck(gender, status));
                params.setAge(json);
            }

            // Only Party
        } else if (gender == KeyExtensionUtils.VALUE_GENDER_FEMALE) {
            // Age and Round Age is json {'option':'1,2' , 'age': 23}
            //Age
            // Round Age
            String json = createJsonAge(aDefault.getAge(), aDefault.getAgeOfOpponent(), 2);

            if (!json.isEmpty()) {
                // addd
                params.setCheckSlot(createJsonCheck(gender, status));
                params.setAge(json);
            }

        } else {
            // none gender
            String json = createJsonAge(aDefault.getAge(), aDefault.getAgeOfOpponent(), 0);
            String checkSlot = createJsonCheck(0, status);
            params.setAge(json);
            params.setCheckSlot(checkSlot);
        }
        InteractorManager.getApiInterface(context).getPartys(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<PartyRespone>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PartyRespone response) {
                        isLoading.set(false);
                        if (response == null) {
                            view.showError(false);
                            return;
                        }

                        if (response.isSuccess()) {
                            view.onSuccess(response);
                        } else {
                            //Todo
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        if (ValidateUtils.isRetrofitErrorNetwork(e)) {
                            view.showError(true);
                        } else {
                            view.showError(false);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private String createJsonAge(int age, String ageOfOpponent, int gender) {
        // Age and Round Age is json {'option':'1,2' , 'age': 23}
        JSONObject object = new JSONObject();
        boolean hasContent = false;
        try {
            if (ageOfOpponent != null && !ageOfOpponent.isEmpty()) {
                hasContent = true;
                object.put(KeyExtensionUtils.KEY_OPTION, ageOfOpponent);
            }

            if (age > 0) {
                hasContent = true;
                object.put(KeyExtensionUtils.KEY_AGE, age);
            }
            if (gender > 0) {
                hasContent = true;
                object.put(KeyExtensionUtils.KEY_GENDER, gender);
            }


        } catch (Exception e) {

        }


        return hasContent ? object.toString() : null;
    }

    private String createJsonCheck(int gender, int status) {
        // Age and Round Age is json {'option':'1,2' , 'age': 23}
        JSONObject object = new JSONObject();
        boolean hasContent = false;
        try {
            if (gender > 0) {
                hasContent = true;
                object.put(KeyExtensionUtils.KEY_GENDER, gender);
            }

            hasContent = true;
            object.put(KeyExtensionUtils.KEY_STATUS, status);

        } catch (Exception e) {

        }


        return hasContent ? object.toString() : "";
    }

    @Override
    public void loadRecommendSliders(Context context) {
        mSliderList.clear();
        mRecommendList.clear();
        InteractorManager.getApiInterface(context).getSettings()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SettingsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SettingsResponse settingsResponse) {
                        if (settingsResponse.getList() != null) {
                            for (RecommendSlider recommendSlider : settingsResponse.getList()) {
                                if (recommendSlider.getStatus() == RecommendSlider.STATUS_ENABLE) {
                                    switch (recommendSlider.getType()) {
                                        case RecommendSlider.TYPE_SLIDER:
                                            mSliderList.add(recommendSlider);
                                            break;

                                        case RecommendSlider.TYPE_RECOMMEND:
                                            if (mRecommendList.size() >= 3) break;
                                            mRecommendList.add(recommendSlider);
                                            break;
                                    }
                                }
                            }
                        }

                        updateRecommendSliderDefault();
                        view.initRecommendSlider();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Throwable", "Throwable",e);
                        updateRecommendSliderDefault();
                        view.initRecommendSlider();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updateRecommendSliderDefault() {
        if (mSliderList.size() == 0) {
            mSliderList.add(new RecommendSlider(RecommendSlider.TYPE_SLIDER));
        }

        for (int i = mRecommendList.size(); i < 3; i++) {
            mRecommendList.add(new RecommendSlider(RecommendSlider.TYPE_RECOMMEND));
        }
    }
}
