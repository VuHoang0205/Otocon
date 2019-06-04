package org.atmarkcafe.otocon.function.advancedsearch;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogAdvancedSearchEventDateBinding;
import org.atmarkcafe.otocon.function.advancedsearch.items.CalendarDayOfWeekItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdvancedSearchEventDateDialog extends Dialog {
    private CalendarDateOfMonthPagerAdapter mCalendarAdapter;

    private DialogAdvancedSearchEventDateBinding mBinding;
    private String range = "";

    private AdvancedSearchDialog.OnAdvancedSearchActionListener mListener;

    public AdvancedSearchEventDateDialog(@NonNull Context context, AdvancedSearchDialog.OnAdvancedSearchActionListener listener) {
        super(context, R.style.AppTheme_Dialog);
        this.mListener = listener;
    }

    public void setFirstValue(String range) {
        this.range = range;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_advanced_search_event_date, null, false);

        setContentView(mBinding.getRoot());


        mBinding.back.setOnClickListener(v -> {
            if (mListener != null) mListener.onBackPress();
            dismiss();
        });

        mBinding.reset.setOnClickListener(v -> {
            mCalendarAdapter.setCurrentData("");
            mCalendarAdapter.notifyDataSetChanged();
        });

        mBinding.submit.setOnClickListener(v -> {
            if (mListener != null) mListener.onSendResult(AdvancedSearchDialog.KEY_EVENT_DATE, mCalendarAdapter.getSelectedRange());
            dismiss();
        });

        // recyclerview for day of week
        GroupAdapter adapterDayOfWeek = new GroupAdapter();
        Section section = new Section();

        section.add(new CalendarDayOfWeekItem(getContext().getString(R.string.material_calendar_monday)));
        section.add(new CalendarDayOfWeekItem(getContext().getString(R.string.material_calendar_tuesday)));
        section.add(new CalendarDayOfWeekItem(getContext().getString(R.string.material_calendar_wednesday)));
        section.add(new CalendarDayOfWeekItem(getContext().getString(R.string.material_calendar_thursday)));
        section.add(new CalendarDayOfWeekItem(getContext().getString(R.string.material_calendar_friday)));
        section.add(new CalendarDayOfWeekItem(getContext().getString(R.string.material_calendar_saturday)));
        section.add(new CalendarDayOfWeekItem(getContext().getString(R.string.material_calendar_sunday)));

        adapterDayOfWeek.add(section);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        mBinding.calendarDayOfWeek.setLayoutManager(layoutManager);
        mBinding.calendarDayOfWeek.setAdapter(adapterDayOfWeek);

        // viewpager for day of month
        mCalendarAdapter = new CalendarDateOfMonthPagerAdapter(getContext(), range);

        mBinding.calendarDateOfMonthViewPager.setAdapter(mCalendarAdapter);

        // init selection current month
        mBinding.calendarMonth.setText(String.format(
                getContext().getString(R.string.advanced_search_event_date_month),
                mCalendarAdapter.getMonth(0)
        ));
        mBinding.calendarArrowLeft.setColorFilter(Color.parseColor("#e0e0e0"));
        mBinding.calendarArrowRight.setColorFilter(Color.BLACK);

        // init control when select item viewpager
        mBinding.calendarDateOfMonthViewPager.setOffscreenPageLimit(1);
        mBinding.calendarDateOfMonthViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mBinding.calendarMonth.setText(String.format(
                        getContext().getString(R.string.advanced_search_event_date_month),
                        mCalendarAdapter.getMonth(i)
                ));

                if (mCalendarAdapter.isEdgeLeft(i)) {
                    mBinding.calendarArrowLeft.setColorFilter(Color.parseColor("#e0e0e0"));
                } else {
                    mBinding.calendarArrowLeft.setColorFilter(Color.BLACK);
                }

                if (mCalendarAdapter.isEdgeRight(i)) {
                    mBinding.calendarArrowRight.setColorFilter(Color.parseColor("#e0e0e0"));
                } else {
                    mBinding.calendarArrowRight.setColorFilter(Color.BLACK);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // event when click button arrow left
        mBinding.calendarArrowLeft.setOnClickListener(v -> {
            int pos = mBinding.calendarDateOfMonthViewPager.getCurrentItem();
            if (mCalendarAdapter.isEdgeLeft(pos)) return;
            mBinding.calendarDateOfMonthViewPager.setCurrentItem(pos - 1);
        });

        // event when click button arrow right
        mBinding.calendarArrowRight.setOnClickListener(v -> {
            int pos = mBinding.calendarDateOfMonthViewPager.getCurrentItem();
            if (mCalendarAdapter.isEdgeRight(pos)) return;
            mBinding.calendarDateOfMonthViewPager.setCurrentItem(pos + 1);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mListener.onBackPress();
    }
}
