package org.atmarkcafe.otocon.function.advancedsearch.items;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemAdvancedSearchBinding;

public class AdvancedSearchItem extends BindableItem<ItemAdvancedSearchBinding> {
    private int advancedSearchId;
    private String title;
    public ObservableField<String> value;

    public AdvancedSearchItem(int id, String title, ObservableField<String> value){
        this.advancedSearchId = id;
        this.title = title;
        this.value = value;
    }

    @Override
    public void bind(@NonNull ItemAdvancedSearchBinding viewBinding, int position) {
        viewBinding.setItem(this);
    }

    @Override
    public int getLayout() {
        return R.layout.item_advanced_search;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAdvancedSearchId() {
        return advancedSearchId;
    }
}
