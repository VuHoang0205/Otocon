package org.atmarkcafe.otocon.function.party.register;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.model.RegisterPartyModel;
import org.atmarkcafe.otocon.model.response.PartyDetailData;

public interface RegisterPartyContact {
    public interface Presenter {
        void getEventDetail(boolean isFirst);

        void validation();
    }

    public interface View {

        void onShowPopup(String title, String message);

        void updateUI(Object model);

        void showOtherErorrMessage(boolean isNotConnectToServer);

        void onSuccess( String price);

        void showMessageDefail(String title, String message);

    }
}
