package org.atmarkcafe.otocon.function.mypage

import android.annotation.SuppressLint
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.LayoutAvatarActionBinding

class AvatarBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: LayoutAvatarActionBinding? = null
    private var editionListener: View.OnClickListener? = null
    private var deleteListener: View.OnClickListener? = null

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        //Set the custom view
        val inflater = LayoutInflater.from(context)

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_avatar_action, null, false)
        dialog.setContentView(binding!!.root)

        if (editionListener != null) binding!!.actionEditPhoto.setOnClickListener(editionListener)
        if (deleteListener != null) binding!!.actionDeletePhoto.setOnClickListener(deleteListener)
    }

    fun setOnClickEditionListener(listener: View.OnClickListener): AvatarBottomSheetFragment {
        this.editionListener = listener
        return this
    }

    fun setOnClickDeleteListener(listener: View.OnClickListener): AvatarBottomSheetFragment {
        this.deleteListener = listener
        return this
    }
}