package org.atmarkcafe.otocon.function.advancedsearch;

import android.content.Context;

import java.util.List;

public class AdvancedSearchCityContract {
    public interface Presenter {
        void onLoadArea(Context context);
        void onSetCheckCity(String idList);
        void onResetCheckCity();
        void onGetCheckCity();
    }

    public interface View {
        void showProgress(boolean isShow);
        void success(List<AdvancedSearchCityExpandableGroup> groupList);
        void showDialogError();
        void getCheckCity(String idList);
    }
}
