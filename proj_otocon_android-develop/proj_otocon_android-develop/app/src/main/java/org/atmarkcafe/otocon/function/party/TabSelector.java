package org.atmarkcafe.otocon.function.party;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

public class TabSelector implements TabLayout.OnTabSelectedListener {
    private boolean isUserScroll = false;
    private boolean isUserTapSelected = false;
    private RecyclerView recyclerView;
    private Context context;
    private TabLayout tabLayouts;

    public TabSelector(RecyclerView recyclerView, Context context, TabLayout tabLayout) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.tabLayouts = tabLayout;

        // set lineSpacingExtra for tabitem
        for (int i = 0; i < tabLayouts.getTabCount(); i++) {
            LinearLayout tab = (LinearLayout) ((ViewGroup) tabLayouts.getChildAt(0)).getChildAt(i);
            TextView tabTextView = (TextView) tab.getChildAt(1);
            tabTextView.setLineSpacing(tabTextView.getContext().getResources().getDimensionPixelOffset(R.dimen.tab_line_spacing_extra), 1f);
        }
    }


    public void setUserScroll(boolean userScroll) {
        isUserScroll = userScroll;
    }

    public void setUserTapSelected(boolean userTapSelected) {
        this.isUserTapSelected = userTapSelected;
    }

    public boolean isUserTapSelected() {
        return isUserTapSelected;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        int position = tab.getPosition();

        if (!isUserScroll) {
            if (recyclerView.getLayoutManager() != null) {
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
            }
        }

        setTypeFontTablayout(tab.getPosition(), R.style.TextAppearance_Tabs_Selected);

        isUserScroll = false;
        isUserTapSelected = true;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTypeFontTablayout(tab.getPosition(), R.style.TextAppearance_Tabs_UnSelected);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void setTypeFontTablayout(int position, int style) {
        LinearLayout tabLayout = (LinearLayout) ((ViewGroup) tabLayouts.getChildAt(0)).getChildAt(position);
        TextView tabTextView = (TextView) tabLayout.getChildAt(1);
        tabTextView.setTextAppearance(context, style);
    }
}
