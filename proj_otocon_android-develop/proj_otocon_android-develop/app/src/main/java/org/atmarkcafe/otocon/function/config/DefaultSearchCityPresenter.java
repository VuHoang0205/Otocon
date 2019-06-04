package org.atmarkcafe.otocon.function.config;

import android.content.Context;
import android.util.Log;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchCityExpandableGroup;
import org.atmarkcafe.otocon.function.advancedsearch.items.AreaItem;
import org.atmarkcafe.otocon.function.advancedsearch.items.CityItem;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.Area;
import org.atmarkcafe.otocon.model.response.City;
import org.atmarkcafe.otocon.model.response.CityByAreaResponse;
import org.atmarkcafe.otocon.utils.ValidateUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DefaultSearchCityPresenter implements DefaultSearchCityContract.Presenter {

    private DefaultSearchCityContract.View mView;
    private List<Area> mAreaList = new ArrayList<>();
    private List<City> mCityList = new ArrayList<>();

    public DefaultSearchCityPresenter(DefaultSearchCityContract.View mView) {
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
                            mView.showDialogError(ValidateUtils.isRetrofitErrorNetwork(e));
                        }
                        mView.showProgress(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    @Override
    public void onSetCheckCity(String cityCheck) {
        String[] ids = cityCheck.split(",");

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
