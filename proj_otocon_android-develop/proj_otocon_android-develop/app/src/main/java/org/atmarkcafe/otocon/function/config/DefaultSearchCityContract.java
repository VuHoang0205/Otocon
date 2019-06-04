package org.atmarkcafe.otocon.function.config;

import android.content.Context;

import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchCityExpandableGroup;

import java.util.List;

public class DefaultSearchCityContract {
    public interface Presenter {
        void onLoadArea(Context context);
        void onResetCheckCity();
        void onGetCheckCity();
        void onSetCheckCity(String cityCheck);
    }

    public interface View {
        void showProgress(boolean isShow);
        void success(List<AdvancedSearchCityExpandableGroup> groupList);
        void showDialogError(boolean errorNetwork);
        void getCheckCity(String idList);
    }
}
