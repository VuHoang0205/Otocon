package org.atmarkcafe.otocon.utils;

import android.content.Context;

import org.atmarkcafe.otocon.R;

public enum Gender {
    none,
    male,
    female;

    public static String getGenderName(Context context, int gender) {
        return Gender.factory(String.valueOf(gender)) == Gender.none ? null : Gender.factory(String.valueOf(gender)) == Gender.male ? context.getString(R.string.male) : context.getString(R.string.female);
    }

    public static Gender factory(String gender) {
        try {
            return Gender.values()[Integer.parseInt(gender)];
        } catch (Exception e) {

        }
        return none;
    }
}
