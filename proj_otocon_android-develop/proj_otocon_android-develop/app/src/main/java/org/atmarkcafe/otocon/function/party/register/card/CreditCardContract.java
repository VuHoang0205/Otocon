package org.atmarkcafe.otocon.function.party.register.card;

import org.atmarkcafe.otocon.model.CreditCard;

public class CreditCardContract {
    public interface View{
        void showYearSelection();
        void showMonthSelection();
        void goStep(int session, CreditCard item);

        void showError(String title, String message);
        void showProgessBar(boolean show);
//        void showError(boolean networkError);
    }
    public interface Presenter {
        void loadCreditCard();
        void confirmCard(CreditCard card);
    }
}
