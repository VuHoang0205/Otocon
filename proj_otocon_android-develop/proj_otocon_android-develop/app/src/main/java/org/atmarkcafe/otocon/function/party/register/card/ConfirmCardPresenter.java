package org.atmarkcafe.otocon.function.party.register.card;

import android.content.Context;
import android.databinding.ObservableField;
import android.provider.Settings;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.CreditCard;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.parameter.PaymentParams;
import org.atmarkcafe.otocon.model.response.PaymentResponse;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;
import org.atmarkcafe.otocon.utils.ValidateUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConfirmCardPresenter implements ConfirmCardContract.Presenter {
    public ConfirmCardContract.View view;
    public ObservableField<String> messageError = new ObservableField<>("");

    public ConfirmCardPresenter(ConfirmCardContract.View view) {
        this.view = view;
    }

    @Override
    public void onExcute(Context context, String transaction_id, CreditCard card) {
        view.showProgess(true);
        messageError.set("");
        PaymentParams params = new PaymentParams();
        params.setData(context, transaction_id, card);

        InteractorManager.getApiInterface(context).payment(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<PaymentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PaymentResponse response) {
                        view.showProgess(false);
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if (response == null) {
                            view.showError(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail));
                        } else if (response.isSuccess()) {
                            if (response.getAccount().getToken() != null && !response.getAccount().getToken().isEmpty()) {
                                DBManager.save(context, response.getAccount());
                            }
                            view.success(response.getHtml_success());
                        } else if(response.isFaileShowDialog()) {
                            view.onShowPopup(null, response.getMessage());
                        }else {
                            messageError.set(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgess(false);
                        if (ValidateUtils.isRetrofitErrorNetwork(e)) {
                            view.showError(null, null);
                        } else {
                            view.showError(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
