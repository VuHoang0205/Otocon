package org.atmarkcafe.otocon.model.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.BR;

public class LoginParams extends BaseObservable {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @Bindable
    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}