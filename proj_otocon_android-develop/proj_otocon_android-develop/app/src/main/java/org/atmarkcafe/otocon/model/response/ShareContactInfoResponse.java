package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.ShareContactInfo;

public class ShareContactInfoResponse extends OnResponse {

    @SerializedName("data")
    ShareContactInfo model;

    @SerializedName("errors")
    ShareContactError error;

    public ShareContactError getError() {
        return error == null ? new ShareContactError() : error;
    }

    public ShareContactInfo getModel() {
        return model == null ? new ShareContactInfo() : model;
    }
}
