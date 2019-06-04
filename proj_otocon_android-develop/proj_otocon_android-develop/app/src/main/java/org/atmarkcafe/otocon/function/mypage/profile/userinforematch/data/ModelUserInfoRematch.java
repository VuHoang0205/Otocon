package org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.model.response.ListData;

public class ModelUserInfoRematch extends BaseObservable {
    private String common_description;
    private String basic_language;
    private String basic_body_type;
    private String job_description;
    private String job_university;

    private String lifestyle_character;
    private String lifestyle_sociability;
    private String lifestyle_charm_point;
    private String lifestyle_cohabitant;
    private String marriage_hope;

    private String marriage_friend;
    private String marriage_first_date_cost;
    private String marriage_hobby;
    private String after_marriage_house_work;
    private String after_marriage_parent;

    // get id pick only
    public String getIdsField(ListData key){
        switch (key){
            case bodyTypes:
                return basic_body_type;
            case marriageHopes:
                return marriage_hope;
            case marriageFriends:
                return marriage_friend;
            case marriagefirstdateCosts:
                return marriage_first_date_cost;
            case marriagehobbys:
                return marriage_hobby;
            case aftermarriagehouseWorks:
                return after_marriage_house_work;
            case afterMarriageParents:
                return after_marriage_parent;
        }
        return "";
    }

    public void setParams(UserInfoRematchParams params){
        common_description = params.getCommon_description();
        job_description = params.getJob_description();
        job_university = params.getJob_university();
    }

    public void setRepons(InfoReMatch repons){
        common_description = repons.common_description;
        basic_language = repons.getTextName(repons.basic_language);
        basic_body_type = repons.getTextName(repons.basic_body_type);
        job_description = repons.job_description;
        job_university = repons.job_university;
        lifestyle_character = repons.getTextName(repons.lifestyle_character);
        lifestyle_sociability = repons.getTextName(repons.lifestyle_sociability);
        lifestyle_charm_point = repons.getTextName(repons.lifestyle_charm_point);
        lifestyle_cohabitant = repons.getTextName(repons.lifestyle_cohabitant);
        marriage_hope = repons.getTextName(repons.marriage_hope);
        marriage_friend = repons.getTextName(repons.marriage_friend);
        marriage_first_date_cost = repons.getTextName(repons.marriage_first_date_cost);
        marriage_hobby = repons.getTextName(repons.marriage_hobby);
        after_marriage_house_work = repons.getTextName(repons.after_marriage_house_work);
        after_marriage_parent = repons.getTextName(repons.after_marriage_parent);
    }

    public void setField(String value, ListData key){
        switch (key){
            case languages:
                setBasic_language(value);
                break;
            case bodyTypes:
                setBasic_body_type(value);
                break;
            case lifestyleCharacters:
                setLifestyle_character(value);
                break;
            case lifestyleSociabilitys:
                setLifestyle_sociability(value);
                break;
            case lifestyleCharmPoints:
                setLifestyle_charm_point(value);
                break;
            case lifestyleCohabitants:
                setLifestyle_cohabitant(value);
                break;
            case marriageHopes:
                setMarriage_hope(value);
                break;
            case marriageFriends:
                setMarriage_friend(value);
                break;
            case marriagefirstdateCosts:
                setMarriage_first_date_cost(value);
                break;
            case marriagehobbys:
                setMarriage_hobby(value);
                break;
            case aftermarriagehouseWorks:
                setAfter_marriage_house_work(value);
                break;
            case afterMarriageParents:
                setAfter_marriage_parent(value);
                break;
        }
    }

    @Bindable
    public String getCommon_description() {
        return common_description;
    }

    public void setCommon_description(String common_description) {
        this.common_description = common_description;
        notifyPropertyChanged(BR.common_description);
    }

    @Bindable
    public String getBasic_language() {
        return basic_language;
    }

    public void setBasic_language(String basic_language) {
        this.basic_language = basic_language;
        notifyPropertyChanged(BR.basic_language);
    }

    @Bindable
    public String getBasic_body_type() {
        return basic_body_type;
    }

    public void setBasic_body_type(String basic_body_type) {
        this.basic_body_type = basic_body_type;
        notifyPropertyChanged(BR.basic_body_type);
    }

    @Bindable
    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
        notifyPropertyChanged(BR.job_description);
    }

    @Bindable
    public String getJob_university() {
        return job_university;
    }

    public void setJob_university(String job_university) {
        this.job_university = job_university;
        notifyPropertyChanged(BR.job_university);
    }

    @Bindable
    public String getLifestyle_character() {
        return lifestyle_character;
    }

    public void setLifestyle_character(String lifestyle_character) {
        this.lifestyle_character = lifestyle_character;
        notifyPropertyChanged(BR.lifestyle_character);
    }

    @Bindable
    public String getLifestyle_sociability() {
        return lifestyle_sociability;
    }

    public void setLifestyle_sociability(String lifestyle_sociability) {
        this.lifestyle_sociability = lifestyle_sociability;
        notifyPropertyChanged(BR.lifestyle_sociability);
    }

    @Bindable
    public String getLifestyle_charm_point() {
        return lifestyle_charm_point;
    }

    public void setLifestyle_charm_point(String lifestyle_charm_point) {
        this.lifestyle_charm_point = lifestyle_charm_point;
        notifyPropertyChanged(BR.lifestyle_charm_point);
    }

    @Bindable
    public String getLifestyle_cohabitant() {
        return lifestyle_cohabitant;
    }

    public void setLifestyle_cohabitant(String lifestyle_cohabitant) {
        this.lifestyle_cohabitant = lifestyle_cohabitant;
        notifyPropertyChanged(BR.lifestyle_cohabitant);
    }

    @Bindable
    public String getMarriage_hope() {
        return marriage_hope;
    }

    public void setMarriage_hope(String marriage_hope) {
        this.marriage_hope = marriage_hope;
        notifyPropertyChanged(BR.marriage_hope);
    }

    @Bindable
    public String getMarriage_friend() {
        return marriage_friend;
    }

    public void setMarriage_friend(String marriage_friend) {
        this.marriage_friend = marriage_friend;
        notifyPropertyChanged(BR.marriage_friend);
    }

    @Bindable
    public String getMarriage_first_date_cost() {
        return marriage_first_date_cost;
    }

    public void setMarriage_first_date_cost(String marriage_first_date_cost) {
        this.marriage_first_date_cost = marriage_first_date_cost;
        notifyPropertyChanged(BR.marriage_first_date_cost);
    }

    @Bindable
    public String getMarriage_hobby() {
        return marriage_hobby;
    }

    public void setMarriage_hobby(String marriage_hobby) {
        this.marriage_hobby = marriage_hobby;
        notifyPropertyChanged(BR.marriage_hobby);
    }

    @Bindable
    public String getAfter_marriage_house_work() {
        return after_marriage_house_work;
    }

    public void setAfter_marriage_house_work(String after_marriage_house_work) {
        this.after_marriage_house_work = after_marriage_house_work;
        notifyPropertyChanged(BR.after_marriage_house_work);
    }

    @Bindable
    public String getAfter_marriage_parent() {
        return after_marriage_parent;
    }

    public void setAfter_marriage_parent(String after_marriage_parent) {
        this.after_marriage_parent = after_marriage_parent;
        notifyPropertyChanged(BR.after_marriage_parent);
    }
}
