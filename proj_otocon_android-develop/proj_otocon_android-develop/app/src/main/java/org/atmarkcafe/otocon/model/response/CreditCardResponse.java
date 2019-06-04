package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.Coupon;
import org.atmarkcafe.otocon.model.CreditCard;

import java.util.List;

public class CreditCardResponse extends OnResponse {
    @SerializedName("data")
    private List<CreditCard> data;

    public List<CreditCard> getCreditCardList() {
        return data;
    }
}
