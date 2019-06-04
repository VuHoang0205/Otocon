package org.atmarkcafe.otocon.model.response;

import org.atmarkcafe.otocon.model.PhotoModel;
import org.atmarkcafe.otocon.model.ProfileCardModel;
import org.atmarkcafe.otocon.model.UserProfileCardModel;

import java.util.List;

public class UserCardRespone extends OnResponse {

     List<UserProfileCardModel> data;

    public UserProfileCardModel getData() {
        return data!=null? data.get(0):new UserProfileCardModel();
    }
}
