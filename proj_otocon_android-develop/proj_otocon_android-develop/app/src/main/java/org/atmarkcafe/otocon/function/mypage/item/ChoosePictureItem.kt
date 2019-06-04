package org.atmarkcafe.otocon.function.mypage.item

import com.bumptech.glide.Glide
import com.xwray.groupie.databinding.BindableItem
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.ItemChoosePictureBinding

class ChoosePictureItem: BindableItem<ItemChoosePictureBinding> {


    lateinit var path: String

    constructor(path: String) {
        this.path = path

    }

    override fun getLayout(): Int {
        return R.layout.item_choose_picture
    }

    override fun bind(viewBinding: ItemChoosePictureBinding, position: Int) {
        Glide.with(viewBinding.tvPicture.context).load(path).into(viewBinding.tvPicture)
    }
}