package org.atmarkcafe.otocon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdvancedSearch {

    @SerializedName("city")
    List<String> cities;

    @SerializedName("day_of_week")
    List<String> dayOfWeek;

    @SerializedName("age_male")
    List<String> ageMale;


    @SerializedName("age_female")
    List<String> ageFemale;

    @SerializedName("check_female_slot")
    String check_female_slot;

    @SerializedName("check_male_slot")
    String check_male_slot;

}
