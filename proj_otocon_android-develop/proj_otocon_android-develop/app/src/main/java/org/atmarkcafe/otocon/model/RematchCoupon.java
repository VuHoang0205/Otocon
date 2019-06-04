package org.atmarkcafe.otocon.model;

import com.google.gson.annotations.SerializedName;

public class RematchCoupon {
    private int id;
    private String code;
    private String title;
   private String body;
    private String valid_start;
    private String valid_end;
    @SerializedName("user_request_avatar")
    private String avatar_request;
    private String user_receive_avatar;

    public String getBody() {
        return body;
    }

    public String getAvatar_request() {
        return avatar_request;
    }

    public String getUser_receive_avatar() {
        return user_receive_avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValid_start() {
        return valid_start;
    }

    public void setValid_start(String valid_start) {
        this.valid_start = valid_start;
    }

    public String getValid_end() {
        return valid_end;
    }

    public void setValid_end(String valid_end) {
        this.valid_end = valid_end;
    }

}
