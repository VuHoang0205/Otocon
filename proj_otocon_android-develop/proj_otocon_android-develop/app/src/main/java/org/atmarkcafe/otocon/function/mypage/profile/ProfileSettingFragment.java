package org.atmarkcafe.otocon.function.mypage.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.databinding.FragmentProfileSettingBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.mypage.card.SettingCardRootFragment;
import org.atmarkcafe.otocon.function.mypage.item.ProfileSettingItem;
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.RematchRootFragment;
import org.atmarkcafe.otocon.function.mypage.updatepremium.LeavePremiumFragment;
import org.atmarkcafe.otocon.function.mypage.updatepremium.UpgradeUserPremiumRootFragment;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.UserProfileResponse;
import org.atmarkcafe.otocon.model.response.UserRespose;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.StringUtils;

import static org.atmarkcafe.otocon.function.mypage.item.ProfileSettingItem.ProfileAction.*;

public class ProfileSettingFragment extends OtoconBindingFragment<FragmentProfileSettingBinding> implements OnItemClickListener, MVPExtension.View<OnResponse> {
    UserProfileResponse response = new UserProfileResponse();
    ProfileSettingPresenter presenter = new ProfileSettingPresenter(this);
    private GroupAdapter mProfileAdapter = new GroupAdapter();

    @Override
    public int layout() {
        return R.layout.fragment_profile_setting;
    }

    @Override
    public void onCreateView(FragmentProfileSettingBinding viewDataBinding) {
        viewDataBinding.toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        viewDataBinding.profileSettingRecyclerView.setAdapter(mProfileAdapter);
        mProfileAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();

        loadInfor();
    }

    private void loadUserProfile() {

        presenter.onExecute(getActivity(), 0, null);
    }

    private void updateList() {
        if (getContext()==null) {
            return;
        }
        mProfileAdapter.clear();

        mProfileAdapter.add(new ProfileSettingItem(LOGIN_INFO)
                .setCompletionRate(2, 2));

        mProfileAdapter.add(new ProfileSettingItem(USER_INFO)
                .setCompletionRate(response.getModel().getComplete_info(), response.getModel().getTotal_info()));

        mProfileAdapter.add(new ProfileSettingItem(SELF_INTRODUCTION_CARD)
                .setCompletionRate(response.getModel().getComplete_card(), response.getModel().getTotal_card()));

        mProfileAdapter.add(new ProfileSettingItem(REMATCH_INFO)
                .setCompletionRate(response.getModel().getComplete_rematch(), response.getModel().getTotal_rematch()));
        mProfileAdapter.add(new ProfileSettingItem(CREDIT_CARD_MANAGER));
        if (DBManager.getMyAccount(getActivity()).isPremium()) {
            mProfileAdapter.add(new ProfileSettingItem(LELEAVE_PREMIUM));
        } else {
            mProfileAdapter.add(new ProfileSettingItem(UPGRADE_PREMIUM));
        }

        mProfileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(@NonNull Item item, @NonNull View view) {
        ProfileSettingItem profileItem = (ProfileSettingItem) item;
        OtoconFragment fragment = null;
        switch (profileItem.getType()) {
            case LOGIN_INFO:
                fragment = new LoginInfoFragment();
                fragment.setOtoconFragmentListener((status, extras) -> {
                    // TODO reLogin when password changed

                });
                FragmentUtils.replace(getActivity(), fragment, true);
                break;
            case USER_INFO:
                fragment = new EditInfoUserRootFragment();
                fragment.setOtoconFragmentListener((status, extras) -> {
                    // TODO reLogin when password changed

                });
                FragmentUtils.replace(getActivity(), fragment, true);
                break;
            case SELF_INTRODUCTION_CARD:
                //TODO listener
                fragment =StringUtils.parseStringToInteger(response.getModel().getComplete_card(), 0) > 0? new IntroductionUserConfirmFragment():new SelfIntroductionCardInfoFragment();
                fragment.setOtoconFragmentListener(new OtoconFragmentListener() {
                    @Override
                    public void onHandlerReult(int status, Bundle extras) {

                    }

                });
                FragmentUtils.replace(getActivity(), fragment, true);
                break;
            case REMATCH_INFO:

                fragment = new RematchRootFragment();
                fragment.setOtoconFragmentListener((status, extras) -> {

                });
                Bundle bundle = new Bundle();
                bundle.putInt("complete_rematch",StringUtils.parseStringToInteger(response.getModel().getComplete_rematch(), 0));
                fragment.setArguments(bundle);

                FragmentUtils.replace(getActivity(), fragment, true);
                break;
            case CREDIT_CARD_MANAGER:
                fragment = new SettingCardRootFragment();
                FragmentUtils.replace(getActivity(), fragment, true);
                break;
            case UPGRADE_PREMIUM:
                fragment = new UpgradeUserPremiumRootFragment();

                fragment.setOtoconFragmentListener(new OtoconFragmentListener() {
                    @Override
                    public void onHandlerReult(int status, Bundle extras) {
                        FragmentUtils.backFragment(getActivity(), ProfileSettingFragment.class);

                    }
                });
                FragmentUtils.replace(getActivity(), fragment, true);
                break;
            case LELEAVE_PREMIUM:
                fragment = new LeavePremiumFragment();
                bundle = new Bundle();
                bundle.putString("date_expired", response.getModel().premium_expired_date);
                fragment.setArguments(bundle);
                fragment.setOtoconFragmentListener(new OtoconFragmentListener() {
                    @Override
                    public void onHandlerReult(int status, Bundle extras) {
                        finish();
                    }
                });
                FragmentUtils.replace(getActivity(), fragment, true);
                break;
        }
    }

    public void loadInfor() {
        presenter.loadInfor(getActivity(), new MVPPresenter.ExecuteListener<UserRespose>() {
            @Override
            public void onNext(UserRespose userRespose) {
                updateList();

                loadUserProfile();
            }

            @Override
            public void onError(Throwable e) {
                loadUserProfile();
            }
        });
    }
    //View

    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void success(OnResponse onResponse) {
        updateList();
    }

    @Override
    public void showProgress(boolean show) {
        
    }

    @Override
    public void showMessage(OnResponse onResponse) {
        if (onResponse instanceof UserProfileResponse) {
            response = (UserProfileResponse) onResponse;
            updateList();
        }
    }
}

class ProfileSettingPresenter extends MVPPresenter<String, OnResponse> {
    public ProfileSettingPresenter(MVPExtension.View<OnResponse> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, String s) {
        execute(InteractorManager.getApiInterface(context).getUserProfile(), new ExecuteListener<UserProfileResponse>() {
            @Override
            public void onNext(UserProfileResponse response) {
                if (response != null && response.isSuccess()) {
                    view.showMessage(response);
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