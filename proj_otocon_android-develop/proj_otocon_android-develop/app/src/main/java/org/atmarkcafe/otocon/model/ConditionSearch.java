package org.atmarkcafe.otocon.model;

import com.google.gson.annotations.SerializedName;

public class ConditionSearch {

    @SerializedName("limit")
    private int limit;

    @SerializedName("page")
    private int page;

    @SerializedName("style_koshitsu")
    private int style_koshitsu;

    @SerializedName("style_mial")
    private int style_mial;

    @SerializedName("style_scale_l")
    private int style_scale_l;

    @SerializedName("style_scale_m")
    private int style_scale_m;

    @SerializedName("style_scale_s")
    private int style_scale_s;

    @SerializedName("style_seminar")
    private int style_seminar;

    @SerializedName("style_special")
    private int style_special;

    @SerializedName("style_talk")
    private int style_talk;

    @SerializedName("city")
    private String city;

    @SerializedName("day_of_weak")
    private String day_of_weak;

    @SerializedName("check_female_slot")
    private int check_female_slot;

    @SerializedName("check_male_slot")
    private int check_male_slot;

    @SerializedName("event_date_from")
    private String event_date_from;

    @SerializedName("event_date_to")
    private String event_date_to;

}
