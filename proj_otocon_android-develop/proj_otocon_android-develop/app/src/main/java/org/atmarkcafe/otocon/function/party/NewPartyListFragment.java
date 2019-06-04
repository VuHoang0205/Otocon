package org.atmarkcafe.otocon.function.party;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;

import org.atmarkcafe.otocon.R;
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

public class NewPartyListFragment extends PartyListFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments().putString(KEY_TITLE, getString(R.string.title_new_party));
        presenter = new NewPartyListPresenter(this);

        presenter.onExecute(getContext(), NewPartyListPresenter.ACTION_MASK_READ, null);
    }
}

class NewPartyListPresenter extends PartyListPrenter {
    public static final int ACTION_MASK_READ = 1;
    public NewPartyListPresenter(PartyListView view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, PartyParams params) {
        if (action == ACTION_GET_LIST) {
            view.showProgress(true);
            InteractorManager.getApiInterface(context).lastestEvent(params.getPage())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .safeSubscribe(new Observer<PartyListResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PartyListResponse response) {
                            view.showProgress(false);
                            if (response != null && response.hasAuthenticationError()) {
                                AuthenticationUtils.Companion.show(response.getMessage());
                                return;
                            }
                            if (response != null && response.isSuccess()) {
                                view.success(response);
                            } else {
                                String[] messages = OnResponse.getMessage(context, null, response);
                                view.showPopup(messages[0], messages[1]);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showProgress(false);
                            String[] messages = OnResponse.getMessage(context, e, null);
                            view.showPopup(messages[0], messages[1]);

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if (action == ACTION_MASK_READ){

            String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            InteractorManager.getApiInterface(context).markAsReadAllParty(deviceId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .safeSubscribe(new Observer<OnResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(OnResponse response) {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        }
    }
}