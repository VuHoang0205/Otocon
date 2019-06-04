package org.atmarkcafe.otocon.function.advancedsearch;

import android.content.Context;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.function.advancedsearch.items.AreaItem;
import org.atmarkcafe.otocon.function.advancedsearch.items.CityItem;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.Area;
import org.atmarkcafe.otocon.model.response.City;
import org.atmarkcafe.otocon.model.response.CityByAreaResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AdvancedSearchCityPresenter implements AdvancedSearchCityContract.Presenter {

    private AdvancedSearchCityContract.View mView;
    private List<Area> mAreaList = new ArrayList<>();
    private List<City> mCityList = new ArrayList<>();

    public AdvancedSearchCityPresenter(AdvancedSearchCityContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onLoadArea(Context context) {
        mView.showProgress(true);
        InteractorManager.getApiInterface(context).getCitys()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityByAreaResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CityByAreaResponse cityByAreaResponse) {
                        // save to DBManager
                        DBManager.saveCityByArea(context, cityByAreaResponse);

                        mView.success(parseListGroup(cityByAreaResponse));
                        mView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        CityByAreaResponse cityByAreaResponse = DBManager.getCityByArea(context);
                        if (cityByAreaResponse != null) {
                            mView.success(parseListGroup(cityByAreaResponse));
                        } else {
                            mView.showDialogError();
                        }
                        mView.showProgress(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onSetCheckCity(String idList) {
        if (idList != null) {
            String[] ids = idList.split(",");

            for (City city : mCityList) {
                for (int i = 0; i < ids.length; i++) {
                    if (city.getId().equals(ids[i])) {
                        city.setCheck(true);
                    }
                }
            }

            for (Area area : mAreaList) {
                for (City city : area.getCityList()) {
                    if (city.isCheck()) {
                        area.setCheck(true);
                        break;
                    }
                }
            }
        }

    }

    @Override
    public void onResetCheckCity() {
        for (Area area : mAreaList) {
            for (City city : area.getCityList()) {
                city.setCheck(false);
            }
            area.setCheck(false);
        }
    }

    @Override
    public void onGetCheckCity() {
        String result = "";
        for (Area area : mAreaList) {
            for (City city : area.getCityList()) {
                if (city.isCheck())
                    result += (result.isEmpty() ? "" : ",") + city.getId();
            }
        }
        mView.getCheckCity(result);
    }

    private ArrayList<AdvancedSearchCityExpandableGroup> parseListGroup(CityByAreaResponse cityByAreaResponse) {
        mAreaList = cityByAreaResponse.getData();
        ArrayList<AdvancedSearchCityExpandableGroup> listGroup = new ArrayList<AdvancedSearchCityExpandableGroup>();
        for (Area area : cityByAreaResponse.getData()) {
            AreaItem areaItem = new AreaItem(area);
            int sumParty = 0;
            AdvancedSearchCityExpandableGroup group = new AdvancedSearchCityExpandableGroup(areaItem);
            ArrayList<CityItem> cityList = new ArrayList<>();
            for (City city : area.getCityList()) {
                mCityList.add(city);
                CityItem cityItem = new CityItem(city);
                cityList.add(cityItem);
                sumParty += city.getParty();
            }
            group.addAll(cityList);
            listGroup.add(group);
            area.setParty(sumParty);
        }
        return listGroup;
    }
}
