package org.atmarkcafe.otocon.function.party.register;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.model.RegisterPartyModel;
import org.atmarkcafe.otocon.model.response.CustomJoinEventResponse;
import org.atmarkcafe.otocon.model.response.UserJoinEventResponse;

public class ConfirmRegisterPartyContact {

    public interface Presenter{
        void onCallAPIRegisterParty(RegisterPartyModel params);
    }

    public interface View {
        void onShowPopup(String title, String message);

        void showProgress(boolean show);
        void customJoinSuccess(CustomJoinEventResponse response);
        void userJoinSuccess(UserJoinEventResponse response);
        void showErrorDialog(boolean errorNetwork);
    }
}
