package org.atmarkcafe.otocon.function.register;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.parameter.RegisterParams;
import org.atmarkcafe.otocon.model.response.RegisterResponse;
import org.atmarkcafe.otocon.model.response.ResponseExtension;
import org.atmarkcafe.otocon.utils.StringUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterConfirmPresenter implements ComfirmAcitivyContact.Presenter {

    private ComfirmAcitivyContact.View view;

    public RegisterParams params;
    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableField<String> name = new ObservableField<>("");
    public final ObservableField<String> nameKana = new ObservableField<>("");
    public final ObservableField<String> gender = new ObservableField<>("");
    public final ObservableField<String> birthday = new ObservableField<>("");
    public final ObservableField<String> phone = new ObservableField<>("");
    public final ObservableField<String> address = new ObservableField<>("");
    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private Context context;

    public RegisterConfirmPresenter(Context context, RegisterParams params, ComfirmAcitivyContact.View view) {
        this.context = context;
        this.params = params;
        this.view = view;

        email.set(params.getEmail());
        password.set(params.getPassword());
        name.set(params.getNameSei() + " " + params.getNameMei());
        nameKana.set(params.getNameKanasei() + " " + params.getNameKanamei());
        gender.set(params.getGenderName());
        birthday.set(params.getBirthdayText(context));
        phone.set(params.getTel());
        address.set(params.getPrefectureName());
    }

    public void callApi() {
        view.setEnableBack(false);
        isLoading.set(true);

        params.setBirthday(params.getBirthday());

        InteractorManager.getApiInterface(context).registerAccount(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<RegisterResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterResponse response) {
                        isLoading.set(false);
                        if (response != null && response.isSuccess()) {
                            view.sucsess(response);
                        } else {
                            String msg[] = ResponseExtension.getMessage(context, null, response);
                            view.showErorrMessage(msg[0], msg[1]);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        String msg[] = ResponseExtension.getMessage(context, e, null);
                        view.showErorrMessage(msg[0], msg[1]);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void finish() {
        view.finishScreen();
    }
}

