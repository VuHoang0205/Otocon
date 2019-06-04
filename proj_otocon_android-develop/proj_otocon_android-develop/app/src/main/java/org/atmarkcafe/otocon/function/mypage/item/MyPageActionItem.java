package org.atmarkcafe.otocon.function.mypage.item;

import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemMyPageActionBinding;

public class MyPageActionItem extends BindableItem<ItemMyPageActionBinding> {

    public static final int ACTION_TYPE_EDIT_PROFILE = 0;
    public static final int ACTION_TYPE_PRINT_INTRODUCTION = 1;
    public static final int ACTION_TYPE_PARTY_LIST = 2;
    public static final int ACTION_TYPE_FAVORITE = 3;
    public static final int ACTION_TYPE_CALENDAR = 4;
    public static final int ACTION_TYPE_COUPON = 5;
    public static final int ACTION_TYPE_REMATH_REQUEST = 6;
    public static final int ACTION_TYPE_NOTIFICATION_SETTINGS = 7;
    public static final int ACTION_TYPE_WIDTH_DRAWAL = 8;

    private int type;
    private int resIcon;
    private int resText;
    private int noti;
    private int sumRequest;

    public MyPageActionItem(int type, int resIcon, int resText) {
        this.type = type;
        this.resIcon = resIcon;
        this.resText = resText;
        this.noti = 0;
        this.sumRequest = 0;
    }

    public MyPageActionItem setNotify(int sumRequest,int noti) {
        this.noti = noti;
        this.sumRequest = sumRequest;
        return this;
    }

    public int getType() {
        return type;
    }

    public int getResIcon() {
        return resIcon;
    }

    public int getResText() {
        return resText;
    }

    public int getNoti() {
        return noti;
    }

    public int getSumRequest() {
        return sumRequest;
    }

    public String getStringSumRequest() {
        return "（" + sumRequest + "リクエスト）";
    }

    @Override
    public void bind(@NonNull ItemMyPageActionBinding viewBinding, int position) {
        viewBinding.setItem(this);
    }

    @Override
    public int getLayout() {
        return R.layout.item_my_page_action;
    }
}
