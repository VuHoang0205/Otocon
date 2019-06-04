package org.atmarkcafe.otocon.model;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.EntryStatus;
import org.atmarkcafe.otocon.utils.Gender;
import org.atmarkcafe.otocon.utils.StringUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * class Party
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class Party implements Serializable {
    public Party() {
    }

    private Context context;
    private final int SUNDAY = 7;
    private final int MONDAY = 1;
    private final int TUESDAY = 2;
    private final int WEDNESDAY = 3;
    private final int THURSDAY = 4;
    private final int FRIDAY = 5;
    private final int SATURDAY = 6;
    private final String DATE_FORMAT = " M/d";
    private final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    private final String EVENFALLS = " 〜";
    private final String QUOTES_RIGHT = ") ";
    private final String QUOTES_LEFT = "(";
    private final String OLD_COLON = ":";
    private final String NEW_COLON = ": ";
    private final String FORMAT_PRICE = "###,###,###";

    public static final int JOIN_STATUS_REGISTERED = 1;
    public static final int JOIN_STATUS_NOT_REGISTERED = 2;
    public static final int JOIN_STATUS_JOINED = 3;

    public void setContext(Context context) {
        this.context = context;
    }

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("sub_name")
    public String subName;

    @SerializedName("event_date")
    public String eventDate;

    @SerializedName("day_of_week")
    public int dayOfWeek;

    @SerializedName("start_time")
    public   String startTime;

    @SerializedName("end_time")
    public String endTime;

    @SerializedName("benefit_early_status")
    public int benefitStatus;

    @SerializedName("entry_start_date")
    public String entryStartDate;

    @SerializedName("age_from_m")
    public String ageFromM;

    @SerializedName("age_to_m")
    public String ageToM;

    @SerializedName("age_from_f")
    public String ageFromF;

    @SerializedName("age_to_f")
    public String ageToF;

    @SerializedName("fee_customer_m")
    public String feeCustomerM;

    @SerializedName("fee_customer_f")
    public String feeCustomerF;

    @SerializedName("special_price_m")
    public String specialPriceM;

    @SerializedName("special_price_f")
    public String specialPriceF;

    @SerializedName("fee_benefit_early_m")
    public String benefitEarlyMale;

    @SerializedName("fee_benefit_early_f")
    public String benefitEarlyFeMale;

    @SerializedName("note")
    public String note;

    @SerializedName("place")
    public String place;

    @SerializedName("place_url")
    public String placeUrl;

    @SerializedName("entry_status_m")
    public int entryStatusM;

    @SerializedName("entry_status_f")
    public int entryStatusF;

    @SerializedName("entry_date_status")
    public int entryDateStatus;

    @SerializedName("city_name")
    public String cityName;

    @SerializedName("area_name")
    public String areaName;

    @SerializedName("area_color")
    public String areaColor;

     void setAreaColor(String areaColor) {
        this.areaColor = areaColor;
    }

    @SerializedName("max_customer_m")
    public int maxCustomerM;

    @SerializedName("max_customer_f")
    public int maxCustomerF;

    @SerializedName("current_customer_m")
    public String currentCustomerM;

    @SerializedName("current_customer_f")
    public String currentCustomerF;
    @SerializedName("condition_m")

    public String conditionM;
    @SerializedName("condition_f")

    private String conditionF;

    @SerializedName("condition")
    public  String condition;

    @SerializedName("benefit_early")
    public String benefitEarly;

    @SerializedName("caution")
    public String caution;

    @SerializedName("cancel_notice")
    public String cancelNotice;

    @SerializedName("access")
    public String access;

    @SerializedName("picture")
    public String picture;

    @SerializedName("available_limit")
    public String availableLimit;

    @SerializedName("special_show_status")
    public int specialShowStatus;

    @SerializedName("like_status")
    public int like = 0;

    @SerializedName("join_status")
    public int joinStatus = 0;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubName() {
        return subName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getBenefitStatus() {
        return benefitStatus;
    }

    public String getEntryStartDate() {
        return entryStartDate;
    }

    public String getAgeFromM() {
        return ageFromM;
    }

    public String getAgeToM() {
        return ageToM;
    }

    public String getAgeFromF() {
        return ageFromF;
    }

    public String getAgeToF() {
        return ageToF;
    }

    public int getFeeCustomerM() {
        return StringUtils.parseStringToInteger(feeCustomerM, 0);
    }

    public int getFeeCustomerF() {
        return StringUtils.parseStringToInteger(feeCustomerF, 0);
    }

    public int getSpecialPriceM() {
        return StringUtils.parseStringToInteger(specialPriceM, -1);
    }

    public int getSpecialPriceF() {
        return StringUtils.parseStringToInteger(specialPriceF, -1);
    }

    public int getFeeBenefitM() {
        return StringUtils.parseStringToInteger(benefitEarlyMale, -1);
    }

    public int getFeeBenefitF() {
        return StringUtils.parseStringToInteger(benefitEarlyFeMale, -1);
    }

    public String getNote() {
        return note;
    }

    public String getPlace() {
        return place;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public int getEntryStatusM() {
        return entryStatusM;
    }

    public int getEntryStatusF() {
        return entryStatusF;
    }

    public int getEntryDateStatus() {
        return entryDateStatus;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public int getMaxCustomerM() {
        return maxCustomerM;
    }

    public int getMaxCustomerF() {
        return maxCustomerF;
    }

    public String getCurrentCustomerM() {
        return currentCustomerM;
    }

    public String getCurrentCustomerF() {
        return currentCustomerF;
    }

    public String getConditionM() {
        return conditionM;
    }

    public String getConditionF() {
        return conditionF;
    }

    public String getCondition() {
        return condition;
    }

    public String getBenefitEarly() {
        return benefitEarly;
    }

    public String getCaution() {
        return caution;
    }

    public String getCancelNotice() {
        return cancelNotice;
    }

    public String getAccess() {
        return access;
    }

    public String getPicture() {
        return picture;
    }

    public String getAvailableLimit() {
        return availableLimit;
    }

    public int getSpecialShowStatus() {
        return specialShowStatus;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getRoundAgeMale() {
        if (ageFromM != null && ageToM != null) {
            return String.format(context.getString(R.string.format_round_age), ageFromM, ageToM);
        }
        return "";
    }

    public String getRoundAgeFemale() {
        if (ageFromF != null && ageToF != null) {
            return String.format(context.getString(R.string.format_round_age), ageFromF, ageToF);
        }
        return "";

    }

    public int getEntryMale() {
        int color = 0;
        if (entryStatusM != 0) {
            if (this != null) {
                EntryStatus entryM = EntryStatus.factory(entryStatusM);
                if (entryM != null) {
                    entryM.getName(context);
                    color = ContextCompat.getColor(context, entryM.color(context));
                }
            }
        }

        return color;
    }

    public int getEntryFemale() {
        int color = 0;
        if (entryStatusF != 0) {
            EntryStatus entryF = EntryStatus.factory(entryStatusF);
            if (entryF != null) {
                entryF.getName(context);
                color = ContextCompat.getColor(context, entryF.color(context));
            }
        }
        return color;
    }

    public String getNameStatusF() {
        String nameFemale = "";
        if (entryStatusF != 0) {
            EntryStatus entryF = EntryStatus.factory(entryStatusF);
            nameFemale = entryF != null ? entryF.getName(context) : "";
        }

        return nameFemale;
    }

    public String getNameStatusM() {
        String nameMale = "";
        if (entryStatusM != 0) {
            EntryStatus entryM = EntryStatus.factory(entryStatusM);
            if (entryM != null) {
                nameMale = entryM.getName(context);
            }
        }

        return nameMale;
    }

    public boolean enableLike(){
        return joinStatus != JOIN_STATUS_REGISTERED && joinStatus != JOIN_STATUS_JOINED;
    }

    public int getAreaColor() {
        return Color.parseColor(areaColor);
    }

    public boolean hasSpecialMale() {
        return getSpecialPriceM() >= 0;
    }

    public boolean hasBenefitlMale() {
        return hasBenifit() && getFeeBenefitM() >= 0;
    }

    public boolean hasBenefitlFemale() {
        return hasBenifit() && getFeeBenefitF() >= 0;
    }

    public boolean hasSpecialFemale() {
        return getSpecialPriceF() >= 0;
    }


    public boolean hasPriceSaleMale() {
        if (hasBenifit() && hasBenefitlMale()) {
            return true;
        }

        if (hasSpecialMale()) {
            return true;
        }

        return false;
    }

    public boolean hasPriceSaleFeMale() {
        if (hasBenifit() && hasBenefitlFemale()) {
            return true;
        }

        if (hasSpecialFemale()) {
            return true;
        }
        return false;
    }


    public String getTextSpecialMale() {
        if (hasSpecialMale()) {
            String price = formatPrice(getSpecialPriceM());
            return String.format(context.getString(R.string.party_special_discount), price);
        } else {
            return "";
        }
    }

    public String getTextSpecialFemale() {
        if (hasSpecialFemale()) {
            String price = formatPrice(getSpecialPriceF());
            return String.format(context.getString(R.string.party_special_discount), price);
        } else {
            return "";
        }
    }

    public SpannableString formatStrikePriceMale(int text) {

        String textStrike = String.format(context.getString(R.string.party_detail_price), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasPriceSaleMale()) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    public SpannableString formatStrikePriceFeMale(int text) {
        String textStrike = String.format(context.getString(R.string.party_detail_price), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasPriceSaleFeMale()) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    public String getTextBenefitFemale() {
        if (hasBenefitlFemale()) {
            String price = formatPrice(getFeeBenefitF());
            return String.format(context.getString(R.string.party_benefit_discount), price);
        } else {
            return "";
        }
    }

    public String getTextBenefitMale() {
        if (hasBenefitlMale()) {
            String price = formatPrice(getFeeBenefitM());
            return String.format(context.getString(R.string.party_benefit_discount), price);
        } else {
            return "";
        }
    }

    public boolean isSpecial() {
        if (hasSpecialMale() || hasSpecialFemale()) {
            return true;
        }
        return false;
    }

    public String getTextEventDate() {
        if (eventDate == null) return "";

        Date dateTime = formatToDateTime(eventDate);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateEvent = dateTime == null ? "" : dateFormat.format(dateTime);
        return dateEvent
                + QUOTES_LEFT
                + getNameDayOfWeak(context, dayOfWeek)
                + QUOTES_RIGHT + getStartTime()
                + EVENFALLS;
    }

    private String getNameDayOfWeak(Context context, int code) {

        switch (code) {
            case SUNDAY:
                return context.getString(R.string.material_calendar_sunday);
            case MONDAY:
                return context.getString(R.string.material_calendar_monday);
            case TUESDAY:
                return context.getString(R.string.material_calendar_tuesday);
            case WEDNESDAY:
                return context.getString(R.string.material_calendar_wednesday);
            case THURSDAY:
                return context.getString(R.string.material_calendar_thursday);
            case FRIDAY:
                return context.getString(R.string.material_calendar_friday);
            case SATURDAY:
                return context.getString(R.string.material_calendar_saturday);
            default:
                return "";
        }
    }

    public boolean hasBenifit() {
        return benefitStatus == 1;
    }

    private Date formatToDateTime(String dateEvent) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
            Date date = dateFormat.parse(dateEvent);

            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isFull() {
        final int ENTRY_STATUS_FULL_SLOST = 4;
        return entryStatusF == ENTRY_STATUS_FULL_SLOST || entryStatusM == ENTRY_STATUS_FULL_SLOST;
    }

    public boolean isExpired() {
        return entryDateStatus != 1;
    }

    private String formatPrice(int numberFormat) {
        return NumberFormat.getInstance(Locale.JAPANESE).format(numberFormat);
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof Party && obj != null && ((Party) obj).getId() != null && ((Party) obj).getId().equals(getId());

    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }
}


