package org.atmarkcafe.otocon.function.party.register.card;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.BuildConfig;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;

import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.party.register.card.items.PriceItem;
import org.atmarkcafe.otocon.databinding.AcitivityChooseConfirmCardBinding;
import org.atmarkcafe.otocon.function.party.register.SuccessRegisterPartyFragment;
import org.atmarkcafe.otocon.model.CreditCard;
import org.atmarkcafe.otocon.utils.FragmentUtils;

import java.text.NumberFormat;
import java.util.Locale;

public class ConfirmCardFragment extends OtoconBindingFragment<AcitivityChooseConfirmCardBinding> implements ConfirmCardContract.View {

    public ConfirmCardPresenter presenter = new ConfirmCardPresenter(this);
    private CreditCard card;

    @Override
    public int layout() {
        return R.layout.acitivity_choose_confirm_card;
    }

    @Override
    public void onCreateView(AcitivityChooseConfirmCardBinding binding) {
        card = new Gson().fromJson(getArguments().getString("card"), CreditCard.class);
        int listedPrice = getArguments().getInt("cost_price");
        int totalPrice = getArguments().getInt("total_price");


        binding.setActivity(this);
        binding.setVariable(BR.card, card);
        binding.setPresenter(presenter);
        binding.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        // add price
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.JAPANESE);
        GroupAdapter priceAdapter = new GroupAdapter();
        Section priceSection = new Section();

        if (listedPrice == totalPrice || listedPrice == 0) {
            priceSection.add(new PriceItem()
                    .setColor(ContextCompat.getColor(getActivity(), R.color.color161616))
                    .setHint(getString(R.string.payment_money))
                    .setPrice(numberFormat.format(listedPrice) + "円")
                    .setCount(1)
            );
        } else {
            priceSection.add(new PriceItem()
                    .setColor(ContextCompat.getColor(getActivity(), R.color.color161616))
                    .setHint(getString(R.string.listed_price))
                    .setPrice(numberFormat.format(listedPrice) + "円")
                    .setCount(2)
            );
            priceSection.add(new PriceItem()
                    .setColor(Color.parseColor("#E30A2F"))
                    .setHint(getString(R.string.discounted_rate))
                    .setPrice(numberFormat.format(totalPrice) + "円")
                    .setCount(2)
            );
        }
        priceAdapter.add(priceSection);
        binding.priceRecyclerview.setAdapter(priceAdapter);

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
        binding.outsourcingBussinessConfirm.setText(ss);
        binding.outsourcingBussinessConfirm.setMovementMethod(LinkMovementMethod.getInstance());

        showProgess(false);

    }

    public void onSubmit() {
        int transaction_id = getArguments().getInt("transaction_id");
        presenter.onExcute(getActivity(), String.valueOf(transaction_id), card);
    }

    @Override
    public void success(String success_html) {

        SuccessRegisterPartyFragment fragment = SuccessRegisterPartyFragment.create(success_html, new OtoconFragmentListener() {
            @Override
            public void onHandlerReult(int status, Bundle extras) {
                getActivity().onBackPressed();
                otoconFragmentListener.onHandlerReult(status, extras);
            }
        });

        FragmentUtils.replace(getActivity(), fragment, true);
    }

    @Override
    public void showProgess(boolean show) {
        viewDataBinding.loading.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void showError(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }


    @Override
    public void onShowPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

}
