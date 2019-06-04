package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorConfirmCreditCardResponse {

    @SerializedName("session_user_id")
    List<String> sessionUserId;

    @SerializedName("payment_card_number")
    List<String> number;

    @SerializedName("payment_card_secure")
    List<String> secure;

    @SerializedName("payment_card_expired")
    List<String> expired;

    public List<String> getSessionUserId() {
        return sessionUserId;
    }

    public List<String> getNumber() {
        return number;
    }

    public List<String> getSecure() {
        return secure;
    }

    public List<String> getExpired() {
        return expired;
    }
}
