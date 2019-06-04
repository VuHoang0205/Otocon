package org.atmarkcafe.otocon.function.home;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentCalendarDayOfWeekBinding;
import org.atmarkcafe.otocon.function.home.items.HomeDayOfWeekItem;
import org.atmarkcafe.otocon.view.ItemDecoration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDayOfWeekAdapter extends PagerAdapter {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar mCurrentDate;
    private HomeFragment.OnChangedSearchConditionListener mSearchConditionListener;
    private int count;
    private List<GroupAdapter> adapters;

    public CalendarDayOfWeekAdapter(HomeFragment.OnChangedSearchConditionListener listener) {
        this.mSearchConditionListener = listener;
        mCurrentDate = Calendar.getInstance();
        mCurrentDate = Calendar.getInstance();
        mCurrentDate.set(Calendar.HOUR, 0);
        mCurrentDate.set(Calendar.MINUTE, 0);
        mCurrentDate.set(Calendar.SECOND, 0);
        mCurrentDate.set(Calendar.MILLISECOND, 0);
        Calendar firstDayOfWeek = Calendar.getInstance();
        firstDayOfWeek.setTime(mCurrentDate.getTime());
        int deltaDayOfWeek = firstDayOfWeek.get(Calendar.DAY_OF_WEEK);
        firstDayOfWeek.add(Calendar.DAY_OF_YEAR, -(deltaDayOfWeek + 5) % 7);
        int currentMonth = mCurrentDate.get(Calendar.MONTH);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH,4);
        count = 0;
        int numberOfDay = 0;
        adapters = new ArrayList<>();
        boolean canNext = true;
        while (canNext) {
            GroupAdapter groupAdapter = new GroupAdapter();
            Section section = new Section();
            groupAdapter.add(section);
            for (int i = 0; i < 7; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(firstDayOfWeek.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, numberOfDay);
                HomeDayOfWeekItem dayOfWeekItem = new HomeDayOfWeekItem(calendar, mCurrentDate);
                section.add(dayOfWeekItem);
                numberOfDay++;
                if (canNext && calendar.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR)) {
                    canNext = false;
                }
            }
            groupAdapter.setOnItemClickListener((item, view) -> {
                HomeDayOfWeekItem dayOfWeekItem = (HomeDayOfWeekItem) item;
                if (dayOfWeekItem.status.get() == HomeDayOfWeekItem.STATUS_NORMAL) {
                    dayOfWeekItem.status.set(HomeDayOfWeekItem.STATUS_SELECTED);
                } else if (dayOfWeekItem.status.get() == HomeDayOfWeekItem.STATUS_SELECTED) {
                    dayOfWeekItem.status.set(HomeDayOfWeekItem.STATUS_NORMAL);
                } else {
                    return;
                }
                if (mSearchConditionListener != null) {
                    mSearchConditionListener.onChangedDate(getSelectedDays());
                }
            });
            adapters.add(groupAdapter);
            count++;
        }
    }

    public void updateSelectedDay(String dates) {
        for (GroupAdapter adapter : adapters) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                HomeDayOfWeekItem dayOfWeekItem = (HomeDayOfWeekItem) adapter.getItem(i);

                if (dayOfWeekItem.status.get() != HomeDayOfWeekItem.STATUS_DISABLE) {
                    String sdate = FORMAT.format(dayOfWeekItem.getCalendar().getTime());
                    dayOfWeekItem.status.set(
                            dates.contains(sdate) ? HomeDayOfWeekItem.STATUS_SELECTED : HomeDayOfWeekItem.STATUS_NORMAL
                    );
                }
            }
        }
    }

    private String getSelectedDays() {
        String result = "";
        for (GroupAdapter adapter : adapters) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                HomeDayOfWeekItem dayOfWeekItem = (HomeDayOfWeekItem) adapter.getItem(i);
                if (dayOfWeekItem.status.get() == HomeDayOfWeekItem.STATUS_SELECTED) {
                    result += (result.isEmpty() ? "" : ",") + FORMAT.format(dayOfWeekItem.getCalendar().getTime());
                }
            }
        }
        return result;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        FragmentCalendarDayOfWeekBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.fragment_calendar_day_of_week, container, true);
        binding.dayOfWeek.addItemDecoration(new ItemDecoration(0.5f, 0.5f, 0.5f, 0.5f));
        binding.dayOfWeek.setLayoutManager(new GridLayoutManager(container.getContext(), 7));
        binding.dayOfWeek.setAdapter(adapters.get(position));
        return binding.getRoot();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}