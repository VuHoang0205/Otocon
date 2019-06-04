package org.atmarkcafe.otocon.function.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentHomeBinding;
import org.atmarkcafe.otocon.dialog.PartyRegisterSuccessDialog;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.extesion.PartyListAdapter;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchDialog;
import org.atmarkcafe.otocon.function.home.items.HomeAreaItem;
import org.atmarkcafe.otocon.function.home.items.ItemParty;
import org.atmarkcafe.otocon.function.home.items.RecommendItem;
import org.atmarkcafe.otocon.function.login.LoginFragment;
import org.atmarkcafe.otocon.function.mypage.MyPageMenuFragment;
import org.atmarkcafe.otocon.function.mypage.profile.SelfIntroductionCardInfoFragment;
import org.atmarkcafe.otocon.function.party.PartyDetailFragment;
import org.atmarkcafe.otocon.function.party.RegisteredPartyListFragment;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.response.PartyRespone;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;

import org.atmarkcafe.otocon.model.RecommendSlider;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.StringUtils;
import org.atmarkcafe.otocon.view.ItemDecoration;

import onactivityresult.ActivityResult;


public class HomeFragment extends OtoconBindingFragment<FragmentHomeBinding> implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener, KeyExtensionUtils {
    public static HomeFragment newInstance(MainFragment mainFragment) {
        HomeFragment fragment = new HomeFragment();
        fragment.mainFragment = mainFragment;
        return fragment;
    }

    MainFragment mainFragment;

    private HomePresenter mPresenter;
    PartyListAdapter partyListAdapter = new PartyListAdapter(new PartyListAdapter.PartyListLoadListener() {
        @Override
        public void onLoadMore(int currentPage) {
            if (partyListAdapter.size() < partyListAdapter.getTotalParty()) {
                mPresenter.callApi(LIMIT_ITEM, PAGE + currentPage);
            }
        }

        @Override
        public void onClickItem(Party party) {
            FragmentUtils.replace(getActivity(), PartyDetailFragment.create(party.getId(), otoconFragmentListener), true);
        }

        @Override
        public void onClickLike(Party party) {
            if (DBManager.isLogin(getContext())) {
                mPresenter.likeParty(party);
            } else {
                LoginFragment fLogin = new LoginFragment();
                fLogin.setOtoconFragmentListener((status, extras) -> refresh());
                FragmentUtils.replace(getActivity(), fLogin, true);
            }
        }
    });

    private boolean isLogedIn = false;

    public static final int REQUEST_CODDE_LIKE = 10;
    public static final int REQUEST_CODDE_ITEM = 11;

    private final int LIMIT_ITEM = 20;
    private final int PAGE = 1;

    private SearchConsiderPresenter mSearchConsiderPresenter;

    private SearchDefault mSearchDefault;

    @Override
    public void onRefresh() {
        viewDataBinding.swiperefresh.setRefreshing(false);
        callFirstApi();
    }


    public interface OnChangedSearchConditionListener {
        void onChangedDate(String dates);

        void onChangedCity(String cities);
    }


    @Override
    public int layout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreateView(FragmentHomeBinding mBinding) {

        mPresenter = new HomePresenter(getContext(), this);
        mBinding.setPresenter(mPresenter);

        partyListAdapter.setRecyclerView(mBinding.homeRecyclerview);

        mSearchDefault = SearchDefault.getInstance();
        initUI();
        mBinding.swiperefresh.setOnRefreshListener(this);

        isLogedIn = DBManager.isLogin(getContext());
        mPresenter.loggedin.set(isLogedIn);

        callFirstApi();

    }

    private void updateTypeScreen() {

        boolean isList = partyListAdapter.getShowType() == ItemParty.ShowType.list;
        viewDataBinding.partyList.setColorFilter(isList ? Color.BLACK : ContextCompat.getColor(getContext(), R.color.color989898));
        viewDataBinding.partyGrid.setColorFilter(!isList ? Color.BLACK : ContextCompat.getColor(getContext(), R.color.color989898));
    }

    boolean requestReLogin = false;
    public void requestReLogin() {
        if (!isAdded()){
            requestReLogin = true;
            return;
        }
        isLogedIn = DBManager.isLogin(getContext());
        mPresenter.loggedin.set(isLogedIn);
        if (!isLogedIn) {
            viewDataBinding.homeLogin.callOnClick();
        }
        requestReLogin = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (requestReLogin) requestReLogin();
        if (handleRegisterSuccess) handelRegisterSuccess(status, extras);
    }

    private void initUI() {
        viewDataBinding.homeLogin.setOnClickListener(v -> {
                    if (DBManager.isLogin(v.getContext())) {

                        MyPageMenuFragment menuFragment = new MyPageMenuFragment();
                        menuFragment.setOtoconFragmentListener(new OtoconFragmentListener() {
                            @Override
                            public void onHandlerReult(int status, Bundle extras) {
                                // TODO tab 4
                                mainFragment.gotoTab(3);
                            }
                        });
                        FragmentUtils.replace(getActivity(), menuFragment, true);
                        // ExtensionActivity.start(HomeFragment.this, ExtensionActivity.Action.mypage, new Bundle());
                    } else {
                        LoginFragment fLogin = new LoginFragment();
                        fLogin.setOtoconFragmentListener((status, extras) -> refresh());
                        FragmentUtils.replace(getActivity(), fLogin, true);
                    }
                }
        );

        viewDataBinding.homeAdvancedSearch.setOnClickListener(v -> showAdvancedSearchDialog());

        initSliderLayout(); // slider layout
        initSearchConditionLayout(); // search condition layout


        viewDataBinding.partyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partyListAdapter.setShowType(ItemParty.ShowType.list);
                updateTypeScreen();
            }
        });

        viewDataBinding.partyGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partyListAdapter.setShowType(ItemParty.ShowType.grid);
                updateTypeScreen();
            }
        });

        // TODO: update for recyclerview layout
        partyListAdapter.setShowType(ItemParty.ShowType.list);


        viewDataBinding.appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            // enable swipe refreshing layout when appbar is full expanded
            viewDataBinding.swiperefresh.setEnabled(verticalOffset == 0);
        });
    }

    private void initSliderLayout() {
        mPresenter.loadRecommendSliders(getActivity());

        // init recommend item
        viewDataBinding.homeSlider.recommendRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        viewDataBinding.homeSlider.recommendRecyclerView.addItemDecoration(
                new ItemDecoration(0, 1.5f, 0, 0).setFirst(0, 0, 0, 0)
        );
    }

    private void initSearchConditionLayout() {
        mSearchConsiderPresenter = new SearchConsiderPresenter(mSearchConditionListener);

        mSearchConsiderPresenter.resetData(getContext());

        viewDataBinding.homeSearchCondition.dayOfWeekViewPager.setAdapter(mSearchConsiderPresenter.mDayOfWeekAdapter);

        viewDataBinding.homeSearchCondition.dayOfWeekArrowLeft.setOnClickListener(v -> {
            int index = viewDataBinding.homeSearchCondition.dayOfWeekViewPager.getCurrentItem();
            if (index > 0) {
                viewDataBinding.homeSearchCondition.dayOfWeekViewPager.setCurrentItem(index - 1);
            }
        });
        viewDataBinding.homeSearchCondition.dayOfWeekArrowRight.setOnClickListener(v -> {
            int index = viewDataBinding.homeSearchCondition.dayOfWeekViewPager.getCurrentItem();
            if (index <= mSearchConsiderPresenter.mDayOfWeekAdapter.getCount() - 1) {
                viewDataBinding.homeSearchCondition.dayOfWeekViewPager.setCurrentItem(index + 1);
            }
        });
        viewDataBinding.homeSearchCondition.dayOfWeekViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    viewDataBinding.homeSearchCondition.dayOfWeekArrowLeft.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getContext(), R.color.color989898))
                    );
                } else {
                    viewDataBinding.homeSearchCondition.dayOfWeekArrowLeft.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getContext(), R.color.color_F7648F))
                    );
                }
                if (i == mSearchConsiderPresenter.mDayOfWeekAdapter.getCount() - 1) {
                    viewDataBinding.homeSearchCondition.dayOfWeekArrowRight.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getContext(), R.color.color989898))
                    );
                } else {
                    viewDataBinding.homeSearchCondition.dayOfWeekArrowRight.setImageTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getContext(), R.color.color_F7648F))
                    );
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        viewDataBinding.homeSearchCondition.setPresenter(mSearchConsiderPresenter);

        FlexboxLayoutManager areaLayoutManager = new FlexboxLayoutManager(getContext());
        areaLayoutManager.setFlexDirection(FlexDirection.ROW);
        areaLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        viewDataBinding.homeSearchCondition.areaRecyclerView.setLayoutManager(areaLayoutManager);

        viewDataBinding.homeSearchCondition.areaRecyclerView.addItemDecoration(new ItemDecoration(4f, 3.5f, 4f, 3.5f));
        viewDataBinding.homeSearchCondition.cityRecyclerView.addItemDecoration(new ItemDecoration(5.5f, 3.5f, 5.5f, 3.5f));

        FlexboxLayoutManager cityLayoutManager = new FlexboxLayoutManager(getContext());
        cityLayoutManager.setFlexDirection(FlexDirection.ROW);
        cityLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        viewDataBinding.homeSearchCondition.cityRecyclerView.setLayoutManager(cityLayoutManager);
        viewDataBinding.homeSearchCondition.areaRecyclerView.setAdapter(mSearchConsiderPresenter.areaAdapter);
        mSearchConsiderPresenter.areaAdapter.setOnItemClickListener((item, view) -> {
            mSearchConsiderPresenter.areaAdapter.notifyDataSetChanged();
            HomeAreaItem areaItem = (HomeAreaItem) item;
            mSearchConsiderPresenter.focusAreaId.set(areaItem.getArea().getId());
            mSearchConsiderPresenter.showAreaId.set(areaItem.getArea().getId());
            int index = areaItem.getIndex();
            viewDataBinding.homeSearchCondition.cityRecyclerView.setAdapter(mSearchConsiderPresenter.cityAdapters[index]);
        });
    }

    private OnChangedSearchConditionListener mSearchConditionListener = new OnChangedSearchConditionListener() {
        @Override
        public void onChangedDate(String dates) {
            mSearchDefault.init(getContext());
            mSearchDefault.setEventDate(dates);
            mSearchDefault.save();

            callFirstApi();
        }

        @Override
        public void onChangedCity(String cities) {
            mSearchDefault.init(getContext());
            mSearchDefault.setCitys(cities);
            mSearchDefault.save();

            callFirstApi();

        }
    };


    boolean handleRegisterSuccess = false;
    int status = 0;
    Bundle extras = null;
    public void handelRegisterSuccess(int status, Bundle extras) {
        if (!isAdded()){
            handleRegisterSuccess = true;
            this.status = status;
            this.extras = extras;
            return;
        }
        requestReLogin();
        handleRegisterSuccess = false;

        if (RESULT.RESULT_REGISTER_PARTY_SUCCESS == status) {
            // show dilalog
            FragmentUtils.replace(getActivity(), new RegisteredPartyListFragment(), true);
        }
        if (RESULT.RESULT_REGISTER_PARTY_SUCCESS_INFO == status) {
            if (StringUtils.parseStringToInteger(extras.getString("total"), -1) == 0) {
                PartyRegisterSuccessDialog dialog = new PartyRegisterSuccessDialog(getActivity(), (status1, extras1) -> {
                    handelRegisterSuccess(status1, extras1);
                }, extras);
                dialog.show();
            }
        }
        if (RESULT.RESULT_REGISTER_PARTY_SUCCESS_INTRO == status) {
            if (StringUtils.parseStringToInteger(extras.getString("total"), -1) == 0) {
                FragmentUtils.replace(getActivity(), new SelfIntroductionCardInfoFragment(), true);
            }
        }
    }

    @Override
    public void refresh() {
        if (isLogedIn != DBManager.isLogin(getContext())) {
            // changed login status
            isLogedIn = !isLogedIn;
            mPresenter.loggedin.set(isLogedIn);
        }
        // reload party list
        callFirstApi();
    }

    @Override
    public void showAdvancedSearchDialog() {
        AdvancedSearchDialog advancedSearchDialog = new AdvancedSearchDialog(getContext(), null,
                new AdvancedSearchDialog.OnAdvancedSearcResultListener() {

                    @Override
                    public void onSendResult(int type, SearchDefault value) {
                        mSearchConsiderPresenter.resetData(getContext());
                        mSearchConsiderPresenter.showAreaId.set("");
                        mSearchConsiderPresenter.focusAreaId.set("");

                        callFirstApi();
                    }

                });
        advancedSearchDialog.show();
    }

    @Override
    public void initRecommendSlider() {
        HomeSliderPagerAdapter sliderPagerAdapter = new HomeSliderPagerAdapter(
                viewDataBinding.homeSlider.sliderViewPager,
                mPresenter.mSliderList,
                this::clickRecommendSlider
        );
        viewDataBinding.homeSlider.sliderViewPager.setAdapter(sliderPagerAdapter);
        if (mPresenter.mSliderList.size() > 1) {
            viewDataBinding.homeSlider.sliderViewPager.setCurrentItem(mPresenter.mSliderList.size());
        }

        GroupAdapter recommendAdapter = new GroupAdapter();
        Section section = new Section();

        for (RecommendSlider recommend : mPresenter.mRecommendList) {
            section.add(new RecommendItem(recommend));
        }

        recommendAdapter.add(section);
        recommendAdapter.setOnItemClickListener((item, view) -> {
            RecommendItem recommendItem = (RecommendItem) item;
            clickRecommendSlider(recommendItem.getItem());
        });

        viewDataBinding.homeSlider.recommendRecyclerView.setAdapter(recommendAdapter);
    }

    @Override
    public void clickRecommendSlider(RecommendSlider recommendSlider) {
        mPresenter.clickBanner(recommendSlider.getId());
        onClickSlide(recommendSlider, otoconFragmentListener);
    }

    OtoconFragment.OtoconFragmentListener otoconFragmentListener = new OtoconFragment.OtoconFragmentListener() {
        @Override
        public void onHandlerReult(int status, Bundle extras) {
            if (RESULT.RESULT_REGISTER_PARTY_SUCCESS == status) {
//                // show dilalog
                FragmentUtils.replace(getActivity(), new RegisteredPartyListFragment(), true);
            }
            if (RESULT.RESULT_REGISTER_PARTY_SUCCESS_INFO == status) {
                if (StringUtils.parseStringToInteger(extras.getString("total"), -1) == 0) {
                    PartyRegisterSuccessDialog dialog = new PartyRegisterSuccessDialog(getContext(), this::onHandlerReult, extras);
                    dialog.show();
//                } else {
//                    FragmentUtils.replace(getActivity(), new IntroductionUserConfirmFragment(), true);
                }
            }
            if (RESULT.RESULT_REGISTER_PARTY_SUCCESS_INTRO == status) {
                if (StringUtils.parseStringToInteger(extras.getString("total"), -1) == 0) {
                    FragmentUtils.replace(getActivity(), new SelfIntroductionCardInfoFragment(), true);
//                } else {
//                    FragmentUtils.replace(getActivity(), new IntroductionUserConfirmFragment(), true);
                }
            }
            refresh();
        }
    };

    @Override
    public void onChangedLike() {
        partyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActivityResult.onResult(requestCode, resultCode, data).into(this);
    }

    @Override
    public void onSuccess(PartyRespone respone) {
        partyListAdapter.setLoading(false);
        if (respone != null) {
            if (mPresenter.getCurrentPage() == 1) partyListAdapter.clear();
            partyListAdapter.updateData(respone.total, respone.getDatas());
        }

        mPresenter.resultSearch.set(partyListAdapter.getTotalParty());
        viewDataBinding.tvNodata.setVisibility(partyListAdapter.getTotalParty() == 0 ? View.VISIBLE : View.GONE);
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

    private void callFirstApi() {
        mPresenter.callApi(LIMIT_ITEM, PAGE);
        viewDataBinding.homeSlider.sliderViewPager.setAdapter(null);
        mPresenter.loadRecommendSliders(getActivity());
    }

}
