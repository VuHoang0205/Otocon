package org.atmarkcafe.otocon.function.mypage.card;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.LayoutCreateCreditCardBinding;
import org.atmarkcafe.otocon.dialog.SelectionDialog;
import org.atmarkcafe.otocon.model.CreditCard;
import org.atmarkcafe.otocon.utils.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreditCardViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

    public static CreditCardViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutCreateCreditCardBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_create_credit_card, parent, false);

        return new CreditCardViewHolder(binding);
    }

    private LayoutCreateCreditCardBinding binding;
    private SettingCardChooseFragment fragment;

    public CreditCardViewHolder(LayoutCreateCreditCardBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        init();
    }

    public void init() {
        binding.setView(this);
        binding.setCard(new NewCreditCard());
        binding.setErrorMessage(null);
        binding.setPaymentCardMonth(false);
        binding.setPaymentCardYear(false);
        binding.setPaymentCardNumber(false);
        binding.setPaymentCardSecure(false);

        binding.checkbox.setOnCheckedChangeListener(this);
    }

    public void bind(final NewCreditCard card, SettingCardChooseFragment fragment, final int size, final String message, final boolean payment_card_number, final boolean payment_card_year, final boolean payment_card_month, final boolean payment_card_secure) {
        this.fragment = fragment;
        binding.checkbox.setOnCheckedChangeListener(this);

        binding.setErrorMessage(message);
        binding.setPaymentCardNumber(payment_card_number);

        binding.setPaymentCardYear(payment_card_year);

        binding.setPaymentCardMonth(payment_card_month);
        binding.setPaymentCardSecure(payment_card_secure);

        binding.setCard(card);
        binding.checkbox.setChecked(card.isCheckCreate);
        onCheckedChanged(null, card.isCheckCreate);

        binding.checkbox.setVisibility(size == 0 ? View.GONE : View.VISIBLE);
        binding.creditCardMessage.setVisibility(size == 0 ? View.GONE : View.VISIBLE);

    }

    public void setHeight(int height){
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        binding.getRoot().setLayoutParams(layoutParams);
    }

    public NewCreditCard getCreditCard() {
        return binding.getCard();
    }

    public void onClickYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        binding.cardYearInput.requestFocus();
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(String.valueOf(year + i));
        }

        SelectionDialog selectionDialog = new SelectionDialog(itemView.getContext(), list);

        selectionDialog.setListener(new SelectionDialog.SelectionDialogListener() {
            @Override
            public void onResult(String str) {
                binding.getCard().setYear(str);
                binding.getCard().updateExpired();
                binding.cardYearInput.setText(str);
            }

            @Override
            public void onDismis() {

            }

        });
        selectionDialog.show();
    }

    public void onClickMonth() {
        binding.cardMonthInput.requestFocus();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i));
        }

        SelectionDialog selectionDialog = new SelectionDialog(itemView.getContext(), list);
        selectionDialog.setListener(new SelectionDialog.SelectionDialogListener() {
            @Override
            public void onResult(String str) {
                fragment.disableScrollOnTime(1000);
                binding.getCard().setMonth(str);
                binding.getCard().updateExpired();
                binding.cardMonthInput.setText(str);
            }

            @Override
            public void onDismis() {

            }
        });
        selectionDialog.show();
    }

    public void onHideKeyboard() {
        fragment.onHideKeyboard();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        binding.getCard().isCheckCreate = isChecked;
        enable(false);

        binding.cover.setVisibility(!isChecked ? View.VISIBLE : View.INVISIBLE);
        binding.checkbox.setBackgroundColor(binding.checkbox.getContext().getColor(isChecked ? R.color.color_EBEBEB : R.color.colorWhite));

        fragment.onCheckedNewCard(isChecked);
        enable(true);
    }

    public void enable(boolean enable) {
    }
}
