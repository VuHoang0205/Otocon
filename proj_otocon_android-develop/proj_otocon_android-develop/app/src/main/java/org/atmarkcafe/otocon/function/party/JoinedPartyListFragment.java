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
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JoinedPartyListFragment extends PartyListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowToolBar(false);
        presenter = new JoinedPartyListFragmentPresenter(this, Party.JOIN_STATUS_JOINED);
        createPresenter(Party.JOIN_STATUS_JOINED);
    }

    public void createPresenter(int joinStatus){
        presenter = new JoinedPartyListFragmentPresenter(this, joinStatus);
    }


    private boolean loadedFirst = false;
    public void loadFirstApi() {
        if (!loadedFirst) {
            loadedFirst = true;
            presenter.onExecute(getContext(), 0, params);
        }
    }
}

class JoinedPartyListFragmentPresenter extends PartyListPrenter {
    private int joinStatus;
    public JoinedPartyListFragmentPresenter(PartyListView view, int joinStatus) {
        super(view);
        this.joinStatus = joinStatus;
    }

    //(1: đăng ký, 2: hủy đăng ký, 3: đã tham gia, 4:không tham gia, 5:đang cân nhắc)
    @Override
    public void onExecute(Context context, int action, PartyParams params) {
        view.showProgress(true);
        InteractorManager.getApiInterface(context).joinedListEvent(params.getPage(), joinStatus)
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