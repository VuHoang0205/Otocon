package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorForgotPasswordResponse {

    @SerializedName("email")
    List<String> emails;

    public List<String> getEmails() {
        return emails;
    }
}
