package org.atmarkcafe.otocon.utils;

import android.content.Context;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StringUtils {

    public static boolean hasError(Map<String, List<String>> datas, String key) {
        return key != null && datas != null && datas.containsKey(key);
    }

    public static String getError(Map<String, List<String>> datas, String key) {
        return hasError(datas, key) && datas.get(key) != null && datas.get(key).size() > 0 ? datas.get(key).get(0) : null;
    }

    public static String fomatDate(String data) {
        if (data == null) {
            return "";
        }

        return String.format("%s%s", data.length() == 1 ? "0" : "", data);
    }

    public static String toOneNumber(String data) {
        if (data == null) {
            return "";
        }
        if (Integer.parseInt(data) < 10) {
            return String.format("%s%s", "", data.substring(1));
        }

        return data;
    }

    public static int parseStringToInteger(String str, int intDefault) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return intDefault;
        }
    }

    public static String getAdId(Context context) {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(context).getId();
        } catch (Exception e) {
        }
        return null;
    }

    public static String replaceAll(String data) {
        if (data != null) {
            return data.replaceAll(",", "\n");
        }
        return null;
    }

    public static String getDates(String event_date_from, String event_date_to) {

        SimpleDateFormat fomatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat outputFomatDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();
        calendarTo.add(Calendar.MONTH,4);
        String eventDate = "";
        if(!isEmpty(event_date_from) || !isEmpty(event_date_to) ){
            if(!isEmpty(event_date_from)){
                try {
                    Date date1 = fomatDate.parse(event_date_from);
                    calendarFrom.setTime(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if(!isEmpty(event_date_to)){
                try {
                    Date date2 = fomatDate.parse(event_date_to);
                    calendarTo.setTime(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //eventDate form -> to
            do{
                String dates = outputFomatDate.format(calendarFrom.getTime());
                eventDate += (eventDate.isEmpty() ? "" : ",") + dates;
                calendarFrom.add(Calendar.DAY_OF_YEAR,1);
            } while (calendarFrom.getTimeInMillis() <= calendarTo.getTimeInMillis());
        }

        return eventDate;
    }

    public static final boolean isEmpty(String str){
        return str == null || str != null && str.isEmpty();
    }
}

