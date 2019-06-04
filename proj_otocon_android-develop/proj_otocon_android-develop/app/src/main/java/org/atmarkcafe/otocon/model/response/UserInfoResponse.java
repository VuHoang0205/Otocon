package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.UserInfo;

import java.util.ArrayList;

public class UserInfoResponse extends OnResponse {
    @SerializedName("data")
    ArrayList<UserInfo> model;

    public UserInfo getUserInfor(){
        return model != null ? model.get(0) : new UserInfo();
    }
}
