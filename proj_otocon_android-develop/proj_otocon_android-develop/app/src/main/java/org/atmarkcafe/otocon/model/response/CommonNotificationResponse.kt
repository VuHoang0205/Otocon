package org.atmarkcafe.otocon.model.response

import com.google.gson.annotations.SerializedName

import org.atmarkcafe.otocon.model.CommonNotification

class CommonNotificationResponse : ResponseExtension<CommonNotification>() {
    @SerializedName("total")
    var total: Int = 0

}