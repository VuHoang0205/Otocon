package org.atmarkcafe.otocon.function.party.register;

import android.content.Context;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.RegisterPartyModel;
import org.atmarkcafe.otocon.model.response.CustomJoinEventResponse;
import org.atmarkcafe.otocon.model.response.UserJoinEventResponse;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;
import org.atmarkcafe.otocon.utils.PaymentType;
import org.atmarkcafe.otocon.utils.ValidateUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConfirmRegisterPartyPresenter implements ConfirmRegisterPartyContact.Presenter {
    private ConfirmRegisterPartyContact.View view;
    private Context mContext;
    private RegisterPartyModel params;
    private Observer<CustomJoinEventResponse> joinEventObserver = new Observer<CustomJoinEventResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(CustomJoinEventResponse response) {
            view.showProgress(false);
            if (response != null && response.hasAuthenticationError()){
                AuthenticationUtils.Companion.show(response.getMessage());
                return;
            }
            if (response == null) {
                view.showErrorDialog(false);
            } else if (response.isSuccess()) {

                if(!DBManager.isLogin(mContext)){
                    //TODO save account when login
                    String token = response.getAccount().getToken();
                    if (PaymentType.factory(params.getPayment() + "") == PaymentType.money) {
                        if (token != null && !token.isEmpty()) {
                            DBManager.save(mContext, response.getAccount());
                        }
                    }
                }

                view.customJoinSuccess(response);
            }else if(response.isFaileShowDialog()) {
                view.onShowPopup(null, response.getMessage());
            } else {
                PopupMessageErrorDialog.show(mContext, null, response.getMessage(), null);
            }
        }

        @Override
        public void onError(Throwable e) {
            view.showProgress(false);
            view.showErrorDialog(ValidateUtils.isRetrofitErrorNetwork(e));
        }

        @Override
        public void onComplete() {

        }
    };

    private Observer<UserJoinEventResponse> userJoinEventObserver = new Observer<UserJoinEventResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(UserJoinEventResponse response) {
            view.showProgress(false);
            if (response != null && response.hasAuthenticationError()){
                AuthenticationUtils.Companion.show(response.getMessage());
                return;
            }
            if (response == null) {
                view.showErrorDialog(false);
            } else if (response.isSuccess()) {

                if(!DBManager.isLogin(mContext)){
                    //TODO save account when login
                    String token = response.getAccount().getToken();

                    if(token!= null && !token.isEmpty()){
                        DBManager.save(mContext, response.getAccount());
                    }
                }

                view.userJoinSuccess(response);
            }else if(response.isFaileShowDialog()) {
                view.onShowPopup(null, response.getMessage());
            } else {
                PopupMessageErrorDialog.show(mContext, null, response.getMessage(), null);
            }
        }

        @Override
        public void onError(Throwable e) {
            view.showProgress(false);
            view.showErrorDialog(ValidateUtils.isRetrofitErrorNetwork(e));
        }

        @Override
        public void onComplete() {

        }
    };

    public ConfirmRegisterPartyPresenter(Context mContext, ConfirmRegisterPartyContact.View view) {
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void onCallAPIRegisterParty(RegisterPartyModel params) {
        this.params = params;
        view.showProgress(true);
        if (DBManager.isLogin(mContext)) {
            InteractorManager.getApiInterface(mContext).userJsoinEvent(params)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userJoinEventObserver);
        } else {
            InteractorManager.getApiInterface(mContext).customJoinEvent(params)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(joinEventObserver);
        }

    }
}
