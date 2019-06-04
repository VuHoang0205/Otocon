package org.atmarkcafe.otocon.function.mypage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import org.atmarkcafe.otocon.ExtensionActivity
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.databinding.FragmentChoosePictureAvatarBinding
import org.atmarkcafe.otocon.dialog.MessageDialog
import org.atmarkcafe.otocon.utils.FragmentUtils
import java.io.File


class ChoosePictureFragment : OtoconBindingFragment<FragmentChoosePictureAvatarBinding>(), OtoconFragment.OtoconFragmentListener, TabLayout.OnTabSelectedListener {
    var requestCode: Int = 0

    override fun onTabSelected(p0: TabLayout.Tab?) {
        when (p0?.position) {
            0 -> {
                if (checkStoratePermission()) {
                    choosePhoto()
                }
            }
            1 -> {
                if (childFragmentManager.findFragmentById(R.id.layoutFragmentChild) is CameraFragment) {
                    return
                }
                if (checkCameraPermission()) {
                    takePhoto()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (ActivityCompat.checkSelfPermission(context!!, STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                    Handler().post { choosePhoto() }
                } else {
                    Handler().post { viewDataBinding.tabLayout.getTabAt(1)?.select() }
                }
                requestCode = 0
            }
            REQUEST_CAMERA_PERMISSION -> {
                if (ActivityCompat.checkSelfPermission(context!!, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    Handler().post { finish() }
                }
                requestCode = 0
            }
            else -> {
                if (childFragmentManager.findFragmentById(R.id.layoutFragmentChild) == null) {
                    viewDataBinding.tabLayout.getTabAt(1)?.select()
                }
            }
        }

    }

    private var bundel: Bundle? = null
    private var fragmentCam = CameraFragment()
    val CAMERA_FRAGMENT = 10

    fun deleteRecursive(fileOrDirectory: File) {

        if (fileOrDirectory.isDirectory) {
            for (child in fileOrDirectory.listFiles()!!) {
                deleteRecursive(child)
            }
        }

        fileOrDirectory.delete()
    }

    override fun layout(): Int {
        return R.layout.fragment_choose_picture_avatar
    }

    override fun onCreateView(viewDataBinding: FragmentChoosePictureAvatarBinding) {
        // covert radiobutton to tablayout
        val tab_layout = viewDataBinding.tabLayout
        tab_layout.addTab(tab_layout.newTab().setCustomView(R.layout.item_tab_choose_picture))
        tab_layout.addTab(tab_layout.newTab().setCustomView(R.layout.item_tab_choose_picture))
        config(tab_layout, 0, getString(R.string.tab_library_choose_picture), R.drawable.custom_radiobutton_library_tab)
        config(tab_layout, 1, getString(R.string.tab_camera_choose_picture), R.drawable.custom_radiobutton_camera_tab)
        tab_layout.addOnTabSelectedListener(this)

        // delete folder picture in camrea otocon
        val fileDirector = File(activity!!.getFilesDir().toString() + "/picture")
        deleteRecursive(fileDirector)

        //
        viewDataBinding.toolbar.setNavigationOnClickListener {
            if (tab_layout.selectedTabPosition == 1 && !fragmentCam.isCameraOpen) {
                fragmentCam.enableButton(true)
                tab_layout.visibility = View.VISIBLE
                viewDataBinding.tvCallBack.visibility = View.GONE
            } else {
                onBackPressed()
            }
        }

        viewDataBinding.tvCallBack.setOnClickListener {
            // call back send bitmap
            onBackPressed()
            otoconFragmentListener?.onHandlerReult(0, bundel)
        }
    }

    private fun choosePhoto() {
        val fragment = LibraryImageFragment()
        fragment.otoconFragmentListener = this
        FragmentUtils.replaceChild(childFragmentManager, R.id.layoutFragmentChild, fragment, false)
        viewDataBinding.tvCallBack.visibility = View.VISIBLE
    }

    private fun takePhoto() {
        viewDataBinding.tvCallBack.visibility = View.GONE
        fragmentCam = CameraFragment()
        fragmentCam.otoconFragmentListener = this
        FragmentUtils.replaceChild(childFragmentManager, R.id.layoutFragmentChild, fragmentCam, false)
    }

    private fun config(tab_layout: TabLayout, i: Int, text: String, logo: Int) {
        val textView = tab_layout.getTabAt(i)!!.customView!!.findViewById<View>(R.id.tv_tab) as TextView
        val imageView = tab_layout.getTabAt(i)!!.customView!!.findViewById<View>(R.id.iv_tab) as ImageView
        if (logo > 0) {
            imageView.setImageResource(logo)
            textView.text = text
        }
    }

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        bundel = extras
        if (status == CAMERA_FRAGMENT) {
            viewDataBinding.tabLayout.visibility = View.GONE
            viewDataBinding.tvCallBack.visibility = View.VISIBLE
        }
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }


    private fun checkStoratePermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(activity!!, STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        // request permission
        val showed = activity?.getSharedPreferences(STORAGE_PERMISSION, 0)?.getBoolean(STORAGE_PERMISSION, false)
        if (showed != null && showed) {
            // show dialog
            showPermissionDialog(getString(R.string.storage_permission_title), getString(R.string.storage_permission_message), REQUEST_STORAGE_PERMISSION)
        } else {
            activity?.getSharedPreferences(STORAGE_PERMISSION, 0)?.edit()?.putBoolean(STORAGE_PERMISSION, true)?.commit()
            requestPermissions(arrayOf(STORAGE_PERMISSION), REQUEST_STORAGE_PERMISSION)
        }

        return false
    }

    private fun checkCameraPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(activity!!, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        // request permission
        val showed = activity?.getSharedPreferences(CAMERA_PERMISSION, 0)?.getBoolean(CAMERA_PERMISSION, false)
        if (showed != null && showed) {
            // show dialog
            showPermissionDialog(getString(R.string.camera_permission_title), getString(R.string.camera_permission_message), REQUEST_CAMERA_PERMISSION)
        } else {
            activity?.getSharedPreferences(CAMERA_PERMISSION, 0)?.edit()?.putBoolean(CAMERA_PERMISSION, true)?.commit()
            requestPermissions(arrayOf(CAMERA_PERMISSION), REQUEST_CAMERA_PERMISSION)
        }

        return false
    }

    private fun showPermissionDialog(title: String, message: String, requestCode: Int) {
        this.requestCode = requestCode
        val dialog = MessageDialog(context!!) { ok ->
            if (ok) {
                ExtensionActivity.openAppSettings(context)
            } else {
                if (requestCode == REQUEST_CAMERA_PERMISSION) {
                    finish()
                } else {
                    viewDataBinding.tabLayout.getTabAt(1)?.select()
                }
            }
        }
        dialog.set(
                title,
                message
        )
        dialog.setButtonOk(getString(R.string.permission_accept_button))
        dialog.setButtonCancel(getString(R.string.permission_deny_button))

        dialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (ActivityCompat.checkSelfPermission(activity!!, STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                    choosePhoto()
                } else {
                    viewDataBinding.tabLayout.getTabAt(1)?.select()
                }
            }
            REQUEST_CAMERA_PERMISSION -> {
                if (ActivityCompat.checkSelfPermission(activity!!, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    finish()
                }
            }
        }
        this.requestCode = 0
    }

    companion object {
        const val REQUEST_STORAGE_PERMISSION = 111
        const val STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

        const val REQUEST_CAMERA_PERMISSION = 112
        const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}