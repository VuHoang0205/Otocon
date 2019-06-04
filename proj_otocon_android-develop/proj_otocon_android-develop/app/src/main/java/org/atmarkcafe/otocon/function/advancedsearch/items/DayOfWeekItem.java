package org.atmarkcafe.otocon.function.advancedsearch.items;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemAdvancedSearchDayOfWeekBinding;

public class DayOfWeekItem extends BindableItem<ItemAdvancedSearchDayOfWeekBinding> {
    private int dayId;
    private String title;
    public ObservableBoolean isChecked;

    public DayOfWeekItem(int id, String title, ObservableBoolean checked){
        this.dayId = id;
        this.title = title;
        this.isChecked = checked;
    }

    @Override
    public void bind(@NonNull ItemAdvancedSearchDayOfWeekBinding viewBinding, int position) {
        viewBinding.setItem(this);
        viewBinding.itemDayOfWeek.setOnClickListener(v -> isChecked.set(!isChecked.get()));
    }

    @Override
    public int getLayout() {
        return R.layout.item_advanced_search_day_of_week;
    }

    public int getDayId() {
        return dayId;
    }

    public String getTitle() {
        return title;
    }
}
