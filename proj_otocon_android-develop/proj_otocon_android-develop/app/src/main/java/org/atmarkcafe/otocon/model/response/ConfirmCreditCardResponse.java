package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

public class ConfirmCreditCardResponse extends OnResponse {
    @SerializedName("data")
    private Data data;

    @SerializedName("errors")
    private ErrorConfirmCreditCardResponse errors;

    public ErrorConfirmCreditCardResponse getErrors() {
        return errors;
    }

    public int getSessionUserId() {
        if (data == null) return -1;
        return data.session;
    }

    class Data {
        @SerializedName("transaction_id")
        private int session;
    }
}
