package org.atmarkcafe.otocon.model.parameter;

import com.google.gson.annotations.SerializedName;

/**
 * class ConditionSearchParams is params post to server
 *
 * @author acv-hoanv
 * @version 1.0
 */

public class ConditionSearchParams extends Params {

    @SerializedName("limit")
    private int limit = 15;

    @SerializedName("page")
    private int page = 1;

    @SerializedName("style_koshitsu")
    private String styleKoshitsu;

    @SerializedName("style_mial")
    private String styleMial;

    @SerializedName("style_scale_l")
    private String styleScalel;

    @SerializedName("style_scale_m")
    private String styleScaleM;

    @SerializedName("style_scale_s")
    private String styleScaleS;

    @SerializedName("style_seminar")
    private String styleSeminar;

    @SerializedName("style_special")
    private String styleSpecial;

    @SerializedName("style_talk")
    private String styleTalk;

    @SerializedName("city")
    private String city;

    @SerializedName("day_of_weak")
    private String dayOfWeak;

    @SerializedName("check_female_slot")
    private String checkFemaleSlot;

    @SerializedName("check_male_slot")
    private String checkMaleSlot;

    @SerializedName("event_date_from")
    private String eventDateFrom;

    @SerializedName("event_date_to")
    private String eventDateTo;

    @SerializedName("age_male")
    private String ageMale;

    @SerializedName("age_female")
    private String ageFemale;

    public String getAgeMale() {
        return ageMale;
    }

    public void setAgeMale(String ageMale) {
        this.ageMale = ageMale;
    }

    public String getAgeFemale() {
        return ageFemale;
    }

    public void setAgeFemale(String ageFemale) {
        this.ageFemale = ageFemale;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getStyleKoshitsu() {
        return styleKoshitsu;
    }

    public void setStyleKoshitsu(String styleKoshitsu) {
        this.styleKoshitsu = styleKoshitsu;
    }

    public String getStyleMial() {
        return styleMial;
    }

    public void setStyleMial(String styleMial) {
        this.styleMial = styleMial;
    }

    public String getStyleScalel() {
        return styleScalel;
    }

    public void setStyleScalel(String styleScalel) {
        this.styleScalel = styleScalel;
    }

    public String getStyleScaleM() {
        return styleScaleM;
    }

    public void setStyleScaleM(String styleScaleM) {
        this.styleScaleM = styleScaleM;
    }

    public String getStyleScaleS() {
        return styleScaleS;
    }

    public void setStyleScaleS(String styleScaleS) {
        this.styleScaleS = styleScaleS;
    }

    public String getStyleSeminar() {
        return styleSeminar;
    }

    public void setStyleSeminar(String styleSeminar) {
        this.styleSeminar = styleSeminar;
    }

    public String getStyleSpecial() {
        return styleSpecial;
    }

    public void setStyleSpecial(String styleSpecial) {
        this.styleSpecial = styleSpecial;
    }

    public String getStyleTalk() {
        return styleTalk;
    }

    public void setStyleTalk(String styleTalk) {
        this.styleTalk = styleTalk;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDayOfWeak() {
        return dayOfWeak;
    }

    public void setDayOfWeak(String dayOfWeak) {
        this.dayOfWeak = dayOfWeak;
    }

    public String getCheckFemaleSlot() {
        return checkFemaleSlot;
    }

    public void setCheckFemaleSlot(String checkFemaleSlot) {
        this.checkFemaleSlot = checkFemaleSlot;
    }

    public String getCheckMaleSlot() {
        return checkMaleSlot;
    }

    public void setCheckMaleSlot(String checkMaleSlot) {
        this.checkMaleSlot = checkMaleSlot;
    }

    public String getEventDateFrom() {
        return eventDateFrom;
    }

    public void setEventDateFrom(String eventDateFrom) {
        this.eventDateFrom = eventDateFrom;
    }

    public String getEventDateTo() {
        return eventDateTo;
    }

    public void setEventDateTo(String eventDateTo) {
        this.eventDateTo = eventDateTo;
    }

}
