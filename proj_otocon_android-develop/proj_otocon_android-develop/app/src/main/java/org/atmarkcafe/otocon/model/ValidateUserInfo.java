package org.atmarkcafe.otocon.model;

import java.util.ArrayList;
import java.util.List;

public class ValidateUserInfo {
    ArrayList<String> name_sei;
    ArrayList<String> name_mei;
    ArrayList<String> name_kanasei;
    ArrayList<String> name_kanamei;
    ArrayList<String> gender;
    ArrayList<String> birthday;
    ArrayList<String> prefecture;
    ArrayList<String> tel;
    private String getStringFromArray(List<String> list){
        return list == null ? "" : (list.size() > 0 ? list.get(0): "");
    }

    public String getNameSei(){
        return getStringFromArray(name_sei) ;
    }
    public String getNameMei(){
        return getStringFromArray(name_mei) ;
    }
    public String getNamekanasei(){
        return getStringFromArray(name_kanasei) ;
    }

    public String getName_kanamei(){
        return getStringFromArray(name_kanamei) ;
    }

    public String getGender(){
        return getStringFromArray(gender) ;
    }

    public String getBirthday(){
        return getStringFromArray(birthday) ;
    }

    public String getPrefecture(){
        return getStringFromArray(prefecture) ;
    }

    public String getTel(){
        return getStringFromArray(tel) ;
    }
}
