package org.atmarkcafe.otocon.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * class HorizontalCalandarWeek
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class HorizontalCalandarWeek {
    private List<Week> calandarWeeks;
    public static int currentDay = 0;
    public static int currentYear = 0;
    public static int monthOfYear = 0;
    public static int dayOfMonth = 0;
    private DateTime dateTime;

    public HorizontalCalandarWeek() {
    }

    public List<Week> getCalandarWeeks() {
        return calandarWeeks;
    }

    public void setCalandarWeeks(List<Week> calandarWeeks) {
        this.calandarWeeks = calandarWeeks;
    }

    /**
     * funcion initDataAllWeek is init data for 4 week default time local
     */
    public void initDataAllWeek() {
        this.dateTime = new DateTime();
        this.calandarWeeks = new ArrayList<>();
        this.calandarWeeks.add(new Week(getCurrentWeek()));

        currentDay = this.dateTime.getDayOfMonth();
        currentYear = this.dateTime.getYear();
        monthOfYear = this.dateTime.getMonthOfYear();
        dayOfMonth = this.dateTime.getDayOfMonth();

        int i = 0;
        while (i < 3) {
            i++;
            this.calandarWeeks.add(new Week(getListNextWeek()));
        }
    }

    /**
     * funcion initDataAllWeek is init data for 4 week with
     *
     * @param milisecondDay
     */
    public void initDataAllWeek(long milisecondDay) {
        this.calandarWeeks = new ArrayList<>();

        this.dateTime = new DateTime();
        DateTime startDate = new DateTime();

        this.dateTime = startDate.withMillis(milisecondDay);
        currentDay = this.dateTime.getDayOfMonth();
        currentYear = this.dateTime.getYear();
        monthOfYear = this.dateTime.getMonthOfYear();
        dayOfMonth = this.dateTime.getDayOfMonth();

        this.calandarWeeks.add(new Week(getCurrentWeek()));

        int i = 0;
        while (i < 4) {
            i++;
            this.calandarWeeks.add(new Week(getListNextWeek()));
        }
    }


    /**
     * funcion getCurrentWeek is get current week
     *
     * @return List<DateTime>
     */
    private List<DateTime> getCurrentWeek() {

        List<DateTime> todayWeeks = new ArrayList<>();
        DateTime midDate = this.dateTime;

        if (midDate != null) {
            midDate = midDate.withDayOfWeek(DateTimeConstants.THURSDAY);
        }

        for (int i = -3; i <= 3; i++)
            todayWeeks.add(midDate != null ? midDate.plusDays(i) : null);

        return todayWeeks;
    }

    /**
     * funcion getListNextWeek is get list for next week
     *
     * @return List<DateTime>
     */
    private List<DateTime> getListNextWeek() {
        List<DateTime> nextWeeks = new ArrayList<>();
        DateTime midDates = onNextDay();

        if (midDates != null) {
            midDates = midDates.withDayOfWeek(DateTimeConstants.THURSDAY);
        }

        for (int i = -3; i <= 3; i++) {
            nextWeeks.add(midDates != null ? midDates.plusDays(i) : null);
        }
        return nextWeeks;
    }

    /**
     * funcion onNextDay get 7 days for week current
     *
     * @return DateTime
     */
    private DateTime onNextDay() {
        // add 7 next dayl
        this.dateTime = this.dateTime.plusDays(7);
        return this.dateTime;
    }

    /**
     * class week is object for week
     */
    public class Week {
        private List<DateTime> weeks;

        public List<DateTime> getWeeks() {
            return weeks;
        }

        public Week(List<DateTime> weeks) {
            this.weeks = weeks;
        }

        public void setWeeks(List<DateTime> weeks) {
            this.weeks = weeks;
        }
    }

    public String getDayOfWeek(String day) {
        if (day.equals("(月)")) {
            return 2 + "";
        } else if (day.equals("(火)")) {
            return 3 + "";
        } else if (day.equals("(水)")) {
            return 4 + "";
        } else if (day.equals("(木)")) {
            return 5 + "";
        } else if (day.equals("(金)")) {
            return 6 + "";
        } else if (day.equals("(土)")) {
            return 7 + "";
        } else if (day.equals("(日)")) {
            return 1 + "";
        } else {
            return "";
        }
    }

    public static int getDayOfWeekByDate(int day) {
        if (day == 1) {
            return day + 1;
        } else if (day == 2) {
            return day + 1;
        } else if (day == 3) {
            return day + 1;
        } else if (day == 4) {
            return day + 1;
        } else if (day == 5) {
            return day + 1;
        } else if (day == 6) {
            return day + 1;
        } else if (day == 7) {
            return 1;
        } else {
            return 0;
        }
    }
}
