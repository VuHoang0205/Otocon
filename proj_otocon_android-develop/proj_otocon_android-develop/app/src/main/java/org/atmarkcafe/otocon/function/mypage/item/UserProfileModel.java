package org.atmarkcafe.otocon.function.mypage.item;


import org.atmarkcafe.otocon.dialog.multiple.MultipleModel;


import java.util.List;

public class UserProfileModel {
    public String user_id;
    public String nickname;
    public String common_hobby;
    public String common_diagnosis;
    public String common_like_count;
    public String common_completed;
    public String common_description;
    public String premium_expired_date;

    public List<MultipleModel> basic_language;

    public MultipleModel basic_body_type;
    public MultipleModel basic_blood_type;
    public List<MultipleModel> basic_brother_sister;
    public MultipleModel basic_marital_status;
    public MultipleModel basic_child_status;
    public MultipleModel basic_weight;
    public MultipleModel basic_height;
    public String address;
    public String type_of_preference;
    public MultipleModel basic_housing;
    public MultipleModel basic_hometown;
    public MultipleModel basic_hometown_prefecture;
    public MultipleModel basic_residence;
    public MultipleModel job_name;
    public MultipleModel job_salary;
    public MultipleModel job_final_education;
    public MultipleModel job_last_educational_qualification;

    public String job_description;

    MultipleModel job_location;
    MultipleModel job_location_prefecture;

    String job_salary_certification;
    String job_education_certification;

    List<MultipleModel> lifestyle_character;
    List<MultipleModel> lifestyle_sociability;
    List<MultipleModel> lifestyle_charm_point;

    MultipleModel lifestyle_sake;
    MultipleModel lifestyle_smoking;

    List<MultipleModel> lifestyle_cohabitant;
    List<MultipleModel> lifestyle_holiday;

    MultipleModel lifestyle_have_car;
    String lifestyle_sort_hobby;
    String lifestyle_sort_hobby1;
    String lifestyle_sort_hobby2;
    String lifestyle_sort_hobby3;

    MultipleModel lifestyle_sex;
    MultipleModel marriage_time;
    MultipleModel marriage_hope;
    MultipleModel marriage_friend;
    MultipleModel marriage_first_date_cost;
    MultipleModel marriage_partner_selection;
    MultipleModel marriage_hobby;
    MultipleModel marriage_holiday;

    String marriage_ideal_wife;

    MultipleModel after_marriage_job;
    MultipleModel after_marriage_house_work;
    MultipleModel after_marriage_parent;

    String after_marriage_committed;
    String deleted_at;
    String created_at;
    String updated_at;

    String total_rematch;
    String total_card;
    String complete_rematch;
    String complete_card;

    String total_info;
    String complete_info;


    public String getTotal_rematch() {
        return total_rematch;
    }

    public void setTotal_rematch(String total_rematch) {
        this.total_rematch = total_rematch;
    }

    public String getTotal_card() {
        return total_card;
    }

    public void setTotal_card(String total_card) {
        this.total_card = total_card;
    }

    public String getComplete_rematch() {
        return complete_rematch;
    }

    public void setComplete_rematch(String complete_rematch) {
        this.complete_rematch = complete_rematch;
    }

    public String getComplete_card() {
        return complete_card;
    }

    public void setComplete_card(String complete_card) {
        this.complete_card = complete_card;
    }

    public String getTotal_info() {
        return total_info;
    }

    public void setTotal_info(String total_info) {
        this.total_info = total_info;
    }

    public String getComplete_info() {
        return complete_info;
    }

    public void setComplete_info(String complete_info) {
        this.complete_info = complete_info;
    }
}
