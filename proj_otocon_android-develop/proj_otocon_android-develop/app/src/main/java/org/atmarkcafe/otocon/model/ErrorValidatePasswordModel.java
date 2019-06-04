package org.atmarkcafe.otocon.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.atmarkcafe.otocon.BR;

import java.util.List;

public class ErrorValidatePasswordModel extends BaseObservable {
    List<String> email;
    List<String> current_password;
    List<String> new_password;
    List<String> new_password_confirmation;

    @Bindable
    public String getEmail() {
        return email != null ? email.get(0) : null;
    }

    public void setEmail(List<String> email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getCurrent_password() {
        return current_password != null ? current_password.get(0) : null;
    }

    public void setCurrent_password(List<String> current_password) {
        this.current_password = current_password;
        notifyPropertyChanged(BR.current_password);
    }

    @Bindable
    public String getNew_password() {
        return new_password != null ? new_password.get(0) : null;
    }

    public void setNew_password(List<String> new_password) {
        this.new_password = new_password;
        notifyPropertyChanged(BR.new_password);
    }

    @Bindable
    public String getNew_password_confirmation() {
        return new_password_confirmation != null ? new_password_confirmation.get(0) : null;
    }

    public void setNew_password_confirmation(List<String> new_password_confirmation) {
        this.new_password_confirmation = new_password_confirmation;
        notifyPropertyChanged(BR.new_password_confirmation);
    }
}
