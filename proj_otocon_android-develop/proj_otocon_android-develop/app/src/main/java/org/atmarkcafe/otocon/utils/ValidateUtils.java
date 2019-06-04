package org.atmarkcafe.otocon.utils;

import android.content.Context;
import android.util.Patterns;

import org.atmarkcafe.otocon.R;

import java.util.regex.Pattern;

import retrofit2.HttpException;

/**
 * Validate utils
 */
public class ValidateUtils {
    private static final String TAG = "ValidateUtils";

    /**
     * validate email
     *
     * @param email
     * @return
     */
    public static final boolean isEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * validate passworld
     *
     * @param passworld
     * @return
     */
    public static final boolean isPassworld(String passworld) {
        return Pattern.compile("[a-zA-Z0-9\\\\!\\\\@\\\\#\\\\$]{8,24}").matcher(passworld).matches();
    }

    /**
     * @param phone
     * @return
     */
    public static final boolean isPhone(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    /**
     * validate url
     *
     * @param url
     * @return
     */
    public static final boolean isUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    /**
     * Check String is null or empty
     *
     * @param str
     * @return
     */
    public static final boolean isBlank(String str) {
        return str == null || str != null && str.trim().isEmpty();
    }

    public static boolean isRetrofitErrorNetwork(Throwable e) {
        if (e instanceof HttpException) {
            return false;
        }

        return true;
    }
}
