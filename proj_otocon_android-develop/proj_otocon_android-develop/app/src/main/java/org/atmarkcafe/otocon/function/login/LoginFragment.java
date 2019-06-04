package org.atmarkcafe.otocon.function.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentLoginBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.forgotpassworld.ForgotPasswordFragment;
import org.atmarkcafe.otocon.function.register.RegisterConfirmFragment;
import org.atmarkcafe.otocon.function.register.RegisterFragment;
import org.atmarkcafe.otocon.model.login.LoginParams;
import org.atmarkcafe.otocon.model.login.LoginRespone;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;

public class LoginFragment extends OtoconBindingFragment<FragmentLoginBinding> implements LoginContract.LoginView {

    public static LoginFragment create(OtoconFragmentListener listener){

        LoginFragment fragment = new LoginFragment();
        fragment.setOtoconFragmentListener(listener);
        return fragment;
    }

    private LoginParams model;
    private LoginPresenter presenter;
    public static final int RESULT_LOGIN_SUCCESS = 1004;
    private AboutPersonalInformationDialog aboutPersonalInformationDialog;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onCreateView(FragmentLoginBinding mLoginDataBinding) {
        // init class
        model = new LoginParams();
        presenter = new LoginPresenter(this);
        mLoginDataBinding.setFragment(this);

        mLoginDataBinding.setViewModel(model);

        mLoginDataBinding.setPresenter(presenter);

        mLoginDataBinding.toolbar2.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        mLoginDataBinding.contrainLogin.setOnClickListener(v -> KeyboardUtils.hiddenKeyBoard(getActivity()));

        if(getArguments() != null && getArguments().containsKey("EXPIRED") && getArguments().getBoolean("EXPIRED")){
            presenter.errMessAge.set(getString(R.string.register_expired));
            mLoginDataBinding.textView20.setVisibility(View.VISIBLE);
        }
    }

    /**
     * funcion showDataSuccess receive
     * when presenter result success
     *
     * @param loginRespone is LoginRespone
     */
    @Override
    public void showDataSuccess(LoginRespone loginRespone) {
        ExtensionActivity.setEnableBack(getActivity(), true);
        KeyboardUtils.hiddenKeyBoard(getActivity());
        getActivity().onBackPressed();
        // TODO send result login
        if (otoconFragmentListener != null) {
            otoconFragmentListener.onHandlerReult(1, null);
        }
    }

    @Override
    public void showMessageError() {
        ExtensionActivity.setEnableBack(getActivity(), true);
        viewDataBinding.textView20.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String title, String message) {
        ExtensionActivity.setEnableBack(getActivity(), true);
        PopupMessageErrorDialog.show(getContext(), title, message, null);
    }

    @Override
    public void submit() {
        ExtensionActivity.setEnableBack(getActivity(), false);
        viewDataBinding.textView20.setVisibility(View.GONE);
        KeyboardUtils.hiddenKeyBoard(getActivity());
        presenter.onLoadData(model, getContext());
    }

    @Override
    public void forgotPassword() {
        // TODO forgot password

        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        fragment.setOtoconFragmentListener((status, extras) -> getActivity().onBackPressed());
        FragmentUtils.replace(getActivity(), fragment, true);
    }

    @Override
    public void register() {
        //@TODO go to screen Register

        RegisterFragment fragment = new RegisterFragment();
        fragment.setOtoconFragmentListener((status, extras) -> {
            getActivity().onBackPressed();
            otoconFragmentListener.onHandlerReult(0, null);
        });
        FragmentUtils.replace(getActivity(), fragment, true);
    }

    /**
     * fun onShowDialogAboutPersonalInformation show dialog AboutPersonalInformationDialog
     */
    @Override
    public void showExplainRegister() {
        aboutPersonalInformationDialog = new AboutPersonalInformationDialog(getContext());
        aboutPersonalInformationDialog.setErrorLoadWebviewListener(listenerErrorWebview);
        aboutPersonalInformationDialog.show();
    }

    private AboutPersonalInformationDialog.ErrorLoadWebviewListener listenerErrorWebview = () -> {
        aboutPersonalInformationDialog.dismiss();
        new PopupMessageErrorDialog(getContext()).show();
    };

}