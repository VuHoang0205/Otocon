package org.atmarkcafe.otocon.function.mypage.rematch

import android.graphics.Color
import android.os.Bundle
import android.view.View
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentRequestRematchListBinding

class TheirRequestRematchFragment : MyRequestRematchFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MyRequestRematchPresenter("0", this)
    }

    override fun onCreateView(viewDataBinding: FragmentRequestRematchListBinding?) {
        super.onCreateView(viewDataBinding)
        viewDataBinding?.btnSubmit?.visibility = View.VISIBLE
        viewDataBinding?.tvNoti11?.visibility = View.VISIBLE
        viewDataBinding?.tvNoti12?.text = getString(R.string.list_request_rematch_noti2)
    }
}