package org.atmarkcafe.otocon.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.model.response.PartyDetailData;
import org.atmarkcafe.otocon.model.response.Prefecture;
import org.atmarkcafe.otocon.pref.BaseShareReferences;
import org.atmarkcafe.otocon.utils.Gender;
import org.atmarkcafe.otocon.utils.StringUtils;
import org.atmarkcafe.otocon.utils.UserRole;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class RegisterPartyModel extends BaseObservable {

    public boolean isLogin;
    private static final String FORMAT_PRICE = "###,###,###";

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @SerializedName("event_id")
    private String eventID = "";

    @SerializedName("payment")
    private int payment;

    @SerializedName("ponta_id")
    private String pontaID = "";

    @SerializedName("total_friends")
    private String friend = "";

    @SerializedName("email")
    private String email = "";

    @SerializedName("name_sei")
    private String nameSei = "";

    @SerializedName("name_mei")
    private String nameMei = "";

    @SerializedName("name_kanasei")
    private String nameKanasei = "";

    @SerializedName("name_kanamei")
    private String nameKanamei = "";

    @SerializedName("gender")
    private String gender;

    @SerializedName("prefecture")
    private String prefecture;

    @SerializedName("birthday")
    private String birthday = "";

    @SerializedName("coupon_id")
    private String coupon = "";


    @SerializedName("password")
    public String passoWord = "";

    @SerializedName("terms_of_service")
    private int term;

    @SerializedName("is_apply_coupon")
    private int isApplyCoupon;

    @SerializedName("device_id")
    private String deviceID = "";

    @SerializedName("friend_1")
    private String friend1 = "";

    @SerializedName("friend_2")
    private String friend2 = "";

    public boolean isCheckCard;

    private String birthdayText;

    public void setBirthdayText(String birthdayText) {
        this.birthdayText = birthdayText;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public int isApplyCoupon() {
        return isApplyCoupon;
    }

    public void setApplyCoupon(int applyCoupon) {
        isApplyCoupon = applyCoupon;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    @Bindable
    public String getPontaID() {
        return pontaID;
    }

    public void setPontaID(String pontaID) {
        this.pontaID = pontaID;
        notifyPropertyChanged(BR.pontaID);
    }

    @Bindable
    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
        notifyPropertyChanged(BR.friend);
    }

    @Bindable
    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
        notifyPropertyChanged(BR.coupon);
    }

    public boolean isCheckCard() {
        return isCheckCard;
    }

    public void setCheckCard(boolean checkCard) {
        isCheckCard = checkCard;
    }

    public String getPassWord() {
        return passoWord;
    }

    public void setPassWord(String passoWord) {
        this.passoWord = passoWord;
    }

    @Bindable
    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
        notifyPropertyChanged(BR.term);
    }


    int age;

    private String genderName;

    private String prefectureName = "";

    private String tel1 = "";
    private String tel2 = "";
    private String tel3 = "";

    private String year = "";
    private String month = "";
    private String day = "";

    private String tel = "";

    private boolean active;

    private boolean use;
    private boolean notUse;

    private boolean card;
    private boolean notCard;

    private boolean termApp;

    private String couponCode;

    @Bindable
    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
        notifyPropertyChanged(BR.couponCode);
    }

    @Bindable
    public boolean isTermApp() {
        return termApp;
    }

    public void setTermApp(boolean termApp) {
        this.termApp = termApp;
        notifyPropertyChanged(BR.termApp);
    }

    @Bindable
    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
        notifyPropertyChanged(BR.use);
    }

    @Bindable
    public boolean isNotUse() {
        return notUse;
    }

    public void setNotUse(boolean notUse) {
        this.notUse = notUse;
        notifyPropertyChanged(BR.notUse);
    }

    @Bindable
    public boolean isCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
        notifyPropertyChanged(BR.card);
    }

    @Bindable
    public boolean isNotCard() {
        return notCard;
    }

    public void setNotCard(boolean notCard) {
        this.notCard = notCard;
        notifyPropertyChanged(BR.notCard);
    }

    @Bindable
    public String getFriend1() {
        return friend1;
    }

    public void setFriend1(String friend1) {
        this.friend1 = friend1;
        notifyPropertyChanged(BR.friend1);
    }

    @Bindable
    public String getFriend2() {
        return friend2;
    }

    public void setFriend2(String friend2) {
        this.friend2 = friend2;
        notifyPropertyChanged(BR.friend2);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    @Bindable
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    @Bindable
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
        notifyPropertyChanged(BR.month);
    }

    @Bindable
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    @Bindable
    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
        notifyPropertyChanged(BR.tel1);
    }

    @Bindable
    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
        notifyPropertyChanged(BR.tel2);
    }

    @Bindable
    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
        notifyPropertyChanged(BR.tel3);
    }

    @Bindable
    public String getGenderName() {
        return genderName;

    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
        notifyPropertyChanged(BR.genderName);
    }

    @Bindable
    public String getPrefectureName() {
        return prefectureName;
    }

    public void setPrefectureName(String prefectureName) {
        this.prefectureName = prefectureName;
        notifyPropertyChanged(BR.prefectureName);
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getNameSei() {
        return nameSei;
    }

    public void setNameSei(String nameSei) {
        this.nameSei = nameSei;
        notifyPropertyChanged(BR.nameSei);
    }

    @Bindable
    public String getNameMei() {
        return nameMei;
    }

    public void setNameMei(String nameMei) {
        this.nameMei = nameMei;
        notifyPropertyChanged(BR.nameMei);
    }

    @Bindable
    public String getNameKanasei() {
        return nameKanasei;
    }

    public void setNameKanasei(String nameKanasei) {
        this.nameKanasei = nameKanasei;
        notifyPropertyChanged(BR.nameKanasei);
    }

    @Bindable
    public String getNameKanamei() {
        return nameKanamei;
    }

    public void setNameKanamei(String nameKanamei) {
        this.nameKanamei = nameKanamei;
        notifyPropertyChanged(BR.nameKanamei);
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Bindable
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        notifyPropertyChanged(BR.active);
    }

    @Bindable
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    public String getBirthDayParam(Context context) {
        return String.format(context.getString(R.string.fomat_date), StringUtils.fomatDate(year), StringUtils.fomatDate(month), StringUtils.fomatDate(day));
    }

    public String getBirthdayText(Context context) {
        String[] data = birthday.split(context.getString(R.string.fall));
        String year = data[0];
        String month = StringUtils.toOneNumber(data[1]);
        String day = StringUtils.toOneNumber(data[2]);
        return String.format(context.getString(R.string.fomat_date_view), year, month, day);

    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }


    public boolean notLoginUser() {
        return !isLogin;
    }

    public String getYearBirthdayUser(String birthday) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return notLoginUser() ? year - 30 + "" : fomatString(birthday, 1);
    }

    public String getMonthBirthdayUser(String birthday) {
        return notLoginUser() ? "1" : String.valueOf(Integer.parseInt(fomatString(birthday, 2)));
    }

    public String getDayBirthdayUser(String birthday) {
        return notLoginUser() ? "1" : String.valueOf(Integer.parseInt(fomatString(birthday, 3)));
    }

    public String getTel1User(String tel) {
        return notLoginUser() ? "" : fomatString(tel, 1);
    }

    public String getTel2User(String tel) {
        return notLoginUser() ? "" : fomatString(tel, 2);
    }

    public String getTel3User(String tel) {
        return notLoginUser() ? "" : fomatString(tel, 3);
    }

    public String getPrefectureUser(Context context, int id) {
        String addrees = "";
        String json = new BaseShareReferences(context).get(BaseShareReferences.KEY_PREFECTURE);
        // if first
        if (json.isEmpty()) {
            json = "[]";
        }

        Gson gson = new Gson();
        List<Prefecture> list = gson.fromJson(json, Prefecture.typeList);
        for (Prefecture pre : list) {
            if (pre.getId().equals(String.valueOf(id))) {
                addrees = pre.getName();
                break;
            }
        }

        return notLoginUser() ? "" : addrees;
    }


    public String fomatString(String data, int index) {
        String number = "";
        StringTokenizer tokenizer = new StringTokenizer(data, "-");
        switch (index) {
            case 1:
                number = tokenizer.nextToken();
                break;
            case 2:
                tokenizer.nextToken();
                number = tokenizer.nextToken();
                break;
            case 3:
                tokenizer.nextToken();
                tokenizer.nextToken();
                number = tokenizer.nextToken();
                break;
            default:
                break;
        }
        return number;
    }

    public boolean hasFriend(String people) {
        if (people != null) {
            return friend.equals("2") || friend.equals("3");
        }
        return false;
    }

    public boolean hasTwoFriend(String people) {
        if (friend != null) {
            return people.equals("3");
        }
        return false;
    }

    public void updateModel(RegisterPartyModel model, Context context) {
        model.setTel(model.getTel1() + model.getTel() + model.getTel3());
        if (!model.getTel1().isEmpty() && !model.getTel2().isEmpty() && !model.getTel3().isEmpty()) {
            model.setTel(String.format("%s-%s-%s", model.getTel1(), model.getTel2(), model.getTel3()));
        }
        String temp = model.getGenderName();
        String genderTemp = null;
        if ((context.getString(R.string.confirm_information_male)).equals(temp)) {
            genderTemp = "1";
        } else if (context.getString(R.string.confirm_information_Female).equals(temp)) {
            genderTemp = "2";
        }
        model.setGender(genderTemp);

        model.setApplyCoupon(model.isUse() ? 1 : 0);

        model.setPayment(model.isCard() ? 1 : 2);
        model.setTerm(model.isTermApp() ? 1 : 0);

        model.setBirthday(model.getBirthDayParam(context));

        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        model.setDeviceID(android_id);
    }
}


