package org.atmarkcafe.otocon.function.forgotpassworld;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentForgotPasswordBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;

import java.security.Key;

public class ForgotPasswordFragment extends OtoconBindingFragment<FragmentForgotPasswordBinding> implements ForgotPasswordContract.View {


    private ForgotPasswordPresenter mPresenter;

    @Override
    public int layout() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    public void onCreateView(FragmentForgotPasswordBinding mBinding) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mPresenter = new ForgotPasswordPresenter(this);
        mBinding.setFragment(this);
        mBinding.setPresenter(mPresenter);
        mBinding.toolbar.setNavigationOnClickListener(v -> {
            KeyboardUtils.hiddenKeyBoard(getActivity());
            getActivity().onBackPressed();
        });

        mBinding.forgotPasswordLayout.setOnClickListener(v -> KeyboardUtils.hiddenKeyBoard(getActivity()));
    }

    @Override
    public void setEnableBack(boolean enable) {
        ExtensionActivity.setEnableBack(getActivity(), enable);
    }

    @Override
    public void showErrorDialog(String title, String message) {
        PopupMessageErrorDialog.show(getContext(), title, message, null);
    }

    public void showCompletedLayout() {
        if (!viewDataBinding.viewstubCompleted.isInflated()) {
            viewDataBinding.viewstubCompleted.getViewStub().inflate();
            viewDataBinding.viewstubCompleted.getBinding().setVariable(BR.submit, (View.OnClickListener) v -> complete());
        }
        viewDataBinding.toolbar.setVisibility(View.GONE);
    }

    @Override
    public void submit() {
        KeyboardUtils.hiddenKeyBoard(getActivity());
        mPresenter.onRequestResetPassword(getContext());
    }

    @Override
    public void complete() {
        setEnableBack(true);
        // FIXME
        getActivity().onBackPressed();
        otoconFragmentListener.onHandlerReult(0, null);
    }
}
