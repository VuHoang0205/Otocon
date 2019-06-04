package org.atmarkcafe.otocon.model.parameter;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.R;

public class ForgotPasswordParams extends Params {

   @SerializedName("email")
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
