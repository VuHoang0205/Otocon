package org.atmarkcafe.otocon.utils;

import android.content.Context;

import org.atmarkcafe.otocon.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AgeUtils {

    public static List<String> getHashMap(String source) {
        List<String> list = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(source, ",");
        while (tokenizer.hasMoreTokens()) {
            list.add(tokenizer.nextToken());
        }
        return list;
    }

    public static String getText(Context context, String ids) {
        String[] array = context.getResources().getStringArray(R.array.advanced_search_ages);
        String dot = context.getString(R.string.dot);
        StringTokenizer tokenizer = new StringTokenizer(ids, ",");

        String names = "";
        while (tokenizer.hasMoreTokens()) {
            if (!names.isEmpty()) {
                names += dot;
            }
            names += array[Integer.parseInt(tokenizer.nextToken()) - 1];
        }

        return names;
    }
}
