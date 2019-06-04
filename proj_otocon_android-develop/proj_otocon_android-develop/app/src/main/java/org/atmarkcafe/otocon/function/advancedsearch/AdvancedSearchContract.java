package org.atmarkcafe.otocon.function.advancedsearch;

import android.content.Context;

public class AdvancedSearchContract {
    interface Presenter {
        void updateArea(Context ctx, String area);
        void updateEventDate(Context ctx, String eventDate);
        void updateDayOfWeek(Context ctx, String dayOfWeek);
        void updateGender(Context ctx, int gender);
        void updateAge(Context ctx, int age);
        void updateAgeOfOpponent(Context ctx, String ageOfOpponent);
    }

    interface View {
        void initView();
    }
}
