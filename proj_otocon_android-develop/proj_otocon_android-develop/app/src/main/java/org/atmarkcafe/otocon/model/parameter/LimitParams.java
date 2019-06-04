package org.atmarkcafe.otocon.model.parameter;

import com.google.gson.annotations.SerializedName;

public class LimitParams extends Params {

    @SerializedName("limit")
    String limit;

    @SerializedName("page")
    String page;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
