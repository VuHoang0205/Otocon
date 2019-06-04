package org.atmarkcafe.otocon.function.advancedsearch.items;

import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemCityBinding;
import org.atmarkcafe.otocon.model.response.City;

public class CityItem extends BindableItem<ItemCityBinding> {

    public interface CheckCity{
        void isCheckCity(boolean city);
    }

    private City city;

    private CheckCity checkCity;

    public void setCheckCity(CheckCity checkCity){
        this.checkCity = checkCity;
    }

    public CityItem(City city) {
        this.city = city;
    }

    @Override
    public void bind(@NonNull final ItemCityBinding viewBinding, int position) {
        viewBinding.setCity(city);
        viewBinding.getRoot().setOnClickListener((view) -> {
                viewBinding.checkBox.setChecked(!viewBinding.checkBox.isChecked());
                checkCity.isCheckCity(city.isCheck());
        });
    }

    @Override
    public int getLayout() {
        return R.layout.item_city;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
