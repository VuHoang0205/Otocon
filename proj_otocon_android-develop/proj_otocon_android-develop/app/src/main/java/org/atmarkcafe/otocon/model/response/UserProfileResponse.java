package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.function.mypage.item.UserProfileModel;

public class UserProfileResponse extends OnResponse {

    @SerializedName("profiles")
    UserProfileModel model;

    public UserProfileModel getModel() {
        return model == null ? new UserProfileModel() : model;
    }
}
