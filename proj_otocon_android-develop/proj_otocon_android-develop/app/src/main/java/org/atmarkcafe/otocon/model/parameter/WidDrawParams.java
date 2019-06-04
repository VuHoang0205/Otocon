package org.atmarkcafe.otocon.model.parameter;

import com.google.gson.annotations.SerializedName;

public class WidDrawParams extends Params {
    @SerializedName("password")
    String password = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
