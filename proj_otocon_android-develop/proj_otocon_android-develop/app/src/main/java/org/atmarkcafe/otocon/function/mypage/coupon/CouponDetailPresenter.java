package org.atmarkcafe.otocon.function.mypage.coupon;

import android.content.Context;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.ResponseExtension;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CouponDetailPresenter extends MVPPresenter<Integer, OnResponse> {

    public CouponDetailPresenter(MVPExtension.View<OnResponse> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, Integer id) {
        executeToView( context, InteractorManager.getApiInterface(context).useCoupon(id), null);
    }
}
