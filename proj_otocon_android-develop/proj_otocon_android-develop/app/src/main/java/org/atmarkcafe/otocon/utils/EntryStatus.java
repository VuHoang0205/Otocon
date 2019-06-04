package org.atmarkcafe.otocon.utils;

import android.content.Context;

import org.atmarkcafe.otocon.R;

public enum EntryStatus {
    RESERVED,
    ONLY_A_FEW_REMAMINING,
    WAITING_LIST,
    NO_VACAINT_TABLES;

    public static final EntryStatus factory(int entryStatutsCode) {
        if (entryStatutsCode <= 0 || entryStatutsCode - 1 >= EntryStatus.values().length) {
            return null;
        }

        return EntryStatus.values()[entryStatutsCode - 1];
    }

    public int color(Context context) {
        int[] colors = new int[]{
                R.color.color_status_orange,
                R.color.color_status_red,
                R.color.color_status_blue,
                R.color.color_status_gray
        };

        return colors[ordinal()];
    }

    public String getName(Context context) {
        String[] names = context.getResources().getStringArray(R.array.entry_status);
        return names[ordinal()];
    }
}
