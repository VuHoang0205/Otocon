package org.atmarkcafe.otocon.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by truongvv on 7/4/17.
 */

public class KeyboardUtils {


    public static void showKeyBoard(Activity activity, EditText editText) {
        String service = Context.INPUT_METHOD_SERVICE;
        InputMethodManager imm = null;
        imm = (InputMethodManager) activity.getSystemService(service);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public static void showKeyBoard(Activity activity) {
        String service = Context.INPUT_METHOD_SERVICE;
        InputMethodManager imm = null;
        imm = (InputMethodManager) activity.getSystemService(service);
        imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_FORCED);
    }

    public static void showKeyBoard(EditText keEditText) {
        keEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) keEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(keEditText.getWindowToken(), 0);
        imm.showSoftInput(keEditText, InputMethodManager.SHOW_IMPLICIT);

    }

    /**
     *
     * @param activity
     */
    public static void hiddenKeyBoard(Activity activity) {
        try {
            String service = Context.INPUT_METHOD_SERVICE;
            InputMethodManager imm = null;
            imm = (InputMethodManager) activity.getSystemService(service);
            IBinder binder = activity.getCurrentFocus().getWindowToken();
            imm.hideSoftInputFromWindow(binder, 0);
        } catch (Exception e) {
        }
    }
    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
