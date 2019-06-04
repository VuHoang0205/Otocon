package org.atmarkcafe.otocon.dialog;

import org.atmarkcafe.otocon.model.response.Prefecture;

public class PrefectureSelectionContract {
    public interface Presenter{
        void onLoad();
        void onClose();
    }

    public interface View{
        void close(Prefecture prefecture);
    }
}
