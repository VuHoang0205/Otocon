package org.atmarkcafe.otocon.function.party;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.atmarkcafe.otocon.model.Party;

public class NoJoinedPartyListFragment extends JoinedPartyListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter(Party.JOIN_STATUS_REGISTERED);
    }

}
