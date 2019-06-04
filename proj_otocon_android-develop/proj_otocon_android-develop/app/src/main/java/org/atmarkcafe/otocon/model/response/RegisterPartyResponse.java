package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.function.party.register.ErrorRegisterModel;
import org.atmarkcafe.otocon.model.Account;
import org.atmarkcafe.otocon.model.RegisterPartyModel;
import org.atmarkcafe.otocon.utils.StringUtils;

import java.util.List;

public class RegisterPartyResponse extends OnResponse {

    @SerializedName("errors")
    ErrorRegisterModel errorRegisterModel;

    @SerializedName("total_price")
    String totalPrice;

    public String getTotalPrice() {
        return totalPrice;
    }

    public ErrorRegisterModel getErrorRegisterResponse() {
        return errorRegisterModel;
    }
}
