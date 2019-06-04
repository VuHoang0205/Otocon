package org.atmarkcafe.otocon.function.config;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import org.atmarkcafe.otocon.IntroFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogDefaultSearchCityBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchCityExpandableGroup;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.FragmentUtils;

import java.util.List;

public class DefaultSearchCityFragment extends OtoconBindingFragment<DialogDefaultSearchCityBinding> implements DefaultSearchCityContract.View {

    private DefaultSearchCityPresenter presenter;
    private boolean BACK_CITY = false;

    @Override
    public int layout() {
        return R.layout.dialog_default_search_city;
    }

    @Override
    public void onCreateView(DialogDefaultSearchCityBinding binding) {

      // clear data
        if (!BACK_CITY){
            SearchDefault.getInstance().clear();
            presenter = new DefaultSearchCityPresenter(this);
            presenter.onLoadArea(getContext());
        }

        binding.reset.setOnClickListener(view -> {
            presenter.onResetCheckCity();
        });

        binding.submit.setOnClickListener((view) -> {
            presenter.onGetCheckCity();
        });

    }

    @Override
    public void showProgress(boolean isShow) {
        viewDataBinding.loadingLayout.getRoot().setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void success(List<AdvancedSearchCityExpandableGroup> groupList) {
        if (getActivity() != null) {
            GroupAdapter groupAdapter = new GroupAdapter();
            groupAdapter.addAll(groupList);
            viewDataBinding.recyclerview.setAdapter(groupAdapter);

            // display set check
            String cityCheck = SearchDefault.getInstance().getCitys();
            if (!cityCheck.equals(""))
                presenter.onSetCheckCity(cityCheck);
        }
    }

    @Override
    public void showDialogError(boolean errorNetwork) {
        if (getActivity() != null) {
            if (errorNetwork) {
                new PopupMessageErrorDialog(getContext()).setPopupMessageErrorListener(null).show();
            } else {
                new PopupMessageErrorDialog(getContext(), getContext().getString(R.string.error_title_Connect_server_fail), getContext().getString(R.string.error_content_Connect_server_fail))
                        .setPopupMessageErrorListener(null)
                        .show();
            }
        }
    }

    @Override
    public void getCheckCity(String idList) {
        if (idList.equals("")) {
            viewDataBinding.tvError.setVisibility(View.VISIBLE);
        } else {
            // TODO SAVE Check to db
            SearchDefault.getInstance().setCitys(idList);
            SearchDefault.getInstance().save();
            viewDataBinding.tvError.setVisibility(View.GONE);

            gotoDefaltAge();
        }
    }

    private void gotoDefaltAge() {
        DefaultSearchAgeFragment defaultSearchAgeDialog = new DefaultSearchAgeFragment();
        defaultSearchAgeDialog.setListener(new DefaultSeachListener() {
            @Override
            public void onBack() {
                // reload
                presenter.onLoadArea(getContext());
            }

            @Override
            public void onComplete() {

            }
        });
        BACK_CITY = true;

        FragmentUtils.replaceChild(getStoreChildFrgementManager(),R.id.frame,  defaultSearchAgeDialog, true);
    }
}
