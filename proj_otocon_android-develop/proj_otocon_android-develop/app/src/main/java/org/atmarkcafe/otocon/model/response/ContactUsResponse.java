package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactUsResponse extends OnResponse {

    @SerializedName("data")
    List<RegisterData> data;

    public RegisterData getData() {
        return (data.size() > 0) ? data.get(0) : null;
    }
    @SerializedName("errors")
    ErrorRegisterResponse errorRegisterResponse;

    public ErrorRegisterResponse getErrorRegisterResponse() {
        return errorRegisterResponse;
    }


}

