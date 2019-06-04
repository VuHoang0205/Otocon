package org.atmarkcafe.otocon.function.beacon;

import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.PartyRespone;

public class GPSBeaconContract {

    interface Presenter{
        void loadParty(PartyParams params);
        void likeParty(Party party);
    }

    interface View{
        void onSuccess(PartyRespone respone);
        void showError(boolean errorNetwork);
        void onChangedLike();
    }
}
