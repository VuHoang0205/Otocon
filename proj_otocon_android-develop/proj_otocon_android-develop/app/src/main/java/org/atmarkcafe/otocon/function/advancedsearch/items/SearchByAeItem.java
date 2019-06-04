package org.atmarkcafe.otocon.function.advancedsearch.items;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemAdvancedSearchSearchByAgeBinding;

public class SearchByAeItem extends BindableItem<ItemAdvancedSearchSearchByAgeBinding> {
    private int dayId;
    private String title;
    public ObservableBoolean isChecked = new ObservableBoolean();

    public SearchByAeItem(int id, String title, boolean checked){
        this.dayId = id;
        this.title = title;
        this.isChecked.set(checked);
    }

    @Override
    public void bind(@NonNull ItemAdvancedSearchSearchByAgeBinding viewBinding, int position) {
        viewBinding.setItem(this);
        viewBinding.itemDayOfWeek.setOnClickListener(v -> isChecked.set(!isChecked.get()));
    }

    @Override
    public int getLayout() {
        return R.layout.item_advanced_search_search_by_age;
    }

    public int getDayId() {
        return dayId;
    }

    public String getTitle() {
        return title;
    }
}
