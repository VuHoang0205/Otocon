package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponse extends OnResponse {

    @SerializedName("data")
    List<RegisterData> data;

    @SerializedName("errors")
    ErrorRegisterResponse errorRegisterResponse;

    public RegisterData getData() {
        return (data.size() > 0) ? data.get(0) : null;
    }

    public ErrorRegisterResponse getErrorRegisterResponse() {
        return errorRegisterResponse;
    }
}
