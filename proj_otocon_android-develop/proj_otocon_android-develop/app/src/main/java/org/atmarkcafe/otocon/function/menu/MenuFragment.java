package org.atmarkcafe.otocon.function.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.BuildConfig;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.databinding.FragmentMenuBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.extesion.fragment.WebFragment;
import org.atmarkcafe.otocon.function.menu.contactus.ContactFragment;
import org.atmarkcafe.otocon.function.menu.item.MenuItem;
import org.atmarkcafe.otocon.function.party.NewPartyListFragment;
import org.atmarkcafe.otocon.model.response.MenuNotifyCountResponse;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.UnreadNotificationResponse;
import org.atmarkcafe.otocon.utils.FragmentUtils;

public class MenuFragment extends OtoconBindingFragment<FragmentMenuBinding> implements OnItemClickListener, MVPExtension.View<OnResponse> {

    private MenuFragmentPresenter presenter = new MenuFragmentPresenter(this);
    private int newCount = 0;
    private int newArrivalsCount = 0;
    private MenuItem menuNew, menuArrival;
    private GroupAdapter groupAdapter = null;

    @Override
    public int layout() {
        return R.layout.fragment_menu;
    }

    @Override
    public void onCreateView(FragmentMenuBinding binding) {
        newCount = 0;
        newArrivalsCount = 0;
        updateMenu();
    }

    private void updateMenu() {
        if (groupAdapter == null) {
            GroupAdapter groupAdapter = new GroupAdapter();
            groupAdapter.setOnItemClickListener(this);

            // Columns
            Section columnSection = new Section();
            columnSection.add(new MenuItem(0, R.drawable.ic_menu_venue, getString(R.string.venue_information)));
            columnSection.add(new MenuItem(1, R.drawable.ic_menu_benefits, getString(R.string.benefits_and_deals)));
            columnSection.add(new MenuItem(2, R.drawable.ic_menu_faq, getString(R.string.faq)));
            columnSection.add(new MenuItem(3, R.drawable.ic_menu_contact, getString(R.string.contact_us)));

            menuNew = new MenuItem(4, R.drawable.ic_menu_new, getString(R.string.news)).setNoti(newCount);
            columnSection.add(menuNew);

            menuArrival = new MenuItem(5, R.drawable.ic_menu_arrivals, getString(R.string.new_arrivals)).setNoti(newArrivalsCount);
            columnSection.add(menuArrival);

            groupAdapter.add(columnSection);

            RecyclerView recyclerView = viewDataBinding.list;
            recyclerView.setAdapter(groupAdapter);
        } else {
            menuNew.setNoti(newCount);
            menuArrival.setNoti(newArrivalsCount);
            groupAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onReload() {
        super.onReload();
        try {
            presenter.onExecute(getActivity(), presenter.ACTION_MENU_NOTIFY_COUNT, null);
            presenter.onExecute(getActivity(), presenter.ACTION_NEW_EVENT_COUNT, null);
        }catch (Exception e){}
    }

    @Override
    public void onItemClick(@NonNull Item item, @NonNull View view) {
        MenuItem menu = (MenuItem) item;
        Fragment f = null;
        Bundle extras = new Bundle();
        switch (menu.getIdMenu()) {
            case 3:
                f = new ContactFragment();
                break;
            case 0:
                f = new WebFragment();
                extras.putString(WebFragment.HEADER, getString(R.string.venue_information));
                extras.putString(WebFragment.URL, BuildConfig.LINK_MENU_VENUE);
                break;
            case 1:
                f = new WebFragment();
                extras.putString(WebFragment.HEADER, getString(R.string.benefits_and_deals));
                extras.putString(WebFragment.URL, BuildConfig.LINK_MENU_PONTA);
                break;
            case 2:
                f = new WebFragment();
                extras.putString(WebFragment.HEADER, getString(R.string.faq));
                extras.putString(WebFragment.URL, BuildConfig.LINK_MENU_FAQ);
                break;
            case 4:
                f = new MenuNewFragment();
                break;
            case 5:
                f = new NewPartyListFragment();
        }

        if (f == null) {
            PopupMessageErrorDialog.show(getContext(), null, getContext().getString(R.string.in_development), null);
        } else {
           // f.setTargetFragment(this, 0);
            f.setArguments(extras);
            FragmentUtils.replace(getActivity(), f, true);
        }
    }

    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void success(OnResponse response) {
        if (response instanceof MenuNotifyCountResponse) {
            if (getActivity() != null) {
                newCount = ((MenuNotifyCountResponse) response).getNewCount();
                updateMenu();
            }
        } else if (response instanceof UnreadNotificationResponse) {
            if (getActivity() != null) {
                newArrivalsCount = ((UnreadNotificationResponse) response).getUnreadCount();
                updateMenu();
            }
        }
    }

    @Override
    public void showProgress(boolean show) {
        viewDataBinding.setVariable(BR.showProgressBar, false);
    }

    @Override
    public void showMessage(OnResponse menuNotifyCountResponse) {

    }
}