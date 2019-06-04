package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.ValidateUserInfo;

public class ValidateUserInfoResponse extends OnResponse {

    @SerializedName("errors")
    ValidateUserInfo error;

    public ValidateUserInfo getError() {
        return error;
    }
}
