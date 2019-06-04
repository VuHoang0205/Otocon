package org.atmarkcafe.otocon.function.home;

import android.content.Context;

import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.RecommendSlider;

import org.atmarkcafe.otocon.model.response.PartyRespone;

public class HomeContract {
    interface Presenter {
        void loadRecommendSliders(Context context);
        void updateRecommendSliderDefault();
        void likeParty(Party party);
    }

    interface View {
        void initRecommendSlider();

        void showAdvancedSearchDialog();
        void clickRecommendSlider(RecommendSlider recommendSlider);

        void refresh();

        void onSuccess(PartyRespone respone);

        void onChangedLike();

        void showError(boolean errorNetwork);
    }
}
