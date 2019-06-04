package org.atmarkcafe.otocon.function.mypage.item;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.dialog.multiple.MultipleModel;

import org.atmarkcafe.otocon.model.response.ListData;
import org.atmarkcafe.otocon.model.response.UserPropertiesRespone;

import static org.atmarkcafe.otocon.model.response.ListData.*;

import org.atmarkcafe.otocon.dialog.multiple.MultipleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserProfileParams extends BaseObservable {

    private String nickname;
    private String common_hobby;

    private String basic_body_type;
    private String basic_blood_type;
    private String basic_brother_sister;
    private String basic_marital_status;
    private String basic_height;
    private String address;
    private String type_of_preference;
    private String basic_housing;
    private String basic_residence;
    private String job_name;
    private String job_salary;
    private String job_final_education;
    private String job_last_educational_qualification;
    private String job_location;
    private String job_salary_certification;
    private String lifestyle_sort_hobby1;
    private String lifestyle_sort_hobby2;
    private String lifestyle_sort_hobby3;

    private String job_education_certification;
    private String lifestyle_sake;
    private String lifestyle_smoking;
    private String lifestyle_holiday;
    private String lifestyle_have_car;

    private String lifestyle_sex;
    private String marriage_time;
    private String marriage_partner_selection;
    private String marriage_holiday;
    private String marriage_ideal_wife;
    private String after_marriage_job;
    private String after_marriage_committed;
    private String basic_child_status;
    private String job_location_prefecture;

    public String getCommon_hobby() {
        return common_hobby;
    }

    public String getBasic_body_type() {
        return basic_body_type;
    }

    public void setBasic_body_type(String basic_body_type) {
        this.basic_body_type = basic_body_type;
    }

    public String getBasic_blood_type() {
        return basic_blood_type;
    }

    public String getBasic_child_status() {
        return basic_child_status;
    }

    public void setBasic_child_status(String basic_child_status) {
        this.basic_child_status = basic_child_status;
    }

    public String getLifestyle_sort_hobby1() {
        return lifestyle_sort_hobby1;
    }

    public void setLifestyle_sort_hobby1(String lifestyle_sort_hobby1) {
        this.lifestyle_sort_hobby1 = lifestyle_sort_hobby1;
    }

    public String getLifestyle_sort_hobby2() {
        return lifestyle_sort_hobby2;
    }

    public void setLifestyle_sort_hobby2(String lifestyle_sort_hobby2) {
        this.lifestyle_sort_hobby2 = lifestyle_sort_hobby2;
    }

    public String getLifestyle_sort_hobby3() {
        return lifestyle_sort_hobby3;
    }

    public void setLifestyle_sort_hobby3(String lifestyle_sort_hobby3) {
        this.lifestyle_sort_hobby3 = lifestyle_sort_hobby3;
    }

    public String getJob_location_prefecture() {
        return job_location_prefecture;
    }

    public void setJob_location_prefecture(String job_location_prefecture) {
        this.job_location_prefecture = job_location_prefecture;
    }

    public void setField(String value, ListData key) {


        switch (key) {
            case yesNos:
                setLifestyle_have_car(value);
                break;

            case residences:
                setBasic_residence(value);
                break;
            case basicHousings:
                setBasic_housing(value);
                break;
            case bloodTypes:
                setBasic_blood_type(value);
                break;
            case brotherSisters:
                setBasic_brother_sister(value);
                break;
            case jobNames:
                setJob_name(value);
                break;
            case jobSalarys:
                setJob_salary(value);
                break;
            case finalEducations:
                setJob_final_education(value);
                break;
            case lastEducationalQualifications:
                setJob_last_educational_qualification(value);
                break;
            case lifestyleSexs:
                setLifestyle_sex(value);
                break;
            case marriageHolidays:
                setMarriage_holiday(value);
                break;
            case lifestyleHolidays:
                setLifestyle_holiday(value);
                break;
            case lifestyleSakes:
                setLifestyle_sake(value);
                break;
            case marriageTimes:
                setMarriage_time(value);
                break;
            case lifestyleSmokings:
                setLifestyle_smoking(value);
                break;
            case afterMarriageJobs:
                setAfter_marriage_job(value);
                break;
            case basic_marital_status:
                setBasic_marital_status(value);
                break;
            case height:
                setBasic_height(value);
                break;
            case marriagepartnerSelections:
                setMarriage_partner_selection(value);
                break;
            case childStatus:
                setBasic_child_status(value);
                break;
            case prefecture:
                setJob_location_prefecture(value);
                break;

        }

    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void setCommon_hobby(String common_hobby) {
        this.common_hobby = common_hobby;
        notifyPropertyChanged(BR.common_hobby);
    }


    public void setBasic_blood_type(String basic_blood_type) {
        this.basic_blood_type = basic_blood_type;
        notifyPropertyChanged(BR.basic_blood_type);

    }

    @Bindable
    public String getBasic_brother_sister() {
        return basic_brother_sister;
    }

    public void setBasic_brother_sister(String basic_brother_sister) {
        this.basic_brother_sister = basic_brother_sister;
        notifyPropertyChanged(BR.basic_brother_sister);

    }

    @Bindable
    public String getBasic_marital_status() {
        return basic_marital_status;
    }

    public void setBasic_marital_status(String basic_marital_status) {
        this.basic_marital_status = basic_marital_status;
        notifyPropertyChanged(BR.basic_marital_status);

    }

    public String getBasic_height() {
        return basic_height;
    }

    public void setBasic_height(String basic_height) {
        this.basic_height = basic_height;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_of_preference() {
        return type_of_preference;
    }

    public void setType_of_preference(String type_of_preference) {
        this.type_of_preference = type_of_preference;

    }

    public String getBasic_housing() {
        return basic_housing;
    }

    public void setBasic_housing(String basic_housing) {
        this.basic_housing = basic_housing;

    }


    public String getBasic_residence() {
        return basic_residence;
    }

    public void setBasic_residence(String basic_residence) {
        this.basic_residence = basic_residence;

    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
        notifyPropertyChanged(BR.job_name);
    }

    public String getJob_salary() {
        return job_salary;
    }

    public void setJob_salary(String job_salary) {
        this.job_salary = job_salary;
    }

    public String getJob_final_education() {
        return job_final_education;
    }

    public void setJob_final_education(String job_final_education) {
        this.job_final_education = job_final_education;
    }

    public String getJob_last_educational_qualification() {
        return job_last_educational_qualification;
    }

    public void setJob_last_educational_qualification(String job_last_educational_qualification) {
        this.job_last_educational_qualification = job_last_educational_qualification;
    }


    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;

    }

    public String getJob_salary_certification() {
        return job_salary_certification;
    }

    public void setJob_salary_certification(String job_salary_certification) {
        this.job_salary_certification = job_salary_certification;
    }

    public String getJob_education_certification() {
        return job_education_certification;
    }

    public void setJob_education_certification(String job_education_certification) {
        this.job_education_certification = job_education_certification;
    }

    public String getLifestyle_sake() {
        return lifestyle_sake;
    }

    public void setLifestyle_sake(String lifestyle_sake) {
        this.lifestyle_sake = lifestyle_sake;
    }



    public String getLifestyle_smoking() {
        return lifestyle_smoking;
    }

    public void setLifestyle_smoking(String lifestyle_smoking) {
        this.lifestyle_smoking = lifestyle_smoking;
    }

    public String getLifestyle_holiday() {
        return lifestyle_holiday;
    }

    public void setLifestyle_holiday(String lifestyle_holiday) {
        this.lifestyle_holiday = lifestyle_holiday;
    }

    public String getLifestyle_have_car() {
        return lifestyle_have_car;
    }

    public void setLifestyle_have_car(String lifestyle_have_car) {
        this.lifestyle_have_car = lifestyle_have_car;
    }

    public String getLifestyle_sex() {
        return lifestyle_sex;
    }

    public void setLifestyle_sex(String lifestyle_sex) {
        this.lifestyle_sex = lifestyle_sex;

    }

    public String getMarriage_time() {
        return marriage_time;
    }

    public void setMarriage_time(String marriage_time) {
        this.marriage_time = marriage_time;
    }


    public String getMarriage_partner_selection() {
        return marriage_partner_selection;
    }

    public void setMarriage_partner_selection(String marriage_partner_selection) {
        this.marriage_partner_selection = marriage_partner_selection;
    }

    public String getMarriage_holiday() {
        return marriage_holiday;
    }

    public void setMarriage_holiday(String marriage_holiday) {
        this.marriage_holiday = marriage_holiday;
    }

    public String getMarriage_ideal_wife() {
        return marriage_ideal_wife;
    }

    public void setMarriage_ideal_wife(String marriage_ideal_wife) {
        this.marriage_ideal_wife = marriage_ideal_wife;
        notifyPropertyChanged(BR.marriage_ideal_wife);
    }

    public String getAfter_marriage_job() {
        return after_marriage_job;
    }

    public void setAfter_marriage_job(String after_marriage_job) {
        this.after_marriage_job = after_marriage_job;
    }

    public String getAfter_marriage_committed() {
        return after_marriage_committed;
    }

    public void setAfter_marriage_committed(String after_marriage_committed) {
        this.after_marriage_committed = after_marriage_committed;
    }

    public void setDataUserParams(UserProfileParams params, ProfileNameModel data) {
        params.common_hobby = data.getCommon_hobby();
        params.nickname = data.getNickname();
        params.lifestyle_sort_hobby1 = data.getRanking_data_introduce_1();
        params.lifestyle_sort_hobby2 = data.getRanking_data_introduce_2();
        params.lifestyle_sort_hobby3 = data.getRanking_data_introduce_3();
        params.marriage_ideal_wife = data.getMarriage_ideal_wife();
        params.setAddress(data.getAddress());
        params.type_of_preference = data.getType_of_preference();
        params.after_marriage_committed=data.getAfter_marriage_committed();
        //set data id Family
        params.basic_brother_sister = data.getIdFamily();
        params.lifestyle_holiday=data.getIdBasicHoliday();
        if (data.isInCountry()) {
            params.job_location="0";
        }else{
            params.job_location="1";
        }
    }

    public void setDefalutParam(UserProfileParams param,UserProfileModel data){
        param.setCommon_hobby(data.common_hobby);
        param.setNickname(data.nickname);
        param.setBasic_housing(getIdData(data.basic_housing));

        //Todo
        param.setAddress(data.address);

        param.setBasic_residence(getIdData(data.basic_residence));
        param.setBasic_height(getIdData(data.basic_height));
        param.setBasic_blood_type(getIdData(data.basic_blood_type));
        param.setJob_name(getIdData(data.job_name));
        param.setJob_salary(getIdData(data.job_salary));
        param.setJob_final_education(getIdData(data.job_final_education));
        param.setJob_last_educational_qualification(getIdData(data.job_last_educational_qualification));
        param.setLifestyle_sake(getIdData(data.lifestyle_sake));
        param.setLifestyle_smoking(getIdData(data.lifestyle_smoking));
        param.setMarriage_time(getIdData(data.marriage_time));
        param.setMarriage_partner_selection(getIdData(data.marriage_partner_selection));
        param.setMarriage_holiday(getIdData(data.marriage_holiday));
        param.setMarriage_ideal_wife(data.marriage_ideal_wife);
        param.setAfter_marriage_job(getIdData(data.after_marriage_job));
        param.setType_of_preference(data.type_of_preference);
        param.setBasic_marital_status(getIdData(data.basic_marital_status));
        param.setAfter_marriage_committed(data.after_marriage_committed);
        param.setJob_location(getIdData(data.job_location));
    }

    public String getIdData(MultipleModel model) {
        return model == null ? "" : model.getId()+"";
    }

}
