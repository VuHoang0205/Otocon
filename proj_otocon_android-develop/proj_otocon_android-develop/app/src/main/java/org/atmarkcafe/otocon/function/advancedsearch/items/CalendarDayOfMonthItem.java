package org.atmarkcafe.otocon.function.advancedsearch.items;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemCalendarDayOfMonthBinding;

import java.util.Calendar;

public class CalendarDayOfMonthItem extends BindableItem<ItemCalendarDayOfMonthBinding> {
    private Calendar calendar;
    private int month;
    private int colorDefault;
    private int backgroundDefault;

    private int mCurrentMonth;
    private int mCurrentDayOfMonth;

    private Calendar mEndCalendar;

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public CalendarDayOfMonthItem setEndCalendar(Calendar endCalendar) {
        this.mEndCalendar = endCalendar;
        if (calendar.get(Calendar.MONTH) == this.month && calendar.getTimeInMillis() > mEndCalendar.getTimeInMillis()) {
            colorDefault = Color.parseColor("#E7E6E3");
        }
        return this;
    }

    public CalendarDayOfMonthItem(int month, Calendar calendar) {
        this.month = month;
        this.calendar = calendar;

        Calendar current = Calendar.getInstance();
        mCurrentMonth = current.get(Calendar.MONTH);
        mCurrentDayOfMonth = current.get(Calendar.DAY_OF_MONTH);

        if (calendar.get(Calendar.MONTH) != this.month) {
            colorDefault = Color.TRANSPARENT;
        } else if (this.month == this.mCurrentMonth) {
            if (calendar.get(Calendar.DAY_OF_MONTH) < mCurrentDayOfMonth) {
                colorDefault = Color.parseColor("#E7E6E3");
            } else {
                colorDefault = Color.BLACK;
            }
        } else {
            colorDefault = Color.BLACK;
        }

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                backgroundDefault = Color.parseColor("#80E8F3FF");
                break;
            case Calendar.SUNDAY:
                backgroundDefault = Color.parseColor("#80FFE2F2");
                break;
            default:
                backgroundDefault = Color.TRANSPARENT;
        }
    }

    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void bind(@NonNull ItemCalendarDayOfMonthBinding viewBinding, int position) {

        if (Calendar.getInstance().getTimeInMillis() >= calendar.getTimeInMillis()) {
            if (!isDateSame(Calendar.getInstance(), calendar)) {
                isChecked = false;
            }
        }

        if (calendar.get(Calendar.MONTH) != this.month || calendar.getTimeInMillis() > mEndCalendar.getTimeInMillis()) {
            isChecked = false;
        }

        int color = colorDefault;
        int background = backgroundDefault;

        if (isChecked) {
            background = Color.BLACK;
            color = Color.WHITE;
        }

        viewBinding.value.setBackgroundColor(background);
        viewBinding.value.setTextColor(color);

        viewBinding.value.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

    }

    @Override
    public int getLayout() {
        return R.layout.item_calendar_day_of_month;
    }
}
