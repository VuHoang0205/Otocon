package org.atmarkcafe.otocon.model.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginErrorResponse extends BaseObservable {


    @SerializedName("email")
    List<String> emails;

    @SerializedName("password")
    List<String> passwords;

    @Bindable
    public List<String> getEmails() {
        return emails;
    }

    @Bindable
    public List<String> getPasswords() {
        return passwords;
    }

}