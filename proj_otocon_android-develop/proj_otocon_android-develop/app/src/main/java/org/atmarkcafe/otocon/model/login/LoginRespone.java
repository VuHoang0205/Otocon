package org.atmarkcafe.otocon.model.login;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.Account;
import org.atmarkcafe.otocon.model.response.OnResponse;

import java.util.List;

public class LoginRespone extends OnResponse {

    @SerializedName("data")
    private List<Account> data;

    @SerializedName("errors")
    LoginErrorResponse errorRegisterResponse;

    public LoginErrorResponse getLoginErrorResponse() {
        return errorRegisterResponse;
    }

    public List<Account> getData() {
        return data;
    }

    public void setData(List<Account> data) {
        this.data = data;
    }

    public Account getAcoount(){
        try{
            return data.get(0);
        }catch (Exception e){
            return  null;
        }
    }

}
