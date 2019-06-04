package org.atmarkcafe.otocon.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CreditCard {
    public static final int TYPE_VISA = 1;
    public static final int TYPE_MASTER = 2;

    @SerializedName("id")
    String id;

    @SerializedName("user_id")
    int userId;

    @SerializedName("is_default")
    int isDefault;

    @SerializedName("card_type")
    int cardType;

    @SerializedName("payment_card_number")
    String number = "";

    @SerializedName("payment_card_expired")
    String expired = "";

    @SerializedName("payment_card_secure")
    String secure = "";

    @SerializedName("created_at")
    String createdAt = "";

    @SerializedName("updated_at")
    String updatedAt = "";

    @SerializedName("month")
    String month;
    @SerializedName("year")
    String year;

    String securityCode = "";


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSecure() {
        return secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    public boolean isBlank() {
        return number.isEmpty() || securityCode.isEmpty() || expired.isEmpty();
    }

    public boolean isYearEmpty() {
        return year == null || year.isEmpty();
    }

    public boolean isMonthEmpty() {
        return month == null || month.isEmpty();
    }

    public boolean hasNumberError() {
        return number == null || number.length() != 16;
    }

    public boolean hasExpiredError() {
        if (expired == null) return true;
        DateFormat dateFormat = new SimpleDateFormat("MM/yy");
        try {
            Date date = dateFormat.parse(expired);
            Calendar calendar = Calendar.getInstance();
            Calendar exp = Calendar.getInstance();
            exp.setTimeInMillis(date.getTime());

            if (isMonthEmpty() || isYearEmpty()) {
                this.month = String.valueOf(exp.get(Calendar.MONTH) + 1);
                this.year = String.valueOf(exp.get(Calendar.YEAR));
            }

            if (exp.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                return exp.get(Calendar.MONTH) < calendar.get(Calendar.MONTH);
            }
            return exp.get(Calendar.YEAR) < calendar.get(Calendar.YEAR);

        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean hasSecureError() {
        return securityCode == null || securityCode.length() < 3 || securityCode.length() > 4;
    }


    public boolean validate() {
        return !hasNumberError() && !hasExpiredError() && !hasSecureError();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public boolean isDefault() {
        return isDefault == 1;
    }

    public int getCardType() {
        return cardType;
    }

    public void setExpired(String year, String month) {
        this.year = year;
        this.month = month;
        try {
            expired = String.format("%02d/%02d", Integer.parseInt(month), Integer.parseInt(year.substring(2)));
        } catch (Exception e) {
            expired = "";
        }
    }

    public String getExpiredText() {
        if (month == null || year == null){
            if (expired != null && expired.contains("/")) {
                StringTokenizer stringTokenizer = new StringTokenizer(expired, "/");

                month = stringTokenizer.nextToken();
                year = "20" + stringTokenizer.nextToken();
            }
        }
        try {
            return String.format("%02d/%2d", Integer.parseInt(year.substring(2)), Integer.parseInt(month));
        } catch (Exception e) {
        }
        return "";
    }

    public String getNumberShow() {
        return this.number;
    }

    public String cardNumberSecurity() {
        if (number.length() <= 2) return number;
        String star = "";
        for (int i = 0; i < number.length() - 2; i++) {
            star = star + "X";
        }

        return star + number.substring(number.length() - 2, number.length());

    }

    public void init() {
        if (expired != null && expired.contains("/")) {
            StringTokenizer stringTokenizer = new StringTokenizer(expired, "/");

            month = stringTokenizer.nextToken();
            year = "20" + stringTokenizer.nextToken();
        }
    }

    public void updateExpired() {
        setExpired(year, month);
    }

    // validate
    public Map<String, List<String>> getErrors(boolean checkSecureCode) {

        if (hasNumberError() || hasExpiredError() || (checkSecureCode && hasSecureError())) {
            Map<String, List<String>> errors = new HashMap<>();
            if (hasNumberError()) {
                errors.put("payment_card_number", new ArrayList<>());
            }

            if (isYearEmpty() || isMonthEmpty()) {
                if (isYearEmpty()) {
                    errors.put("payment_card_year", new ArrayList<>());
                }

                if (isMonthEmpty()) {
                    errors.put("payment_card_month", new ArrayList<>());
                }
            } else if (hasExpiredError()) {
                errors.put("payment_card_expired", new ArrayList<>());
                errors.put("payment_card_year", new ArrayList<>());
                errors.put("payment_card_month", new ArrayList<>());
            }

            if (hasSecureError() && checkSecureCode) {
                errors.put("payment_card_secure", new ArrayList<>());
            }

            return errors;
        }

        return null;
    }

    public String getErrorString(Context context, boolean checkSecureCode) {
        if (context != null) {
            if (checkSecureCode){
                if (isBlank()){
                    return context.getString(R.string.credit_card_blank);
                }
            } else {
                if (number.isEmpty() || expired.isEmpty()) {
                    return context.getString(R.string.credit_card_blank);
                }
            }
            if (hasNumberError() || hasExpiredError() || isYearEmpty() || isMonthEmpty() || (checkSecureCode && hasSecureError())) {
                return context.getString(R.string.credit_card_unavailable);
            }
        }

        return null;
    }


}
