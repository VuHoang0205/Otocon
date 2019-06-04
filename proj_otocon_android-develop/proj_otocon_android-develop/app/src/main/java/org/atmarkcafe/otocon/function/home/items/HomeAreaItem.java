package org.atmarkcafe.otocon.function.home.items;

import android.content.res.ColorStateList;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemHomeCityAreaBinding;
import org.atmarkcafe.otocon.model.response.Area;

public class HomeAreaItem extends BindableItem<ItemHomeCityAreaBinding> {
    private int countCityChecked = 0;
    private int index = -1;
    private Area area;

    public ObservableField<String> selectedAreaId;

    public HomeAreaItem(Area area, ObservableField<String> selectedAreaId) {
        this.area = area;
        this.selectedAreaId = selectedAreaId;
    }

    public Area getArea() {
        return area;
    }

    public int getCountCityChecked() {
        return countCityChecked;
    }

    public void setCountCityChecked(int countCityChecked) {
        this.countCityChecked = countCityChecked;
    }

    public int getIndex() {
        return index;
    }

    public HomeAreaItem setIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public int getLayout() {
        return R.layout.item_home_city_area;
    }

    @Override
    public void bind(@NonNull ItemHomeCityAreaBinding viewBinding, int position) {
        viewBinding.setArea(area.getName());
        try {
            viewBinding.areaItem.setTypeface(ResourcesCompat.getFont(
                    viewBinding.getRoot().getContext(),
                    selectedAreaId.get().equals(area.getId()) ? R.font.hiragino_kaku_gothic_pro_w6 : R.font.hiragino_kaku_gothic_pro_w3
            ));
        }catch (Throwable e){

        }

        if (selectedAreaId.get().equals(area.getId())) {
            viewBinding.areaItem.setTextColor(Color.WHITE);
            viewBinding.areaItem.setBackgroundResource(R.drawable.xml_background_status_party);
            viewBinding.areaItem.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1ABA9A")));
        } else if (countCityChecked > 0) {
            viewBinding.areaItem.setTextColor(Color.parseColor("#161616"));
            viewBinding.areaItem.setBackgroundResource(R.drawable.xml_background_status_party);
            viewBinding.areaItem.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1ABA9A")));
        } else {
            viewBinding.areaItem.setTextColor(Color.parseColor("#161616"));
            viewBinding.areaItem.setBackgroundResource(R.drawable.xml_background_status_party_transparent);
            viewBinding.areaItem.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
        }
    }
}