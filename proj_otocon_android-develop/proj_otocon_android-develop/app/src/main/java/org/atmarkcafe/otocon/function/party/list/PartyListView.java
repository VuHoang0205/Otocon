package org.atmarkcafe.otocon.function.party.list;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.model.response.OnResponse;

public interface PartyListView extends MVPExtension.View<PartyListResponse>{
    public void successLike(OnResponse response);
}