package org.atmarkcafe.otocon.function.party.register;

import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;

import org.atmarkcafe.otocon.databinding.AcitivityPartyRegisterConfirmBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.party.register.card.CreditCardFragment;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.RegisterPartyModel;

import org.atmarkcafe.otocon.model.response.CustomJoinEventResponse;
import org.atmarkcafe.otocon.model.response.PartyDetailData;
import org.atmarkcafe.otocon.model.response.UserJoinEventResponse;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.PaymentType;
import org.atmarkcafe.otocon.utils.StringUtils;

public class ConfirmRegisterPartyFragment extends OtoconBindingFragment<AcitivityPartyRegisterConfirmBinding> implements ConfirmRegisterPartyContact.View {

    public ObservableBoolean isLogin = new ObservableBoolean(false);
    private RegisterPartyModel mModel;
    private ConfirmRegisterPartyPresenter mPresenter;

    @Override
    public int layout() {
        return R.layout.acitivity_party_register_confirm;
    }

    @Override
    public void onCreateView(AcitivityPartyRegisterConfirmBinding binding) {

        mPresenter = new ConfirmRegisterPartyPresenter(getActivity(), this);

        String party = getArguments().getString("party");
        String registerModel = getArguments().getString("params");

        Gson gson = new Gson();
        PartyDetailData partyDetail = gson.fromJson(party, PartyDetailData.class);
        mModel = gson.fromJson(registerModel, RegisterPartyModel.class);
        isLogin.set(DBManager.isLogin(getActivity()));

        partyDetail.setFeeCustomerM(getArguments().getString("price"));
        partyDetail.setFeeCustomerF(getArguments().getString("price"));

        partyDetail.setSpecialPriceM("-1");
        partyDetail.setSpecialPriceF("-1");
        partyDetail.setFeePremiumM("-1");
        partyDetail.setFeePremiumF("-1");
        partyDetail.setBenefitEarlyMale("-1");
        partyDetail.setBenefitEarlyFeMale("-1");

        binding.setActivity(this);
        binding.setParty(partyDetail);
        binding.setModel(mModel);


        binding.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

    }

    public void onSubmit() {
        String json = getArguments().getString("params");
        RegisterPartyModel params = new Gson().fromJson(json, RegisterPartyModel.class);
        mPresenter.onCallAPIRegisterParty(params);
    }

    @Override
    public void customJoinSuccess(CustomJoinEventResponse response) {
        //TODO get from response
        int cost_price = StringUtils.parseStringToInteger(response.getJoinEventData().getCost_price(), 0);
        int total_price = StringUtils.parseStringToInteger(response.getJoinEventData().getTotal_price(), 0);
        int transaction_id = StringUtils.parseStringToInteger(response.getTransaction_id(), 0);

        startAcvtivity(response.getHtml_success(), cost_price, total_price, transaction_id);
    }

    @Override
    public void userJoinSuccess(UserJoinEventResponse response) {
        int cost_price = StringUtils.parseStringToInteger(response.getJoinEventData().getCost_price(), 0);
        int total_price = StringUtils.parseStringToInteger(response.getJoinEventData().getTotal_price(), 0);
        int transaction_id = StringUtils.parseStringToInteger(response.getTransaction_id(), 0);

        startAcvtivity(response.getHtml_success(), cost_price, total_price, transaction_id);
    }

    private void startAcvtivity(String html_success, int cost_price, int total_price, int transaction_id) {

        String json = getArguments().getString("params");
        RegisterPartyModel params = new Gson().fromJson(json, RegisterPartyModel.class);

        switch (PaymentType.factory(params.getPayment() + "")) {
            case card:

                FragmentUtils.replace(getActivity(), CreditCardFragment.start(getActivity(), transaction_id, cost_price, total_price, new OtoconFragmentListener() {
                    @Override
                    public void onHandlerReult(int status, Bundle extras) {
                        getActivity().onBackPressed();
                        otoconFragmentListener.onHandlerReult(status, extras);
                    }
                }), true);
                break;
            case money:
                SuccessRegisterPartyFragment fragment = SuccessRegisterPartyFragment.create(html_success, new OtoconFragmentListener() {
                    @Override
                    public void onHandlerReult(int status, Bundle extras) {
                        getActivity().onBackPressed();
                        otoconFragmentListener.onHandlerReult(status, extras);
                    }
                });

                FragmentUtils.replace(getActivity(), fragment, true);
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {

        ExtensionActivity.setEnableBack(getActivity(), !show);

        viewDataBinding.loading.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void showErrorDialog(boolean errorNetwork) {
        if (errorNetwork) {
            new PopupMessageErrorDialog(getActivity()).show();
        } else {

            new PopupMessageErrorDialog(getActivity(),
                    getString(R.string.error_title_Connect_server_fail),
                    getString(R.string.error_content_Connect_server_fail)
            ).show();
        }
    }

    @Override
    public void onShowPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

}
