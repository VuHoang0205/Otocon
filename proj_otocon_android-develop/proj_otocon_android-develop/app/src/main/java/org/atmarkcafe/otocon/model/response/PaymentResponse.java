package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.Account;

import java.util.ArrayList;
import java.util.List;

public class PaymentResponse extends OnResponse {

    @SerializedName("data")
    List<Account> list;

    @SerializedName("account")
    Account account;

    @SerializedName("html_success")
    String html_success;

    public String getHtml_success() {
        return html_success;
    }

    public void setHtml_success(String html_success) {
        this.html_success = html_success;
    }

    public Account getAccount() {
        if(account != null && account.hasToken()){
            return account;
        }
        
        return list!= null && !list.isEmpty() ? list.get(0) : new Account();
    }
}