package org.atmarkcafe.otocon.function.party.register.card;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.provider.Settings;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.function.party.register.card.items.CreditCardItem;
import org.atmarkcafe.otocon.model.CreditCard;
import org.atmarkcafe.otocon.model.parameter.ConfirmCreditCardParams;
import org.atmarkcafe.otocon.model.response.ConfirmCreditCardResponse;
import org.atmarkcafe.otocon.model.response.CreditCardResponse;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.ResponseExtension;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreditCardPresenter implements CreditCardContract.Presenter {
    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableBoolean hasMessageHeader = new ObservableBoolean(true);
    public final ObservableBoolean newCreditCard = new ObservableBoolean(true);
    public final ObservableBoolean selectedNewCreditCard = new ObservableBoolean(false);
    public final ObservableInt selectedCreditCardPosition = new ObservableInt(-1);

    public final ObservableField<String> errorMessage = new ObservableField<>("");

    public final ObservableBoolean hasNumberError = new ObservableBoolean(false);
    public final ObservableBoolean hasYearError = new ObservableBoolean(false);
    public final ObservableBoolean hasMonthError = new ObservableBoolean(false);
    public final ObservableBoolean hasSecureError = new ObservableBoolean(false);

    public final ObservableField<String> creditCardNumber = new ObservableField<>("");
    public final ObservableField<String> creditCardDateYear = new ObservableField<>("");
    public final ObservableField<String> creditCardDateMonth = new ObservableField<>("");
    public final ObservableField<String> creditCardSecurityCode = new ObservableField<>("");

    public GroupAdapter priceAdapter = new GroupAdapter();
    public GroupAdapter creditCardAdapter = new GroupAdapter();

    public Context context;
    public CreditCardContract.View view;

    public int session;


    public CreditCardPresenter(Activity activity, CreditCardContract.View view) {
        this.context = activity;
        this.view = view;
    }

    public void setSessionUserId(int id) {
        this.session = id;
    }

    @Override
    public void loadCreditCard() {

        isLoading.set(true);

        InteractorManager.getApiInterface(context).getCreditCardList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<CreditCardResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CreditCardResponse response) {
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if (response != null && response.isSuccess()) {
                            Section sn = new Section();
                            if (response.getCreditCardList() != null && response.getCreditCardList().size() > 0) {
                                int pos = 0;
                                for (int i = 0; i < response.getCreditCardList().size(); i++) {
                                    CreditCard c = response.getCreditCardList().get(i);
                                    CreditCardItem item = new CreditCardItem(c);
                                    sn.add(item.setSelectedIndex(selectedCreditCardPosition));
                                    if (c.isDefault()) {
                                        pos = i;
                                    }
                                }
                                newCreditCard.set(false);
                                selectedNewCreditCard.set(false);
                                selectedCreditCardPosition.set(pos);
                            } else {
                                newCreditCard.set(true);
                                selectedNewCreditCard.set(true);
                                selectedCreditCardPosition.set(-1);
                            }

                            creditCardAdapter.add(sn);
                            creditCardAdapter.notifyDataSetChanged();
                        } else {
                            newCreditCard.set(true);
                            selectedNewCreditCard.set(true);
                            selectedCreditCardPosition.set(-1);
                        }
                        isLoading.set(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        String[] messages = OnResponse.getMessage(context, e, null);
                        view.showError(messages[0], messages[1]);
                        // load list card fail
                        newCreditCard.set(true);
                        selectedNewCreditCard.set(true);
                        selectedCreditCardPosition.set(-1);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void resetError() {
        errorMessage.set("");
        hasNumberError.set(false);
        hasYearError.set(false);
        hasMonthError.set(false);
        hasSecureError.set(false);
    }

    public boolean validate(CreditCard card) {
        if (!card.getId().isEmpty()){
            return true;
        }
        ObservableField<String> errorMessage;
        if (selectedNewCreditCard.get()) {
            errorMessage = this.errorMessage;
            hasNumberError.set(card.hasNumberError());

            if (card.hasExpiredError()) {
                if (card.isMonthEmpty() || card.isYearEmpty()) {
                    hasYearError.set(card.isYearEmpty());
                    hasMonthError.set(card.isMonthEmpty());
                } else {
                    hasYearError.set(true);
                    hasMonthError.set(true);
                }
            }

            hasSecureError.set(card.hasSecureError());
        } else {
            CreditCardItem item = (CreditCardItem) creditCardAdapter.getItem(selectedCreditCardPosition.get());
            errorMessage = item.errorMessage;
        }

        if (card.isBlank()) {
            errorMessage.set(context.getString(R.string.credit_card_blank));
            return false;
        }

        if (!card.validate()) {
            errorMessage.set(context.getString(R.string.credit_card_unavailable));
            return false;
        }

        return true;
    }

    @Override
    public void confirmCard(final CreditCard card) {
        resetError();

        if (!validate(card)) return;

        isLoading.set(true);
        view.showProgessBar(true);
        final ConfirmCreditCardParams params = new ConfirmCreditCardParams();

        if (card.getId().isEmpty()){
            params.setCardId(null);

            params.setSecure(card.getSecurityCode());
            params.setNumber(card.getNumber());
            params.setExpired(card.getExpired());
        } else {
            params.setCardId(card.getId());
        }

        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        params.setDevice_id(deviceId);
        params.setTransaction_id(String.valueOf(session));

        InteractorManager.getApiInterface(context).confirmCreditCard(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(new Observer<ConfirmCreditCardResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ConfirmCreditCardResponse response) {
                        view.showProgessBar(false);
                        if (response != null && response.hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(response.getMessage());
                            return;
                        }
                        if (response == null) {
                            String msg[] = ResponseExtension.getMessage(context, null, response);
                            view.showError(msg[0], msg[1]);
                        } else if (response.isSuccess()) {
                            view.goStep(response.getSessionUserId(), card);
                        } else {
                            String msg;
                            if (response.getErrors().getNumber()!=null && response.getErrors().getNumber().size()>0){
                                msg = response.getErrors().getNumber().get(0);
                            } else {
                                msg = response.getMessage();
                            }
                            errorMessage.set(msg);
                        }

                        isLoading.set(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgessBar(false);
                        isLoading.set(false);
                        String msg[] = ResponseExtension.getMessage(context, e, null);
                        view.showError(msg[0], msg[1]);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void submit() {
        CreditCard creditCard;
        if (selectedNewCreditCard.get()) {
            // call api create new card
            creditCard = new CreditCard();
            creditCard.setId("");
            creditCard.setSecurityCode(creditCardSecurityCode.get());
            creditCard.setNumber(creditCardNumber.get());
            creditCard.setExpired(creditCardDateYear.get(), creditCardDateMonth.get());
        } else {
            // call api update card
            CreditCardItem item = (CreditCardItem) creditCardAdapter.getItem(selectedCreditCardPosition.get());
            item.errorMessage.set("");
            creditCard = item.creditCard;
            creditCard.setSecurityCode(item.securityCode.get());
        }
        confirmCard(creditCard);
    }
}
