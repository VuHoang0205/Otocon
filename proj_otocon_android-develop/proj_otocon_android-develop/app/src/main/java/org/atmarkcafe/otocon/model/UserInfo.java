package org.atmarkcafe.otocon.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.atmarkcafe.otocon.BR;
import com.google.gson.Gson;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.model.response.Prefecture;
import org.atmarkcafe.otocon.pref.BaseShareReferences;

import java.util.List;

public class UserInfo extends BaseObservable {
    private int id;
    //    private String email;
//    private String name;
    private String name_sei;
    private String name_mei;
    private String name_kanasei;
    private String name_kanamei;
    private int gender;
    private String birthday;
    private int prefecture;
    private String ponta_id;
    private String tel;

    private String year;
    private String month;
    private String day;
    public String tel1;
    public String tel2;
    public String tel3;
    private String nameGender;
    private String namePrefecture;

    public void setDefaul(Context context) {
        year = getBirthdayIndex(1);
        month = getBirthdayIndex(2);
        day = getBirthdayIndex(3);
        tel1 = getTelIndex(1);
        tel2 = getTelIndex(2);
        tel3 = getTelIndex(3);
        nameGender = gender == 2 ? "女性" : "男性";
        namePrefecture = namePrefexture(context);
    }

    public void setLate() {
        if (!tel1.isEmpty() || !tel2.isEmpty() || !tel3.isEmpty()) {
            tel = tel1 + "-" + tel2 + "-" + tel3;
        }
        birthday = String.format("%s-%s-%s", year, fomatDate(month), fomatDate(day));
    }

    private String getTelIndex(int index) {
        if (tel.isEmpty()) return "";
        else {
            String[] tels = tel.split("-");
            switch (index) {
                case 1:
                    return tels[0];
                case 2:
                    return tels[1];
                case 3:
                    return tels[2];
            }
        }
        return "";
    }

    private String getBirthdayIndex(int index) {
        if (birthday.isEmpty()) return "";
        else {
            String[] days = birthday.split("-");
            switch (index) {
                case 1:
                    return String.valueOf(Integer.parseInt(days[0]));
                case 2:
                    return String.valueOf(Integer.parseInt(days[1]));
                case 3:
                    return String.valueOf(Integer.parseInt(days[2]));
            }
        }
        return "";
    }

    public String getTextGender() {
        return gender == 2 ? "女性" : "男性";
    }

    public String getBirthdayText() {
        if (this.birthday == null || this.birthday.isEmpty()) {
            return "";
        }
        return String.format("%s年%s月%s日", year, month, day);
    }

    public String namePrefexture(Context context) {
        String json = new BaseShareReferences(context).get(BaseShareReferences.KEY_PREFECTURE);
        Gson gson = new Gson();
        List<Prefecture> list = gson.fromJson(json, Prefecture.typeList);
        for (Prefecture item : list) {
            if (item.getId().equals(String.valueOf(prefecture))) {
                return item.getName();
            }
        }
        return "";
    }

    private static String fomatDate(String data) {
        if (data == null) {
            return "";
        }
        return String.format("%s%s", data.length() == 1 ? "0" : "", data);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_sei() {
        return name_sei;
    }

    public void setName_sei(String name_sei) {
        this.name_sei = name_sei;
    }

    public String getName_mei() {
        return name_mei;
    }

    public void setName_mei(String name_mei) {
        this.name_mei = name_mei;
    }

    public String getName_kanasei() {
        return name_kanasei;
    }

    public void setName_kanasei(String name_kanasei) {
        this.name_kanasei = name_kanasei;
    }

    public String getName_kanamei() {
        return name_kanamei;
    }

    public void setName_kanamei(String name_kanamei) {
        this.name_kanamei = name_kanamei;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(int prefecture) {
        this.prefecture = prefecture;
    }

    public void setPrefecture(int prefecture, Context context) {
        this.prefecture = prefecture;
    }

    public String getPonta_id() {
        return ponta_id;
    }

    public void setPonta_id(String ponta_id) {
        this.ponta_id = ponta_id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Bindable
    public String getNameGender() {
        return nameGender;
    }

    public void setNameGender(String nameGender) {
        this.nameGender = nameGender;
        notifyPropertyChanged(BR.nameGender);
    }

    public String getFullName() {
        return name_sei + " " + name_mei;
    }

    public String getFullNameKana() {
        return name_kanasei + " " + name_kanamei;
    }

    @Bindable
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    @Bindable
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
        notifyPropertyChanged(BR.month);
    }

    @Bindable
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    @Bindable
    public String getNamePrefecture() {
        return namePrefecture;
    }

    public void setNamePrefecture(String namePrefecture) {
        this.namePrefecture = namePrefecture;
        notifyPropertyChanged(BR.namePrefecture);
    }
}
