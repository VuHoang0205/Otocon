package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * class ConditionsearchData
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class ConditionsearchData implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("sub_name")
    private String subName;

    @SerializedName("event_date")
    private String eventDate;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("age_from_m")
    private String ageFromM;

    @SerializedName("age_to_m")
    private String ageToM;

    @SerializedName("age_from_f")
    private String ageFromF;

    @SerializedName("age_to_f")
    private String ageToF;

    @SerializedName("fee_customer_m")
    private String feeCustomerM;

    @SerializedName("fee_customer_f")
    private String feeCustomerF;

    @SerializedName("special_price_m")
    private String specialPriceM;

    @SerializedName("special_price_f")
    private String specialPriceF;

    @SerializedName("city")
    private String city;

    @SerializedName("station")
    private String station;

    @SerializedName("address")
    private String address;

    @SerializedName("picture")
    private String picture;

    @SerializedName("show_start_date")
    @Expose
    private String showStartDate;

    @SerializedName("show_end_date")
    @Expose
    private String showEndDate;

    @SerializedName("entry_start_date")
    @Expose
    private String entryStartDate;

    @SerializedName("show_status")
    @Expose
    private Integer showStatus;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("place")
    @Expose
    private String place;

    @SerializedName("place_url")
    @Expose
    private String placeUrl;

    @SerializedName("entry_status_m")
    @Expose
    private String entryStatusM;

    @SerializedName("entry_status_f")
    @Expose
    private String entryStatusF;

    @SerializedName("recepit_time")
    @Expose
    private String recepitTime;

    @SerializedName("day_of_weak")
    @Expose
    private String dayOfWeak;

    @SerializedName("address_map")
    @Expose
    private String addressMap;

    @SerializedName("payment_cash")
    @Expose

    private String paymentCash;

    @SerializedName("payment_credit")
    @Expose
    private String paymentCredit;
    @SerializedName("payment_transfer")
    @Expose
    private String paymentTransfer;

    @SerializedName("max_customer_m")
    @Expose
    private String maxCustomerM;

    @SerializedName("max_customer_f")
    @Expose
    private String maxCustomerF;

    @SerializedName("current_customer_m")
    @Expose
    private String currentCustomerM;

    @SerializedName("current_customer_f")
    @Expose
    private String currentCustomerF;

    @SerializedName("condition_m")
    @Expose
    private String conditionM;

    @SerializedName("condition_f")
    @Expose
    private String conditionF;

    @SerializedName("condition")
    @Expose
    private String condition;

    @SerializedName("benefit_friend")
    @Expose
    private String benefitFriend;

    @SerializedName("benefit_birthday")
    @Expose
    private String benefitBirthday;

    @SerializedName("benefit_early")
    @Expose
    private String benefitEarly;

    @SerializedName("style_koshitsu")
    @Expose
    private String styleKoshitsu;

    @SerializedName("style_mial")
    @Expose
    private String styleMial;

    @SerializedName("style_scale_s")
    @Expose
    private String styleScaleS;

    @SerializedName("style_scale_m")
    @Expose
    private String styleScaleM;

    @SerializedName("style_scale_l")
    @Expose
    private String styleScaleL;

    @SerializedName("style_seminar")
    @Expose
    private String styleSeminar;

    @SerializedName("style_special")
    @Expose
    private String styleSpecial;

    @SerializedName("style_talk")
    @Expose
    private String styleTalk;

    @SerializedName("caution")
    @Expose
    private String caution;

    @SerializedName("cancel_notice")
    @Expose
    private String cancelNotice;

    @SerializedName("access")
    @Expose
    private String access;

    @SerializedName("available_limit")
    @Expose
    private String availableLimit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAgeFromM() {
        return ageFromM;
    }

    public void setAgeFromM(String ageFromM) {
        this.ageFromM = ageFromM;
    }

    public String getAgeToM() {
        return ageToM;
    }

    public void setAgeToM(String ageToM) {
        this.ageToM = ageToM;
    }

    public String getAgeFromF() {
        return ageFromF;
    }

    public void setAgeFromF(String ageFromF) {
        this.ageFromF = ageFromF;
    }

    public String getAgeToF() {
        return ageToF;
    }

    public void setAgeToF(String ageToF) {
        this.ageToF = ageToF;
    }

    public String getFeeCustomerM() {
        return feeCustomerM;
    }

    public void setFeeCustomerM(String feeCustomerM) {
        this.feeCustomerM = feeCustomerM;
    }

    public String getDayOfWeak() {
        return dayOfWeak;
    }

    public void setDayOfWeak(String dayOfWeak) {
        this.dayOfWeak = dayOfWeak;
    }

    public String getFeeCustomerF() {
        return feeCustomerF;
    }

    public void setFeeCustomerF(String feeCustomerF) {
        this.feeCustomerF = feeCustomerF;
    }

    public String getSpecialPriceM() {
        return specialPriceM;
    }

    public void setSpecialPriceM(String specialPriceM) {
        this.specialPriceM = specialPriceM;
    }

    public String getSpecialPriceF() {
        return specialPriceF;
    }

    public void setSpecialPriceF(String specialPriceF) {
        this.specialPriceF = specialPriceF;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getShowStartDate() {
        return showStartDate;
    }

    public void setShowStartDate(String showStartDate) {
        this.showStartDate = showStartDate;
    }

    public String getShowEndDate() {
        return showEndDate;
    }

    public void setShowEndDate(String showEndDate) {
        this.showEndDate = showEndDate;
    }

    public String getEntryStartDate() {
        return entryStartDate;
    }

    public void setEntryStartDate(String entryStartDate) {
        this.entryStartDate = entryStartDate;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public String getEntryStatusM() {
        return entryStatusM;
    }

    public void setEntryStatusM(String entryStatusM) {
        this.entryStatusM = entryStatusM;
    }

    public String getEntryStatusF() {
        return entryStatusF;
    }

    public void setEntryStatusF(String entryStatusF) {
        this.entryStatusF = entryStatusF;
    }

    public String getRecepitTime() {
        return recepitTime;
    }

    public void setRecepitTime(String recepitTime) {
        this.recepitTime = recepitTime;
    }

    public String getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(String addressMap) {
        this.addressMap = addressMap;
    }

    public String getPaymentCash() {
        return paymentCash;
    }

    public void setPaymentCash(String paymentCash) {
        this.paymentCash = paymentCash;
    }

    public String getPaymentCredit() {
        return paymentCredit;
    }

    public void setPaymentCredit(String paymentCredit) {
        this.paymentCredit = paymentCredit;
    }

    public String getPaymentTransfer() {
        return paymentTransfer;
    }

    public void setPaymentTransfer(String paymentTransfer) {
        this.paymentTransfer = paymentTransfer;
    }

    public String getMaxCustomerM() {
        return maxCustomerM;
    }

    public void setMaxCustomerM(String maxCustomerM) {
        this.maxCustomerM = maxCustomerM;
    }

    public String getMaxCustomerF() {
        return maxCustomerF;
    }

    public void setMaxCustomerF(String maxCustomerF) {
        this.maxCustomerF = maxCustomerF;
    }

    public String getCurrentCustomerM() {
        return currentCustomerM;
    }

    public void setCurrentCustomerM(String currentCustomerM) {
        this.currentCustomerM = currentCustomerM;
    }

    public String getCurrentCustomerF() {
        return currentCustomerF;
    }

    public void setCurrentCustomerF(String currentCustomerF) {
        this.currentCustomerF = currentCustomerF;
    }

    public String getConditionM() {
        return conditionM;
    }

    public void setConditionM(String conditionM) {
        this.conditionM = conditionM;
    }

    public String getConditionF() {
        return conditionF;
    }

    public void setConditionF(String conditionF) {
        this.conditionF = conditionF;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getBenefitFriend() {
        return benefitFriend;
    }

    public void setBenefitFriend(String benefitFriend) {
        this.benefitFriend = benefitFriend;
    }

    public String getBenefitBirthday() {
        return benefitBirthday;
    }

    public void setBenefitBirthday(String benefitBirthday) {
        this.benefitBirthday = benefitBirthday;
    }

    public String getBenefitEarly() {
        return benefitEarly;
    }

    public void setBenefitEarly(String benefitEarly) {
        this.benefitEarly = benefitEarly;
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

    public String getStyleScaleS() {
        return styleScaleS;
    }

    public void setStyleScaleS(String styleScaleS) {
        this.styleScaleS = styleScaleS;
    }

    public String getStyleScaleM() {
        return styleScaleM;
    }

    public void setStyleScaleM(String styleScaleM) {
        this.styleScaleM = styleScaleM;
    }

    public String getStyleScaleL() {
        return styleScaleL;
    }

    public void setStyleScaleL(String styleScaleL) {
        this.styleScaleL = styleScaleL;
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

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getCancelNotice() {
        return cancelNotice;
    }

    public void setCancelNotice(String cancelNotice) {
        this.cancelNotice = cancelNotice;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(String availableLimit) {
        this.availableLimit = availableLimit;
    }


    public String dateEventSplit(String name_year) {
        String[] date = this.eventDate.split("-");
        return String.format(name_year, date[0], date[1], timeSlip(date[2]));
    }

    public String datePartyEventSplit(String dateEventParty, String rexText) {
        if (!dateEventParty.equals("")) {
            String[] date = dateEventParty.split("-");

            return String.format(rexText, charAtDayOfMonth(date[1].trim()),
                    charAtDayOfMonth(timeSlip(date[2]).trim()),
                    nameDayOfWeek(),
                    this.startTime == null ? "" : this.startTime
            );

        } else {
            return "";
        }
    }

    public String charAtDayOfMonth(String dayOfMonth) {
        return Integer.parseInt(dayOfMonth.charAt(0) + "") == 0 ? dayOfMonth.charAt(1) + "" : dayOfMonth;
    }

    public String timeSlip(String time_event) {
        String[] date = time_event.split(" ");
        return date[0];
    }

    public String nameDayOfWeek() {
        int dayOfWeak = Integer.valueOf(this.dayOfWeak == null ? "0" : this.dayOfWeak);
        String nameDayOfWeek = "";

        switch (dayOfWeak) {

            case 1:
                nameDayOfWeek = "(日)";
                break;
            case 2:
                nameDayOfWeek = "(月)";
                break;
            case 3:
                nameDayOfWeek = "(火)";
                break;
            case 4:
                nameDayOfWeek = "(水)";
                break;
            case 5:
                nameDayOfWeek = "(木)";
                break;
            case 6:
                nameDayOfWeek = "(金)";
                break;
            case 7:
                nameDayOfWeek = "(土)";
                break;
            case 8:
                nameDayOfWeek = "(日)";
                break;
            default:
                nameDayOfWeek = "";
                break;
        }

        return nameDayOfWeek;
    }
}
