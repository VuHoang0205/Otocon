package org.atmarkcafe.otocon.model.parameter;

import com.google.gson.annotations.SerializedName;

public class ConfirmCreditCardParams extends Params {
    @SerializedName("device_id")
    String device_id;

    @SerializedName("payment_card_number")
    String number;

    @SerializedName("payment_card_secure")
    String secure;

    @SerializedName("payment_card_expired")
    String expired;

    int session;

    @SerializedName("card_id")
    String cardId;

    @SerializedName("transaction_id")
    String transaction_id;

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getNumber() {
        return number;
    }

    public String getSecure() {
        return secure;
    }

    public String getExpired() {
        return expired;
    }

    public int getSession() {
        return session;
    }

    public String getCardId() {
        return cardId;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
