package org.atmarkcafe.otocon.function.advancedsearch;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogAdvancedSearchChoiceDayOfWeekBinding;
import org.atmarkcafe.otocon.function.advancedsearch.items.DayOfWeekItem;
import org.atmarkcafe.otocon.utils.DateUtils;

public class AdvancedSearchDayOfWeekDialog extends Dialog {
    private DialogAdvancedSearchChoiceDayOfWeekBinding mBinding;

    private AdvancedSearchDayOfWeekViewModel mViewModel;
    private AdvancedSearchDialog.OnAdvancedSearchActionListener mListener;
    private String mFirstValue = "";


    public AdvancedSearchDayOfWeekDialog(@NonNull Context context, AdvancedSearchDialog.OnAdvancedSearchActionListener listener) {
        super(context, R.style.AppTheme_Dialog);
        this.mListener = listener;
        mViewModel = new AdvancedSearchDayOfWeekViewModel();
    }

    public AdvancedSearchDayOfWeekDialog setFirstValue(String value) {
        mFirstValue = value;
        initData();
        return this;
    }

    public void initData() {
        if (mViewModel == null) return;
        mViewModel.isCheckedMonday.set(DateUtils.checkedDay(mFirstValue, DateUtils.MONDAY));
        mViewModel.isCheckedTuesday.set(DateUtils.checkedDay(mFirstValue, DateUtils.TUESDAY));
        mViewModel.isCheckedWednesday.set(DateUtils.checkedDay(mFirstValue, DateUtils.WEDNESDAY));
        mViewModel.isCheckedThursday.set(DateUtils.checkedDay(mFirstValue, DateUtils.THURSDAY));
        mViewModel.isCheckedFriday.set(DateUtils.checkedDay(mFirstValue, DateUtils.FRIDAY));
        mViewModel.isCheckedSaturday.set(DateUtils.checkedDay(mFirstValue, DateUtils.SATURDAY));
        mViewModel.isCheckedSunday.set(DateUtils.checkedDay(mFirstValue, DateUtils.SUNDAY));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_advanced_search_choice_day_of_week, null, false);

        setContentView(mBinding.getRoot());

        mBinding.reset.setOnClickListener(v -> {
            mViewModel.isCheckedMonday.set(false);
            mViewModel.isCheckedTuesday.set(false);
            mViewModel.isCheckedWednesday.set(false);
            mViewModel.isCheckedThursday.set(false);
            mViewModel.isCheckedFriday.set(false);
            mViewModel.isCheckedSaturday.set(false);
            mViewModel.isCheckedSunday.set(false);
        });

        mBinding.back.setOnClickListener(v -> {
            if (mListener != null) mListener.onBackPress();
            dismiss();
        });

        GroupAdapter advancedSearchAdapter = new GroupAdapter();
        // Columns
        Section columnSection = new Section();
        Context ctx = getContext();
        columnSection.add(new DayOfWeekItem(DateUtils.MONDAY, ctx.getString(R.string.material_calendar_monday), mViewModel.isCheckedMonday));
        columnSection.add(new DayOfWeekItem(DateUtils.TUESDAY, ctx.getString(R.string.material_calendar_tuesday), mViewModel.isCheckedTuesday));
        columnSection.add(new DayOfWeekItem(DateUtils.WEDNESDAY, ctx.getString(R.string.material_calendar_wednesday), mViewModel.isCheckedWednesday));
        columnSection.add(new DayOfWeekItem(DateUtils.THURSDAY, ctx.getString(R.string.material_calendar_thursday), mViewModel.isCheckedThursday));
        columnSection.add(new DayOfWeekItem(DateUtils.FRIDAY, ctx.getString(R.string.material_calendar_friday), mViewModel.isCheckedFriday));
        columnSection.add(new DayOfWeekItem(DateUtils.SATURDAY, ctx.getString(R.string.material_calendar_saturday), mViewModel.isCheckedSaturday));
        columnSection.add(new DayOfWeekItem(DateUtils.SUNDAY, ctx.getString(R.string.material_calendar_sunday), mViewModel.isCheckedSunday));
        advancedSearchAdapter.add(columnSection);
        mBinding.recyclerview.setAdapter(advancedSearchAdapter);


        // event click submit
        mBinding.submit.setOnClickListener(v -> {
            if (mListener == null) {
                dismiss();
                return;
            }
            String result = DateUtils.getCodeList(
                    mViewModel.isCheckedMonday.get(),
                    mViewModel.isCheckedTuesday.get(),
                    mViewModel.isCheckedWednesday.get(),
                    mViewModel.isCheckedThursday.get(),
                    mViewModel.isCheckedFriday.get(),
                    mViewModel.isCheckedSaturday.get(),
                    mViewModel.isCheckedSunday.get()
            );

            mListener.onSendResult(AdvancedSearchDialog.KEY_DAY_OF_WEEK, result);
            dismiss();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mListener.onBackPress();
    }
}
