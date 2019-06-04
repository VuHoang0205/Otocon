package org.atmarkcafe.otocon.function.login;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.Account;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.login.LoginParams;
import org.atmarkcafe.otocon.model.login.LoginRespone;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.ResponseExtension;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends ViewModel implements LoginContract.Presenter {
    private LoginContract.LoginView view;

    public final ObservableBoolean isShowProgressBar = new ObservableBoolean(false);
    public final ObservableField<String> errorEmail = new ObservableField(null);
    public final ObservableField<String> errorPassworld = new ObservableField(null);
    public final ObservableField<String> errMessAge = new ObservableField("");
    public final ObservableBoolean errorValidate = new ObservableBoolean(false);

    public LoginPresenter(LoginContract.LoginView view) {
        this.view = view;
    }

    @Override
    public void onLoadData(LoginParams loginData, Context context) {
        reset();
        isShowProgressBar.set(true);
        InteractorManager.getApiInterface(context).login(loginData)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginRespone>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(LoginRespone loginRespone) {

                        if (loginRespone == null) {
                            String msg[] = ResponseExtension.getMessage(context, null, loginRespone);
                            view.showError(msg[0], msg[1]);
                        } else if (loginRespone.isSuccess()) {
                            Account myAccount = loginRespone.getData().get(0);
                            DBManager.save(context, myAccount);
                            view.showDataSuccess(loginRespone);
                            requestOnline(context);

                        } else if (loginRespone.getLoginErrorResponse() == null) {
                            view.showMessageError();
                            errorValidate.set(true);
                            errMessAge.set(loginRespone.getMessage());

                        } else {
                            errMessAge.set(loginRespone.getMessage());
                            if (loginRespone.getLoginErrorResponse().getEmails() != null
                                    && loginRespone.getLoginErrorResponse().getEmails().size() > 0) {
                                errorEmail.set(loginRespone.getLoginErrorResponse().getEmails().get(0));
                            }

                            if (loginRespone.getLoginErrorResponse().getPasswords() != null
                                    && loginRespone.getLoginErrorResponse().getPasswords().size() > 0) {
                                errorPassworld.set(loginRespone.getLoginErrorResponse().getPasswords().get(0));
                            }
                            view.showMessageError();
                        }
                        isShowProgressBar.set(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isShowProgressBar.set(false);
                        String msg[] = ResponseExtension.getMessage(context, e, null);
                        view.showError(msg[0], msg[1]);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void requestOnline(Context context) {
        InteractorManager.getApiInterface(context).updateTimeStartOnline()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OnResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(OnResponse loginRespone) {


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void reset() {
        errorEmail.set(null);
        errorPassworld.set(null);
        errorValidate.set(false);
    }
}