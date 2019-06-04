package org.atmarkcafe.otocon.function.mypage

import android.database.Cursor
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.ViewHolder
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentLibraryImageBinding
import org.atmarkcafe.otocon.function.mypage.item.ChoosePictureItem
import android.graphics.BitmapFactory



class LibraryImageFragment: OtoconBindingFragment<FragmentLibraryImageBinding>(), OnItemClickListener {
    override fun onItemClick(item: Item<*>, view: View) {
        Glide.with(activity!!).load((item as ChoosePictureItem).path).into(viewDataBinding.ivAvatar)

        val bundle = Bundle()
        bundle.putString("Bitmap_key", item.path)
        otoconFragmentListener?.onHandlerReult(0, bundle)
    }

    override fun layout(): Int {
        return R.layout.fragment_library_image
    }

    override fun onCreateView(viewDataBinding: FragmentLibraryImageBinding) {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.setOnItemClickListener(this)
        viewDataBinding.recylerview.adapter = adapter
        // get list path image
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val orderBy: String = MediaStore.Images.Media._ID
        //Stores all the images from the gallery in Cursor
        val cursor: Cursor? = activity!!.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy)
        if (cursor != null && cursor.count > 0){
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                val dataColumnIndex: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                adapter.add(ChoosePictureItem(cursor.getString(dataColumnIndex)))
                if (i == 0){
                    Glide.with(activity!!).load(cursor.getString(dataColumnIndex)).into(viewDataBinding.ivAvatar)
                    val bundle = Bundle()
                    bundle.putString("Bitmap_key", cursor.getString(dataColumnIndex))
                    otoconFragmentListener?.onHandlerReult(0, bundle)
                }
            }
        }

    }

}