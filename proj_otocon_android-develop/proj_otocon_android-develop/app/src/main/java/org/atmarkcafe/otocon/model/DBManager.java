package org.atmarkcafe.otocon.model;

import android.content.Context;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.model.parameter.ConditionSearchParams;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.response.Area;
import org.atmarkcafe.otocon.model.response.City;
import org.atmarkcafe.otocon.model.response.CityByAreaResponse;
import org.atmarkcafe.otocon.model.response.UserPropertiesRespone;
import org.atmarkcafe.otocon.pref.BaseShareReferences;

import java.util.ArrayList;
import java.util.List;

/**
 * class DBManager to save data
 *
 * @author acv-truongvv
 * @version 1.0
 */
public class DBManager {
    private static final String KEY_USER_INFORMARION = "user_informarion";
    private static final String KEY_CONDITION_SEARCH = "user_condition_search";
    private static final String KEY_SHOW_DIALOG_RECOMMEND = "is_first_running_dialog";
    private static final String KEY_MENU_LIST_NEW = "menu_list_new";
    private static final String VALUE_SHOW_RECOMMEND = "true";
    private static final String KEY_CARD_INFO = "card_info";
    private static final String KEY_CALENDAR_ID = "calendar_id";
    private static final String KEY_ADVANCED_GPS = "advancedsearch_GPS";

    /**
     * @param account
     */
    public static final void save(Context context, Account account) {
        Gson gson = new Gson();

        // 2. Java object to JSON, and assign to a String
        String json = gson.toJson(account);

        // save to db
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(KEY_USER_INFORMARION, json);
    }

    /**
     * get Account
     *
     * @param context
     * @return
     */
    public static final Account getMyAccount(Context context) {
        BaseShareReferences references = new BaseShareReferences(context);
        String userInformation = references.get(KEY_USER_INFORMARION);

        Gson gson = new Gson();
        Account account = gson.fromJson(userInformation, Account.class);

        return account == null ? new Account() : account;
    }

    /**
     * @param context
     * @return
     */
    public static final boolean isLogin(Context context) {
        return getMyAccount(context).getToken() != null;
    }

    /**
     * @param context
     * @return
     */
    public static final String getToken(Context context) {
        String token = !isLogin(context) ? "" : getMyAccount(context).getToken();

        return token;
    }

    public static final void saveConditionSearch(Context context, ConditionSearchParams conditionSearchParams) {
        Gson gson = new Gson();

        // 2. Java object to JSON, and assign to a String
        String json = gson.toJson(conditionSearchParams);

        // save to db
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(KEY_CONDITION_SEARCH, json);
    }

    public static ConditionSearchParams getConditionSearchParams(Context context) {
        BaseShareReferences references = new BaseShareReferences(context);
        String conditionSearchParams = references.get(KEY_CONDITION_SEARCH);

        Gson gson = new Gson();
        ConditionSearchParams conditionSearch = gson.fromJson(conditionSearchParams, ConditionSearchParams.class);

        return conditionSearch == null ? null : conditionSearch;
    }

    // save advanced search GPS
    public static final void saveAdvancedGPS(Context context, PartyParams params){
        Gson gson = new Gson();
        String json = gson.toJson(params);
        // save to db
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(KEY_ADVANCED_GPS, json);
    }
    public static PartyParams getParamsSearchGPS(Context context){
        BaseShareReferences references = new BaseShareReferences(context);
        String conditionSearchParams = references.get(KEY_ADVANCED_GPS);
        Gson gson = new Gson();
        PartyParams conditionSearch = gson.fromJson(conditionSearchParams, PartyParams.class);
        return conditionSearch == null ? null : conditionSearch;
    }


    public static boolean isFristShowRecomendDialog(Context context) {
        BaseShareReferences references = new BaseShareReferences(context);
        String isFristShowDialog = references.get(KEY_SHOW_DIALOG_RECOMMEND);
        return isFristShowDialog == "" ? false : true;
    }

    public static void setIsFirstShowDialogRecommend(Context context) {
        // save to db
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(KEY_SHOW_DIALOG_RECOMMEND, VALUE_SHOW_RECOMMEND);
    }

    public static boolean setIsFirst(Context context, String key) {
        // save to db
        BaseShareReferences references = new BaseShareReferences(context);
        boolean isFirst = references.getBoolean(key, true);

        references.set(key, false);

        return isFirst;
    }

    public static final void saveCityByArea(Context context, CityByAreaResponse cityByArea) {
        Gson gson = new Gson();
        String json = gson.toJson(cityByArea);

        // save area list to db
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(BaseShareReferences.KEY_CITY_BY_AREA, json);

        // save city list to db
        List<City> list = new ArrayList<>();
        if (cityByArea != null) {
            for (Area area : cityByArea.getData()) {
                for (City city : area.getCityList()) {
                    list.add(city);
                }
            }
        }

        String jsonCitys = gson.toJson(list);
        references.set(BaseShareReferences.KEY_CITY_BY_CITIES, jsonCitys);
    }

    public static List<City> getCitys(Context context) {
        BaseShareReferences references = new BaseShareReferences(context);
        String jsonCities = references.get(BaseShareReferences.KEY_CITY_BY_CITIES);
        List<City> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonCities, City.type);
        } catch (Exception e) {

        }
        return list;
    }


    public static CityByAreaResponse getCityByArea(Context context) {
        BaseShareReferences references = new BaseShareReferences(context);
        String conditionSearchParams = references.get(BaseShareReferences.KEY_CITY_BY_AREA);
        Gson gson = new Gson();
        CityByAreaResponse cityByArea = gson.fromJson(conditionSearchParams, CityByAreaResponse.class);

        return cityByArea;
    }

    public static void saveDataHasReadMenuNew(Context context, String data) {
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(KEY_MENU_LIST_NEW, data);
    }

    public static String getDataMenuNew(Context context) {
        BaseShareReferences references = new BaseShareReferences(context);
        return references.get(KEY_MENU_LIST_NEW) == null ? "" : references.get(KEY_MENU_LIST_NEW);
    }

    public static boolean get(Context context, String key, boolean value){
        BaseShareReferences references = new BaseShareReferences(context);
        return references.getBoolean(key, value);
    }

    public static void save(Context context, String key, boolean value){
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(key, value);
    }
    public static final void saveCardInfo(Context context, UserPropertiesRespone respone) {
        Gson gson = new Gson();

        // 2. Java object to JSON, and assign to a String
        String json = gson.toJson(respone);

        // save to db
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(KEY_CARD_INFO, json);
    }

    /**
     * get Account
     *
     * @param context
     * @return
     */
    public static final UserPropertiesRespone getCardInfo(Context context) {
        BaseShareReferences references = new BaseShareReferences(context);
        String userInformation = references.get(KEY_CARD_INFO);

        Gson gson = new Gson();
        UserPropertiesRespone respone = gson.fromJson(userInformation, UserPropertiesRespone.class);

        return respone == null ? new UserPropertiesRespone() : respone;
    }

    public static final String getCalendarId(Context context){
        BaseShareReferences references = new BaseShareReferences(context);
        String calId = references.get(KEY_CALENDAR_ID);
        return calId;
    }

    public static final void setCalendarId(Context context, String calId){
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(KEY_CALENDAR_ID, calId);
    }
}

