package org.atmarkcafe.otocon.function.mypage.item;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import org.atmarkcafe.otocon.BR;


public final class SelfIntroductionModel extends BaseObservable {
    
    @SerializedName("hobby")
    private String hobby = "";
    
    @SerializedName("nickname")
    private String nickName = "";

    @SerializedName("house")
    private String house = "";

    @SerializedName("current_address")
    private String currentAddress = "";

    @SerializedName("residence")
    private String residence = "";

    @SerializedName("height")
    private String height = "";

    @SerializedName("blood_type")
    private String bloodType = "";

    @SerializedName("family_structure")
    private String familyStructure = "";

    @SerializedName("marital_status")
    private String maritalStatus = "";

    @SerializedName("children")
    private String children = "";

    @SerializedName("profession")
    private String profession = "";

    @SerializedName("work_location")
    private String workLocation = "";

    @SerializedName("annual_income")
    private String annualIncome = "";

    @SerializedName("final_education")
    private String finalEducation = "";

    @SerializedName("state_of_final_academic_record")
    private String stateOfFinalAcademicRecord = "";

    @SerializedName("sake")
    private String sake = "";

    @SerializedName("tobacco")
    private String tobacco = "";

    @SerializedName("car")
    private String car = "";

    @SerializedName("favorite_ranking_0")
    private String favoriteRanking0 = "";

    @SerializedName("favorite_ranking_1")
    private String favoriteRanking1 = "";

    @SerializedName("favorite_ranking_2")
    private String favoriteRanking2 = "";

    @SerializedName("holiday")
    private String holiday = "";

    @SerializedName("emphasize")
    private String emphasize = "";

    @SerializedName("spend_holiday")
    private String spendHoliday = "";

    @SerializedName("ideal")
    private String ideal = "";

    @SerializedName("working_together")
    private String workingTogether = "";

    @SerializedName("promise_mary")
    private String promiseMary = "";

    @SerializedName("preference")
    private String preference = "";

    public final void setHobby(@Nullable String hobby) {
        this.hobby = hobby;
        this.notifyPropertyChanged(BR.hobby);
    }

    @Bindable
    public final String getHobby() {
        return this.hobby;
    }

    @Bindable
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        notifyPropertyChanged(BR.nickName);
    }

    @Bindable
    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
        notifyPropertyChanged(BR.house);
    }

    @Bindable
    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
        notifyPropertyChanged(BR.currentAddress);
    }

    @Bindable
    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
        notifyPropertyChanged(BR.residence);
    }

    @Bindable
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
        notifyPropertyChanged(BR.height);
    }

    @Bindable
    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
        notifyPropertyChanged(BR.bloodType);
    }

    @Bindable
    public String getFamilyStructure() {
        return familyStructure;
    }

    public void setFamilyStructure(String familyStructure) {
        this.familyStructure = familyStructure;
        notifyPropertyChanged(BR.familyStructure);
    }

    @Bindable
    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        notifyPropertyChanged(BR.maritalStatus);
    }

    @Bindable
    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
        notifyPropertyChanged(BR.children);
    }

    @Bindable
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
        notifyPropertyChanged(BR.profession);
    }

    @Bindable
    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
        notifyPropertyChanged(BR.workLocation);

    }

    @Bindable
    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
        notifyPropertyChanged(BR.annualIncome);
    }

    @Bindable
    public String getFinalEducation() {
        return finalEducation;
    }

    public void setFinalEducation(String finalEducation) {
        this.finalEducation = finalEducation;
        notifyPropertyChanged(BR.finalEducation);
    }

    @Bindable
    public String getStateOfFinalAcademicRecord() {
        return stateOfFinalAcademicRecord;
    }

    public void setStateOfFinalAcademicRecord(String stateOfFinalAcademicRecord) {
        this.stateOfFinalAcademicRecord = stateOfFinalAcademicRecord;
        notifyPropertyChanged(BR.stateOfFinalAcademicRecord);
    }

    @Bindable
    public String getSake() {
        return sake;
    }

    public void setSake(String sake) {
        this.sake = sake;
        notifyPropertyChanged(BR.sake);
    }

    @Bindable
    public String getTobacco() {
        return tobacco;
    }

    public void setTobacco(String tobacco) {
        this.tobacco = tobacco;
        notifyPropertyChanged(BR.tobacco);
    }

    @Bindable
    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
        notifyPropertyChanged(BR.car);
    }

    @Bindable
    public String getFavoriteRanking0() {
        return favoriteRanking0;
    }

    public void setFavoriteRanking0(String favoriteRanking0) {
        this.favoriteRanking0 = favoriteRanking0;
        notifyPropertyChanged(BR.favoriteRanking0);
    }

    @Bindable
    public String getFavoriteRanking1() {
        return favoriteRanking1;
    }

    public void setFavoriteRanking1(String favoriteRanking1) {
        this.favoriteRanking1 = favoriteRanking1;
        notifyPropertyChanged(BR.favoriteRanking1);
    }

    @Bindable
    public String getFavoriteRanking2() {
        return favoriteRanking2;
    }

    public void setFavoriteRanking2(String favoriteRanking2) {
        this.favoriteRanking2 = favoriteRanking2;
        notifyPropertyChanged(BR.favoriteRanking2);
    }

    @Bindable
    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
        notifyPropertyChanged(BR.holiday);
    }

    @Bindable
    public String getEmphasize() {
        return emphasize;
    }

    public void setEmphasize(String emphasize) {
        this.emphasize = emphasize;
        notifyPropertyChanged(BR.emphasize);
    }

    @Bindable
    public String getSpendHoliday() {
        return spendHoliday;
    }

    public void setSpendHoliday(String spendHoliday) {
        this.spendHoliday = spendHoliday;
        notifyPropertyChanged(BR.spendHoliday);
    }

    @Bindable
    public String getIdeal() {
        return ideal;
    }

    public void setIdeal(String ideal) {
        this.ideal = ideal;
        notifyPropertyChanged(BR.ideal);
    }

    @Bindable
    public String getWorkingTogether() {
        return workingTogether;
    }

    public void setWorkingTogether(String workingTogether) {
        this.workingTogether = workingTogether;
        notifyPropertyChanged(BR.workingTogether);
    }

    @Bindable
    public String getPromiseMary() {
        return promiseMary;
    }

    public void setPromiseMary(String promiseMary) {
        this.promiseMary = promiseMary;
        notifyPropertyChanged(BR.promiseMary);
    }

    @Bindable
    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
        notifyPropertyChanged(BR.preference);
    }
}
