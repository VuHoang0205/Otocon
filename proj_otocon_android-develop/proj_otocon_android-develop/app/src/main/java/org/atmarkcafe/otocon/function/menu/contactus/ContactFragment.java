package org.atmarkcafe.otocon.function.menu.contactus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;

import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.model.parameter.ContactUsParams;
import org.atmarkcafe.otocon.databinding.ActivityContactUsBinding;
import org.atmarkcafe.otocon.utils.FragmentUtils;

public class ContactFragment extends OtoconBindingFragment<ActivityContactUsBinding> implements ContactActivityContract.View {

    ContactPresenter presenter;

    @Override
    public int layout() {
        return R.layout.activity_contact_us;
    }

    @Override
    public void onCreateView(ActivityContactUsBinding binding) {
        presenter = new ContactPresenter(getActivity(), this);

        binding.setPresenter(presenter);
        binding.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
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

        presenter.onExecuteLoadPreficture(getActivity());

    }

    public void onSubmit(){
        presenter.callApi();
    }

    @Override
    public void showProgess(boolean show) {
        ExtensionActivity.setEnableBack(getActivity(), !show);
    }

    @Override
    public void success(ContactUsParams params) {
        ContactConfirmFragment fragment = new ContactConfirmFragment();
        fragment.setOtoconFragmentListener(new OtoconFragmentListener() {
            @Override
            public void onHandlerReult(int status, Bundle extras) {
                getActivity().onBackPressed();
            }
        });
        Bundle extras = new Bundle();
        extras.putSerializable("data", params);
        fragment.setArguments(extras);

        FragmentUtils.replace(getActivity(), fragment, true);
    }

    @Override
    public void showDialog(String message) {
        PopupMessageErrorDialog dialog = new PopupMessageErrorDialog(getActivity(), null, message);
        dialog.show();
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
    public void prefectureError(String[] message) {
        PopupMessageErrorDialog.show(getContext(), message[0], message[1], new PopupMessageErrorDialog.PopupMessageErrorListener() {
            @Override
            public void onClickOke() {
                getActivity().onBackPressed();
            }
        });
    }
}