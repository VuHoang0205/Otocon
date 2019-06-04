package org.atmarkcafe.otocon.function.party;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.util.Log;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.PartyDetailData;
import org.atmarkcafe.otocon.model.response.PartyDetailRespone;
import org.atmarkcafe.otocon.model.response.Prefecture;
import org.atmarkcafe.otocon.model.response.PrefectureResponse;
import org.atmarkcafe.otocon.pref.BaseShareReferences;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;
import org.atmarkcafe.otocon.utils.ValidateUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PartyDetailPresenter implements PartyDetailActivityContract.Presenter {
    public ObservableBoolean isShowProgress = new ObservableBoolean(false);

    private PartyDetailActivityContract.PartyDetailView partyDetailView;

    private Context mContect;

    public PartyDetailPresenter(Context context, PartyDetailActivityContract.PartyDetailView partyDetailView) {
        this.mContect = context;
        this.partyDetailView = partyDetailView;
    }

    @Override
    public void onLoadData(String id) {
        isShowProgress.set(true);
        InteractorManager.getApiInterface(mContect).partyDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PartyDetailRespone>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PartyDetailRespone partyDetailRespone) {
                        isShowProgress.set(false);
                        if (partyDetailRespone != null && partyDetailRespone.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(partyDetailRespone.getMessage());
                            return;
                        }
                        if (partyDetailRespone == null) {
                        } else if (partyDetailRespone.isSuccess()) {
                            partyDetailView.showDataSuccess(partyDetailRespone.getData().get(0));
                        } else if (!partyDetailRespone.isSuccess()) {
                            partyDetailView.showPopupPartyExpired(partyDetailRespone.getMessage());
                        } else {
                            partyDetailView.showOtherErorrMessage(false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        isShowProgress.set(false);
                        partyDetailView.showOtherErorrMessage(!ValidateUtils.isRetrofitErrorNetwork(e));
//                        partyDetailView.showOtherErorrMessage(true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void likeParty(PartyDetailData partyDetail) {
        Observable<OnResponse> response = partyDetail.getLike() == 0 ? InteractorManager.getApiInterface(mContect).likeEvent(partyDetail.getId()) : InteractorManager.getApiInterface(mContect).unlikeEvent(partyDetail.getId());
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
                            partyDetail.setLike(partyDetail.getLike() == 0 ? 1 : 0);
                        } else {
                            partyDetailView.showOtherErorrMessage(true);
                        }
                        isShowProgress.set(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isShowProgress.set(false);
                        partyDetailView.showOtherErorrMessage(!ValidateUtils.isRetrofitErrorNetwork(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
