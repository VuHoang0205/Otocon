package org.atmarkcafe.otocon.model

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.Color
import android.util.Log
import com.google.gson.annotations.SerializedName
import org.atmarkcafe.otocon.BR
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RematchDetail : BaseObservable() {
    @SerializedName("id")
    var id: String = ""

    @SerializedName("name_mei")
    var nameMei: String = ""

    @SerializedName("event_city")
    var eventCity: String = ""

    @SerializedName("event_area")
    var eventArea: String = ""

    @SerializedName("event_area_color")
    var eventAreaColor: String = ""

    @SerializedName("event_day_of_week")
    var eventDayOfWeek: Int = 0

    @SerializedName("event_sub_name")
    var eventSubName: String = ""

    @SerializedName("install_app_status")
    var installAppStatus: Int = 0

    @SerializedName("install_app_status_title")
    var installAppStatusTitle: String = ""

    @SerializedName("install_app_color")
    var installAppColor: String = ""

    @SerializedName("online_status")
    var onlineStatus: String = ""

    @SerializedName("rematch_title")
    var rematchTitle: String = ""

    @SerializedName("rematch_color")
    var rematchColor: String = ""

    @SerializedName("common_completed")
    var commonCompleted: String = ""

    @SerializedName("common_description")
    var commonDescription: String = ""

    @SerializedName("event_date")
    var eventDate: String = ""

    @SerializedName("start_time")
    var startTime: String = ""

    @SerializedName("share_contact_id")
    var shareContactId: String = ""

    @SerializedName("withdrawal_status")
    var withdrawalStatus: Int = 0

    @SerializedName("rematch_user_info_status")
    var rematchUserInfoStatus: Int = 0

    @SerializedName("avatar")
    var avatar: ArrayList<Avatar> = ArrayList<Avatar>()

    @SerializedName("rematch_user_request_email")
    var userRequestEmail: String = ""

    @SerializedName("rematch_user_request_tel")
    var userRequestTel: String = ""

    @SerializedName("rematch_user_request_line_id")
    var userRequestLineId: String = ""

    @SerializedName("rematch_send_request_status")
    var sendRequestStatus: Int = 1

    @SerializedName("rematch_expired_status")
    var rematchExpiredStatus: Int = 0

    var gender: Int = 0

    private var selectedAvatar: Int = 0

    fun getAvatar(index: Int): String {
        if (avatar == null) return ""
        if (avatar.size <= index) return ""
        return avatar[index].picture
    }

    private fun getColor(color: String): Int {
        try {
            return Color.parseColor(color)
        } catch (e: Throwable) {

        }
        return Color.TRANSPARENT
    }

    fun getEventAreColor(): Int {
        return getColor(eventAreaColor)
    }

    fun getRematchColor(): Int {
        return getColor(rematchColor)
    }

    fun isWithDrawalUser(): Boolean {
        return withdrawalStatus == 1
    }

    fun getTitle(context: Context): String {
        if (isWithDrawalUser()) {
            return context.getString(R.string.rematch_detail_title_with_drawal)
        }
        return String.format(context.getString(R.string.rematch_detail_title), nameMei)
    }

    fun getTextEventDate(ctx: Context): String {
        if (eventDate == null) return ""

        val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eventDate)
        val dateFormat = SimpleDateFormat("M/d", Locale.JAPAN)
        val dateEvent = if (dateTime == null) "" else dateFormat.format(dateTime)
        return "$dateEvent(${DateUtils.getText(ctx, eventDayOfWeek)}) $startTime 〜"
    }

    fun getShareInfo(): String {
        var res = ""

        if (!userRequestLineId.isNullOrEmpty()) {
            res = "Line ID: $userRequestLineId"
        }
        if (!userRequestEmail.isNullOrEmpty()) {
            res = if (res.isEmpty()) "メールアドレス: $userRequestEmail" else "$res\nメールアドレス: $userRequestEmail"
        }

        if (!userRequestTel.isNullOrEmpty()) {
            res = if (res.isEmpty()) "電話番号: $userRequestTel" else "$res\n電話番号: $userRequestTel"
        }
        return res
    }

    fun isSendRequestList(): Boolean {
        return sendRequestStatus != 1
    }

    fun enableAction(): Boolean {
        // disable when exprired
        return rematchExpiredStatus == 0 // 0: enable, 1: disable
    }

    @Bindable
    fun getSelectedAvatar(): Int {
        return selectedAvatar
    }

    fun setSeletedAvatar(index: Int) {
        this.selectedAvatar = index
        notifyPropertyChanged(BR.selectedAvatar)
    }

    fun refactorAvatar() {
        // sort array avatar
        if (avatar.size <= 1) return
        Collections.sort(avatar, Comparator<Avatar> { o1, o2 ->
            if (o1!!.order < o2!!.order) {
                return@Comparator -1
            }
            1
        })
        if (avatar[0].type == RematchDetail.TYPE_IMAGE_AVATAR) {
            avatar.removeAt(0)
        }
        setSeletedAvatar(0)
    }

    companion object {
        const val TYPE_IMAGE_AVATAR = 2

        const val STATUS_SEND_REQUEST = 1
        const val STATUS_APPROVE_REQUEST = 2
        const val STATUS_SENT_REQUEST = 3
    }
}

class Avatar {
    @SerializedName("type")
    var type: Int = 0

    @SerializedName("picture")
    var picture: String = ""

    @SerializedName("order")
    var order: Int = 0

}