package org.atmarkcafe.otocon.function.menu.contactus;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.function.register.ComfirmAcitivyContact;
import org.atmarkcafe.otocon.model.parameter.ContactUsParams;
import org.atmarkcafe.otocon.model.parameter.RegisterParams;
import org.atmarkcafe.otocon.model.response.ContactUsResponse;
import org.atmarkcafe.otocon.model.response.RegisterResponse;
import org.atmarkcafe.otocon.utils.StringUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactConfirmPresenter implements ComfirmAcitivyContact.Presenter {

    private ComfirmUsActivivyContact.View view;

    public ContactUsParams params;
    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> content = new ObservableField<>("");
    public final ObservableField<String> name = new ObservableField<>("");
    public final ObservableField<String> nameKana = new ObservableField<>("");
    public final ObservableField<String> gender = new ObservableField<>("");
    public final ObservableField<String> birthday = new ObservableField<>("");
    public final ObservableField<String> phone = new ObservableField<>("");
    public final ObservableField<String> address = new ObservableField<>("");
    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    public final ObservableBoolean isReadRules = new ObservableBoolean(false);

    private Context context;

    public ContactConfirmPresenter(Context context, ContactUsParams params, ComfirmUsActivivyContact.View view) {
        this.context = context;
        this.params = params;
        this.view = view;

        email.set(params.getEmail());
        content.set(params.getContent());
        name.set(params.getNameSei() + " " + params.getNameMei());
        nameKana.set(params.getNameKanasei() + " " + params.getNameKanamei());
        gender.set(params.getGenderName());

        birthday.set(params.getBirthdayText(context));
        phone.set(params.getTel());
        address.set(params.getPrefectureName());
//        params.setTerm(isReadRules.get() ? "1" : "0");

    }

    public void callApi() {
        isLoading.set(true);
        params.setBirthday(params.getBirthday());

        InteractorManager.getApiInterface(context).contactUs(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<ContactUsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ContactUsResponse response) {
                        isLoading.set(false);
                        if (response.isSuccess()) {
                            view.sucsess(response);

                        } else if (response == null) {
                            view.showOtherErorrMessage(false);
                        } else {
                            view.showDialogTerm(response.getErrorRegisterResponse().getTermsOfService().get(0));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        view.showOtherErorrMessage(true);
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

