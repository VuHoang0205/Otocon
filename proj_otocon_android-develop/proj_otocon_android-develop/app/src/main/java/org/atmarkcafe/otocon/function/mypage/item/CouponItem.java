package org.atmarkcafe.otocon.function.mypage.item;

import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemCouponBinding;
import org.atmarkcafe.otocon.model.Coupon;

public class CouponItem extends BindableItem<ItemCouponBinding> {

    private Coupon coupon;

    public CouponItem(Coupon coupon) {
        this.coupon = coupon;
    }

    public Coupon getCoupon(){
        return coupon;
    }

    @Override
    public void bind(@NonNull ItemCouponBinding viewBinding, int position) {
        viewBinding.setCoupon(coupon);
    }

    @Override
    public int getLayout() {
        return R.layout.item_coupon;
    }
}
