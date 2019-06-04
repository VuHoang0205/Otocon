package org.atmarkcafe.otocon.function.forgotpassworld;

import android.content.Context;

public class ForgotPasswordContract {
    public interface Presenter {
        void onRequestResetPassword(Context context);
    }

    public interface View {
        void setEnableBack(boolean enable);
        void submit();
        void complete();
        void showCompletedLayout();
        void showErrorDialog(String title, String message);
    }
}
