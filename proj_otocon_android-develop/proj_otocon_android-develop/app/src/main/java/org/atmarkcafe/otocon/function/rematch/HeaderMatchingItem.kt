package org.atmarkcafe.otocon.function.rematch

import android.view.View
import com.xwray.groupie.databinding.BindableItem
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.HeaderMatchingBinding
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.DBManager.isLogin


open class HeaderMatchingItem : BindableItem<HeaderMatchingBinding> {
    var isShow: Boolean = false
    var listener: Listener? = null;

    interface Listener {
        fun onClickGuide()
    }

    constructor(listener: RematchPairingFragment) {
        this.listener = listener;
        this.listener = listener
    }

    override fun bind(viewBinding: HeaderMatchingBinding, position: Int) {
        viewBinding.header = this
    }

    override fun getLayout(): Int {
        return R.layout.layout_header_matching
    }

}
