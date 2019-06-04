package org.atmarkcafe.otocon.function.mypage.coupon;

import android.content.Context;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.Coupon;
import org.atmarkcafe.otocon.model.response.ResponseExtension;

public class CouponListPresenter extends MVPPresenter<String, ResponseExtension<Coupon>> {

    public CouponListPresenter(MVPExtension.View<ResponseExtension<Coupon>> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, String s) {
        executeToView(context,InteractorManager.getApiInterface(context).getCoupons(), null);
    }

}
