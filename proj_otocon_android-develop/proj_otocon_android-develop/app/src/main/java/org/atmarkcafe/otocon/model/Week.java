package org.atmarkcafe.otocon.model;

import org.joda.time.DateTime;

import java.util.List;

/**
 * class Week
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class Week {
    public List<DateTime> getWeeks() {
        return weeks;
    }

    public Week(List<DateTime> weeks) {
        this.weeks = weeks;
    }

    public void setWeeks(List<DateTime> weeks) {
        this.weeks = weeks;
    }

    private List<DateTime> weeks;
}
