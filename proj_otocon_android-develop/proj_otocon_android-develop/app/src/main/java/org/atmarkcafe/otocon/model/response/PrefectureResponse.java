package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * class PrefecturesResponse get data when api response
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class PrefectureResponse extends OnResponse {

    /**
     * Property  List<PrefecturesData> list is list for PrefecturesData
     */
    @SerializedName("data")
    List<org.atmarkcafe.otocon.model.response.Prefecture> list;


    public List<org.atmarkcafe.otocon.model.response.Prefecture> getList() {
        return list;
    }

}