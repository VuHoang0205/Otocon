package org.atmarkcafe.otocon.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.google.gson.annotations.SerializedName
import org.atmarkcafe.otocon.BR

class LoginInfoModel : BaseObservable() {
    @SerializedName("email")
    internal var email: String = ""

    @SerializedName("current_password")
    internal var currentPassword: String = ""

    @SerializedName("new_password")
    internal var newPassword: String = ""

    @SerializedName("new_password_confirmation")
    internal var confirmPassword: String = ""

    @Bindable
    fun getEmail(): String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    @Bindable
    fun getCurrentPassword(): String {
        return currentPassword
    }

    fun setCurrentPassword(currentPassword: String) {
        this.currentPassword = currentPassword
        notifyPropertyChanged(BR.currentPassword)
    }

    @Bindable
    fun getNewPassword(): String {
        return newPassword
    }

    fun setNewPassword(newPassword: String) {
        this.newPassword = newPassword
        notifyPropertyChanged(BR.newPassword)
    }

    @Bindable
    fun getConfirmPassword(): String {
        return confirmPassword
    }

    fun setConfirmPassword(newPassword: String) {
        this.confirmPassword = newPassword
        notifyPropertyChanged(BR.confirmPassword)
    }

}