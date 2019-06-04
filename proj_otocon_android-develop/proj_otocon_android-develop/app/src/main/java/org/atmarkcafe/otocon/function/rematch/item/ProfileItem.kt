package org.atmarkcafe.otocon.function.rematch.item

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.view.View
import com.xwray.groupie.databinding.BindableItem
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.ItemProfileBinding
import org.atmarkcafe.otocon.databinding.ItemRequestRematchBinding
import org.atmarkcafe.otocon.model.RequestRematch

class ProfileItem(val key: String, val value: String, val bold: Boolean) : BindableItem<ItemProfileBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_profile
    }

    override fun bind(viewBinding: ItemProfileBinding, position: Int) {
        viewBinding.key = key
        viewBinding.value = value

        val font = ResourcesCompat.getFont(viewBinding.root.context, if (bold) R.font.hiragino_kaku_gothic_pro_w6 else R.font.hiragino_kaku_gothic_pro_w3)
        viewBinding.keyTxt.typeface = font
    }

}