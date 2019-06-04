package org.atmarkcafe.otocon.function.register;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.RegisterBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.model.parameter.RegisterParams;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;


public class RegisterFragment extends OtoconBindingFragment<RegisterBinding> implements RegisterActitvityContract.View {

    RegisterPresenterImpl presenter;

    @Override
    public int layout() {
        return  R.layout.fragment_register;
    }

    @Override
    public void onCreateView(RegisterBinding binding) {


        presenter = new RegisterPresenterImpl(getContext(), this);

        binding.setPresenter(presenter);
        binding.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
            KeyboardUtils.hiddenKeyBoard(getActivity());
        });


        binding.tel1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 3)
                    binding.tel2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tel2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.tel1.setSelection(binding.tel1.getText().toString().length());
                    binding.tel1.requestFocus();
                } else if (charSequence.length() == 4) {
                    binding.tel3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tel3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.tel2.setSelection(binding.tel2.getText().toString().length());
                    binding.tel2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        presenter.loadPrefecture();

    }

    @Override
    public void prefectureError(String[] message) {
        PopupMessageErrorDialog.show(getContext(), message[0], message[1], () -> finish());
    }

    @Override
    public void setEnableBack(boolean enable) {
        ExtensionActivity.setEnableBack(getActivity(), enable);
    }

    @Override
    public void showErorrMessage(String title, String message) {
        setEnableBack(true);
        PopupMessageErrorDialog.show(getContext(), title, message, null);
    }


    @Override
    public void success(RegisterParams params) {
        ExtensionActivity.setEnableBack(getActivity(), true);
        RegisterConfirmFragment fragment = new RegisterConfirmFragment();
        fragment.setData(params);
        fragment.setOtoconFragmentListener((status, extras) -> {
            getActivity().onBackPressed();
            otoconFragmentListener.onHandlerReult(0, null);
        });
        FragmentUtils.replace(getActivity(),fragment, true);

    }
}