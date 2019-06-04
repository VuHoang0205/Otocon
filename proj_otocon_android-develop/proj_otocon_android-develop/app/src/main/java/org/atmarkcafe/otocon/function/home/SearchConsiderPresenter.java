package org.atmarkcafe.otocon.function.home;

import android.content.Context;
import android.databinding.ObservableField;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.function.home.items.HomeAreaItem;
import org.atmarkcafe.otocon.function.home.items.HomeCityItem;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.Area;
import org.atmarkcafe.otocon.model.response.City;
import org.atmarkcafe.otocon.model.response.CityByAreaResponse;
import org.atmarkcafe.otocon.pref.SearchDefault;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchConsiderPresenter {
    public final ObservableField<String> showAreaId = new ObservableField<>("");
    public final ObservableField<String> focusAreaId = new ObservableField<>("");
    public final List<Area> areaList = new ArrayList<>();
    public CalendarDayOfWeekAdapter mDayOfWeekAdapter;
    public GroupAdapter areaAdapter;
    public GroupAdapter[] cityAdapters;
    private HomeFragment.OnChangedSearchConditionListener mSearchConditionListener;

    public SearchConsiderPresenter(HomeFragment.OnChangedSearchConditionListener listener) {
        this.mSearchConditionListener = listener;
        mDayOfWeekAdapter = new CalendarDayOfWeekAdapter(mSearchConditionListener);
        areaAdapter = new GroupAdapter();
    }

    public void resetData(Context ctx){
        // load cities
        CityByAreaResponse cityByAreaResponse = DBManager.getCityByArea(ctx);
        this.areaList.clear();

        if(cityByAreaResponse!= null) {
            this.areaList.addAll(cityByAreaResponse.getData());
        }

        // remove item in areaAdapter if exist
        while (areaAdapter.getItemCount()>0){
            areaAdapter.removeGroup(0);
        }

        // add item for areaAdapter and cityAdapter
        Section areaSection = new Section();
        cityAdapters = new GroupAdapter[areaList.size()];
        for (int index = 0; index < areaList.size(); index++) {
            Area area = areaList.get(index);
            HomeAreaItem areaItem = new HomeAreaItem(area, focusAreaId).setIndex(index);
            areaSection.add(areaItem);
            initCityAdapter(ctx, areaItem);
        }
        areaAdapter.add(areaSection);

        SearchDefault searchDefault = SearchDefault.getInstance().init(ctx);
        setCheckedCities(searchDefault.getCitys());
        mDayOfWeekAdapter.updateSelectedDay(searchDefault.getEventDate());
    }

    public void setCheckedCities(String idList) {
        String[] ids = idList.split(",");
        Set<String> setIds = new HashSet<>();
        setIds.addAll(Arrays.asList(ids));
        for (int i = 0; i < cityAdapters.length; i++) {
            int count = 0;
            for (int iCity = 1; iCity < cityAdapters[i].getItemCount(); iCity++) {
                HomeCityItem cityItem = (HomeCityItem) cityAdapters[i].getItem(iCity);
                if (setIds.contains(cityItem.getCity().getId())) {
                    cityItem.setChecked(true);
                    count++;
                } else {
                    cityItem.setChecked(false);
                }
            }
            if (count == cityAdapters[i].getItemCount() - 1) {
                HomeCityItem cityAllItem = (HomeCityItem) cityAdapters[i].getItem(0);
                cityAllItem.setChecked(true);
                removeCheckedAllCities(cityAdapters[i]);
            }
            HomeAreaItem areaItem = (HomeAreaItem) areaAdapter.getItem(i);
            areaItem.setCountCityChecked(count);
        }
    }

    private void removeCheckedAllCities(GroupAdapter cityAdapter) {
        for (int iCity = 1; iCity < cityAdapter.getItemCount(); iCity++) {
            HomeCityItem cityItem = (HomeCityItem) cityAdapter.getItem(iCity);
            cityItem.setChecked(false);
        }
    }

    private String getCheckedCity() {
        String result = "";
        for (GroupAdapter groupAdapter : cityAdapters) {
            HomeCityItem cityAllItem = (HomeCityItem) groupAdapter.getItem(0);
            for (int i = 1; i < groupAdapter.getItemCount(); i++) {
                HomeCityItem cityItem = (HomeCityItem) groupAdapter.getItem(i);
                if (cityAllItem.isChecked() || cityItem.isChecked()) {
                    result += (result.isEmpty() ? "" : ",") + cityItem.getCity().getId();
                }
            }
        }
        return result;
    }

    private void initCityAdapter(Context ctx, HomeAreaItem areaItem) {
        cityAdapters[areaItem.getIndex()] = new GroupAdapter();
        Section citySection = new Section();
        City allCity = new City("", ctx.getString(R.string.all), areaItem.getArea().getParty());
        final HomeCityItem allCityItem = new HomeCityItem(allCity);
        citySection.add(allCityItem);
        for (City city : areaItem.getArea().getCityList()) {
            citySection.add(new HomeCityItem(city));
        }
        cityAdapters[areaItem.getIndex()].setOnItemClickListener((item, view) -> {
            HomeCityItem cityItem = (HomeCityItem) item;
            boolean newCheched = !cityItem.isChecked();
            int countCityChecked = 0;
            cityItem.setChecked(newCheched);
            if (cityItem.getCity().getId().equals("")) {
                // remove check when check all
                removeCheckedAllCities(cityAdapters[areaItem.getIndex()]);
                countCityChecked = newCheched ? cityAdapters[areaItem.getIndex()].getItemCount() : 0;
            } else if (allCityItem.isChecked()) {
                allCityItem.setChecked(false);
                countCityChecked = 1;
            } else {
                countCityChecked = areaItem.getCountCityChecked() + (newCheched ? 1 : -1);

                // check all when check full
                if (countCityChecked == areaItem.getArea().getCityList().size()){
                    removeCheckedAllCities(cityAdapters[areaItem.getIndex()]);
                    allCityItem.setChecked(true);
                }
            }

            if ((countCityChecked > 0) != (areaItem.getCountCityChecked() > 0)) {
                // changed checked to notify adapter
                areaItem.setCountCityChecked(countCityChecked);
                areaAdapter.notifyItemChanged(areaItem.getIndex());
            } else {
                areaItem.setCountCityChecked(countCityChecked);
            }
            cityAdapters[areaItem.getIndex()].notifyDataSetChanged();
            if (mSearchConditionListener != null) {
                mSearchConditionListener.onChangedCity(getCheckedCity());
            }
        });
        cityAdapters[areaItem.getIndex()].add(citySection);
    }
}