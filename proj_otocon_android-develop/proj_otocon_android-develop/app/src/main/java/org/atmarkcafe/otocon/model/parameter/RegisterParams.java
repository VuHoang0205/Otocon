package org.atmarkcafe.otocon.model.parameter;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.utils.StringUtils;

import java.io.Serializable;


public class RegisterParams extends Params implements Serializable {

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("password_confirmation")
    String passwordConfirmation;

    @SerializedName("name_kanasei")
    String nameKanasei;

    @SerializedName("name_kanamei")
    String nameKanamei;

    @SerializedName("name_sei")
    String nameSei;

    @SerializedName("name_mei")
    String nameMei;

    @SerializedName("gender")
    String gender;

    @SerializedName("birthday")
    String birthday;

    @SerializedName("tel")
    String tel;

    @SerializedName("prefecture")
    String prefecture;

    @SerializedName("terms_of_service")
    String term;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    String prefectureName;

    String genderName;

    public String getPrefectureName() {
        return prefectureName;
    }

    public void setPrefectureName(String prefectureName) {
        this.prefectureName = prefectureName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameKanasei() {
        return nameKanasei;
    }

    public void setNameKanasei(String nameKanasei) {
        this.nameKanasei = nameKanasei;
    }

    public String getNameKanamei() {
        return nameKanamei;
    }

    public void setNameKanamei(String nameKanamei) {
        this.nameKanamei = nameKanamei;
    }

    public String getNameSei() {
        return nameSei;
    }

    public void setNameSei(String nameSei) {
        this.nameSei = nameSei;
    }

    public String getNameMei() {
        return nameMei;
    }

    public void setNameMei(String nameMei) {
        this.nameMei = nameMei;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String password_confirmation) {
        this.passwordConfirmation = password_confirmation;
    }

    public String getBirthDayParam(Context context, String year, String month, String day) {
        return String.format(context.getString(R.string.fomat_date), StringUtils.fomatDate(year), StringUtils.fomatDate(month), StringUtils.fomatDate(day));
    }

    public String getBirthdayText(Context context) {
        String[] data = birthday.split(context.getString(R.string.fall));
        String year = data[0];
        String month = StringUtils.toOneNumber(data[1]);
        String day = StringUtils.toOneNumber(data[2]);
        return String.format(context.getString(R.string.fomat_date_view), year, month, day);

    }
}
