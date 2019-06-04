package org.atmarkcafe.otocon.function.mypage.coupon;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.databinding.FragmentCouponDetailBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.model.Coupon;
import org.atmarkcafe.otocon.model.response.OnResponse;

public class CouponDetailFragment extends OtoconBindingFragment<FragmentCouponDetailBinding> implements MVPExtension.View<OnResponse> {

    public static final String ARG_COUPON = "coupondetail.arg_coupon";
    public static final String ARG_APPLY_COUPON_ID = "coupondetail.arg_apply_coupon_id";
    public static final String ARG_APPLY_COUPON_TITLE = "coupondetail.arg_apply_coupon_title";


    private CouponDetailPresenter mPresenter = new CouponDetailPresenter(this);
    private int applyCouponId = -1;
    private String applyCouponTitle = "";
    private Coupon coupon;

    public static CouponDetailFragment newInstance(Coupon coupon, int applyCouponId, String applyCouponTitle) {
        CouponDetailFragment fragment = new CouponDetailFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        args.putString(ARG_COUPON, gson.toJson(coupon));
        args.putString(ARG_APPLY_COUPON_TITLE, applyCouponTitle);
        args.putInt(ARG_APPLY_COUPON_ID, applyCouponId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int layout() {
        return R.layout.fragment_coupon_detail;
    }

    @Override
    public void onCreateView(FragmentCouponDetailBinding mBinding) {

        String jCoupon = getArguments().getString(ARG_COUPON);
        applyCouponId = getArguments().getInt(ARG_APPLY_COUPON_ID);
        applyCouponTitle = getArguments().getString(ARG_APPLY_COUPON_TITLE);
        Gson gson = new Gson();
        coupon = gson.fromJson(jCoupon, Coupon.class);

        mBinding.setFragment(this);
        mBinding.setCoupon(coupon);

        mBinding.toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getContext(), title, message, null);
    }

    @Override
    public void success(OnResponse response) {
        if (applyCouponId != -1 && coupon.getId() != applyCouponId) {
            String message = String.format(getString(R.string.fomat_message_change_coupon), applyCouponTitle, coupon.getTitle());
            PopupMessageErrorDialog.show(getContext(), R.string.app_name, message, new PopupMessageErrorDialog.PopupMessageErrorListener() {
                @Override
                public void onClickOke() {
                    getTargetFragment().onActivityResult(CouponListFragment.REQUEST_CODE_USING_COUPON, Activity.RESULT_OK, null);
                    getActivity().onBackPressed();
                }
            });
        } else {
            getTargetFragment().onActivityResult(CouponListFragment.REQUEST_CODE_USING_COUPON, Activity.RESULT_OK, null);
            getActivity().onBackPressed();
        }

    }

    @Override
    public void showProgress(boolean show) {
        setEnableBack(!show);
        viewDataBinding.loadingLayout.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(OnResponse response) {

    }

    public void onSubmit(int id) {
        // call api to use coupon
        ExtensionActivity.setEnableBack(getActivity(), false);
        mPresenter.onExecute(getActivity(), 0, id);

    }
}
