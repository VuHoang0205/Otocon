package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.RematchCoupon;

public class RematchCouponResponse extends OnResponse {

    @SerializedName("data")
    RematchCoupon model;

    public RematchCoupon getModel() {
        return model;
    }
}
