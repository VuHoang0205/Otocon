package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResponse extends OnResponse {

    @SerializedName("errors")
    ErrorForgotPasswordResponse errorForgotPasswordResponse;

    public ErrorForgotPasswordResponse getErrorForgotPasswordResponse() {
        return errorForgotPasswordResponse;
    }
}
