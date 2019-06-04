package org.atmarkcafe.otocon.function.forgotpassworld;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.parameter.ForgotPasswordParams;
import org.atmarkcafe.otocon.model.response.ForgotPasswordResponse;
import org.atmarkcafe.otocon.model.response.ResponseExtension;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {
    public final ObservableField<String> emailAddress = new ObservableField<>("");
    public final ObservableField<String> messageError = new ObservableField<>("");
    public final ObservableField<String> message = new ObservableField<>("");
    public final ObservableBoolean hasError = new ObservableBoolean(false);
    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private ForgotPasswordContract.View mView;

    public ForgotPasswordPresenter(ForgotPasswordContract.View view) {
        this.mView = view;

        emailAddress.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
    }

    @Override
    public void onRequestResetPassword(Context context) {
        hasError.set(false);
        isLoading.set(true);
        mView.setEnableBack(false);

        ForgotPasswordParams params = new ForgotPasswordParams();
        params.setEmail(emailAddress.get().trim());
        InteractorManager.getApiInterface(context).forgotPassworldSendEmail(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForgotPasswordResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ForgotPasswordResponse loginResponse) {
                        isLoading.set(false);
                        if (loginResponse == null) {
                            String msg[] = ResponseExtension.getMessage(context, null, loginResponse);
                            mView.showErrorDialog(msg[0], msg[1]);
                            mView.setEnableBack(true);
                        }
                        if (loginResponse.isSuccess()) {
                            mView.showCompletedLayout();
                        } else {
                            // has error
                            hasError.set(true);
                            if (loginResponse.getErrorForgotPasswordResponse() != null
                                    && loginResponse.getErrorForgotPasswordResponse().getEmails() != null
                                    && loginResponse.getErrorForgotPasswordResponse().getEmails().size() > 0) {
                                messageError.set(loginResponse.getErrorForgotPasswordResponse().getEmails().get(0));
                                message.set(loginResponse.getMessage());
                            } else {
                                messageError.set("");
                                message.set(loginResponse.getMessage());
                            }
                            mView.setEnableBack(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hasError.set(false);
                        isLoading.set(false);
                        String msg[] = ResponseExtension.getMessage(context, e, null);
                        mView.showErrorDialog(msg[0], msg[1]);
                        mView.setEnableBack(true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
