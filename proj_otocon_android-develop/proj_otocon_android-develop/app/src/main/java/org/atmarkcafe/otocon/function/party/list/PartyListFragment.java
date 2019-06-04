package org.atmarkcafe.otocon.function.party.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RadioGroup;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentListPartyBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.extesion.PartyListAdapter;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchDialog;
import org.atmarkcafe.otocon.function.home.items.ItemParty;
import org.atmarkcafe.otocon.function.party.LikePartyListFragment;
import org.atmarkcafe.otocon.function.party.PartyDetailFragment;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;

public class PartyListFragment extends OtoconBindingFragment<FragmentListPartyBinding> implements KeyExtensionUtils, RadioGroup.OnCheckedChangeListener,
        PartyListView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public PartyListPrenter presenter = new PartyListPrenter(this);


    public PartyParams params = new PartyParams();
    private boolean showToolBar = true;

    private boolean showIcSearch = false;

    private boolean enableCallApi = true;

    private String textNoData = "";
    private String formartNumberCount = "";

    public void setTextNoData(String textNoData) {
        this.textNoData = textNoData;
    }

    public void setFormartNumberCount(String formartNumberCount) {
        this.formartNumberCount = formartNumberCount;
    }

    public void setEnableCallApi(boolean enableCallApi) {
        this.enableCallApi = enableCallApi;
    }

    public void setShowIcSearch(boolean showIcSearch) {
        this.showIcSearch = showIcSearch;
    }

    public void setShowToolBar(boolean showToolBar) {
        this.showToolBar = showToolBar;
    }


    PartyListAdapter adapter = new PartyListAdapter(new PartyListAdapter.PartyListLoadListener() {
        @Override
        public void onLoadMore(int currentPage) {
            params.setPage(currentPage + 1);
            presenter.onExecute(getActivity(), presenter.ACTION_GET_LIST, params);

        }

        // Item clicklistener
        @Override
        public void onClickLike(Party party) {
            if (DBManager.isLogin(getContext())) {
                currentParty = party;
                presenter.likeParty(getContext(), party);
            } else {
                gotoLogin(new OtoconFragmentListener() {
                    @Override
                    public void onHandlerReult(int status, Bundle extras) {
                        // reload  api
                        adapter.clear();
                        params.setPage(1);
                        presenter.onExecute(getActivity(), 0, params);
                    }
                });
            }
        }

        @Override
        public void onClickItem(Party party) {
            currentParty = party;
            FragmentUtils.replace(getActivity(), PartyDetailFragment.create(party.getId(), new OtoconFragment.OtoconFragmentListener() {
                @Override
                public void onHandlerReult(int status, Bundle extras) {

                    FragmentUtils.backToTop(getActivity());
                    Fragment currentFragment = FragmentUtils.getFragment(getActivity());
                    if (currentFragment instanceof MainFragment){
                        ((MainFragment)currentFragment).handelRegisterSuccess(status, extras);
                    }
                }
            }), true);
        }
    });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null) {
            setArguments(new Bundle());
        }

        if (getArguments().containsKey(KEY_ADVANCED_SEARCH)) {
            params = new Gson().fromJson(getArguments().getString(KEY_ADVANCED_SEARCH), PartyParams.class);
//            params.updateEventDate();
        }else{
            params.setCheckSlot("1");
        }

        params.setPage(1);
        params.setLimit(20);

    }

    @Override
    public int layout() {
        return R.layout.fragment_list_party;
    }

    @Override
    public void onCreateView(FragmentListPartyBinding mBinding) {
        if (getArguments() != null && getArguments().containsKey(KEY_SHOW_BACK)) {
            mBinding.icBack.setVisibility(getArguments().getBoolean(KEY_SHOW_BACK, true)?View.VISIBLE:View.GONE);
        }

        if (getArguments() != null && getArguments().containsKey(KEY_TITLE)) {
            mBinding.title.setText(getArguments().getString(KEY_TITLE));
        }

        mBinding.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        adapter.setRecyclerView(mBinding.recyclerview);
        mBinding.recyclerview.setAdapter(adapter);
        mBinding.rgStypeShow.setOnCheckedChangeListener(this);
        mBinding.swiperefresh.setOnRefreshListener(this);

        setCount(0);

        if (enableCallApi) {
            presenter.onExecute(getActivity(), 0, params);
        }

        if (!textNoData.isEmpty()) {
            mBinding.tvNodata.setText(textNoData);
        }

        mBinding.icSearch.setOnClickListener(this);

        mBinding.icSearch.setVisibility(showIcSearch ? View.VISIBLE : View.GONE);
        mBinding.toolbar.setVisibility(showToolBar ? View.VISIBLE : View.GONE);

        mBinding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setCount(int count) {
        if (formartNumberCount.isEmpty()) {
            formartNumberCount = getString(R.string.result_search);
        }

        viewDataBinding.txtCount.setText(String.format(formartNumberCount, count));
    }

    @Override
    public void onClick(View v) {
        //TODO click to search
        // set to params
        //
        // TODO
        AdvancedSearchDialog advancedSearchDialog = new AdvancedSearchDialog(getContext(), this.params,
                new AdvancedSearchDialog.OnAdvancedSearcResultListener() {
                    @Override
                    public void onSendResult(int type, SearchDefault value) {
                        // FIXME
                        PartyListFragment.this.params.updateFromAdvancedSearch(value);
                        // udpate params
                        onRefresh();
                    }
                });
        advancedSearchDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // login

        // party detail

        // other
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        adapter.setShowType(checkedId == R.id.rb_list ? ItemParty.ShowType.list : ItemParty.ShowType.grid);
    }

    // Refesh
    @Override
    public void onRefresh() {
        viewDataBinding.swiperefresh.setRefreshing(false);
        params.setPage(1);
        params.setLimit(20);
//        adapter.clear();

        presenter.onExecute(getActivity(), 0, params);
    }


    // View
    @Override
    public void showPopup(String title, String message) {
        adapter.setLoading(false);
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void success(PartyListResponse response) {
        adapter.setLoading(false);
        //infiniteScrollListener.setLoading(true);
        if (params.getPage() == 1) {
            adapter.clear();
        }
        if (response != null) {
            //adapter.setTotalParty(response.total);
            adapter.updateData(response.total, response.getDataList());
            setCount(adapter.getTotalParty());

            viewDataBinding.tvNodata.setVisibility(adapter.getTotalParty() == 0 ? View.VISIBLE : View.GONE);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress(boolean show) {
        viewDataBinding.tvNodata.setVisibility(View.GONE);
        viewDataBinding.loading.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(PartyListResponse response) {
        //
    }

    public Party currentParty;

    @Override
    public void successLike(OnResponse response) {
        if (this instanceof LikePartyListFragment) {
            if (adapter.remove(currentParty)) {
                setCount(adapter.getTotalParty());
                if (adapter.getTotalParty() == 0) {
                    viewDataBinding.tvNodata.setVisibility(View.VISIBLE);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
