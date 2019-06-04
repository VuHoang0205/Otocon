package org.atmarkcafe.otocon.dialog;

public class SelectionContact {
    public interface Presenter {
        void onClose();
    }

    public interface View {
        void close(String selected);
    }
}
