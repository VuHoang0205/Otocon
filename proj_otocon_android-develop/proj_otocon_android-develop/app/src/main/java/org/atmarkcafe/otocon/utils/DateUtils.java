package org.atmarkcafe.otocon.utils;

import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import org.atmarkcafe.otocon.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final DateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final int getEndDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int updateDay(String srtYear, String strMonth, String strDay) {

        if (srtYear.isEmpty()) {
            srtYear = "0";
        }
        if (strMonth.isEmpty()) {
            strMonth = "0";
        }
        if (strDay.isEmpty()) {
            strDay = "0";
            return Integer.parseInt(strDay);
        }
        int year = Integer.parseInt(srtYear);
        int month = Integer.parseInt(strMonth);
        int day = Integer.parseInt(strDay);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (month == 2 && year % 4 == 0) {
            maxDay = 29;
        } else if (month == 2) {
            maxDay = 28;
        }

        if (maxDay < day) {
            return maxDay;
        } else {
            return day;
        }
    }

    public static int GetMaxDay(String strYear, String strMonth) {
        int dayMax = 31;
        if (strYear.isEmpty()) {
            strYear = "0";
        }
        if (strMonth.isEmpty()) {
            strMonth = "0";
        }
        int month = Integer.parseInt(strMonth);
        int year = Integer.parseInt(strYear);
        if (month == 2) {
            return 29;
        }
        if (month == 2 && year % 4 != 0) {
            return 28;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        dayMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return dayMax;
    }

    public static final int SUNDAY = 7;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    public static final String DOT = "・";
    public static final String COMMAS = ",";

    public static String getDayOfWeekText(Context ctx, long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int code = calendar.get(Calendar.DAY_OF_WEEK);
        return getText(ctx, code);
    }

    public static String getText(Context ctx, int code) {
        switch (code) {
            case SUNDAY:
                return ctx.getString(R.string.material_calendar_sunday);
            case MONDAY:
                return ctx.getString(R.string.material_calendar_monday);
            case TUESDAY:
                return ctx.getString(R.string.material_calendar_tuesday);
            case WEDNESDAY:
                return ctx.getString(R.string.material_calendar_wednesday);
            case THURSDAY:
                return ctx.getString(R.string.material_calendar_thursday);
            case FRIDAY:
                return ctx.getString(R.string.material_calendar_friday);
            case SATURDAY:
                return ctx.getString(R.string.material_calendar_saturday);
            default:
                return "";
        }
    }

    public static boolean checkedDay(String source, int code) {
        if (source == null || source.isEmpty()) return false;
        return source.contains(String.valueOf(code));
    }

    public static String getCodeList(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        String result = "";

        if (monday) result = join(COMMAS, result, MONDAY);
        if (tuesday) result = join(COMMAS, result, TUESDAY);
        if (wednesday) result = join(COMMAS, result, WEDNESDAY);
        if (thursday) result = join(COMMAS, result, THURSDAY);
        if (friday) result = join(COMMAS, result, FRIDAY);
        if (saturday) result = join(COMMAS, result, SATURDAY);
        if (sunday) result = join(COMMAS, result, SUNDAY);

        return result;
    }

    public static String getDayList(Context ctx, String source) {
        if (source != null) {
            String[] list = source.split(COMMAS);
            String result = "";

            for (int i = 0; i < list.length; i++) {
                String s = list[i];
                if (s.length() == 0) continue;
                try {
                    int code = Integer.parseInt(s);
                    result = join(DOT, result, getText(ctx, code));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        return "";
    }

    private static String join(String delimiter, String source, int code) {
        return join(delimiter, source, String.valueOf(code));
    }

    private static String join(String delimiter, String source, String value) {
        if (source == null || source.length() == 0) {
            return value;
        }
        return source + delimiter + value;
    }

    public static boolean sameMonth(String birthday, String eventDate) {

        if (birthday != null && eventDate != null) {
            // 2018-11-22 18:00:00
            try {
                String month1 = birthday.substring(5, 7);
                String month2 = eventDate.substring(5, 7);
                return month1.equals(month2);
            } catch (Exception e) {

            }
        }
        return false;
    }

    public static String getLongDate(long time) {
        return LONG_DATE_FORMAT.format(new Date(time));
    }
}
