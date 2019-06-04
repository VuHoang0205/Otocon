package org.atmarkcafe.otocon.function.party;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.function.party.list.PartyListFragment;
import org.atmarkcafe.otocon.function.party.list.PartyListPrenter;
import org.atmarkcafe.otocon.function.party.list.PartyListResponse;
import org.atmarkcafe.otocon.function.party.list.PartyListView;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EarlyBenefitPartyListFragment extends PartyListFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments().putString(KEY_TITLE, getString(R.string.title_early_benefit));
        presenter = new EarlyBenefitPartyListPresenter(this);
        setShowIcSearch(getArguments().getBoolean(KEY_SHOW_ADVANCED_SEARCH, true));
    }
}

class EarlyBenefitPartyListPresenter extends PartyListPrenter {
    public EarlyBenefitPartyListPresenter(PartyListView view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, PartyParams params) {
        view.showProgress(true);
        InteractorManager.getApiInterface(context).earlyBenefitEvent(params.getPage(),params.getJsonParamsCity(context),
                params.getJsonParamsEventDate(),params.getJsonParamsDayofWeek(),params.getJsonParamsAge(),params.getJsonParamsCheckSlot())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<PartyListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PartyListResponse response) {
                        view.showProgress(false);
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if(response != null && response.isSuccess()){
                            view.success(response);
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
}