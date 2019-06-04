package org.atmarkcafe.otocon.utils

import android.content.Intent
import android.support.v4.app.FragmentActivity
import org.atmarkcafe.otocon.MainFragment
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.model.Account
import org.atmarkcafe.otocon.model.DBManager

class AuthenticationUtils {
    companion object {
        var act: FragmentActivity?=null
        var dialog: PopupMessageErrorDialog? = null

        fun setActivity(act: FragmentActivity){
            this.act = act
            this.dialog = null
        }

        fun show(msg: String) {
            if (act == null || act!!.isFinishing) {
                return
            }
            if (dialog == null) {
                dialog = PopupMessageErrorDialog(act!!, msg)
                        .setPopupMessageErrorListener {
                            act?.sendBroadcast(Intent(KeyExtensionUtils.ACTION_HIDE_NOTI_MY_REMATCH))
                            FragmentUtils.backToTop(act)
                            val currentFragment = FragmentUtils.getFragment(act)
                            (currentFragment as? MainFragment)?.requestReLogin()
                        }
            }

            DBManager.save(act, Account())
            if (dialog != null && !dialog!!.isShowing) {
                dialog?.show()
            }
        }
    }
}