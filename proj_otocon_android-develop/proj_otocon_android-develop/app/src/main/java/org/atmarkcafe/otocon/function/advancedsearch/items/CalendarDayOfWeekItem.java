package org.atmarkcafe.otocon.function.advancedsearch.items;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemCalendarDayOfWeekBinding;

public class CalendarDayOfWeekItem extends BindableItem<ItemCalendarDayOfWeekBinding> {
    private String day;

    public CalendarDayOfWeekItem(String day) {
        this.day = day;
    }

    @Override
    public void bind(@NonNull ItemCalendarDayOfWeekBinding viewBinding, int position) {
        viewBinding.setDay(day);
    }

    @Override
    public int getLayout() {
        return R.layout.item_calendar_day_of_week;
    }
}
