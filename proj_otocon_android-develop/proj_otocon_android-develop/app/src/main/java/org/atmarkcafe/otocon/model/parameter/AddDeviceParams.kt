package org.atmarkcafe.otocon.model.parameter

import android.content.Context
import com.google.gson.annotations.SerializedName
import android.os.Build
import android.provider.Settings
import org.atmarkcafe.otocon.AddDeviceNoLoginPresenter.KEY_DEVICE_TOKKEN
import org.atmarkcafe.otocon.BuildConfig
import org.atmarkcafe.otocon.pref.BaseShareReferences
import org.atmarkcafe.otocon.utils.DateUtils
import org.atmarkcafe.otocon.utils.StringUtils


class AddDeviceParams {
    @SerializedName("device_id")
    var deviceId: String? = null

    @SerializedName("device_token")
    var deviceToken: String? = null

    var platform = "2"

    @SerializedName("device_name")
    var deviceName: String? = null

    @SerializedName("adid")
    var adId: String? = null

    @SerializedName("app_version")
    var appVersion: String? = null

    @SerializedName("date_install")
    var dateInstall: String? = null

    constructor(context: Context) {
        deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        deviceToken = BaseShareReferences(context).get(KEY_DEVICE_TOKKEN)

        deviceName = "${Build.MANUFACTURER} ${Build.MODEL}"

        adId = StringUtils.getAdId(context)

        appVersion = BuildConfig.VERSION_NAME

        dateInstall = DateUtils.getLongDate(context.packageManager.getPackageInfo(context.packageName, 0).firstInstallTime)
    }
}