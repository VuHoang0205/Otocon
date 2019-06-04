package org.atmarkcafe.otocon.function.party.list;

import android.content.Context;
import android.util.Log;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.PartyRespone;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PartyListPrenter implements MVPExtension.Presenter<PartyParams> {
    public static final int ACTION_GET_LIST = 0;

    public PartyListView view;

    public PartyListPrenter(PartyListView view) {
        this.view = view;
    }

    @Override
    public void onExecute(Context context, int action, PartyParams params) {
        view.showProgress(true);
        InteractorManager.getApiInterface(context).getPartys(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<PartyRespone>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PartyRespone response) {
                        view.showProgress(false);
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if(response != null && response.isSuccess()){
                            PartyListResponse response1 = new PartyListResponse();
                            response1.setCode(response.getCode());
                            response1.setMessage(response.getMessage());
                            response1.setData(response.getDatas());
                            response1.setTotal(response.total);
                            view.success(response1);
                        }else{
                            String [] messages = OnResponse.getMessage(context, null, response);
                            view.showPopup(messages[0], messages[1]);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgress(false);
                        String [] messages = OnResponse.getMessage(context, e, null);
                        view.showPopup(messages[0], messages[1]);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void likeParty(final Context context, final Party party) {
        Observable<OnResponse> response = party.getLike() == 0 ? InteractorManager.getApiInterface(context).likeEvent(party.getId()) : InteractorManager.getApiInterface(context).unlikeEvent(party.getId());
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<OnResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OnResponse response) {
                        view.showProgress(false);
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if (response != null && response.isSuccess()) {
                            party.setLike(party.getLike() == 0 ? 1 : 0);
                            view.successLike(null);
                        } else{
                            String [] messages = OnResponse.getMessage(context, null, response);
                            view.showPopup(messages[0], messages[1]);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgress(false);
                        String [] messages = OnResponse.getMessage(context, e, null);
                        view.showPopup(messages[0], messages[1]);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
