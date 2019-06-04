package org.atmarkcafe.otocon.function.party;

import android.content.Context;

import org.atmarkcafe.otocon.model.response.PartyDetailData;

public class PartyDetailActivityContract {

    public interface Presenter {
        void onLoadData(String id);
        void likeParty(PartyDetailData partyDetail);
    }

    interface PartyDetailView {
        void showDataSuccess(PartyDetailData partyDetailData);
        void showOtherErorrMessage(boolean isNotConnectToServer);
        void showPopupPartyExpired(String message);
    }
}
