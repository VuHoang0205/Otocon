package org.atmarkcafe.otocon.pref;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.atmarkcafe.otocon.model.response.Prefecture;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CityDB {
    private static final String KEY = CityDB.class.getName();

    private static CityDB instance = new CityDB();

    public static CityDB getInstance() {
        return instance;
    }

    private Map<String, Prefecture> prefectureMap = new HashMap<>();
    private Context context;

    private CityDB() {

    }

    public void init(Context context) {
        this.context = context;
        load();
    }


    public String getCityName(String id) {
        return prefectureMap.containsKey(id) ? prefectureMap.get(id).getName() : "";
    }

    public void load() {
        prefectureMap.clear();
        BaseShareReferences references = new BaseShareReferences(context);

        // load from db
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Prefecture>>() {
        }.getType();

        List<Prefecture> list = new Gson().fromJson(references.get(KEY), listType);
        set(list);

    }

    public void set(List<Prefecture> list) {
        prefectureMap.clear();
        if(list != null) {
            for (Prefecture prefecture : list) {
                prefectureMap.put(prefecture.getId(), prefecture);
            }
        }
    }

    public void save() {
        BaseShareReferences references = new BaseShareReferences(context);
        references.set(KEY, toJsonString());
    }

    private String toJsonString() {
        List<Prefecture> prefectures = new ArrayList<>();
        for (String key : prefectureMap.keySet()) {
            prefectures.add(prefectureMap.get(key));
        }

        return new Gson().toJson(prefectures);
    }
}
