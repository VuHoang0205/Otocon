package org.atmarkcafe.otocon.function.register;


import org.atmarkcafe.otocon.model.response.RegisterResponse;

public interface ComfirmAcitivyContact {
    public interface Presenter {


    }

    public interface View {
        void setEnableBack(boolean enable);
        void finishScreen();

        void sucsess(RegisterResponse response);
        void showErorrMessage(String title, String message);
    }
}
