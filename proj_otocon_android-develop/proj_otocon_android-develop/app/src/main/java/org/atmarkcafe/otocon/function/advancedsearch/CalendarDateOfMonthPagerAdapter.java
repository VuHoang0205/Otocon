package org.atmarkcafe.otocon.function.advancedsearch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableLong;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentCalendarDateOfMonthBinding;
import org.atmarkcafe.otocon.function.advancedsearch.items.CalendarDayOfMonthItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarDateOfMonthPagerAdapter extends PagerAdapter {
    private static final int NUMBER_MONTH_DEFAULT = 4;
    private Context mContext;
    private Calendar mCurrentDate;
    private Calendar mEndDate;

    private int count = NUMBER_MONTH_DEFAULT;

    private GroupAdapter[] adapters;

    private String currentData = "";

    public void setCurrentData(String currentData) {
        this.currentData = currentData;

        initAdapter();
    }

    public CalendarDateOfMonthPagerAdapter(Context context, String range) {
        this.mContext = context;
        this.currentData = range;

        mCurrentDate = Calendar.getInstance();
        mCurrentDate.set(mCurrentDate.HOUR, 0);
        mCurrentDate.set(mCurrentDate.MINUTE, 0);
        mCurrentDate.set(mCurrentDate.SECOND, 0);
        mCurrentDate.set(mCurrentDate.MILLISECOND, 0);

        mEndDate = Calendar.getInstance();
        mEndDate.setTimeInMillis(mCurrentDate.getTimeInMillis());
        mEndDate.add(Calendar.MONTH, 4);

        if (mEndDate.get(Calendar.YEAR) != mCurrentDate.get(Calendar.YEAR)) {
            this.count = mEndDate.get(Calendar.MONTH) + 13 - mCurrentDate.get(Calendar.MONTH);
        } else {
            this.count = mEndDate.get(Calendar.MONTH) - mCurrentDate.get(Calendar.MONTH) + 1;
        }

        adapters = new GroupAdapter[this.count];
        initAdapter();
    }

    // TODO
    public String getSelectedRange() {
        String dataChoose = "";
        for (GroupAdapter adapter : adapters) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                CalendarDayOfMonthItem monthItem = (CalendarDayOfMonthItem) adapter.getItem(i);
                if (monthItem.isChecked()) {

                    Calendar calendar = monthItem.getCalendar();

                    String day = String.format("%s-%s-%s",
                            calendar.get(Calendar.YEAR),
                            (calendar.get(Calendar.MONTH) + 1 < 10 ? "0" : "") + (calendar.get(Calendar.MONTH) + 1),
                            (calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + (calendar.get(Calendar.DAY_OF_MONTH))
                    );

                    dataChoose = dataChoose + (dataChoose.isEmpty() ? "" : ",");
                    dataChoose = dataChoose + day;
                }
            }
        }

        return dataChoose;
    }

    public CalendarDateOfMonthPagerAdapter setCount(int count) {
        this.count = count;
        adapters = new GroupAdapter[this.count];
        return this;
    }

    public int getMonth(int pos) {
        return (mCurrentDate.get(Calendar.MONTH) + pos) % 12 + 1;
    }

    public boolean isEdgeLeft(int pos) {
        return pos == 0;
    }

    public boolean isEdgeRight(int pos) {
        return pos >= (this.count - 1);
    }

    @Override
    public int getCount() {
        return count;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        FragmentCalendarDateOfMonthBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.fragment_calendar_date_of_month, container, true);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 7);
        binding.dateOfMonth.setLayoutManager(layoutManager);
        binding.dateOfMonth.setAdapter(adapters[position]);

        return binding.getRoot();
    }

    private void initAdapter() {
        // init 4 adapter, and set data default
        for (int index = 0; index < adapters.length; index++) {
            final int position = index;
            GroupAdapter adapter = new GroupAdapter();
            adapters[position] = adapter;
            Section section = new Section();

            Calendar firstDateOfMonth = Calendar.getInstance();
            firstDateOfMonth.setTimeInMillis(mCurrentDate.getTimeInMillis());
            firstDateOfMonth.set(Calendar.DAY_OF_MONTH, 1);
            firstDateOfMonth.add(Calendar.MONTH, position);

            int month = firstDateOfMonth.get(Calendar.MONTH);

            Calendar firstMonthCalender;
            firstMonthCalender = Calendar.getInstance();
            firstMonthCalender.setTimeInMillis(firstDateOfMonth.getTimeInMillis());

            List<Calendar> list = new ArrayList<>();
            int firstDayOfWeek = firstDateOfMonth.get(Calendar.DAY_OF_WEEK);
            firstMonthCalender.add(Calendar.DAY_OF_YEAR, -(firstDayOfWeek + 5) % 7);

            for (int i = 0; i < 42; i++) {
                Calendar item = Calendar.getInstance();
                item.setTimeInMillis(firstMonthCalender.getTimeInMillis());
                item.add(Calendar.DAY_OF_YEAR, i);
                list.add(item);
            }

            for (Calendar calendar : list) {
                CalendarDayOfMonthItem monthItem = new CalendarDayOfMonthItem(month, calendar).setEndCalendar(mEndDate);
//            yyyy-MM-dd
                String day = String.format("%s-%s-%s",
                        calendar.get(Calendar.YEAR),
                        (calendar.get(Calendar.MONTH) + 1 < 10 ? "0" : "") + (calendar.get(Calendar.MONTH) + 1),
                        (calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + (calendar.get(Calendar.DAY_OF_MONTH))
                );
                if(currentData != null )
                monthItem.setChecked(currentData.contains(day));

                section.add(monthItem);
            }

            adapter.add(section);

            //TODO
            adapter.setOnItemClickListener((item, view) -> {
                CalendarDayOfMonthItem calendarDayOfMonthItem = (CalendarDayOfMonthItem) item;
                calendarDayOfMonthItem.setChecked(!calendarDayOfMonthItem.isChecked());
                adapter.notifyDataSetChanged();
                if (position > 0 && adapters[position - 1] != null) {
                    adapters[position - 1].notifyDataSetChanged();
                }

                if (position < count - 1 && adapters[position + 1] != null) {
                    adapters[position + 1].notifyDataSetChanged();
                }
            });
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        int month = mCurrentDate.get(Calendar.MONTH);
        return month + "";
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
