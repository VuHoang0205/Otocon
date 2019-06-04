package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorContactResponse {

    @SerializedName("email")
    List<String> emails;

    @SerializedName("name_kanasei")
    List<String> nameKanaseis;

    @SerializedName("name_kanamei")
    List<String> nameKanameis;

    @SerializedName("name_sei")
    List<String> nameSeis;

    @SerializedName("name_mei")
    List<String> nameMeis;

    @SerializedName("gender")
    List<String> gender;


    @SerializedName("birthday")
    List<String> birthdays;

    @SerializedName("tel")
    List<String> tels;

    @SerializedName("prefecture")
    List<String> prefectures;

    @SerializedName("terms_of_service")
    List<String> termsOfService;

    @SerializedName("content")
    List<String> contents;

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public List<String> getTermsOfService() {
        return termsOfService;
    }

    public List<String> getEmails() {
        return emails;
    }


    public List<String> getNameKanaseis() {
        return nameKanaseis;
    }

    public List<String> getNameKanameis() {
        return nameKanameis;
    }

    public List<String> getNameSeis() {
        return nameSeis;
    }

    public List<String> getNameMeis() {
        return nameMeis;
    }

    public List<String> getGender() {
        return gender;
    }

    public List<String> getBirthdays() {
        return birthdays;
    }

    public List<String> getTels() {
        return tels;
    }

    public List<String> getPrefectures() {
        return prefectures;
    }
}
