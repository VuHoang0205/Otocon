package org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.function.mypage.item.UserProfileModel;
import org.atmarkcafe.otocon.model.response.OnResponse;

public class UserInfoRematchResponse extends OnResponse {

    @SerializedName("profiles")
    InfoReMatch model;

    public InfoReMatch getModel() {
        return model == null ? new InfoReMatch() : model;
    }
}