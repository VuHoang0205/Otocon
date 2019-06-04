package org.atmarkcafe.otocon.function.rematch.item

import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.item_list_user.view.*
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.UserBinding
import org.atmarkcafe.otocon.dialog.SelectionContact
import org.atmarkcafe.otocon.model.MatchingModel
import org.atmarkcafe.otocon.model.User
import org.atmarkcafe.otocon.model.UserRematch

class UserItem(val response: UserRematch) : BindableItem<UserBinding>() {

    override fun bind(viewBinding: UserBinding, position: Int) {
        if (response.withdrawal_status==1) {
            response.nickname = viewBinding.root.context.getString(R.string.with_deawal_name)
            response.online_status=viewBinding.root.context.getString(R.string.unsubscribed)
            viewBinding.timeOnline.visibility=View.GONE
            viewBinding.statusRequest.visibility=View.GONE
        }
        viewBinding.user = this
    }

    override fun getLayout(): Int {
        return R.layout.item_list_user
    }
}