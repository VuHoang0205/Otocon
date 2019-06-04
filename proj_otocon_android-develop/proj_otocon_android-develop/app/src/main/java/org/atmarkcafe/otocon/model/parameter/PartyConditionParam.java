package org.atmarkcafe.otocon.model.parameter;

public class PartyConditionParam extends Params {
    public int page;
    public int limit;

    public PartyConditionParam(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }
}
