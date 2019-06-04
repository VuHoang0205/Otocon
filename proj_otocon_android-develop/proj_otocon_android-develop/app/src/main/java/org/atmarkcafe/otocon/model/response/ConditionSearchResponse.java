package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * class ConditionSearchResponse
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class ConditionSearchResponse extends OnResponse {

    @SerializedName("data")
    private List<ConditionsearchData> data;

    public List<ConditionsearchData> getData() {
        return data;
    }

    public void setData(List<ConditionsearchData> data) {
        this.data = data;
    }
}
