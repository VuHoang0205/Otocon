package org.atmarkcafe.otocon.function.beacon;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentGpsBeaconBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.extesion.PartyListAdapter;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchDialog;
import org.atmarkcafe.otocon.function.home.HomeFragment;
import org.atmarkcafe.otocon.function.home.SearchConsiderPresenter;
import org.atmarkcafe.otocon.function.home.items.ItemParty;
import org.atmarkcafe.otocon.function.login.LoginFragment;
import org.atmarkcafe.otocon.function.party.PartyDetailFragment;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.PartyRespone;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.FragmentUtils;

import onactivityresult.ActivityResult;
import onactivityresult.OnActivityResult;

public class GPSBeaconFragment extends OtoconBindingFragment<FragmentGpsBeaconBinding> implements GPSBeaconContract.View, SwipeRefreshLayout.OnRefreshListener, GpsManager.LocationCallbackListener {

    GpsManager gpsManager = new GpsManager(null);
    FragmentGpsBeaconBinding mBinding;
    SearchConsiderPresenter mSearchConsiderPresenter;
    GPSBeaconPresenter mPresenter;

    PartyListAdapter partyListAdapter;

    {
        partyListAdapter = new PartyListAdapter(new PartyListAdapter.PartyListLoadListener() {
            @Override
            public void onLoadMore(int currentPage) {
                if (partyListAdapter.size() < partyListAdapter.getTotalParty()) {
                    mParams.setPage(PAGE + currentPage);
                    mPresenter.loadParty(mParams);
                }
            }

            @Override
            public void onClickLike(Party party) {
                if (DBManager.isLogin(getContext())) {
                    mPresenter.likeParty(party);
                } else {
                    gotoLogin(new OtoconFragment.OtoconFragmentListener() {
                        @Override
                        public void onHandlerReult(int status, Bundle extras) {
                            //TODO
                            // reload data

                        }
                    });

                }
            }

            @Override
            public void onClickItem(Party party) {
                FragmentUtils.replace(getActivity(), PartyDetailFragment.create(party.getId(), new OtoconFragment.OtoconFragmentListener() {
                    @Override
                    public void onHandlerReult(int status, Bundle extras) {
                        // TODO reload
                        Fragment currentFragment = FragmentUtils.getFragment(getActivity());
                        if (currentFragment instanceof MainFragment){
                            ((MainFragment)currentFragment).handelRegisterSuccess(status, extras);
                        }
                    }
                }), true);

            }
        });
    }

    private PartyParams mParams;
    private boolean isLogin;

    private final int LIMIT_ITEM = 20;
    private final int PAGE = 1;
    public static final int REQUEST_CODDE_LIKE = 700;
    public static final int REQUEST_CODDE_ITEM = 701;


    @Override
    public int layout() {
        return R.layout.fragment_gps_beacon;
    }

    @Override
    public void onCreateView(FragmentGpsBeaconBinding mBinding) {


        this.mBinding = mBinding;
        mPresenter = new GPSBeaconPresenter(getContext(), this);
        isLogin = DBManager.isLogin(getContext());

        if (DBManager.getParamsSearchGPS(getContext()) != null){
            mParams = DBManager.getParamsSearchGPS(getContext());
        } else {
            mParams = new PartyParams();
            mParams.setCheckSlot("1");
            mParams.setLimit(LIMIT_ITEM);
            mParams.setPage(PAGE);
        }

        initSearchDay();
        initUI();
        mBinding.swiperefresh.setOnRefreshListener(this);

        partyListAdapter.setRecyclerView(mBinding.homeRecyclerview);
        mBinding.homeRecyclerview.setAdapter(partyListAdapter);

        mBinding.setVariable(BR.presenter, mPresenter);

        gpsManager.addLocationCallbackListener(this);
        gpsManager.requestLocation(getActivity());


    }

    private HomeFragment.OnChangedSearchConditionListener mSearchConditionListener = new HomeFragment.OnChangedSearchConditionListener() {

        @Override
        public void onChangedDate(String dates) {
            mParams.setEventDate(dates);
            callApiReload();
        }

        @Override
        public void onChangedCity(String cities) {

        }
    };

    private void initUI() {

        mBinding.homeAdvancedSearch.setOnClickListener(v -> {
            AdvancedSearchDialog advancedSearchDialog = new AdvancedSearchDialog(getContext(), mParams,
                    new AdvancedSearchDialog.OnAdvancedSearcResultListener() {
                        @Override
                        public void onSendResult(int type, SearchDefault value) {
                            // FIXME
                            mParams.updateFromAdvancedSearch(value);
                            DBManager.saveAdvancedGPS(getContext(), mParams);
                            // udpate params
                            onRefresh();
                        }
                    });
            advancedSearchDialog.setShowArea(false).show();
        });

        mBinding.partyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partyListAdapter.setShowType(ItemParty.ShowType.list);

                updateTypeScreen();
            }
        });

        mBinding.partyGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partyListAdapter.setShowType(ItemParty.ShowType.grid);

                updateTypeScreen();
            }
        });

        // TODO: update for recyclerview layout
        mBinding.homeRecyclerview.setAdapter(partyListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLogin != DBManager.isLogin(getContext())) {
            callApiReload();
        }
    }

    @Override
    public void onReload() {
        super.onReload();
        if (mParams.getGps() != null) callApiReload();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActivityResult.onResult(requestCode, resultCode, data).into(this);
    }

    @OnActivityResult(requestCode = REQUEST_CODDE_LIKE, resultCodes = LoginFragment.RESULT_LOGIN_SUCCESS)
    public void onLoginSuccess() {
        //Todo login scccess
    }

    @OnActivityResult(requestCode = REQUEST_CODDE_ITEM, resultCodes = LoginFragment.RESULT_LOGIN_SUCCESS)
    public void onDetailParty() {
        //Todo DetailParty example
    }

    private void initSearchDay() {
        mSearchConsiderPresenter = new SearchConsiderPresenter(mSearchConditionListener);

        mBinding.dayOfWeekViewPager.setAdapter(mSearchConsiderPresenter.mDayOfWeekAdapter);

        mBinding.dayOfWeekArrowLeft.setOnClickListener(v -> {
            int index = mBinding.dayOfWeekViewPager.getCurrentItem();
            if (index > 0) {
                mBinding.dayOfWeekViewPager.setCurrentItem(index - 1);
            }
        });
        mBinding.dayOfWeekArrowRight.setOnClickListener(v -> {
            int index = mBinding.dayOfWeekViewPager.getCurrentItem();
            if (index <= mSearchConsiderPresenter.mDayOfWeekAdapter.getCount() - 1) {
                mBinding.dayOfWeekViewPager.setCurrentItem(index + 1);
            }
        });
        mBinding.dayOfWeekViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    mBinding.dayOfWeekArrowLeft.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getContext(), R.color.color989898))
                    );
                } else {
                    mBinding.dayOfWeekArrowLeft.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getContext(), R.color.color_F7648F))
                    );
                }
                if (i == mSearchConsiderPresenter.mDayOfWeekAdapter.getCount() - 1) {
                    mBinding.dayOfWeekArrowRight.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getContext(), R.color.color989898))
                    );
                } else {
                    mBinding.dayOfWeekArrowRight.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getContext(), R.color.color_F7648F))
                    );
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }

    @Override
    public void onSuccess(PartyRespone respone) {
        partyListAdapter.setLoading(false);
        if (respone != null) {
            if (mParams.getPage() == 1){
                partyListAdapter.clear();
            }
            partyListAdapter.updateData(respone.total, respone.getDatas());
        }

        mPresenter.resultSearch.set(partyListAdapter.getTotalParty());
        mBinding.tvNodata.setVisibility(partyListAdapter.getTotalParty() == 0 ? View.VISIBLE : View.GONE);
        partyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(boolean errorNetwork) {
        partyListAdapter.setLoading(false);
        if (errorNetwork) {
            new PopupMessageErrorDialog(getContext()).show();
        } else {
            new PopupMessageErrorDialog(getContext(), getString(R.string.error_title_Connect_server_fail), getString(R.string.error_content_Connect_server_fail)).show();
        }
    }

    @Override
    public void onChangedLike() {
        partyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mBinding.swiperefresh.setRefreshing(false);
        callApiReload();
    }

    private void callApiReload() {
        mParams.setPage(PAGE);
        mPresenter.loadParty(mParams);
    }

    private void updateTypeScreen() {

        boolean isList = partyListAdapter.getShowType() == ItemParty.ShowType.list;
        mBinding.partyList.setColorFilter(isList ? Color.BLACK : ContextCompat.getColor(getContext(), R.color.color989898));
        mBinding.partyGrid.setColorFilter(!isList ? Color.BLACK : ContextCompat.getColor(getContext(), R.color.color989898));
    }

    @Override
    public void locationChange(double latitude, double longitude) {
        mBinding.layoutLogoLocation.setVisibility(View.GONE);
        gpsManager.removeLocationCallbackListener();
        mParams.setGps(latitude + "," + longitude);
        callApiReload();

    }
}
