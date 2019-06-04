package org.atmarkcafe.otocon.function.home.items;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemHomeCityAreaBinding;
import org.atmarkcafe.otocon.model.response.City;

public class HomeCityItem extends BindableItem<ItemHomeCityAreaBinding> {
    private boolean isChecked = false;
    private City city;

    public HomeCityItem(City city) {
        this.city = city;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public City getCity() {
        return city;
    }

    @Override
    public int getLayout() {
        return R.layout.item_home_city_area;
    }

    @Override
    public void bind(@NonNull ItemHomeCityAreaBinding viewBinding, int position) {
        viewBinding.setArea(city.getName());
        if (isChecked) {
            viewBinding.areaItem.setTextColor(Color.WHITE);
            viewBinding.areaItem.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B52A80")));
        } else {
            viewBinding.areaItem.setTextColor(Color.parseColor("#161616"));
            viewBinding.areaItem.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EDEDED")));
        }
    }
}