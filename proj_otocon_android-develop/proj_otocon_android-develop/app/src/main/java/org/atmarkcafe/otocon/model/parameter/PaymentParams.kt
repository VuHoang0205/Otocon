package org.atmarkcafe.otocon.model.parameter

import android.content.Context
import android.provider.Settings
import com.google.gson.annotations.SerializedName
import org.atmarkcafe.otocon.model.CreditCard

class PaymentParams {
    @SerializedName("transaction_id")
    var transactionId: String ?= null

    @SerializedName("device_id")
    var deviceId: String ?= null

    @SerializedName("payment_card_number")
    var paymentCardNumber: String ?= null

    @SerializedName("payment_card_expired")
    var paymentCardExpired: String ?= null

    @SerializedName("payment_card_secure")
    var paymentCardSecure: String ?= null

    @SerializedName("card_id")
    var cardId: String ?= null

    fun setData(context: Context, transId: String, card: CreditCard){
        transactionId = transId
        deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        if (card.id.isNotEmpty()){
            cardId = card.id
        } else {
            paymentCardNumber = card.number
            paymentCardExpired = card.expired
            paymentCardSecure = card.securityCode
        }
    }
}
