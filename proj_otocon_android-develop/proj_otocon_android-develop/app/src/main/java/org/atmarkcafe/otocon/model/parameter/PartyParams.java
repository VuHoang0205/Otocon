package org.atmarkcafe.otocon.model.parameter;

import android.content.Context;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.CityByAreaUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;
import org.atmarkcafe.otocon.utils.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class PartyParams extends Params {
    // User for api setting and Menu new
    @SerializedName("gender")
    public String gender;

    @SerializedName("event_date_from")
    String event_date_from;

    @SerializedName("event_date_to")
    String event_date_to;

    @SerializedName("check_slot_status")
    public String check_slot_status;

    @SerializedName("age_option")
    public String age_option;

    public void listValidation() {
        try {

            // gen age option
            JSONObject objAge = new JSONObject();
            if (gender != null) {
                objAge.put(KeyExtensionUtils.KEY_GENDER, Integer.parseInt(gender));
            }
            objAge.put(KeyExtensionUtils.KEY_OPTION, age_option);

            age = objAge.toString();

            // check_slot
            JSONObject objCheckSlot = new JSONObject();
            if (gender != null) {
                objCheckSlot.put(KeyExtensionUtils.KEY_GENDER, Integer.parseInt(gender));
            }
            if (check_slot_status != null) {
                objCheckSlot.put(KeyExtensionUtils.KEY_STATUS, Integer.parseInt(check_slot_status));
            }

            checkSlot = objCheckSlot.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //=======================
    @SerializedName("limit")
    private int limit;

    @SerializedName("page")
    private int page;

    @SerializedName("city")
    private String city;

    @SerializedName("day_of_week")
    private String dayofWeek;

    @SerializedName("event_date")
    private String eventDate;

    @SerializedName("age")
    private String age;

    @SerializedName("check_slot")
    private String checkSlot;

    @SerializedName("gps")
    private String gps;

    @SerializedName("link_label")
    String link_label;

    String ageComponent;

    public String getLink_label() {
        return link_label;
    }

    public void setLink_label(String link_label) {
        this.link_label = link_label;
    }

    public String getCheckSlot() {
        return checkSlot;
    }

    public void setCheckSlot(String checkSlot) {
        this.checkSlot = checkSlot;
    }

    public PartyParams() {

    }

    public void init(){
        check_slot_status = null;
        gender = null;
        eventDate = null;
        city = null;
        age = null;
        age_option = null;
        checkSlot = "1";
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDayofWeek() {
        return dayofWeek;
    }

    public void setDayofWeek(String dayofWeek) {
        this.dayofWeek = dayofWeek;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getAgeComponent() {
        return ageComponent;
    }

    public void setAgeComponent(String ageComponent) {
        this.ageComponent = ageComponent;
    }

    public void updateFromAdvancedSearch(SearchDefault value) {
//        value.getGender();
//        value.getAgeOfOpponent();
//        value.getAge();
//        value.getDayOfWeek();
//        value.getCitys();
//        value.getEventDate();

        setCity(value.getCitys());
        setEventDate(value.getEventDate());
        setDayofWeek(value.getDayOfWeek());
        setAge(value.getSttringAge());
        setCheckSlot(value.isOnlyParty() ? "1" : "0");
        gender = value.getStringGender();
        setAgeComponent(value.getAgeOfOpponent());
        age_option = value.getAgeOfOpponent();
    }

    public String getJsonParamsCity(Context context) {
        if (city != null && !city.isEmpty())
            return CityByAreaUtils.getParamsNameCity(context, city);
        else
            return null;
    }

    public String getJsonParamsEventDate() {
        if (eventDate != null && !eventDate.isEmpty())
            return getEventDate();
        else
            return null;
    }

    public String getJsonParamsDayofWeek() {
        if (dayofWeek != null && !dayofWeek.isEmpty())
            return dayofWeek;
        else
            return null;
    }

    public String getJsonParamsAge() {
        int gender = this.gender == null ? 0 : Integer.parseInt(this.gender);

        if (gender == KeyExtensionUtils.VALUE_GENDER_MALE) {
            // Age and Round Age is json {'option':'1,2' , 'age': 23}
            //Age
            // Round Age
            String json = createJsonAge(age, age_option, 1);

            if (!json.isEmpty()) {
                // addd
                return json;
            }

            // Only Party
        } else if (gender == KeyExtensionUtils.VALUE_GENDER_FEMALE) {
            // Age and Round Age is json {'option':'1,2' , 'age': 23}
            //Age
            // Round Age
            String json = createJsonAge(age, age_option, 2);

            if (!json.isEmpty()) {
                // addd
                return json;
            }

        } else {
            // none gender
            String json = createJsonAge(age, age_option, 0);
            return json;
        }
        return null;
    }

    public String getJsonParamsCheckSlot() {
        int gender = this.gender == null ? 0 : Integer.parseInt(this.gender);
        int status = (checkSlot == null || checkSlot.toLowerCase().equals("true") || checkSlot.toLowerCase().equals("1") ) ? 1 : 0;


        if (gender == KeyExtensionUtils.VALUE_GENDER_MALE) {
            // Age and Round Age is json {'option':'1,2' , 'age': 23}
            //Age
            // Round Age
            String json = createJsonAge(age, age_option, 1);

            if (!json.isEmpty()) {
                // addd
                return createJsonCheck(gender, status);
            }

            // Only Party
        } else if (gender == KeyExtensionUtils.VALUE_GENDER_FEMALE) {
            // Age and Round Age is json {'option':'1,2' , 'age': 23}
            //Age
            // Round Age
            String json = createJsonAge(age, age_option, 2);

            if (!json.isEmpty()) {
                // addd
                return createJsonCheck(gender, status);
            }

        } else {
            // none gender
            return createJsonCheck(0, status);
        }
        return null;
    }

    private String createJsonAge(String strAge, String ageOfOpponent, int gender) {
        // Age and Round Age is json {'option':'1,2' , 'age': 23}
        JSONObject object = new JSONObject();
        int age = 0;
        if (strAge != null) {
            age = Integer.parseInt(strAge);
        }
        boolean hasContent = false;
        try {
            if (ageOfOpponent != null && !ageOfOpponent.isEmpty()) {
                hasContent = true;
                object.put(KeyExtensionUtils.KEY_OPTION, ageOfOpponent);
            }

            if (age > 0) {
                hasContent = true;
                object.put(KeyExtensionUtils.KEY_AGE, age);
            }
            if (gender > 0) {
                hasContent = true;
                object.put(KeyExtensionUtils.KEY_GENDER, gender);
            }


        } catch (Exception e) {

        }


        return hasContent ? object.toString() : null;
    }

    private String createJsonCheck(int gender, int status) {
        // Age and Round Age is json {'option':'1,2' , 'age': 23}
        JSONObject object = new JSONObject();
        boolean hasContent = false;
        try {
            if (gender > 0) {
                hasContent = true;
                object.put(KeyExtensionUtils.KEY_GENDER, gender);
            }

            hasContent = true;
            object.put(KeyExtensionUtils.KEY_STATUS, status);

        } catch (Exception e) {

        }


        return hasContent ? object.toString() : "";
    }

    public void updateEventDate() {
        if(event_date_from != null && !event_date_from.isEmpty() || event_date_to != null && !event_date_to.isEmpty()){
            // check
            eventDate = StringUtils.getDates(event_date_from,event_date_to);
        }
    }
}