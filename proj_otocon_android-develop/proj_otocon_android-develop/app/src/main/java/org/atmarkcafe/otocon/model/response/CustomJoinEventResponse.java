package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.Account;
import org.atmarkcafe.otocon.model.JoinEventData;

import java.util.ArrayList;
import java.util.List;

public class CustomJoinEventResponse extends OnResponse {

    @SerializedName("transaction_id")
    String transaction_id;

    @SerializedName("html_success")
    String html_success;

    @SerializedName("data")
    List<JoinEventData> list;

    @SerializedName("account")
    Account account;

    public JoinEventData getJoinEventData(){
        return  list!= null && list.size() > 0? list.get(0): new JoinEventData();
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getHtml_success() {
        return html_success;
    }

    public void setHtml_success(String html_success) {
        this.html_success = html_success;
    }

    public Account getAccount(){
        return account != null ? account : new Account();
    }
}