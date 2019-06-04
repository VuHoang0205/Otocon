package org.atmarkcafe.otocon.function.mypage.card;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.databinding.FragmentUpdateCreditCardBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.dialog.SelectionDialog;
import org.atmarkcafe.otocon.model.CreditCard;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class UpdateCreditCardFragment extends OtoconBindingFragment<FragmentUpdateCreditCardBinding> implements MVPExtension.View<OnResponse> {
    private UpdateCreditCardPresenter presenter = new UpdateCreditCardPresenter(this);

    public static UpdateCreditCardFragment create(CreditCard card, OtoconFragmentListener listener) {
        Bundle extras = new Bundle();
        extras.putString("card", new Gson().toJson(card));
        UpdateCreditCardFragment fragment = new UpdateCreditCardFragment();
        fragment.setOtoconFragmentListener(listener);
        fragment.setArguments(extras);

        return fragment;
    }

    @Override
    public int layout() {
        return R.layout.fragment_update_credit_card;
    }

    @Override
    public void onCreateView(FragmentUpdateCreditCardBinding viewDataBinding) {
        viewDataBinding.toolbar.setNavigationOnClickListener((v) -> {
            getActivity().onBackPressed();
        });

        CreditCard card = new Gson().fromJson(getArguments().getString("card"), CreditCard.class);
        card.init();
        viewDataBinding.setVariable(BR.card, card);
        viewDataBinding.setFragment(this);
        viewDataBinding.setErrorMessage(null);
        viewDataBinding.setPaymentCardNumber(false);
        viewDataBinding.setPaymentCardYear(false);
        viewDataBinding.setPaymentCardMonth(false);

        viewDataBinding.cardNumberInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (viewDataBinding.cardNumberInput.getText().toString().contains("*")){
                viewDataBinding.cardNumberInput.setText("");
            }
        });

    }

    public void onSubmit() {
        viewDataBinding.setErrorMessage(null);
        viewDataBinding.setPaymentCardNumber(false);
        viewDataBinding.setPaymentCardYear(false);
        viewDataBinding.setPaymentCardMonth(false);
        presenter.onExecute(getActivity(), 0, viewDataBinding.getCard());
    }

    public void onClickYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(String.valueOf(year + i));
        }

        SelectionDialog selectionDialog = new SelectionDialog(getActivity(), list);

        selectionDialog.setListener(new SelectionDialog.SelectionDialogListener() {
            @Override
            public void onResult(String str) {
                viewDataBinding.getCard().setYear(str);
                viewDataBinding.getCard().updateExpired();
                viewDataBinding.cardYearInput.setText(str);
            }

            @Override
            public void onDismis() {

            }
        });
        selectionDialog.show();
    }

    public void onClickMonth() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i));
        }

        SelectionDialog selectionDialog = new SelectionDialog(getActivity(), list);
        selectionDialog.setListener(new SelectionDialog.SelectionDialogListener() {
            @Override
            public void onResult(String str) {
                viewDataBinding.getCard().setMonth(str);
                viewDataBinding.getCard().updateExpired();
                viewDataBinding.cardMonthInput.setText(str);
            }

            @Override
            public void onDismis() {

            }
        });
        selectionDialog.show();
    }
    //View

    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void success(OnResponse onResponse) {
        getActivity().onBackPressed();
        otoconFragmentListener.onHandlerReult(0, null);
    }

    @Override
    public void showProgress(boolean show) {
        KeyboardUtils.hiddenKeyBoard(getActivity());
        setEnableBack(!show);
        viewDataBinding.loading.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(OnResponse onResponse) {
        if (onResponse != null && onResponse instanceof UpdateCreditCardResponse) {
            UpdateCreditCardResponse response = (UpdateCreditCardResponse) onResponse;
            viewDataBinding.setErrorMessage(onResponse == null ? null : onResponse.getMessage());
            viewDataBinding.setPaymentCardNumber(response.hasError("payment_card_number"));
            viewDataBinding.setPaymentCardYear(response.hasError("payment_card_year"));
            viewDataBinding.setPaymentCardMonth(response.hasError("payment_card_month"));
            viewDataBinding.setPaymentCardSecure(response.hasError("payment_card_secure"));
        }

    }
}

class UpdateCreditCardPresenter extends MVPPresenter<CreditCard, OnResponse> {
    public UpdateCreditCardPresenter(MVPExtension.View<OnResponse> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, CreditCard creditCard) {


        Map<String, List<String>> errors = creditCard.getErrors(true);
        if (errors != null) {
            UpdateCreditCardResponse response = new UpdateCreditCardResponse();
            response.setCode(0);
            response.setErrors(errors);
            response.setMessage(creditCard.getErrorString(context, true));
            view.showMessage(response);
            return;
        }

        creditCard.setSecure(creditCard.getSecurityCode());

        execute(InteractorManager.getApiInterface(context).updateCreditCard(creditCard.getId() + "", creditCard), new ExecuteListener<UpdateCreditCardResponse>() {
            @Override
            public void onNext(UpdateCreditCardResponse response) {
                if (response != null && response.isSuccess()) {
                    view.success(response);
                } else {
                    view.showMessage(response);
                    // String[] messages = OnResponse.getMessage(context, null, response);
                    //view.showPopup(messages[0], messages[1]);
                }
            }

            @Override
            public void onError(Throwable e) {
                String[] messages = OnResponse.getMessage(context, e, null);
                view.showPopup(messages[0], messages[1]);
            }
        });
    }
}