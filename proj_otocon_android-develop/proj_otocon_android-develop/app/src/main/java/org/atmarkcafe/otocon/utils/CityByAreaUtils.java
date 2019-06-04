package org.atmarkcafe.otocon.utils;

import android.content.Context;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.function.home.items.HomeCityItem;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.Area;
import org.atmarkcafe.otocon.model.response.City;
import org.atmarkcafe.otocon.model.response.CityByAreaResponse;
import org.atmarkcafe.otocon.model.response.Prefecture;
import org.atmarkcafe.otocon.pref.BaseShareReferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CityByAreaUtils {

    public static String getNameCity(Context context, String idList) {
        if (idList == null || idList.equals(""))
            return "";

        String dot = context.getString(R.string.format_advanced_dot);
        String result = "";

        final String[] ids = idList.split(",");
        Set<String> setIds = new HashSet<>();
        setIds.addAll(Arrays.asList(ids));

        CityByAreaResponse areaResponse = DBManager.getCityByArea(context);
        List<Area> areaList = areaResponse.getData();

        for (Area area : areaList) {
            String name = "";
            int count = 0;
            for (City city : area.getCityList()) {
                if (setIds.contains(city.getId())) {
                    city.setCheck(true);
                    count++;
                    name += (name.isEmpty() ? "" : dot) + city.getName();
                } else {
                    city.setCheck(false);
                }
            }
            if (count == area.getCityList().size()) {
                name = area.getName();
            }
            result += (result.isEmpty() ? name : (name.isEmpty() ? "" : (dot + name)));
        }

        return result;
    }

    public static String getParamsNameCity(Context context, String idList) {
        if (idList.equals(""))
            return "";

        String result = "";

        final String[] ids = idList.split(",");
        Set<String> setIds = new HashSet<>();
        setIds.addAll(Arrays.asList(ids));

        CityByAreaResponse areaResponse = DBManager.getCityByArea(context);
        List<Area> areaList = areaResponse.getData();

        for (Area area : areaList) {

            for (City city : area.getCityList()) {
                if (setIds.contains(city.getId())) {
                    city.setCheck(true);
                    result += (result.isEmpty() ? "" : ",") + city.getName();
                }
            }

        }
        return result;
    }

    public static String getPrefectureName(Context context, String id){
        String addrees = "";
        String json = new BaseShareReferences(context).get(BaseShareReferences.KEY_PREFECTURE);
        // if first
        if (json.isEmpty()) {
            json = "[]";
        }

        Gson gson = new Gson();
        List<Prefecture> list = gson.fromJson(json, Prefecture.typeList);
        for (Prefecture pre : list) {
            if (pre.getId().equals(id)) {
                addrees = pre.getName();
                break;
            }
        }
        return addrees;
    }
}
