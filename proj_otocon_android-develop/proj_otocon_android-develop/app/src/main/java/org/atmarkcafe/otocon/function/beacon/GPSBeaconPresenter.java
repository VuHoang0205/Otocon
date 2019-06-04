package org.atmarkcafe.otocon.function.beacon;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.PartyRespone;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;
import org.atmarkcafe.otocon.utils.CityByAreaUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;
import org.atmarkcafe.otocon.utils.ValidateUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GPSBeaconPresenter implements GPSBeaconContract.Presenter{

    private Context mContext;
    private GPSBeaconContract.View mView;
    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableInt resultSearch = new ObservableInt(0);

    public GPSBeaconPresenter(Context mContext, GPSBeaconContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void loadParty(PartyParams params) {
        PartyParams paramsApi = new PartyParams();

        paramsApi.setLimit(params.getLimit());
        paramsApi.setPage(params.getPage());

        paramsApi.setGps(params.getGps());
        paramsApi.setEventDate(params.getJsonParamsEventDate());
        paramsApi.setDayofWeek(params.getJsonParamsDayofWeek());
        paramsApi.setCheckSlot(params.getJsonParamsCheckSlot( ));
        paramsApi.setAge(params.getJsonParamsAge());

        isLoading.set(true);
        InteractorManager.getApiInterface(mContext).getPartys(paramsApi)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<PartyRespone>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PartyRespone response) {
                        isLoading.set(false);
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if (response == null) {
                            mView.showError(false);
                            return;
                        }

                        if (response.isSuccess()) {
                            mView.onSuccess(response);
                        } else {
                            //Todo
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        if (ValidateUtils.isRetrofitErrorNetwork(e)) {
                            mView.showError(true);
                        } else {
                            mView.showError(false);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void likeParty(Party party) {
        Observable<OnResponse> response = party.getLike() == 0 ? InteractorManager.getApiInterface(mContext).likeEvent(party.getId()) : InteractorManager.getApiInterface(mContext).unlikeEvent(party.getId());
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<OnResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OnResponse response) {
                        if (response != null && response.isSuccess()) {
                            // TODO success
                            party.setLike(party.getLike() == 0 ? 1 : 0);
                            mView.onChangedLike();
                        } else {
                            mView.showError(false);
                        }
                        isLoading.set(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        mView.showError(ValidateUtils.isRetrofitErrorNetwork(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
