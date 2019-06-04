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

public class LikePartyListFragment extends PartyListFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments().putString(KEY_TITLE, getString(R.string.title_like_party_list));

        setFormartNumberCount(getString(R.string.party_like_fomat_number_count));
        setTextNoData(getString(R.string.party_like_list_no_data));

        presenter = new LikePartyListPresenter(this);
    }
}

class LikePartyListPresenter extends PartyListPrenter {
    public LikePartyListPresenter(PartyListView view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, PartyParams params) {
        view.showProgress(true);
        InteractorManager.getApiInterface(context).likedListEvent(params.getPage(), 1)
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