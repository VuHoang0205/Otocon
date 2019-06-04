package org.atmarkcafe.otocon.function.advancedsearch;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogAdvancedSearchBinding;
import org.atmarkcafe.otocon.function.advancedsearch.items.AdvancedSearchItem;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.pref.SearchDefault;

public class AdvancedSearchDialog extends Dialog implements AdvancedSearchContract.View, OnItemClickListener {

    private PartyParams params;
    public interface OnAdvancedSearchActionListener {
        void onBackPress();

        void onSendResult(int type, String value);
    }

    public interface OnAdvancedSearcResultListener {

        void onSendResult(int type, SearchDefault value);
    }

    public static final int KEY_AREA = 0;
    public static final int KEY_EVENT_DATE = 1;
    public static final int KEY_DAY_OF_WEEK = 2;
    public static final int KEY_GENDER = 3;
    public static final int KEY_AGE = 4;
    public static final int KEY_AGE_OF_OPPONENT = 5;

    private SearchDefault mSearchDefault;

    private DialogAdvancedSearchBinding mBinding;
    private AdvancedSearchPresenter mPresenter;
    private AdvancedSearchDialog.OnAdvancedSearcResultListener mResponseListener;
    private boolean isShowArea = true;

    public AdvancedSearchDialog(@NonNull Context context, PartyParams params,  AdvancedSearchDialog.OnAdvancedSearcResultListener listener) {
        super(context, R.style.AppTheme_Dialog);
        this.params = params;
        this.mPresenter = new AdvancedSearchPresenter(this);
        this.mResponseListener = listener;
        if(params != null){
            mSearchDefault = new SearchDefault();
            mSearchDefault.initFrom(params);
        }else{
            mSearchDefault = SearchDefault.getInstance();
            mSearchDefault.init(getContext());
        }
    }

    public AdvancedSearchDialog setShowArea(boolean showArea) {
        isShowArea = showArea;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_advanced_search, null, false);
        mBinding.setPresenter(mPresenter);

        setContentView(mBinding.getRoot());

        mBinding.submit.setOnClickListener(v -> {
            if(params == null){
                mSearchDefault.save();
            }

            mResponseListener.onSendResult(-1, mSearchDefault);
            dismiss();
        });
        mBinding.reset.setOnClickListener(v -> {
            mSearchDefault.reset();
            initView();
        });
        mBinding.close.setOnClickListener(v -> dismiss());
        mBinding.advancedSearchLayout.setOnClickListener(v -> dismiss());

        GroupAdapter advancedSearchAdapter = new GroupAdapter();
        advancedSearchAdapter.setOnItemClickListener(this);
        // Columns
        Section columnSection = new Section();
        if (isShowArea){
            columnSection.add(new AdvancedSearchItem(KEY_AREA, getContext().getString(R.string.advanced_menu_area), mPresenter.area));
        }
        columnSection.add(new AdvancedSearchItem(KEY_EVENT_DATE, getContext().getString(R.string.advanced_menu_event_date), mPresenter.eventDate));
        columnSection.add(new AdvancedSearchItem(KEY_DAY_OF_WEEK, getContext().getString(R.string.advanced_menu_day_of_week), mPresenter.dayOfWeek));
        columnSection.add(new AdvancedSearchItem(KEY_GENDER, getContext().getString(R.string.advanced_menu_your_gender), mPresenter.gender));
        columnSection.add(new AdvancedSearchItem(KEY_AGE, getContext().getString(R.string.advanced_menu_your_age), mPresenter.age));
        columnSection.add(new AdvancedSearchItem(KEY_AGE_OF_OPPONENT, getContext().getString(R.string.advanced_menu_age_of_opponent), mPresenter.ageOfOpponent));
        advancedSearchAdapter.add(columnSection);
        mBinding.recyclerview.setAdapter(advancedSearchAdapter);

        mPresenter.isOnlyParty.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mSearchDefault.setOnlyParty(mPresenter.isOnlyParty.get());
            }
        });

        initView();
    }

    @Override
    public void initView() {
        mPresenter.updateArea(getContext(), mSearchDefault.getCitys());

        mPresenter.updateEventDate(getContext(), mSearchDefault.getEventDate());

        mPresenter.updateDayOfWeek(getContext(), mSearchDefault.getDayOfWeek());

        mPresenter.updateGender(getContext(), mSearchDefault.getGender());

        mPresenter.updateAge(getContext(), mSearchDefault.getAge());

        mPresenter.updateAgeOfOpponent(getContext(), mSearchDefault.getAgeOfOpponent());

        mPresenter.isOnlyParty.set(mSearchDefault.isOnlyParty());
    }

    @Override
    public void onItemClick(@NonNull Item item, @NonNull View view) {
        hideScreenn();
        AdvancedSearchItem advancedSearchItem = (AdvancedSearchItem) item;
        switch (advancedSearchItem.getAdvancedSearchId()) {
            case KEY_AREA:
                AdvancedSearchCityDialog cityDialog = new AdvancedSearchCityDialog(getContext(), mOnChildDialogActionListener);
                cityDialog.setFirstValue(mSearchDefault.getCitys());
                cityDialog.show();
                break;
            case KEY_EVENT_DATE:
                AdvancedSearchEventDateDialog eventDateDialog = new AdvancedSearchEventDateDialog(getContext(), mOnChildDialogActionListener);
                eventDateDialog.setFirstValue(mSearchDefault.getEventDate());
                eventDateDialog.show();
                break;
            case KEY_DAY_OF_WEEK:
                AdvancedSearchDayOfWeekDialog advancedSearchDayOfWeekDialog = new AdvancedSearchDayOfWeekDialog(getContext(), mOnChildDialogActionListener)
                        .setFirstValue(mSearchDefault.getDayOfWeek());
                advancedSearchDayOfWeekDialog.show();
                break;
            case KEY_GENDER:
                AdvancedGenderDialog genderDialog = new AdvancedGenderDialog(getContext(), mSearchDefault.getGender(), mOnChildDialogActionListener);
                genderDialog.show();
                break;
            case KEY_AGE:

                AdvancedSearchAgeDialog ageDialog = new AdvancedSearchAgeDialog(getContext(), mSearchDefault.getAge(), mOnChildDialogActionListener);
                ageDialog.show();

                break;
            case KEY_AGE_OF_OPPONENT:
                AdvancedSearchAroundAgeDialog searchByDialog = new AdvancedSearchAroundAgeDialog(getContext(), mSearchDefault.getAgeOfOpponent(), mOnChildDialogActionListener);
                searchByDialog.show();
                break;
        }
    }

    private void hideScreenn() {
        mBinding.getRoot().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.getRoot().setVisibility(View.GONE);
            }
        }, 400);
    }

    private AdvancedSearchDialog.OnAdvancedSearchActionListener mOnChildDialogActionListener = new OnAdvancedSearchActionListener() {
        @Override
        public void onBackPress() {
            // child dialog back, show top dialog
            mBinding.getRoot().setVisibility(View.VISIBLE);
//            show();
        }

        @Override
        public void onSendResult(int key, String value) {
            mBinding.getRoot().setVisibility(View.VISIBLE);
            switch (key) {
                case KEY_AREA:
                    mSearchDefault.setCitys(value);
                    mPresenter.updateArea(getContext(), value);
                    break;
                case KEY_EVENT_DATE:
                    mSearchDefault.setEventDate(value);
                    mPresenter.updateEventDate(getContext(), value);
                    break;
                case KEY_DAY_OF_WEEK:
                    mSearchDefault.setDayOfWeek(value);
                    mPresenter.updateDayOfWeek(getContext(), value);
                    break;
                case KEY_GENDER:
                    int gender = 0;
                    try {
                        gender = Integer.parseInt(value);
                    } catch (Exception e) {

                    }
                    
                    mSearchDefault.setGender(gender);
                    mPresenter.updateGender(getContext(), gender);
                    break;
                case KEY_AGE:
                    int age = 0;
                    try {
                        age = Integer.parseInt(value);
                    } catch (Exception e) {

                    }
                    mSearchDefault.setAge(age);
                    mPresenter.updateAge(getContext(), age);

                    break;
                case KEY_AGE_OF_OPPONENT:
                    mSearchDefault.setAgeOfOpponent(value);
                    mPresenter.updateAgeOfOpponent(getContext(), value);
                    break;
            }
            show();
        }
    };

}
