package org.atmarkcafe.otocon.model.response;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;


import org.atmarkcafe.otocon.BR;

import java.lang.reflect.Type;
import java.util.List;

public class City extends BaseObservable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("total_party")
    private int party;

    private boolean check;

    public static Type type = new TypeToken<List<City>>() {}.getType();

    public City(String id, String name, int party) {
        this.id = id;
        this.name = name;
        this.party = party;
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
