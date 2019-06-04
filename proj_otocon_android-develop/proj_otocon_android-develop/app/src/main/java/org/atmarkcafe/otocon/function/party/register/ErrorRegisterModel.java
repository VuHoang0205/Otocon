package org.atmarkcafe.otocon.function.party.register;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.R;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorRegisterModel extends BaseObservable {

    @SerializedName("email")
    List<String> emails;

    @SerializedName("password")
    List<String> passwords;

    @SerializedName("password_confirmation")
    List<String> passwordConfirmations;

    @SerializedName("name_sei")
    List<String> nameSeiList;

    @SerializedName("name_mei")
    List<String> nameMeiList;

    @SerializedName("name_kanasei")
    List<String> nameKanaSeiList;

    @SerializedName("name_kanamei")
    List<String> nameKanaMeiList;

    @SerializedName("gender")
    List<String> genderList;

    @SerializedName("birthday")
    List<String> birthdays;

    @SerializedName("tel")
    List<String> telList;

    @SerializedName("prefecture")
    List<String> prefectures;

    @SerializedName("terms_of_service")
    List<String> termsOfServiceList;

    @SerializedName("event_id")
    List<String> eventIdList;

    @SerializedName("ponta_id")
    List<String> pontaIdList;

    @SerializedName("payment")
    List<String> paymentList;

    @SerializedName("friend_1")
    List<String> friendList1;

    @SerializedName("friend_2")
    List<String> friendList2;


    public String getPaymentList() {
        return getStringErrorMessage(paymentList);
    }

    public void setPaymentList(List<String> paymentList) {
        this.paymentList = paymentList;
    }

    public String getEmails() {
        return emails == null ? null : emails.size() > 0 ? emails.get(0) : "";
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getPasswords() {
        return passwords == null ? null : passwords.size() > 0 ? passwords.get(0) : "";
    }

    public void setPasswords(List<String> passwords) {
        this.passwords = passwords;
    }

    public String getPasswordConfirmations() {
        return getStringErrorMessage(passwordConfirmations);
    }

    public void setPasswordConfirmations(List<String> passwordConfirmations) {
        this.passwordConfirmations = passwordConfirmations;
    }

    public String getNameSeiList() {
        return getStringErrorMessage(nameSeiList);
    }

    public void setNameSeiList(List<String> nameSeiList) {
        this.nameSeiList = nameSeiList;
    }

    public String getNameMeiList() {
        return getStringErrorMessage(nameMeiList);
    }

    public void setNameMeiList(List<String> nameMeiList) {
        this.nameMeiList = nameMeiList;
    }

    public String getNameKanaSeiList() {
        return getStringErrorMessage(nameKanaSeiList);
    }

    public void setNameKanaSeiList(List<String> nameKanaSeiList) {
        this.nameKanaSeiList = nameKanaSeiList;
    }

    private String getStringErrorMessage(List<String> errors) {
        return errors != null && errors.size() > 0 ? errors.get(0) : "";
    }

    public String getNameKanaMeiList() {
        return getStringErrorMessage(nameKanaMeiList);
    }

    public void setNameKanaMeiList(List<String> nameKanaMeiList) {
        this.nameKanaMeiList = nameKanaMeiList;
    }

    public String getFriendList1() {
        return getStringErrorMessage(friendList1);
    }

    public void setFriendList1(List<String> friendList1) {
        this.friendList1 = friendList1;
    }

    public String getFriendList2() {
        return getStringErrorMessage(friendList2);
    }

    public void setFriendList2(List<String> friendList2) {
        this.friendList2 = friendList2;
    }

    public String getGenderList() {
        return getStringErrorMessage(genderList);
    }

    public void setGenderList(List<String> genderList) {
        this.genderList = genderList;
    }

    public String getBirthdays() {
        return getStringErrorMessage(birthdays);
    }

    public void setBirthdays(List<String> birthdays) {
        this.birthdays = birthdays;
    }

    public String getTelList() {
        return getStringErrorMessage(telList);
    }

    public void setTelList(List<String> telList) {
        this.telList = telList;
    }

    public String getPrefectures() {
        return getStringErrorMessage(prefectures);
    }

    public void setPrefectures(List<String> prefectures) {
        this.prefectures = prefectures;
    }

    public String getTermsOfServiceList() {
        return getStringErrorMessage(termsOfServiceList);
    }

    public void setTermsOfServiceList(List<String> termsOfServiceList) {
        this.termsOfServiceList = termsOfServiceList;
    }

    public List<String> getEventIdList() {
        return eventIdList;
    }

    public void setEventIdList(List<String> eventIdList) {
        this.eventIdList = eventIdList;
    }

    public List<String> getPontaIdList() {
        return pontaIdList;
    }

    public void setPontaIdList(List<String> pontaIdList) {
        this.pontaIdList = pontaIdList;
    }


}
