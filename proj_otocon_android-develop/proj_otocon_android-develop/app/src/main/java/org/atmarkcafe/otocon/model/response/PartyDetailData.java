package org.atmarkcafe.otocon.model.response;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.EntryStatus;
import org.atmarkcafe.otocon.utils.Gender;
import org.atmarkcafe.otocon.utils.StringUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyDetailData extends BaseObservable {
    private final int SUNDAY = 1;
    private final int MONDAY = 2;
    private final int TUESDAY = 3;
    private final int WEDNESDAY = 4;
    private final int THURSDAY = 5;
    private final int FRIDAY = 6;
    private final int SATURDAY = 7;
    private final String DATE_FORMAT = "yyyy年 M月 d日";
    private final String DATE_FORMAT_SHORT = "M/d";
    private final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    private final String EVENFALLS = " 〜";
    private final String QUOTES_RIGHT = ") ";
    private final String QUOTES_LEFT = " (";
    private final String QUOTES_LEFT_NO_SPACE = "(";
    private final String FORMAT_PRICE = "###,###,###";
    private final String OLD_COLON = ":";
    private final String NEW_COLON = ": ";

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("sub_name")
    private String subName;

    @SerializedName("event_date")
    private String eventDate;

    @SerializedName("day_of_week")
    private int dayOfWeek;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("entry_start_date")
    private String entryStartDate;

    @SerializedName("entry_end_date")
    private String entryEndDate;

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

    @SerializedName("special_show_status")
    private String specialShowStatus;

    @SerializedName("show_status")
    private String showStatus;

    @SerializedName("note")
    private String note;

    @SerializedName("place")
    private String place;

    @SerializedName("place_url")
    private String placeUrl;

    @SerializedName("entry_status_m")
    private int entryStatusM;

    @SerializedName("entry_status_f")
    private int entryStatusF;

    @SerializedName("city")
    private String city;

    @SerializedName("max_customer_m")
    private String maxCustomerM;

    @SerializedName("max_customer_f")
    private String maxCustomerF;

    @SerializedName("current_customer_m")
    private String currentCustomerM;

    @SerializedName("current_customer_f")
    private String currentCustomerF;

    @SerializedName("condition_m")
    private String conditionM;

    @SerializedName("condition_f")
    private String conditionF;

    @SerializedName("condition")
    private String condition;

    @SerializedName("benefit_friend")
    private String benefitFriend;

    @SerializedName("benefit_birthday")
    private String benefitBirthday;

    @SerializedName("benefit_early")
    private String benefitEarly;

    @SerializedName("caution")
    private String caution;

    @SerializedName("cancel_notice")
    private String cancelNotice;

    @SerializedName("access")
    private String access;

    @SerializedName("picture")
    private String picture;

    @SerializedName("available_limit")
    private String availableLimit;

    @SerializedName("deleted_at")
    private String deletedAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("benefit_early_status")
    private String benefitEarlyStatus;

    @SerializedName("fee_benefit_early_m")
    private String benefitEarlyMale;

    @SerializedName("fee_benefit_early_f")
    private String benefitEarlyFeMale;

    @SerializedName("area_name")
    private String areaName;

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("area_color")
    private String areaColor;

    @SerializedName("entry_date_status")
    private String entry_date_status;

    @SerializedName("like_status")
    private int like = 0;

    @SerializedName("fee_premium_m")
    private String feePremiumM;

    @SerializedName("fee_premium_f")
    private String feePremiumF;

    @SerializedName("fee_birthday_m")
    private String feeBirthdayM;

    @SerializedName("fee_birthday_f")
    private String feeBirthdayF;

    @SerializedName("join_status")
    private int joinStatus = 2;

    public int getFeePremiumM() {
        return StringUtils.parseStringToInteger(feePremiumM, 0);
    }

    public void setFeePremiumM(String feePremiumM) {
        this.feePremiumM = feePremiumM;
    }

    public int getFeePremiumF() {
        return StringUtils.parseStringToInteger(feePremiumF, 0);
    }

    public void setFeePremiumF(String feePremiumF) {
        this.feePremiumF = feePremiumF;
    }

    public int getFeeBirthdayM() {
        return StringUtils.parseStringToInteger(feeBirthdayM, 0);
    }

    public void setFeeBirthdayM(String feeBirthdayM) {
        this.feeBirthdayM = feeBirthdayM;
    }

    public int getFeeBirthdayF() {
        return StringUtils.parseStringToInteger(feeBirthdayF, 0);
    }

    public void setFeeBirthdayF(String feeBirthdayF) {
        this.feeBirthdayF = feeBirthdayF;
    }

    public void setBenefitEarlyFeMale(String benefitEarlyFeMale) {
        this.benefitEarlyFeMale = benefitEarlyFeMale;
    }

    public void setBenefitEarlyMale(String benefitEarlyMale) {
        this.benefitEarlyMale = benefitEarlyMale;
    }

    @Bindable
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
        notifyPropertyChanged(BR.like);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    @Bindable
    public String getName() {
        return name == null ? "" : name;
    }

    public String getTitleParty() {
        String title = getName();
        Pattern pattern = Pattern.compile("【.+?】");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return title;
        }
    }

    @Bindable
    public String getSubName() {
        return subName == null ? "" : subName;
    }

    public int getEntryDateStatus() {
        return StringUtils.parseStringToInteger(entry_date_status, 0);
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

    public int getBenefitEarlyMale() {
        return StringUtils.parseStringToInteger(benefitEarlyMale, -1);
    }

    public int getBenefitEarlyFeeMale() {
        return StringUtils.parseStringToInteger(benefitEarlyFeMale, -1);
    }

    public String getNote() {
        return note == null ? "" : note;
    }

    public String getPlace() {
        return place == null ? "" : place;
    }


    public int getBenefitEarlyStatus() {
        return StringUtils.parseStringToInteger(benefitEarlyStatus, 0);
    }

    public String getCity() {
        return city;
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

    public String getCaution() {
        return caution;
    }

    public String getCancelNotice() {
        return cancelNotice;
    }

    public String getPicture() {
        return picture == null ? "" : picture;
    }

    public String getEventDate() {
        return eventDate == null ? "" : eventDate;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getAreaName() {
        return areaName == null ? "" : areaName;
    }

    public String getCityName() {
        return cityName == null ? "" : cityName;
    }

    public int getBenefitBirthday() {
        return StringUtils.parseStringToInteger(benefitBirthday, 0);
    }

    public int getBenefitEarly() {
        return StringUtils.parseStringToInteger(benefitEarly, 0);
    }

    public int getAreaColor() {
        try {
            return Color.parseColor(areaColor);
        } catch (Exception e) {
            return 0;
        }
    }

    @Bindable
    public String getStartTime() {
        return startTime == null ? "" : startTime;
    }

    @Bindable
    public String getEndTime() {
        return endTime == null ? "" : endTime;
    }

    public int getEntryStatusM() {
        return entryStatusM;
    }

    public int getEntryStatusF() {
        return entryStatusF;
    }

    public String getAgeFromM() {
        return ageFromM == null ? "" : ageFromM;
    }

    public String getAgeToM() {
        return ageToM == null ? "" : ageToM;
    }

    public int getBenefitFriend() {
        return StringUtils.parseStringToInteger(benefitFriend, 0);
    }

    public String getAgeFromF() {
        return ageFromF == null ? "" : ageFromF;
    }

    public String getAgeToF() {
        return ageToF == null ? "" : ageToF;
    }

    public int getColorStatusMale(Context context) {
        int color = EntryStatus.factory(entryStatusM).color(context);
        return ContextCompat.getColor(context, color);
    }

    public String getNameStatusMale(Context context) {
        return EntryStatus.factory(entryStatusM).getName(context);
    }

    public int getColorStatusFeeMale(Context context) {
        int color = EntryStatus.factory(entryStatusF).color(context);
        return ContextCompat.getColor(context, color);
    }

    public String getNameStatusFeeMale(Context context) {
        return EntryStatus.factory(entryStatusF).getName(context);
    }

    public void setFeeCustomerM(String feeCustomerM) {
        this.feeCustomerM = feeCustomerM;
    }

    public void setFeeCustomerF(String feeCustomerF) {
        this.feeCustomerF = feeCustomerF;
    }

    public void setSpecialPriceF(String specialPriceF) {
        this.specialPriceF = specialPriceF;
    }

    public void setSpecialPriceM(String specialPriceM) {
        this.specialPriceM = specialPriceM;
    }

    public boolean hasBenefitFriend() {
        return getBenefitFriend() == 1;
    }

    public boolean hasBenefitBirthday() {
        return getBenefitBirthday() == 1;
    }

    public boolean hasSpecialMale() {
        return getSpecialPriceM() >= 0;
    }

    public boolean hasBenifit() {
        return getBenefitEarlyStatus() == 1;
    }

    public boolean hasSpecialFeeMale() {
        return getSpecialPriceF() >= 0;
    }

    public boolean hasDiscountFeeMale() {
        return hasSpecialFeeMale() || hasBenifit();
    }

    public boolean hasDiscountMale() {
        return hasSpecialMale() || hasBenifit();
    }

    public boolean hasTextPriceUnBoldMale() {
        return hasSpecialMale() || hasBenifitPriceM();
    }

    public boolean hasTextPriceUnBoldFeeMale() {
        return hasSpecialFeeMale() || hasBenifitPriceF();
    }

    public boolean isNoSpecialDiscountMale() {
        return hasSpecialFeeMale() && !hasSpecialMale();
    }

    public boolean isNoSpecialDiscountFeeMale() {
        return !hasSpecialFeeMale() && hasSpecialMale();
    }

    public String getLimitAgeMale(Context context) {
        return String.format(context.getString(R.string.party_detail_age_limit), getAgeFromM(), getAgeToM());
    }

    public String getLimitAgeFeeMale(Context context) {
        return String.format(context.getString(R.string.party_detail_age_limit), getAgeFromF(), getAgeToF());
    }

    public boolean hasThreeTextMale() {
        return hasSpecialFeeMale() && hasBenifit() && !hasSpecialMale();
    }

    public boolean hasSalePriceM(Context context) {
        if (hasBenifitM(context) || hasSpecialMale() || hasPremiumPriceM(context)) {
            return true;
        }
        return false;
    }

    public boolean hasSalePriceF(Context context) {
        if (hasBenifitF(context) || hasSpecialFeeMale() || hasPremiumPriceF(context)) {
            return true;
        }
        return false;
    }

    public boolean hasThreeTextFeeMale() {
        return hasSpecialMale() && hasBenifit() && !hasSpecialFeeMale();
    }

    public String getTextPriceSpecial(Context context, int text) {
        return String.format(context.getString(R.string.party_detail_special_discount), formatPrice(text));
    }

    public String getTextPriceBenifit(Context context, int text) {
        return String.format(context.getString(R.string.party_detail_special_benifit_early), formatPrice(text));
    }

    public String getTextPricePremium(Context context, int text) {
        return String.format(context.getString(R.string.price_register_party), formatPrice(text));
    }

    // return format Strike price
    public SpannableString formatStrikePriceMale(Context context, int text) {

        String textStrike = String.format(context.getString(R.string.party_detail_price), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasDiscountMale()) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    // return format Strike price
    public SpannableString formatStrikePriceFeeMale(Context context, int text) {

        String textStrike = String.format(context.getString(R.string.party_detail_price), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasDiscountFeeMale()) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    private String formatPrice(int numberFormat) {
        return NumberFormat.getInstance(Locale.JAPANESE).format(numberFormat);
    }

    public String getMaxCustmerMale(Context context) {
        return String.format(context.getString(R.string.party_detail_entry_status_male), maxCustomerM);
    }

    public String getMaxCustmerFeeMale(Context context) {
        return String.format(context.getString(R.string.party_detail_entry_status_male), maxCustomerF);
    }

    public String getTextEventDate(Context context) {
        if (eventDate == null) return "";

        Date dateTime = formatToDateTime(eventDate);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateEvent = dateTime == null ? "" : dateFormat.format(dateTime);
        return dateEvent
                + QUOTES_LEFT
                + getNameDayOfWeak(context, dayOfWeek)
                + QUOTES_RIGHT + getStartTime().replace(OLD_COLON, NEW_COLON)
                + EVENFALLS + getEndTime().replace(OLD_COLON, NEW_COLON);
    }

    public String getTextEventShortDate(Context context) {
        if (eventDate == null) return "";

        Date dateTime = formatToDateTime(eventDate);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_SHORT);
        String dateEvent = dateTime == null ? "" : dateFormat.format(dateTime);
        return dateEvent
                + QUOTES_LEFT_NO_SPACE
                + getNameDayOfWeak(context, dayOfWeek)
                + QUOTES_RIGHT + getStartTime()
                + EVENFALLS;
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

    /**
     * @param context
     * @param code    : values is 1 -> 7, 1 is sunday
     * @return
     */
    private String getNameDayOfWeak(Context context, int code) {

        try {
            return context.getResources().getStringArray(R.array.day_of_week)[code - 1];
        } catch (Exception e) {
            return "";
        }
    }

    public String getRoundAgeMale(Context context) {
        if (ageFromM != null && ageToM != null) {
            return String.format(context.getString(R.string.format_round_age), ageFromM, ageToM);
        }
        return "";
    }

    public String getRoundAgeFemale(Context context) {
        if (ageFromF != null && ageToF != null) {
            return String.format(context.getString(R.string.format_round_age), ageFromF, ageToF);
        }
        return "";
    }

    public boolean isFull() {
        final int ENTRY_STATUS_FULL = 4;
        return (entryStatusF == ENTRY_STATUS_FULL && entryStatusM == ENTRY_STATUS_FULL);
    }

    public boolean isExpired() {
        return getEntryDateStatus() != 1;
    }

    public boolean hasBenifitPriceM() {
        if (getBenefitEarlyMale() >= 0 && getBenefitEarlyStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasPremiumPriceM(Context context) {
        if (getSpecialPriceM() >= 0) {
            return false;
        } else if (DBManager.isLogin(context) && getFeePremiumM() >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasBenifitM(Context context) {
        if (getBenefitEarlyMale() >= 0 && getBenefitEarlyStatus() == 1 && !hasPremiumPriceM(context)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasBenifitF(Context context) {
        if (getBenefitEarlyFeeMale() >= 0 && getBenefitEarlyStatus() == 1 && !hasPremiumPriceF(context)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasBenifitPriceF() {
        if (getBenefitEarlyFeeMale() >= 0 && getBenefitEarlyStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasPremiumPriceF(Context context) {
        if (getSpecialPriceF() >= 0) {
            return false;
        } else if (DBManager.isLogin(context) && getFeePremiumF() >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public SpannableString formatPriceM(Context context, int text) {

        String textStrike = String.format(context.getString(R.string.price_register_party), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasBenifit() && getBenefitEarlyMale() >= 0) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    public SpannableString formatPriceF(Context context, int text) {

        String textStrike = String.format(context.getString(R.string.price_register_party), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasBenifit() && getBenefitEarlyFeeMale() >= 0) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    public String getPricePremium(Context context, int text) {
        return String.format(context.getString(R.string.price_register_party), formatPrice(text));
    }

    public SpannableString formatBenefitM(Context context, int text) {

        String textStrike = String.format(context.getString(R.string.price_register_party), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasPremiumPriceM(context) && getSpecialPriceM() < 0) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    public SpannableString formatBenefitF(Context context, int text) {

        String textStrike = String.format(context.getString(R.string.price_register_party), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasPremiumPriceF(context) && getSpecialPriceF() < 0) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    // return format Strike price
    public SpannableString formatPriceMale(Context context, int text) {

        String textStrike = String.format(context.getString(R.string.party_detail_price), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasSalePriceM(context)) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    // return format Strike price
    public SpannableString formatPriceFeeMale(Context context, int text) {

        String textStrike = String.format(context.getString(R.string.party_detail_price), formatPrice(text));
        SpannableString formatListPrice = new SpannableString(textStrike);

        if (hasSalePriceF(context)) {
            formatListPrice.setSpan(new StrikethroughSpan(), 0, textStrike.length(), Spanned.SPAN_MARK_MARK);
            return formatListPrice;
        } else {
            return formatListPrice;
        }
    }

    public boolean isRegistered() {
        return joinStatus != Party.JOIN_STATUS_NOT_REGISTERED;
    }

    public int getHeight(PartyDetailData data) {
        if (data.hasThreeTextMale() || data.hasThreeTextFeeMale()) {
            return 160;
        } else if (data.hasSpecialMale()||data.hasBenifitPriceM()||data.hasSpecialFeeMale()||data.hasBenifitPriceF()) {
            return 125;
        }
        return 100;
    }
}
