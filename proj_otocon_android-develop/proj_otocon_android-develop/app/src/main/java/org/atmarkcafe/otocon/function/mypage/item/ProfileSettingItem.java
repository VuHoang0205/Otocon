package org.atmarkcafe.otocon.function.mypage.item;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xwray.groupie.Group;
import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemProfileSettingBinding;
import org.atmarkcafe.otocon.utils.StringUtils;

public class ProfileSettingItem extends BindableItem<ItemProfileSettingBinding> {

    private ProfileAction type;
    private int remainItem = 0;
    private int totalItem = 0;

    public ProfileSettingItem(ProfileAction type) {
        this.type = type;
    }

    public ProfileAction getType() {
        return type;
    }

    @Override
    public void bind(@NonNull ItemProfileSettingBinding viewBinding, int position) {
        Context context = viewBinding.getRoot().getContext();
        switch (type) {
            case LOGIN_INFO:
                viewBinding.title.setText(String.format(context.getString(R.string.profile_setting_login_info), remainItem, totalItem)
                    .replace("（0／0）", "")
                );
                break;
            case USER_INFO:
                viewBinding.title.setText(String.format(context.getString(R.string.profile_setting_user_info), remainItem, totalItem)
                        .replace("（0／0）", "")
                );
                break;
            case SELF_INTRODUCTION_CARD:
                viewBinding.title.setText(String.format(context.getString(R.string.profile_setting_self_introduction_card), remainItem, totalItem)
                        .replace("（0／0）", "") );
                break;
            case REMATCH_INFO:
                viewBinding.title.setText(String.format(context.getString(R.string.profile_setting_rematch_info), remainItem, totalItem)
                        .replace("（0／0）", "") );
                break;
            case CREDIT_CARD_MANAGER:
                viewBinding.title.setText(R.string.profile_setting_credit_card_manager);
                break;
            case UPGRADE_PREMIUM:
                viewBinding.title.setText(R.string.profile_setting_upgrade_premium);
                break;
            case LELEAVE_PREMIUM:
                viewBinding.title.setText(R.string.profile_setting_leave_premium);
                break;
        }
    }

    public ProfileSettingItem setCompletionRate(int remain, int total) {
        this.remainItem = remain;
        this.totalItem = total;
        return this;
    }

    @Override
    public int getLayout() {
        return R.layout.item_profile_setting;
    }

    public Group setCompletionRate(String complete_rematch, String total_rematch) {
        setCompletionRate(StringUtils.parseStringToInteger(complete_rematch, 0), StringUtils.parseStringToInteger(total_rematch, 0));

        return this;
    }

    public enum ProfileAction {
        LOGIN_INFO,
        USER_INFO,
        SELF_INTRODUCTION_CARD,
        REMATCH_INFO,
        CREDIT_CARD_MANAGER,
        UPGRADE_PREMIUM,
        LELEAVE_PREMIUM,
    }
}