package org.atmarkcafe.otocon.function.menu.contactus;

import org.atmarkcafe.otocon.model.parameter.ContactUsParams;
import org.atmarkcafe.otocon.model.parameter.RegisterParams;

public class ContactActivityContract {
    public interface Presenter {


    }

    public interface View {

        void showProgess(boolean show);

        void success(ContactUsParams params);

        void showDialog(String message);

        void showOtherErorrMessage(boolean isNotConnectToServer);

        void  prefectureError(String [] message);

    }
}
