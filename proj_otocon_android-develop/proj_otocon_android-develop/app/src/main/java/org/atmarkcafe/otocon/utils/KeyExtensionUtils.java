package org.atmarkcafe.otocon.utils;

public interface KeyExtensionUtils {
    public static final String KEY_TITLE = "title";
    public static final String KEY_ADVANCED_SEARCH = "KEY_ADVANCED_SEARCH";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_URL = "url";
    public static final String KEY_SHOW_BACK = "show_icBack";
    public static final String KEY_SHOW_ADVANCED_SEARCH = "show_advanced_search";
    public static final String ACTION_NOTI_MY_REMATCH = "action_noti_my_rematch";
    public static final String ACTION_HIDE_NOTI_MY_REMATCH = "action_hide_noti_my_rematch";

    public static final String KEY_ICON_LEFT = "icon_left";
    public static final String KEY_ICON_RIGHT = "icon_right";

    public static final String KEY_ID = "id";
    public static final String KEY_DATA = "data";
    public static final String KEY_USER = "user";

    public static final String KEY_USER_NAME = "username";
    public static final String KEY_NAME = "name";

    public static final String KEY_AGE = "age";
    public static final String KEY_OPTION = "option";
    public static final String KEY_GENDER = "gender";

    public static final String KEY_ACTION = "action";
    public static final String KEY_ACTION_LOGIN = "login";
    public static final String KEY_ACTION_EXPIRED_REGISTRATION = "expired_registration";
    public static final String KEY_ACTION_REMATCH = "rematch";
    public static final String KEY_ACTION_REMATCH_DETAIL = "rematch_detail";
    public static final String KEY_ACTION_REMINDER = "reminder";


    public static final String KEY_SEX = "sex";

    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STATUS = "status";
    public static final String KEY_MESSAGE = "message";

    public static final String KEY_FIRST_LOGIN = "first_login";

    public static final String KEY_TYPE = "type";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_EVENT_ID = "event_id";
    public static final String KEY_REMATCH_TYPE = "rematch_type";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_CLASS = "key_class";

    // is gender male
    public static final String MALE = "Male";

    // is gender female
    public static final String FEMALE = "Female";

    public static final String PAYMENT_CREDIT_CARD = "true";

    public static final String CASH_PAYMENT_DAY = "fale";

    public static final String KEY_TOKEN = "token";

    //Avanced search Area
    public static final String KEY_AREA_CITY_DATA = "area_city_data";
    public static final String KEY_AGE_DATA = "age_data";


    public static final int VALUE_GENDER_DEFAULT = 0;

    public static final int VALUE_GENDER_MALE = 1;

    public static final int VALUE_GENDER_FEMALE = 2;

    public static final String KEY_REDIRECT = "redirect";

    public static interface RESULT{
        public static final int RESULT_REGISTER_PARTY_SUCCESS =  100;

        public static final int RESULT_REGISTER_PARTY_SUCCESS_INTRO =  101;

        public static final int RESULT_TOTOREMATCH =  102;

        public static final int RESULT_REGISTER_PARTY_SUCCESS_INFO =  103;
    }

}