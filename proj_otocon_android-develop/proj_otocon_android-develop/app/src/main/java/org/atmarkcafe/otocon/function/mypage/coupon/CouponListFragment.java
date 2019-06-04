package org.atmarkcafe.otocon.function.mypage.coupon;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.databinding.FragmentCouponListBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.mypage.item.CouponItem;
import org.atmarkcafe.otocon.model.Coupon;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.ResponseExtension;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.view.ItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class CouponListFragment extends OtoconBindingFragment<FragmentCouponListBinding> implements MVPExtension.View<ResponseExtension<Coupon>>, PopupMessageErrorDialog.PopupMessageErrorListener, OnItemClickListener {
    public static final int REQUEST_CODE_USING_COUPON = 123;

    CouponListPresenter mPresenter = new CouponListPresenter(this);

    public final GroupAdapter couponAdapter = new GroupAdapter();
    private int id_coupon_apply = -1;
    private String title_coupon_apply = "";

    @Override
    public int layout() {
        return R.layout.fragment_coupon_list;
    }

    @Override
    public void onCreateView(FragmentCouponListBinding mBinding) {

        mPresenter = new CouponListPresenter(this);
        mBinding.setFragment(this);

        mBinding.toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        mBinding.counponRecyclerView.addItemDecoration(new ItemDecoration(20f, 15f, 0, 15f));
        mBinding.counponRecyclerView.setAdapter(couponAdapter);
        couponAdapter.setOnItemClickListener(this);

        mBinding.couponRefreshLayout.setOnRefreshListener(() -> {
            mBinding.couponRefreshLayout.setRefreshing(false);
            mPresenter.onExecute(getActivity(), 0, null);

        });

        // load coupon list
        mPresenter.onExecute(getActivity(), 0, null);
    }

    // View
    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getContext(), title, message, this);
    }

    @Override
    public void success(ResponseExtension<Coupon> response) {
        couponAdapter.clear();
        viewDataBinding.noData.setVisibility(response.getDataList().size() == 0 ? View.VISIBLE : View.GONE);
        List<CouponItem> listCoupon = new ArrayList<>();
        for (Coupon coupon : response.getDataList()) {
            listCoupon.add(new CouponItem(coupon));
            if (coupon.getApply() == 1){
                id_coupon_apply = coupon.getId();
                title_coupon_apply = coupon.getTitle();
            }
        }
        couponAdapter.addAll(listCoupon);
        couponAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress(boolean show) {
        viewDataBinding.noData.setVisibility(View.GONE);
        viewDataBinding.loadingLayout.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(ResponseExtension<Coupon> response) {

    }

    @Override
    public void onClickOke() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().finish();
        }
    }

    @Override
    public void onItemClick(@NonNull Item item, @NonNull View view) {
        CouponItem couponItem = (CouponItem) item;
        CouponDetailFragment fCouponDetail = CouponDetailFragment.newInstance(couponItem.getCoupon(), id_coupon_apply, title_coupon_apply);
        fCouponDetail.setTargetFragment(this, REQUEST_CODE_USING_COUPON);
        FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, fCouponDetail, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onExecute(getActivity(), 0, null);
    }
}
