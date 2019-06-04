package org.atmarkcafe.otocon.function.menu.contactus;


import org.atmarkcafe.otocon.model.response.ContactUsResponse;
import org.atmarkcafe.otocon.model.response.RegisterResponse;

public interface ComfirmUsActivivyContact {
    public interface Presenter {


    }

    public interface View {
        void finishScreen();

        void sucsess(ContactUsResponse response);

        void showOtherErorrMessage(boolean isNotConnectToServer);

        void showDialogTerm(String message);
    }
}
