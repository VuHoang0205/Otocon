package org.atmarkcafe.otocon.function.party.register.card;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.BuildConfig;
import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ActivityCreditCardBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.dialog.SelectionDialog;
import org.atmarkcafe.otocon.function.party.register.card.items.PriceItem;
import org.atmarkcafe.otocon.model.CreditCard;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;

public class CreditCardFragment extends OtoconBindingFragment<ActivityCreditCardBinding> implements CreditCardContract.View {

    public static Fragment start(Activity activity, int transaction_id, int cost_price, int total_price, OtoconFragmentListener listener) {
        CreditCardFragment fragment = new CreditCardFragment();
        fragment.setOtoconFragmentListener(listener);
        Bundle intent = new Bundle();
        intent.putInt("cost_price", cost_price);
        intent.putInt("total_price", total_price);
        intent.putInt("transaction_id", transaction_id);
        fragment.setArguments(intent);

        return fragment;
    }


    public CreditCardPresenter mPresenter;

    public int listedPrice;
    public int paymentPrice;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new CreditCardPresenter(getActivity(), this);
    }

    @Override
    public int layout() {
        return R.layout.activity_credit_card;
    }

    @Override
    public void onCreateView(ActivityCreditCardBinding mBinding) {

        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hiddenKeyBoard(getActivity());
                getActivity().onBackPressed();
            }
        });

        // set transaction_id
        listedPrice = getArguments().getInt("cost_price");
        paymentPrice = getArguments().getInt("total_price");
        int transaction_id = getArguments().getInt("transaction_id");
        mPresenter.setSessionUserId(transaction_id);

        // add price
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.JAPANESE);

        if (listedPrice == paymentPrice || listedPrice == 0) {
            mPresenter.priceAdapter.add(new PriceItem()
                    .setColor(ContextCompat.getColor(getActivity(), R.color.color161616))
                    .setHint(getString(R.string.payment_money))
                    .setPrice(numberFormat.format(listedPrice) + "円")
                    .setCount(1)
            );
        } else
            {
            mPresenter.priceAdapter.add(new PriceItem()
                    .setColor(ContextCompat.getColor(getActivity(), R.color.color161616))
                    .setHint(getString(R.string.listed_price))
                    .setPrice(numberFormat.format(listedPrice) + "円")
                    .setCount(2)
            );
            mPresenter.priceAdapter.add(new PriceItem()
                    .setColor(Color.parseColor("#E30A2F"))
                    .setHint(getString(R.string.discounted_rate))
                    .setPrice(numberFormat.format(paymentPrice) + "円")
                    .setCount(2)
            );
        }

        mBinding.priceRecyclerview.setAdapter(mPresenter.priceAdapter);

        // TODO: init credit card adapter
        mBinding.creditCardRecyclerview.setAdapter(mPresenter.creditCardAdapter);
        mPresenter.creditCardAdapter.setOnItemClickListener((item, view) -> {
            KeyboardUtils.hiddenKeyBoard(getActivity());
            int index = mPresenter.creditCardAdapter.getAdapterPosition(item);
            mPresenter.selectedCreditCardPosition.set(index);
            mPresenter.creditCardAdapter.notifyDataSetChanged();
            mPresenter.selectedNewCreditCard.set(false);
        });

        mBinding.newCreditCardItem.setOnClickListener(v -> {
            KeyboardUtils.hiddenKeyBoard(getActivity());
            mPresenter.selectedCreditCardPosition.set(-1);
            mPresenter.creditCardAdapter.notifyDataSetChanged();
            mPresenter.selectedNewCreditCard.set(true);
        });

        // click こちら
        String here = getString(R.string.here);
        String outsourcing_confirm = getString(R.string.outsourcing_bussiness_confirm);
        SpannableString ss = new SpannableString(outsourcing_confirm);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(BuildConfig.LINK_OUTSOURCING_BUSINESS));
                startActivity(i);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(getActivity(), R.color.color161616));
                ds.setUnderlineText(true);
            }
        };
        int index = outsourcing_confirm.indexOf(here);
        ss.setSpan(clickableSpan, index, index + here.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.outsourcingBussinessConfirm.setText(ss);
        mBinding.outsourcingBussinessConfirm.setMovementMethod(LinkMovementMethod.getInstance());

        mBinding.scrollContent.setOnClickListener(v -> KeyboardUtils.hiddenKeyBoard(getActivity()));

        mBinding.cardYearInput.setOnClickListener(v -> showYearSelection());
        mBinding.cardMonthInput.setOnClickListener(v -> showMonthSelection());

        mBinding.setPresenter(mPresenter);

        if (DBManager.isLogin(getActivity())) {
            mPresenter.loadCreditCard();
        } else {
            mPresenter.selectedNewCreditCard.set(true);
        }
    }


    @Override
    public void showMonthSelection() {
        viewDataBinding.scrollView.scrollBy(0, viewDataBinding.logoVeritrans.getBottom());
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i));
        }

        SelectionDialog selectionDialog = new SelectionDialog(getActivity(), list);
        selectionDialog.setObversableResult(mPresenter.creditCardDateMonth);
        selectionDialog.show();
    }

    @Override
    public void showYearSelection() {
        viewDataBinding.scrollView.scrollBy(0, viewDataBinding.logoVeritrans.getBottom());

        int year = Calendar.getInstance().get(Calendar.YEAR);

        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(String.valueOf(year + i));
        }

        SelectionDialog selectionDialog = new SelectionDialog(getActivity(), list);
        selectionDialog.setObversableResult(mPresenter.creditCardDateYear);
        selectionDialog.show();
    }

    @Override
    public void goStep(int session, CreditCard item) {
        KeyboardUtils.hiddenKeyBoard(getActivity());

        ConfirmCardFragment fragment = new ConfirmCardFragment();
        fragment.setOtoconFragmentListener(new OtoconFragmentListener() {
            @Override
            public void onHandlerReult(int status, Bundle extras) {
                getActivity().onBackPressed();
                otoconFragmentListener.onHandlerReult(status, extras);
            }
        });
        Bundle bundle = new Bundle();

        bundle.putString("card", new Gson().toJson(item));

        bundle.putInt("cost_price", listedPrice);
        bundle.putInt("total_price", paymentPrice);
        bundle.putInt("transaction_id", session);

        fragment.setArguments(bundle);

        FragmentUtils.replace(getActivity(), fragment, true);

    }

    @Override
    public void showProgessBar(boolean show) {
        ExtensionActivity.setEnableBack(getActivity(), !show);
    }

    @Override
    public void showError(String title, String messgae) {
        PopupMessageErrorDialog.show(getActivity(), title, messgae, null);
    }


}
