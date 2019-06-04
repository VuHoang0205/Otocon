package org.atmarkcafe.otocon.model

import com.google.gson.annotations.SerializedName
import org.atmarkcafe.otocon.model.response.OnResponse

class PrintIntroductionSelf : OnResponse() {
    @SerializedName("data")
    var data: FileData? = null

    fun getFile(): String {
        if (data == null || data?.fileUrl == null) return ""
        return data?.fileUrl!!
    }
}

class FileData {
    @SerializedName("file")
    var fileUrl: String? = null
}