package org.atmarkcafe.otocon.model.response;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;


import org.atmarkcafe.otocon.BR;

import java.util.ArrayList;

public class Area extends BaseObservable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("city")
    private ArrayList<City> cityList = new ArrayList<>();

    private int party;

    private boolean check;

    public Area(String id, String name, ArrayList<City> cityList) {
        this.id = id;
        this.name = name;
        this.cityList = cityList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    @Bindable
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
        notifyPropertyChanged(BR.check);
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public String getStringTotalParty() {
        return "(" + party + ")";
    }
}
