package org.atmarkcafe.otocon.model

import android.databinding.BaseObservable
import com.google.gson.annotations.SerializedName

class CommonNotification : BaseObservable() {
    @SerializedName("id")
    var id: String = ""

    @SerializedName("user_id")
    var userId: String? = ""

    @SerializedName("event_id")
    var eventId: String = ""

    var title: String = ""

    var content: String = ""

    @SerializedName("read_at")
    var readAt: String? = ""

    @SerializedName("created_at")
    var createdAt: String = ""

    @SerializedName("updated_at")
    var updatedAt: String = ""

    @SerializedName("picture")
    var picture: String = ""

    override fun equals(other: Any?): Boolean {
        return id == (other as CommonNotification).id
    }

}
