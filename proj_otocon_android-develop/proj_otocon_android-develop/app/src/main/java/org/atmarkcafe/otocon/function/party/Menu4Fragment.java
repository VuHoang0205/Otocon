package org.atmarkcafe.otocon.function.party;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.atmarkcafe.otocon.function.party.list.PartyListFragment;
import org.atmarkcafe.otocon.function.party.list.PartyListPrenter;

public class Menu4Fragment extends PartyListFragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO change to new Presenter
        presenter = new PartyListPrenter(this);
    }
}
