package org.atmarkcafe.otocon.function.login;

import android.content.Context;

import org.atmarkcafe.otocon.model.login.LoginParams;
import org.atmarkcafe.otocon.model.login.LoginRespone;

public class LoginContract {

    public interface Presenter {
        void onLoadData(LoginParams model, Context context);
    }

    interface LoginView {
        void submit();
        void forgotPassword();
        void register();
        void showExplainRegister();
        void showDataSuccess(LoginRespone loginRespone);
        void showMessageError();
        void showError(String title, String message);
    }
}
