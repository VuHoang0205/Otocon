package org.atmarkcafe.otocon.function.rematch

import org.atmarkcafe.otocon.extesion.PartyListAdapter
import org.atmarkcafe.otocon.model.Party

class MatchingItemAdapter(val listener: PartyListLoadListener) : PartyListAdapter(listener) {

     override fun updateData(total: Int, parties: MutableList<Party>?) {

    }

}