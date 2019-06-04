package org.atmarkcafe.otocon.function.advancedsearch.items;

import android.graphics.Color;
import android.support.annotation.NonNull;


import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemAreaBinding;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchCityExpandableGroup;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchCityExpandableItem;
import org.atmarkcafe.otocon.model.response.Area;

public class AreaItem extends BindableItem<ItemAreaBinding> implements AdvancedSearchCityExpandableItem {

    public interface CheckArea {
        void isCheckArea(boolean area);
    }

    private Area area;

    private AdvancedSearchCityExpandableGroup expandableGroup;

    private CheckArea checkArea;

    public AreaItem(Area area) {
        this.area = area;
    }

    public void setCheckArea(CheckArea checkArea) {
        this.checkArea = checkArea;
    }

    @Override
    public void bind(@NonNull ItemAreaBinding viewBinding, int position) {
        viewBinding.setArea(area);
        viewBinding.viewExpan.setOnClickListener((view) -> {
            expandableGroup.onToggleExpanded();
        });

        viewBinding.getRoot().setBackgroundResource(expandableGroup.isExpanded() ? R.drawable.background_expan : R.drawable.background_item_area);
        viewBinding.tvIdArea.setTextColor(expandableGroup.isExpanded() ? Color.parseColor("#ffffff") : Color.parseColor("#161616"));
        viewBinding.checkBox.setTextColor(expandableGroup.isExpanded() ? Color.parseColor("#ffffff") : Color.parseColor("#161616"));
        viewBinding.imageView.setImageResource(expandableGroup.isExpanded() ? R.drawable.ic_up_while_advanced : R.drawable.ic_down_black_advanced);

        viewBinding.viewCheck.setOnClickListener((view) -> {
            area.setCheck(!area.isCheck());
            checkArea.isCheckArea(area.isCheck());
        });
    }

    @Override
    public int getLayout() {
        return R.layout.item_area;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public void setExpandableGroup(@NonNull AdvancedSearchCityExpandableGroup onToggleListener) {
        this.expandableGroup = onToggleListener;
    }
}
