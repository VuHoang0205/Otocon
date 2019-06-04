package org.atmarkcafe.otocon.api;

import android.content.Context;

public interface MVPExtension {
    public interface View<Response> {
        void showPopup(String title, String message);

        void success(Response response);

        void showProgress(boolean show);

        void showMessage(Response response);
    }

    public interface Presenter<Params> {
        void onExecute(Context context, int action, Params params);
    }
}
