package org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.dialog.multiple.MultipleModel;
import org.atmarkcafe.otocon.model.response.ListData;

import java.util.List;

public class InfoReMatch {
    String common_description;
    List<MultipleModel> basic_language;
    MultipleModel basic_body_type;
    String job_description;
    String job_university;
    List<MultipleModel> lifestyle_character;
    List<MultipleModel> lifestyle_sociability;
    List<MultipleModel> lifestyle_charm_point;
    List<MultipleModel> lifestyle_cohabitant;
    MultipleModel marriage_hope;
    MultipleModel marriage_friend;
    MultipleModel marriage_first_date_cost;
    MultipleModel marriage_hobby;
    MultipleModel after_marriage_house_work;
    MultipleModel after_marriage_parent;

    @SerializedName("total_rematch")
    String total_rematch;

    @SerializedName("complete_rematch")
    String complete_rematch;

    public String getTextName(MultipleModel value) {
        if (value == null) {
            return null;
        } else {
            return value.getTitle();
        }
    }

    public String getTextName(List<MultipleModel> value){
        if (value == null){
            return null;
        }else {
            String names = "";
            for (MultipleModel model : value) {
                names = names + (names.isEmpty() ? "" : ",") + model.getTitle();
            }
            return names;
        }
    }


    public String getTextId(MultipleModel value) {
        if (value == null) {
            return null;
        } else {
            return value.getId()+"";
        }
    }

    public String getTextId(List<MultipleModel> value){
        if (value == null){
            return null;
        }else {
            String names = "";
            for (MultipleModel model : value) {
                names = names + (names.isEmpty() ? "" : ",") + model.getId();
            }
            return names;
        }
    }

    public String getCommon_description() {
        return common_description;
    }

    public void setCommon_description(String common_description) {
        this.common_description = common_description;
    }

    public List<MultipleModel> getBasic_language() {
        return basic_language;
    }

    public void setBasic_language(List<MultipleModel> basic_language) {
        this.basic_language = basic_language;
    }

    public MultipleModel getBasic_body_type() {
        return basic_body_type;
    }

    public void setBasic_body_type(MultipleModel basic_body_type) {
        this.basic_body_type = basic_body_type;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getJob_university() {
        return job_university;
    }

    public void setJob_university(String job_university) {
        this.job_university = job_university;
    }

    public List<MultipleModel> getLifestyle_character() {
        return lifestyle_character;
    }

    public void setLifestyle_character(List<MultipleModel> lifestyle_character) {
        this.lifestyle_character = lifestyle_character;
    }

    public List<MultipleModel> getLifestyle_sociability() {
        return lifestyle_sociability;
    }

    public void setLifestyle_sociability(List<MultipleModel> lifestyle_sociability) {
        this.lifestyle_sociability = lifestyle_sociability;
    }

    public List<MultipleModel> getLifestyle_charm_point() {
        return lifestyle_charm_point;
    }

    public void setLifestyle_charm_point(List<MultipleModel> lifestyle_charm_point) {
        this.lifestyle_charm_point = lifestyle_charm_point;
    }

    public List<MultipleModel> getLifestyle_cohabitant() {
        return lifestyle_cohabitant;
    }

    public void setLifestyle_cohabitant(List<MultipleModel> lifestyle_cohabitant) {
        this.lifestyle_cohabitant = lifestyle_cohabitant;
    }

    public MultipleModel getMarriage_hope() {
        return marriage_hope;
    }

    public void setMarriage_hope(MultipleModel marriage_hope) {
        this.marriage_hope = marriage_hope;
    }

    public MultipleModel getMarriage_friend() {
        return marriage_friend;
    }

    public void setMarriage_friend(MultipleModel marriage_friend) {
        this.marriage_friend = marriage_friend;
    }

    public MultipleModel getMarriage_first_date_cost() {
        return marriage_first_date_cost;
    }

    public void setMarriage_first_date_cost(MultipleModel marriage_first_date_cost) {
        this.marriage_first_date_cost = marriage_first_date_cost;
    }

    public MultipleModel getMarriage_hobby() {
        return marriage_hobby;
    }

    public void setMarriage_hobby(MultipleModel marriage_hobby) {
        this.marriage_hobby = marriage_hobby;
    }

    public MultipleModel getAfter_marriage_house_work() {
        return after_marriage_house_work;
    }

    public void setAfter_marriage_house_work(MultipleModel after_marriage_house_work) {
        this.after_marriage_house_work = after_marriage_house_work;
    }

    public MultipleModel getAfter_marriage_parent() {
        return after_marriage_parent;
    }

    public void setAfter_marriage_parent(MultipleModel after_marriage_parent) {
        this.after_marriage_parent = after_marriage_parent;
    }

    public String getTotal_rematch() {
        return total_rematch;
    }

    public void setTotal_rematch(String total_rematch) {
        this.total_rematch = total_rematch;
    }

    public String getComplete_rematch() {
        return complete_rematch;
    }

    public void setComplete_rematch(String complete_rematch) {
        this.complete_rematch = complete_rematch;
    }
}
