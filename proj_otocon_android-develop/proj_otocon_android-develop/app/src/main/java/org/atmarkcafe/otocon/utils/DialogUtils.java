package org.atmarkcafe.otocon.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import org.atmarkcafe.otocon.R;

public class DialogUtils {
    /**
     * set statusbar color for dialog
     * @param dialog
     */
    public static void setStatusBarColor(android.app.Dialog dialog ){
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        dialog.getWindow().setStatusBarColor(dialog.getContext ().getResources ().getColor (R.color.colorPrimaryDark));
    }

    /**
     * show dialog with title and
     * @param context
     * @param title
     * @param message
     */
    public static void showDialog(Context context,String title, String message){
        AlertDialog dialog =  new AlertDialog.Builder (context)
                .setTitle (title)
                .setMessage (message)
                .setPositiveButton (context.getString (R.string.ok), null)
                .show ();
    }
}
