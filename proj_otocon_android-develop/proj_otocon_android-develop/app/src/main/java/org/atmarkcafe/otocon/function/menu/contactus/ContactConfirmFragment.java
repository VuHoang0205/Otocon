package org.atmarkcafe.otocon.function.menu.contactus;

import android.support.v4.app.Fragment;
import android.view.View;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ConfirmBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.model.parameter.ContactUsParams;
import org.atmarkcafe.otocon.model.response.ContactUsResponse;
import org.atmarkcafe.otocon.utils.FragmentUtils;


public class ContactConfirmFragment extends OtoconBindingFragment<ConfirmBinding> implements ComfirmUsActivivyContact.View {

    ContactUsParams params;

    @Override
    public int layout() {
        return R.layout.acitivity_contact_confirm;
    }

    @Override
    public void onCreateView(ConfirmBinding binding) {

    params = (ContactUsParams) getArguments().getSerializable("data");
        binding.setPresenter(new ContactConfirmPresenter(getActivity(), params, this));

        binding.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

    }

    @Override
    public void finishScreen() {
        ExtensionActivity.setEnableBack(getActivity(), true);
        getActivity().onBackPressed();
        otoconFragmentListener.onHandlerReult(0, null);
    }

    @Override
    public void sucsess(ContactUsResponse response) {
        ExtensionActivity.setEnableBack(getActivity(), false);
        viewDataBinding.toolbar.setNavigationIcon(null);
        if (!viewDataBinding.viewstubCompleted.isInflated()) {
            viewDataBinding.viewstubCompleted.getViewStub().inflate();
            viewDataBinding.viewstubCompleted.getBinding().setVariable(BR.submit, (View.OnClickListener) v -> {
                ExtensionActivity.setEnableBack(getActivity(), true);
                FragmentUtils.backToTop(getActivity());
                Fragment currentFragment = FragmentUtils.getFragment(getActivity());
                if (currentFragment instanceof MainFragment){
                    ((MainFragment)currentFragment).gotoTab(0);
                }
            });
        }
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
    public void showDialogTerm(String message) {
        PopupMessageErrorDialog dialog = new PopupMessageErrorDialog(getActivity(), message);
        dialog.show();
    }

}
