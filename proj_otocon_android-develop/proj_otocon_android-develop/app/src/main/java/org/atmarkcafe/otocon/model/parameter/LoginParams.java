package org.atmarkcafe.otocon.model.parameter;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.R;

public class LoginParams extends Params {

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
