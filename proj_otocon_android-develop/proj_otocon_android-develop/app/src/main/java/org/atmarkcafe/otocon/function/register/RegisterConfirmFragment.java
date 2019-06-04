package org.atmarkcafe.otocon.function.register;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.RegisteConfirmBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.model.parameter.RegisterParams;
import org.atmarkcafe.otocon.model.response.RegisterResponse;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;


public class RegisterConfirmFragment extends OtoconBindingFragment<RegisteConfirmBinding> implements ComfirmAcitivyContact.View {

    RegisterParams params;

    public RegisterConfirmFragment setData(RegisterParams params){
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", params);
        setArguments(bundle);
        return this;
    }

    @Override
    public int layout() {
        return R.layout.fragment_register_confirm;
    }

    @Override
    public void onCreateView(RegisteConfirmBinding binding) {


        params = (RegisterParams) getArguments().getSerializable("data");
        binding.setPresenter(new RegisterConfirmPresenter(getContext(), params, this));

        binding.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

    }

    @Override
    public void setEnableBack(boolean enable) {
        ExtensionActivity.setEnableBack(getActivity(), enable);
    }

    @Override
    public void finishScreen() {
        setEnableBack(true);
        getActivity().onBackPressed();
    }

    @Override
    public void sucsess(RegisterResponse response) {
        if (!viewDataBinding.viewstubCompleted.isInflated()) {
            viewDataBinding.viewstubCompleted.getViewStub().inflate();
            viewDataBinding.viewstubCompleted.getBinding().setVariable(BR.submit, (View.OnClickListener) v -> complete());
            viewDataBinding.toolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErorrMessage(String title, String message) {
        ExtensionActivity.setEnableBack(getActivity(), true);
        PopupMessageErrorDialog.show(getContext(), title, message, null);
    }

    public void complete() {
        KeyboardUtils.hiddenKeyBoard(getActivity());
        ExtensionActivity.setEnableBack(getActivity(), true);
        getActivity().onBackPressed();
        otoconFragmentListener.onHandlerReult(0, null);
    }
}
