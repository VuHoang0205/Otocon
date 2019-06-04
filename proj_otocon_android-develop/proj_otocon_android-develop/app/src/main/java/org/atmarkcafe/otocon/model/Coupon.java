package org.atmarkcafe.otocon.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Coupon {
    public static final int COUPON_NORMAL = 1;
    public static final int COUPON_REMATCH = 2;

    @SerializedName("id")
    int id;

    @SerializedName("code")
    String code;

    @SerializedName("title")
    String title;

    @SerializedName("body")
    String body;

    @SerializedName("valid_start")
    String validStart;

    @SerializedName("valid_end")
    String validEnd;

    @SerializedName("image")
    String image;

    @SerializedName("discount")
    long discount;

    @SerializedName("is_apply")
    int apply;

    @SerializedName("type")
    int type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public String getTextTitleToolbarCouponDetail(Context context){
        return String.format(context.getString(R.string.coupon_title_fomat), title, discount);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getValidStart() {
        return validStart;
    }

    public void setValidStart(String validStart) {
        this.validStart = validStart;
    }

    public String getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(String validEnd) {
        this.validEnd = validEnd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public int getApply() {
        return apply;
    }

    public void setApply(int apply) {
        this.apply = apply;
    }

    public int getType() {
        return type;
    }

    public String getValidDate(){
        DateFormat fromDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat toDf = new SimpleDateFormat("yyyy年M月d日（E）", Locale.JAPANESE);
        try{
            String result = toDf.format(fromDf.parse(validStart)) + "～" + toDf.format(fromDf.parse(validEnd));
            return result;
        }catch (Exception e) {
            return "";
        }
    }
}
