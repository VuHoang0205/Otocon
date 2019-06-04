package org.atmarkcafe.otocon.function.party.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.BuildConfig;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.AcitivityPartyRegisterBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.dialog.TermDialog;
import org.atmarkcafe.otocon.model.RegisterPartyModel;
import org.atmarkcafe.otocon.model.response.PartyDetailData;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;

import java.text.DecimalFormat;

public class RegisterPartyFragment extends OtoconBindingFragment<AcitivityPartyRegisterBinding> implements RegisterPartyContact.View {

    public static RegisterPartyFragment create(String party, OtoconFragmentListener listener) {
        RegisterPartyFragment fragment = new RegisterPartyFragment();
        Bundle extras = new Bundle();
        extras.putString("party", party);
        fragment.setOtoconFragmentListener(listener);
        fragment.setArguments(extras);
        return fragment;
    }

    public static final int REQUEST_CODE_REGISTER_PARTY = 101;

    PartyDetailData party = new PartyDetailData();
    RegisterPartyModel model = new RegisterPartyModel();

    RegisterPartyPresenter presenter;
    boolean isFirst = true;

    @Override
    public int layout() {
        return  R.layout.acitivity_party_register;
    }

    @Override
    public void onCreateView(AcitivityPartyRegisterBinding binding) {
        String json = getArguments().getString("party");
        party = new Gson().fromJson(json, PartyDetailData.class);

        binding.setVariable(BR.activity, this);

        binding.setError(new ErrorRegisterModel());
        presenter = new RegisterPartyPresenter(getActivity(), binding.getError(), model, this);
        presenter.setIdParty(party.getId());

        binding.setUser(model);
        binding.setVariable(BR.presenter, presenter);

        binding.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
            KeyboardUtils.hiddenKeyBoard(getActivity());
        });

        updateTextWatcherForPhone(true);

        String text = getString(R.string.register_party_term);
        SpannableString ss = new SpannableString(text);
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do some thing
                TermDialog dialog = new TermDialog(getActivity(), getString(R.string.title_rules_register_party), BuildConfig.LINK_RELUS_PARTY_REGISTER.toString());
                dialog.show();
            }
        };

        ClickableSpan span2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do another thing
                TermDialog dialog = new TermDialog(getActivity(), getString(R.string.title_security_party_register), BuildConfig.LINK_SECURITY_PARTY_REGISTER.toString());
                dialog.show();
            }
        };

        ss.setSpan(span1, 0, text.indexOf("・"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(span2, text.indexOf("・") + 1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.registePartyTerm.setText(ss);
        binding.registePartyTerm.setTextSize(12f);
        binding.registePartyTerm.setMovementMethod(LinkMovementMethod.getInstance());

        presenter.getEventDetail(true);

        // click こちら
        String here = getString(R.string.here);
        String outsourcing_confirm = getString(R.string.party_register_noti_10);
        SpannableString zz = new SpannableString(outsourcing_confirm);
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
        zz.setSpan(clickableSpan, index, index + here.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.noti10.setText(zz);
        binding.noti10.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private PhoneTextWatcher phone1TextWatcher;
    private PhoneTextWatcher phone2TextWatcher;
    private PhoneTextWatcher phone3TextWatcher;

    public void updateTextWatcherForPhone(boolean isAdd) {
        if (isAdd) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (phone1TextWatcher != null) {
                        phone1TextWatcher.setEnable(false);
                        phone2TextWatcher.setEnable(false);
                    }
                    phone1TextWatcher = new PhoneTextWatcher(viewDataBinding.tel2, 3);
                    phone2TextWatcher = new PhoneTextWatcher(viewDataBinding.tel1, viewDataBinding.tel3, 4);
                    phone3TextWatcher = new PhoneTextWatcher(viewDataBinding.tel2,null, 4);

                    viewDataBinding.tel1.addTextChangedListener(phone1TextWatcher);
                    viewDataBinding.tel2.addTextChangedListener(phone2TextWatcher);
                    viewDataBinding.tel3.addTextChangedListener(phone3TextWatcher);
                }
            }, 1000);
        } else {
            if (phone1TextWatcher != null) {
                phone1TextWatcher.setEnable(false);
                phone2TextWatcher.setEnable(false);
                phone3TextWatcher.setEnable(false);
            }
        }
    }

    public void onSubmit() {
        presenter.validation();
    }

    @Override
    public void updateUI(Object model) {

        updateTextWatcherForPhone(false);

        if (model != null) {
            if (model instanceof RegisterPartyModel) {
                viewDataBinding.setUser((RegisterPartyModel) model);
            }

            if (model instanceof ErrorRegisterModel) {
                viewDataBinding.setError((ErrorRegisterModel) model);
            }

            if (model instanceof PartyDetailData) {
                viewDataBinding.setParty((PartyDetailData) model);
            }
        }

        updateTextWatcherForPhone(true);
    }

    @Override
    public void showMessageDefail(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, new PopupMessageErrorDialog.PopupMessageErrorListener() {
            @Override
            public void onClickOke() {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onShowPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void showOtherErorrMessage(boolean isNotConnectToServer) {
        if (isNotConnectToServer) {
            new PopupMessageErrorDialog(getActivity()).show();
        } else {
            new PopupMessageErrorDialog(getActivity(),
                    getString(R.string.error_title_Connect_server_fail),
                    getString(R.string.error_content_Connect_server_fail)
            ).show();
        }
    }

    @Override
    public void onSuccess(String price) {

        ConfirmRegisterPartyFragment fragment = new ConfirmRegisterPartyFragment();


        Bundle bundle = new Bundle();
        bundle.putString("party", new Gson().toJson(party));
        bundle.putString("price", price);
        bundle.putString("params", new Gson().toJson(model));

        fragment.setArguments(bundle);

        fragment.setOtoconFragmentListener(new OtoconFragmentListener() {
            @Override
            public void onHandlerReult(int status, Bundle extras) {
                getActivity().onBackPressed();
                otoconFragmentListener.onHandlerReult(status, extras);
            }
        });

        FragmentUtils.replace(getActivity(), fragment, true);
    }

    private class PhoneTextWatcher implements TextWatcher {
        private boolean enable = true;

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        private PhoneTextWatcher(EditText nextText, int lenngth) {
            this.beforeText = null;
            this.nextText = nextText;
            this.lenngth = lenngth;
        }

        private PhoneTextWatcher(EditText beforeText, EditText nextText, int lenngth) {
            this.beforeText = beforeText;
            this.nextText = nextText;
            this.lenngth = lenngth;
        }


        EditText beforeText;
        EditText nextText;
        private int lenngth = 3;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable charSequence) {
            if (enable && charSequence.length() == 0 && beforeText != null) {
                beforeText.setSelection(beforeText.getText().toString().length());
                beforeText.requestFocus();
            }
            if (enable && charSequence.length() == lenngth && nextText != null) {
                nextText.requestFocus();
            }
        }
    }
}
