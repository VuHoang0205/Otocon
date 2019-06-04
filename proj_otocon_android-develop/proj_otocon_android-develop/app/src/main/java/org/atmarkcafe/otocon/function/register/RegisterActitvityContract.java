package org.atmarkcafe.otocon.function.register;

import org.atmarkcafe.otocon.model.parameter.RegisterParams;

public interface RegisterActitvityContract {
    public interface Presenter {


    }

    public interface View {
        void setEnableBack(boolean enable);
        void success(RegisterParams params);

        void showErorrMessage(String title, String message);
        void prefectureError(String[] message);

    }
}
