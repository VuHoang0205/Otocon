package org.atmarkcafe.otocon.model.response;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.utils.ValidateUtils;


public class OnResponse {

    @SerializedName("status")
    //@Expose
    private int code;

    @SerializedName("message")
    //@Expose
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess(){
        return code == 1;
    }

    public boolean hasAuthenticationError(){
        return code == 3;
    }

    public boolean isFaileShowDialog(){
        return code == 2;
    }

    public static String[] getMessage(Context context, Throwable th, OnResponse response) {
        String title = null;
        String message = null;

        if (th != null) {
            if (!ValidateUtils.isRetrofitErrorNetwork(th)) {
                title = getString(context, R.string.error_title_Connect_server_fail);
                message = getString(context, R.string.error_content_Connect_server_fail);
            }else{
                title = getString(context, R.string.network_error_title);
                message = getString(context, R.string.network_error_content);
            }
        } else if (response == null) {
            title = getString(context, R.string.error_title_Connect_server_fail);
            message = getString(context, R.string.error_content_Connect_server_fail);
        } else {
            title = getString(context, R.string.app_name);
            message = response.getMessage();
        }

        return new String[]{title, message};
    }

    public static String getString(Context context, int id) {
        try {
            return context.getString(id);
        } catch (Exception e) {
            return null;
        }
    }
}
