package org.atmarkcafe.otocon.function.party;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.xwray.groupie.GroupAdapter;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ActivityPartyDetailBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;

import org.atmarkcafe.otocon.function.login.LoginFragment;
import org.atmarkcafe.otocon.function.party.register.RegisterPartyFragment;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.PartyDetailData;
import org.atmarkcafe.otocon.utils.FragmentUtils;

/**
 * class PartyDetailActivity
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class PartyDetailFragment extends OtoconBindingFragment<ActivityPartyDetailBinding> implements PartyDetailActivityContract.PartyDetailView {

    public static PartyDetailFragment create(String partId, OtoconFragment.OtoconFragmentListener listener) {
        PartyDetailFragment f = new PartyDetailFragment();
        f.setOtoconFragmentListener(listener);
        Bundle extras = new Bundle();
        extras.putString(PARTY_ID, partId);
        f.setArguments(extras);

        return f;
    }

    private TabLayout tableLayout;
    private PartyDetailPresenter presenter;
    private PartyDetailData partyDetailData;
    private GroupAdapter groupAdapter;
    private LinearSnapHelper mSnapHelper;
    private TabSelector tabSelector;
    private int currentSelected = 0;

    private String id_party;
    public static final String PARTY_ID = "id";

    @Override
    public int layout() {
        return R.layout.activity_party_detail;
    }

    @Override
    public void onCreateView(ActivityPartyDetailBinding binding) {

        init(binding);

        id_party = getArguments().getString(PARTY_ID);
        presenter.onLoadData(id_party);

        binding.setPresenter(presenter);

        binding.toolbar3.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        binding.btnConfirm.setOnClickListener(v -> {
            onStartRegisterActivity();
        });

    }

    public void onStartRegisterActivity() {

        Gson gson = new Gson();
        String json = gson.toJson(partyDetailData);

        FragmentUtils.replace(getActivity(), RegisterPartyFragment.create(json, new OtoconFragmentListener() {
            @Override
            public void onHandlerReult(int status, Bundle extras) {
                getActivity().onBackPressed();
                otoconFragmentListener.onHandlerReult(status, extras);
            }
        }), true);
    }

    private void init(ActivityPartyDetailBinding binding) {

        presenter = new PartyDetailPresenter(getActivity(), this);
        groupAdapter = new GroupAdapter();
        mSnapHelper = new LinearSnapHelper();
        partyDetailData = new PartyDetailData();

        RecyclerView recyclerView = binding.layoutContainerBottomAppbar.recyclerView;
        tableLayout = binding.layoutContainerBottomAppbar.tabLayout;

        tabSelector = new TabSelector(recyclerView, getActivity(), tableLayout);
        tableLayout.addOnTabSelectedListener(tabSelector);

        // Add line beetween tap item
        LinearLayout linearLayout = (LinearLayout) tableLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(getActivity().getDrawable(R.drawable.xml_divider_taplayout));

        tabSelector.setTypeFontTablayout(0, R.style.TextAppearance_Tabs_Selected);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(onScrollChangeListener);
        recyclerView.setAdapter(groupAdapter);

        binding.layoutContainerTop.cbLike.setOnClickListener(v -> {
            if (DBManager.getToken(getActivity()).isEmpty()) {

                FragmentUtils.replace(getActivity(), LoginFragment.create(new OtoconFragmentListener() {
                    @Override
                    public void onHandlerReult(int status, Bundle extras) {
                        //TODO
                    }
                }), true);
            } else {
                presenter.likeParty(partyDetailData);
            }
        });
    }

    @Override
    public void showDataSuccess(PartyDetailData partyDetailData) {
        try {
            viewDataBinding.viewLayout.setVisibility(View.GONE);
            this.partyDetailData = partyDetailData;
            viewDataBinding.setViewModel(this.partyDetailData);


            String note = partyDetailData.getNote().replaceAll("\\r\\n", "<br>")
                    .replaceAll("\\n", "<br>")
                    .replaceAll("\\r", "<br>");

            String caution = partyDetailData.getCaution().replaceAll("\\r\\n", "<br>")
                    .replaceAll("\\n", "<br>")
                    .replaceAll("\\r", "<br>");

            String cancleCaution = partyDetailData.getCancelNotice().replaceAll("\\r\\n", "<br>")
                    .replaceAll("\\n", "<br>")
                    .replaceAll("\\r", "<br>");

            groupAdapter.add(new CautionItem(Html.fromHtml(note), getString(R.string.party_detail_title_note), R.drawable.ic_glass));
            groupAdapter.add(new CautionItem(Html.fromHtml(caution), getString(R.string.party_detail_title_caution), R.drawable.ic_pen));
            groupAdapter.add(new CautionItem(Html.fromHtml(cancleCaution), getString(R.string.party_detail_title_get_cancle_notice), R.drawable.ic_cancle_caution));

            if (partyDetailData.hasThreeTextMale()) {
                setMarginTop(viewDataBinding.layoutContainerTop.layoutContainerPriceMan.imageView6, 0);
                setMarginTop(viewDataBinding.layoutContainerTop.layoutContainerPriceMan.lineThreeText, (int) this.getResources().getDimension(R.dimen.top_line_three_text));
            }

            if (partyDetailData.hasThreeTextFeeMale()) {
                setMarginTop(viewDataBinding.layoutContainerTop.include.imageView6, 0);
                setMarginTop(viewDataBinding.layoutContainerTop.include.textView24, (int) this.getResources().getDimension(R.dimen.top_line_three_text));
            }
        } catch (Exception e) {

        }

        // Set minHeight for layout
        viewDataBinding.layoutContainerTop.layoutContainerPriceMan.constraintLayout8.setMinHeight((int) (getResources().getDisplayMetrics().density*partyDetailData.getHeight(partyDetailData)));
        viewDataBinding.layoutContainerTop.include.constraintLayout8.setMinHeight((int) (getResources().getDisplayMetrics().density*partyDetailData.getHeight(partyDetailData)));
    }

    @Override
    public void showOtherErorrMessage(boolean isNotConnectToServer) {
        if (isNotConnectToServer) {
            PopupMessageErrorDialog popupMessageErrorDialog = new PopupMessageErrorDialog(getActivity(),
                    getString(R.string.error_title_Connect_server_fail),
                    getString(R.string.error_content_Connect_server_fail)
            );

            popupMessageErrorDialog.setPopupMessageErrorListener(() -> {
                getActivity().onBackPressed();
            });
            popupMessageErrorDialog.show();

        } else {
            PopupMessageErrorDialog popupMessageErrorDialog = new PopupMessageErrorDialog(getActivity());
            popupMessageErrorDialog.setPopupMessageErrorListener(() -> {
                getActivity().onBackPressed();
            });
            popupMessageErrorDialog.show();
        }
    }

    @Override
    public void showPopupPartyExpired(String message) {
        PopupMessageErrorDialog.show(getContext(), R.string.title_dialog_expired, message, new PopupMessageErrorDialog.PopupMessageErrorListener() {
            @Override
            public void onClickOke() {
               getFragmentManager().popBackStack();
            }
        }).show();
    }


    private RecyclerView.OnScrollListener onScrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            View snap = mSnapHelper.findSnapView(recyclerView.getLayoutManager());

            if (snap == null) return;

            int selectedPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if (currentSelected != selectedPosition) {

                currentSelected = selectedPosition;

                tabSelector.setUserScroll(true);

                if (currentSelected != 1) {
                    configScroll(tableLayout.getTabAt(currentSelected));

                } else if (currentSelected == 1 && tabSelector.isUserTapSelected()) {
                    tabSelector.setUserTapSelected(false);
                    tabSelector.setUserScroll(false);
                    return;
                } else {
                    configScroll(tableLayout.getTabAt(currentSelected));
                }
            }
        }
    };

    private void configScroll(TabLayout.Tab tab) {
        tab.select();
        tabSelector.setUserTapSelected(false);
        tabSelector.setUserScroll(false);
    }

    private void setMarginTop(View view, int top) {
        ViewGroup.MarginLayoutParams lineThreeText = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lineThreeText.setMargins(lineThreeText.leftMargin, top, lineThreeText.rightMargin, lineThreeText.bottomMargin);
        lineThreeText.setMargins(lineThreeText.leftMargin, top, lineThreeText.rightMargin, lineThreeText.bottomMargin);
    }

}