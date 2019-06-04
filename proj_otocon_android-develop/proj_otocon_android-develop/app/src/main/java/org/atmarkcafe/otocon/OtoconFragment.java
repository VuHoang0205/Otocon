package org.atmarkcafe.otocon;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.function.login.LoginFragment;
import org.atmarkcafe.otocon.function.party.EarlyBenefitPartyListFragment;
import org.atmarkcafe.otocon.function.party.NextWeekPartyListFragment;
import org.atmarkcafe.otocon.function.party.PartyDetailFragment;
import org.atmarkcafe.otocon.function.party.SpecialDiscountPartyListFragment;
import org.atmarkcafe.otocon.function.party.list.PartyListFragment;
import org.atmarkcafe.otocon.model.RecommendSlider;
import org.atmarkcafe.otocon.model.menunew.MenuNewModel;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;

public class OtoconFragment extends Fragment implements KeyExtensionUtils {
    public interface OtoconFragmentListener {
        public void onHandlerReult(int status, Bundle extras);
    }

    public OtoconFragmentListener otoconFragmentListener;

    public void setOtoconFragmentListener(OtoconFragmentListener otoconFragmentListener) {
        this.otoconFragmentListener = otoconFragmentListener;
    }

    public void onClickSlide(RecommendSlider slider, OtoconFragmentListener listener) {
        OtoconFragment otoconFragment = fragmentFromAction(slider.getAction(), slider.getAdvancedSearch().getLink_label(), slider.getAdvancedSearch(), slider.getEventId(), slider.getType() == RecommendSlider.TYPE_SLIDER);

        if (otoconFragment != null) {
            otoconFragment.setOtoconFragmentListener(listener);
            FragmentUtils.replace(getActivity(), otoconFragment, true);
        }
    }

    public void onReload(){
    }

    public void onClickNew(MenuNewModel model, OtoconFragmentListener listener) {
        OtoconFragment otoconFragment = fragmentFromAction(model.getAction(), model.getTitleAction(), model.getAdvancedSearch(), model.getEvent_id());

        if (otoconFragment != null) {
            otoconFragment.setOtoconFragmentListener(listener);
            FragmentUtils.replace(getActivity(), otoconFragment, true);
        }
    }

    public OtoconFragment fragmentFromAction(int action, String title, PartyParams searchKey, String partId) {
        return fragmentFromAction(action, title, searchKey, partId, false);
    }

    public OtoconFragment fragmentFromAction(int action, String title, PartyParams searchKey, String partId, boolean isSlider) {
        OtoconFragment fragment = null;
        Bundle extras = new Bundle();
        switch (RecommendSlider.ActionEvent.pactory(action)) {
            case none:
                break;
            case link_to_list_event:

                fragment = new PartyListFragment();

                extras.putString(KEY_TITLE, title);
                searchKey.listValidation();
                extras.putString(KEY_ADVANCED_SEARCH, new Gson().toJson(searchKey));


                break;
            case link_to_event_detail:
                fragment = new PartyDetailFragment();
                extras.putString(PartyDetailFragment.PARTY_ID, partId);

                break;
            case party_next_week:
                fragment = new NextWeekPartyListFragment();
                extras.putBoolean(KEY_SHOW_ADVANCED_SEARCH, !isSlider);

                break;
            case special_discount:
                fragment = new SpecialDiscountPartyListFragment();
                extras.putBoolean(KEY_SHOW_BACK,true);
                extras.putBoolean(KEY_SHOW_ADVANCED_SEARCH, !isSlider);
                break;
            case benefit_early:

                fragment = new EarlyBenefitPartyListFragment();
                extras.putBoolean(KEY_SHOW_ADVANCED_SEARCH, !isSlider);
                break;
        }

        if (fragment != null) {
            fragment.setArguments(extras);
        }

        return fragment;
    }

    public void gotoLogin(OtoconFragmentListener listener) {
        FragmentUtils.replace(getActivity(), LoginFragment.create(listener), true);
    }
}
