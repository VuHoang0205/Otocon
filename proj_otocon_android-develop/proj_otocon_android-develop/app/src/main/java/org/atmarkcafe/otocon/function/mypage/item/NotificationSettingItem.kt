package org.atmarkcafe.otocon.function.mypage.item

import com.xwray.groupie.databinding.BindableItem
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.ItemNotificationSettingBinding
import org.atmarkcafe.otocon.model.NotificationSetting

class NotificationSettingItem(val type: Int, val setting: NotificationSetting, val listener: OnChangedCheckboxListener) : BindableItem<ItemNotificationSettingBinding>() {
    interface OnChangedCheckboxListener {
        fun onChanged(type: Int)
    }

    var title = ""
    var description = ""
    var checked = true

    override fun getLayout(): Int {
        return R.layout.item_notification_setting
    }

    override fun bind(viewBinding: ItemNotificationSettingBinding, position: Int) {
        val ctx = viewBinding.root.context
        when (type) {
            NotificationSetting.NEW_PARTY -> {
                title = ctx.getString(R.string.notification_new_party_title)
                description = ctx.getString(R.string.notification_new_party_description)
                checked = (setting.newParty == 1)
            }
            NotificationSetting.COUPON -> {
                title = ctx.getString(R.string.notification_coupon_title)
                description = ctx.getString(R.string.notification_coupon_description)
                checked = (setting.coupon == 1)
            }
            NotificationSetting.BEFORE_PARTY_LIKED -> {
                title = ctx.getString(R.string.notification_liked_party_title)
                description = ctx.getString(R.string.notification_liked_party_description)
                checked = (setting.beforePartyLiked == 1)
            }
            NotificationSetting.PARTY_SLOT -> {
                title = ctx.getString(R.string.notification_party_slot_title)
                description = ctx.getString(R.string.notification_party_slot_description)
                checked = (setting.partySlot == 1)
            }
            NotificationSetting.BEFORE_PARTY_JOINED -> {
                title = ctx.getString(R.string.notificatin_before_party_joined_title)
                description = ctx.getString(R.string.notificatin_before_party_joined_description)
                checked = (setting.beforePartyJoined == 1)
            }
            NotificationSetting.REMATCH -> {
                title = ctx.getString(R.string.notification_rematch_title)
                description = ctx.getString(R.string.notification_rematch_description)
                checked = (setting.rematch == 1)
            }
        }

        viewBinding.item = this

        viewBinding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (checked != isChecked) listener.onChanged(type)
        }
    }

}