package org.atmarkcafe.otocon.function.menu;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.GroupAdapter;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.databinding.ActivityMenuNewBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.menu.item.MenuNewItem;
import org.atmarkcafe.otocon.model.menunew.MenuNewModel;
import org.atmarkcafe.otocon.model.menunew.MenuNewRespone;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.utils.LogUtils;
import org.atmarkcafe.otocon.view.InfiniteScrollListener;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class MenuNewFragment extends OtoconBindingFragment<ActivityMenuNewBinding> implements MVPExtension.View<OnResponse>, SwipeRefreshLayout.OnRefreshListener {
    private MenuNewPresenter presenter = new MenuNewPresenter(this);

    private MenuAdapter groupAdapter = new MenuAdapter();

    @Override
    public int layout() {
        return R.layout.activity_menu_new;
    }

    @Override
    public void onCreateView(ActivityMenuNewBinding binding) {

        binding.swiperefresh.setOnRefreshListener(this);

        binding.setPresnter(presenter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvListNew.setLayoutManager(linearLayoutManager);
        infiniteScrollListener.setLoading(false);
        infiniteScrollListener.setLinearLayoutManager(linearLayoutManager);
        binding.rvListNew.addOnScrollListener(infiniteScrollListener);
        binding.rvListNew.setAdapter(groupAdapter);
        infiniteScrollListener.setCurrentPage(1);
        presenter.setPage(1).onExecute(getActivity(), MenuNewPresenter.LOAD_LIST_NEW, null);
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            if (getTargetFragment() != null) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
            }
        });

    }

    public void onRefresh() {
        viewDataBinding.swiperefresh.setRefreshing(false);
        groupAdapter.clear();
        groupAdapter.notifyDataSetChanged();

        infiniteScrollListener.setCurrentPage(1);
        presenter.setPage(1).onExecute(getActivity(), MenuNewPresenter.LOAD_LIST_NEW, null);
    }

    private MenuNewItem.OnItemMenuNewListener itemMenuNewListener = new MenuNewItem.OnItemMenuNewListener() {
        @Override
        public void gotoNewDetail(MenuNewModel model) {
            onClickNew(model, new OtoconFragmentListener() {
                @Override
                public void onHandlerReult(int status, Bundle extras) {
                    // TODO reload
                }
            });
        }

        @Override
        public void onReadNew(MenuNewModel model) {
            // call api for updated read
            if (!model.isReaded()) {
                presenter.onExecute(getActivity(), MenuNewPresenter.MARK_READ, model);
            }
        }
    };

    // VIEW
    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void success(OnResponse response) {
        if (response instanceof MenuNewRespone) {
            if (((MenuNewRespone) response).getTotal() == 0) {
                viewDataBinding.tvNodata.setVisibility(View.VISIBLE);
            } else {
                groupAdapter.addData(((MenuNewRespone) response).getData(), getActivity(), itemMenuNewListener);
                infiniteScrollListener.setLoading(true);
            }
            if (groupAdapter.getItemCount() == ((MenuNewRespone) response).getTotal()) {
                infiniteScrollListener.setLoading(false);
            } else {
                infiniteScrollListener.setLoading(true);
            }

        } else {

            // READ
            groupAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress(boolean show) {
        viewDataBinding.tvNodata.setVisibility(View.GONE);
        viewDataBinding.loadingLayout.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(OnResponse response) {

    }

    private InfiniteScrollListener infiniteScrollListener = new InfiniteScrollListener() {
        @Override
        public void onLoadMore(int current_page) {
            infiniteScrollListener.setLoading(false);
            presenter.setPage(1 + current_page).onExecute(getActivity(), MenuNewPresenter.LOAD_LIST_NEW, null);
        }
    };
}

class MenuAdapter extends GroupAdapter {
    List<MenuNewItem> datas = new ArrayList<>();

    @Override
    public void clear() {
        datas.clear();
        super.clear();
    }

    public void addData(List<MenuNewModel> menuNewRespone, Context context, MenuNewItem.OnItemMenuNewListener listener) {


        for (MenuNewModel menuNewModel : menuNewRespone) {
            MenuNewItem item = new MenuNewItem(menuNewModel, context, listener);

            if (datas.contains(item)) {
                int index = datas.indexOf(item);
                datas.remove(item);
                datas.add(index, item);
            } else {
                datas.add(item);
            }

        }

        update(datas);

    }
}
