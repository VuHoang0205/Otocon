package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.Account;

import java.util.List;

public class PartyRegisterInfomationRespone extends OnResponse {
    @SerializedName("data")
    private List<PartyDetailData> data;

    @SerializedName("account")
    private List<Account> account;

    public List<PartyDetailData> getData() {
        return data;
    }

    public void setData(List<PartyDetailData> data) {
        this.data = data;
    }

    public Account getAccount() {
        return (account != null && account.size() > 0) ? account.get(0) : new Account();
    }

    public boolean usableCoupon(int gender) {
        PartyDetailData party = getData().get(0);
        Account account = getAccount();
        if (account != null && account.getCouponCode() != null && !account.getCouponCode().isEmpty()) {
            return !(
                    (gender == 1 && party.getSpecialPriceM() >= 0) || (gender == 2 && party.getSpecialPriceF() >= 0)
            );
        }
        return false;
    }
}
