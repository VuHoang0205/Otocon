package org.atmarkcafe.otocon.function.home.items;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemHomeCalendarDayOfWeekBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class HomeDayOfWeekItem extends BindableItem<ItemHomeCalendarDayOfWeekBinding> {
    private static final SimpleDateFormat format = new SimpleDateFormat("M/d\n(E)", Locale.JAPANESE);
    public final static int STATUS_DISABLE = 0;
    public final static int STATUS_NORMAL = 1;
    public final static int STATUS_SELECTED = 2;
    public final static int DATE_TYPE_TODAY = 1;
    public final static int DATE_TYPE_NORMAL = 2;
    public final static int DATE_TYPE_SATURDAY = 3;
    public final static int DATE_TYPE_SUNDAY = 4;
    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableInt status = new ObservableInt(0);
    public final ObservableInt dateType = new ObservableInt(0);
    private Calendar mCalendar;

    public HomeDayOfWeekItem(Calendar calendar, Calendar today) {
        this.mCalendar = calendar;
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 4);
        if (calendar.getTimeInMillis() < today.getTimeInMillis() || calendar.getTimeInMillis() > endDate.getTimeInMillis()) {
            status.set(STATUS_DISABLE);
        } else if (calendar.getTimeInMillis() == today.getTimeInMillis()) {
            status.set(STATUS_NORMAL);
            dateType.set(DATE_TYPE_TODAY);
        } else {
            status.set(STATUS_NORMAL);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                dateType.set(DATE_TYPE_SUNDAY);
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                dateType.set(DATE_TYPE_SATURDAY);
            } else {
                dateType.set(DATE_TYPE_NORMAL);
            }
        }
        text.set(format.format(calendar.getTime()));
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    @Override
    public void bind(@NonNull ItemHomeCalendarDayOfWeekBinding viewBinding, int position) {
        viewBinding.setItem(this);
        // update typeface if item is today
        try {
            viewBinding.day.setTypeface(ResourcesCompat.getFont(
                    viewBinding.getRoot().getContext(),
                    dateType.get() == DATE_TYPE_TODAY ? R.font.hiragino_kaku_gothic_pro_w6 : R.font.hiragino_kaku_gothic_pro_w3
            ));
        } catch (Throwable e) {

        }
    }

    @Override
    public int getLayout() {
        return R.layout.item_home_calendar_day_of_week;
    }
}