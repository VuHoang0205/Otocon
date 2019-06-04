package org.atmarkcafe.otocon.model;

import android.graphics.Color;

public class RequestRematch {
    private int user_id;
    private int event_id;
    private String nickname;
    private int age;
    private String read_at;
    private int share_contact_id;
    private String prefecture;
    private String picture;
    private int install_app_status;
    private String install_app_color;
    private String install_app_status_title;
    private String online_status;
    private String rematch_status;
    private String rematch_color;
    private String rematch_request;
    private int withdrawal_status;
    private int total;

    public int getColor(String color) {
        try {
            return Color.parseColor(color);
        } catch (Exception e) {

        }
        return Color.TRANSPARENT;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRead_at() {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }

    public int getShare_contact_id() {
        return share_contact_id;
    }

    public void setShare_contact_id(int share_contact_id) {
        this.share_contact_id = share_contact_id;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getInstall_app_status() {
        return install_app_status;
    }

    public void setInstall_app_status(int install_app_status) {
        this.install_app_status = install_app_status;
    }

    public String getInstall_app_status_title() {
        return install_app_status_title;
    }

    public void setInstall_app_status_title(String install_app_status_title) {
        this.install_app_status_title = install_app_status_title;
    }

    public String getInstall_app_color() {
        return install_app_color;
    }

    public void setInstall_app_color(String install_app_color) {
        this.install_app_color = install_app_color;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getRematch_status() {
        return rematch_status;
    }

    public void setRematch_status(String rematch_status) {
        this.rematch_status = rematch_status;
    }

    public String getRematch_color() {
        return rematch_color;
    }

    public void setRematch_color(String rematch_color) {
        this.rematch_color = rematch_color;
    }

    public String getRematch_request() {
        return rematch_request;
    }

    public void setRematch_request(String rematch_request) {
        this.rematch_request = rematch_request;
    }

    public int getWithdrawal_status() {
        return withdrawal_status;
    }

    public void setWithdrawal_status(int withdrawal_status) {
        this.withdrawal_status = withdrawal_status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
