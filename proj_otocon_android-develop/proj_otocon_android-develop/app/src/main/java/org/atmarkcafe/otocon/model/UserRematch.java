package org.atmarkcafe.otocon.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.R;

public class UserRematch extends BaseObservable {
    private String user_id;
    private String nickname;
    private String age;
    private String prefecture;
    private String picture;
    private String install_app_status;
    private String install_app_status_title;
    private String install_app_color;
    private String online_status;
    private String rematch_status;
    private String rematch_color;
    private int withdrawal_status;
    private String rematch_request;

    @Bindable
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        notifyPropertyChanged(BR.user_id);
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
        notifyPropertyChanged(BR.prefecture);
    }

    @Bindable
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
        notifyPropertyChanged(BR.picture);
    }

    @Bindable
    public String getInstall_app_status() {
        return install_app_status;
    }

    public void setInstall_app_status(String install_app_status) {
        this.install_app_status = install_app_status;
        notifyPropertyChanged(BR.install_app_status);
    }

    @Bindable
    public String getInstall_app_color() {
        return install_app_color;
    }

    public void setInstall_app_color(String install_app_color) {
        this.install_app_color = install_app_color;
        notifyPropertyChanged(BR.install_app_color);
    }

    @Bindable
    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
        notifyPropertyChanged(BR.online_status);
    }

    @Bindable
    public String getRematch_status() {
        return rematch_status;
    }

    public void setRematch_status(String rematch_status) {
        this.rematch_status = rematch_status;
        notifyPropertyChanged(BR.rematch_status);
    }

    @Bindable
    public String getRematch_color() {
        return rematch_color;
    }

    public void setRematch_color(String rematch_color) {
        this.rematch_color = rematch_color;
        notifyPropertyChanged(BR.rematch_color);
    }

    @Bindable
    public int getWithdrawal_status() {
        return withdrawal_status;
    }

    public void setWithdrawal_status(int withdrawal_status) {
        this.withdrawal_status = withdrawal_status;
        notifyPropertyChanged(BR.withdrawal_status);
    }

    public String getAgePrefecture(Context context) {
        if (withdrawal_status == 1) {
            return context.getString(R.string.person_withdrawal);
        }
        return age + context.getString(R.string.hours_fomat) + prefecture;
    }

    @Bindable
    public String getInstall_app_status_title() {
        return install_app_status_title;
    }

    public void setInstall_app_status_title(String install_app_status_title) {
        this.install_app_status_title = install_app_status_title;
        notifyPropertyChanged(BR.install_app_status_title);
    }

    @Bindable
    public String getRematch_request() {
        return rematch_request;
    }

    public void setRematch_request(String rematch_request) {
        this.rematch_request = rematch_request;
        notifyPropertyChanged(BR.rematch_request);
    }

    public int getColor(String color) {
        try {
            return Color.parseColor(color);
        } catch (Exception e) {

        }
        return Color.TRANSPARENT;
    }
}
