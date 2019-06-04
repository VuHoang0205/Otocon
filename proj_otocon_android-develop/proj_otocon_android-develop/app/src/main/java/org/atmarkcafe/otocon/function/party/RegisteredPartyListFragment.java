package org.atmarkcafe.otocon.function.party;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentRegisteredPartyListBinding;
import org.atmarkcafe.otocon.model.Party;

public class RegisteredPartyListFragment extends OtoconBindingFragment<FragmentRegisteredPartyListBinding> {

    private FragmentRegisteredPartyListBinding mBinding;
    private RegisteredPartyPagerAdapter pagerAdapter;

    @Override
    public int layout() {
        return R.layout.fragment_registered_party_list;
    }

    @Override
    public void onCreateView(FragmentRegisteredPartyListBinding mBinding) {


        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                mBinding.registeredPartyViewPager.setCurrentItem(index);
                if (!(pagerAdapter.get(index) instanceof NoJoinedPartyListFragment)) {
                    pagerAdapter.get(index).loadFirstApi();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBinding.toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        for (Fragment f : getChildFragmentManager().getFragments()) {
            getChildFragmentManager().beginTransaction().remove(f).commit();
        }
        pagerAdapter = new RegisteredPartyPagerAdapter(getChildFragmentManager());
        mBinding.registeredPartyViewPager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();

    }

    class RegisteredPartyPagerAdapter extends FragmentPagerAdapter {
        JoinedPartyListFragment[] fragments;

        public RegisteredPartyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new JoinedPartyListFragment[2];

            fragments[0] = new NoJoinedPartyListFragment();
            fragments[0].setFormartNumberCount(getString(R.string.before_party_list_format_number_count));
            fragments[0].setTextNoData(getString(R.string.before_party_list_no_data));

            fragments[1] = new JoinedPartyListFragment();
            fragments[1].setEnableCallApi(false);
            fragments[1].setFormartNumberCount(getString(R.string.joined_party_list_format_number_count));
            fragments[1].setTextNoData(getString(R.string.joined_party_list_no_data));
        }

        public JoinedPartyListFragment get(int i) {
            return fragments[i];
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
