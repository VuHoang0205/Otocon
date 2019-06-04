package org.atmarkcafe.otocon.function.party.register.card;

import android.content.Context;

import org.atmarkcafe.otocon.model.CreditCard;

public class ConfirmCardContract {
    public interface View {
        void onShowPopup(String title, String message);

        void success(String success_html);

        void showError(String title, String message);

        void showProgess(boolean show);
    }

    public interface Presenter {
        public void onExcute(Context context, String transaction_id, CreditCard card);
    }
}
