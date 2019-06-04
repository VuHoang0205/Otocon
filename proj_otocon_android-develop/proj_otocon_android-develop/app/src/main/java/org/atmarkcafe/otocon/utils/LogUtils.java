package org.atmarkcafe.otocon.utils;

import android.util.Log;

public class LogUtils {
    public static final String TAG = "LogUtils";

    public static final boolean ENABLE = true;
    public static void d(String message, Throwable throwable){
        if(ENABLE){
            if(message == null){
                message = TAG;
            }

            if(throwable == null){
                Log.d(TAG, message);
            }else{
                Log.d(TAG, message, throwable);
            }
        }

    }
}
