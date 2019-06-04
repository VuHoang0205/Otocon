package org.atmarkcafe.otocon.pref;

import android.content.Context;
import android.content.SharedPreferences;

import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.utils.AgeUtils;
import org.atmarkcafe.otocon.utils.StringUtils;

public class SearchDefault {
    private static final String KEY_CITYS = "citys";
    private static final String KEY_EVENT_DATE = "event_date";
    private static final String KEY_DAY_OF_WEEK = "day_of_week";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_AGE = "age";
    private static final String KEY_AGE_OF_OPPONENT = "age_of_opponent";
    private static final String KEY_ONLY_PARTY = "only_party";

    private static final String KEY_IS_FIRST = "KEY_IS_FIRST";

    private static final String KEY_NAME = "DBSearchDefault";

    public static SearchDefault getInstance() {
        return instance;
    }

    private static SearchDefault instance = new SearchDefault();

    private SharedPreferences preferences;

    private String citys = "";
    private String eventDate = "";
    private String dayOfWeek = "";
    private String ageOfOpponent = "";
    private boolean onlyParty = true;

    private int age = 0;

    private int gender = 0;

    private boolean isFirst = true;

    public SearchDefault() {

    }

    public void reset() {
        age = 0;
        gender = 0;
        citys = "";
        eventDate = "";
        dayOfWeek = "";
        ageOfOpponent = "";
        onlyParty = true;
    }

    /**
     * reset and save database
     */
    public void clear() {
        reset();
        save();
    }

    public SearchDefault init(Context context) {
        preferences = context.getSharedPreferences(KEY_NAME, 0);
        load();

        return this;
    }

    private void load() {
        citys = preferences.getString(KEY_CITYS, "");
        eventDate = preferences.getString(KEY_EVENT_DATE, "");
        dayOfWeek = preferences.getString(KEY_DAY_OF_WEEK, "");
        age = preferences.getInt(KEY_AGE, 0);
        gender = preferences.getInt(KEY_GENDER, 0);
        ageOfOpponent = preferences.getString(KEY_AGE_OF_OPPONENT, "");
        onlyParty = preferences.getBoolean(KEY_ONLY_PARTY, true);

        isFirst = preferences.getBoolean(KEY_IS_FIRST, true);
    }

    public void save() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_CITYS, citys);
        editor.putString(KEY_EVENT_DATE, eventDate);
        editor.putString(KEY_DAY_OF_WEEK, dayOfWeek);
        editor.putInt(KEY_AGE, age);
        editor.putInt(KEY_GENDER, gender);
        editor.putString(KEY_AGE_OF_OPPONENT, ageOfOpponent);
        editor.putBoolean(KEY_ONLY_PARTY, onlyParty);
        editor.putBoolean(KEY_IS_FIRST, isFirst);
        editor.commit();
    }

    public boolean isSaveDefault() {
        //!citys.isEmpty() && age > 0 && 
        return isFirst;
    }

    public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public String getStringGender() {
        return gender == 0 ? null : (gender + "");
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getAgeOfOpponent() {
        return ageOfOpponent;
    }

    public void setAgeOfOpponent(String ageOfOpponent) {
        this.ageOfOpponent = ageOfOpponent;
    }

    public boolean isOnlyParty() {
        return onlyParty;
    }

    public void setOnlyParty(boolean onlyParty) {
        this.onlyParty = onlyParty;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public String getSttringAge() {
        if (age <= 0) {
            return null;
        }
        return age + "";
    }

    public void initFrom(PartyParams params) {
        citys = params.getCity();
        eventDate = params.getEventDate();
        dayOfWeek = params.getDayofWeek();
        onlyParty = "1".equals(params.getCheckSlot());
        gender = StringUtils.parseStringToInteger(params.gender, 0);
        age = StringUtils.parseStringToInteger(params.getAge(), 0);
        ageOfOpponent = params.getAgeComponent() == null ? "" :  params.getAgeComponent();


    }
}
