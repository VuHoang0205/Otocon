package org.atmarkcafe.otocon.model

import android.databinding.BaseObservable
import com.google.gson.annotations.SerializedName


class NotificationSetting : BaseObservable() {
    var id: String = ""

    @SerializedName("user_id")
    var userId: String = ""

    @SerializedName("new_party")
    var newParty: Int = 1

    var coupon: Int = 1

    @SerializedName("before_party_liked")
    var beforePartyLiked: Int = 1

    @SerializedName("party_slot")
    var partySlot: Int = 1

    @SerializedName("before_party_joined")
    var beforePartyJoined: Int = 1

    @SerializedName("rematch")
    var rematch: Int = 1

    @SerializedName("created_at")
    var createdAt: String = ""

    @SerializedName("updated_at")
    var updatedAt: String = ""

    fun change(type: Int){
        when (type){
            NEW_PARTY -> newParty = 1 - newParty
            COUPON -> coupon = 1 - coupon
            BEFORE_PARTY_LIKED -> beforePartyLiked = 1 - beforePartyLiked
            PARTY_SLOT -> partySlot = 1 - partySlot
            BEFORE_PARTY_JOINED -> beforePartyJoined = 1 - beforePartyJoined
            REMATCH -> rematch = 1 - rematch
        }
    }

    companion object {
        const val NEW_PARTY = 0
        const val COUPON = 1
        const val BEFORE_PARTY_LIKED = 2
        const val PARTY_SLOT = 3
        const val BEFORE_PARTY_JOINED = 4
        const val REMATCH = 5
    }

}