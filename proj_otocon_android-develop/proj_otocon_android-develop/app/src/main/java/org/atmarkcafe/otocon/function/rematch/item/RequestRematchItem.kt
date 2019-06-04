package org.atmarkcafe.otocon.function.rematch.item

import android.graphics.Color
import android.view.View
import com.xwray.groupie.databinding.BindableItem
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.ItemRequestRematchBinding
import org.atmarkcafe.otocon.model.RequestRematch

class RequestRematchItem : BindableItem<ItemRequestRematchBinding> {

    var item: RequestRematch = RequestRematch()

    constructor(item: RequestRematch) {
        this.item = item
    }

    override fun getLayout(): Int {
        return R.layout.item_request_rematch
    }

    override fun bind(viewBinding: ItemRequestRematchBinding, position: Int) {

        if (item.withdrawal_status == 1) {
            item.rematch_status = "退会済み"
            item.rematch_color = "#E5E5E5"
            viewBinding.tvRematch.setTextColor(Color.parseColor("#161616"))
            viewBinding.tvSttApp.visibility = View.GONE
            viewBinding.tvSttOnline.visibility = View.GONE
        }
        viewBinding.item = item

        if (item.read_at.isNullOrBlank()) viewBinding.root.setBackgroundColor(Color.parseColor("#EDF2FA")) else viewBinding.root.setBackgroundColor(Color.parseColor("#ffffff"))

    }

}