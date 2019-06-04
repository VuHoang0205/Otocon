package org.atmarkcafe.otocon.function.party.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.atmarkcafe.otocon.BuildConfig;
import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.databinding.AcitivityPartyRegisterSuccessBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.UserProfileResponse;

public class SuccessRegisterPartyFragment extends OtoconBindingFragment<AcitivityPartyRegisterSuccessBinding> implements MVPExtension.View<UserProfileResponse> {

    private SuccessRegisterPartyPresenter presenter = new SuccessRegisterPartyPresenter(this);

    public static SuccessRegisterPartyFragment create(String html_success, OtoconFragmentListener listener) {
        SuccessRegisterPartyFragment fragment = new SuccessRegisterPartyFragment();
        fragment.setOtoconFragmentListener(listener);
        Bundle bundle = new Bundle();
        bundle.putString("html_success", html_success);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int layout() {
        return R.layout.acitivity_party_register_success;
    }

    @Override
    public void onCreateView(AcitivityPartyRegisterSuccessBinding binding) {
        binding.setActivity(this);
        binding.webView.loadData(getArguments().getString("html_success"), null, null);
        ExtensionActivity.setEnableBack(getActivity(), false);
    }


    public void onGotoMyPartyList() {
        ExtensionActivity.setEnableBack(getActivity(), true);
        getActivity().onBackPressed();
        otoconFragmentListener.onHandlerReult(RESULT.RESULT_REGISTER_PARTY_SUCCESS, null);
    }

    public void toTheTopScreen() {
        ExtensionActivity.setEnableBack(getActivity(), true);
        presenter.onExecute(getContext(), 0, null);
    }

    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void success(UserProfileResponse response) {
        Bundle bundle = new Bundle();
        bundle.putString("total", response.getModel().getComplete_card());
        onBackPressed();
        otoconFragmentListener.onHandlerReult(RESULT.RESULT_REGISTER_PARTY_SUCCESS_INFO, bundle);
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(UserProfileResponse userProfileResponse) {

    }
}

class SuccessRegisterPartyPresenter extends MVPPresenter<String, UserProfileResponse> {
    public SuccessRegisterPartyPresenter(MVPExtension.View<UserProfileResponse> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, String s) {
        execute(InteractorManager.getApiInterface(context).getUserProfile(), new ExecuteListener<UserProfileResponse>() {
            @Override
            public void onNext(UserProfileResponse response) {
                if (response != null && response.isSuccess()) {
                    view.success(response);
                } else {
                    String[] messages = OnResponse.getMessage(context, null, response);
                    view.showPopup(messages[0], messages[1]);

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
