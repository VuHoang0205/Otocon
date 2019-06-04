package org.atmarkcafe.otocon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.utils.KeyExtensionUtils;
import org.atmarkcafe.otocon.utils.UserRole;

import java.io.Serializable;

/**
 * The Account is object model1 contains the basic information of the user
 *
 * @author acv-vuht
 * @version 1.0
 * @since 2018-08-17
 */
public class Account implements Serializable {

    @SerializedName("id")
    String id;

    @SerializedName("type")
    int type;

    @SerializedName("username")
    String username;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("is_active")
    String is_active;

    @SerializedName("name_sei")
    String name_sei;

    @SerializedName("name_mei")
    String name_mei;

    @SerializedName("name_kanasei")
    String name_kanasei;

    @SerializedName("name_kanamei")
    String name_kanamei;

    @SerializedName("age")
    int age;

    @SerializedName("birthday")
    String birthday;

    @SerializedName("role_value")
    String role_value;

    @SerializedName("prefecture")
    int prefecture;

    @SerializedName("gender")
    int gender;

    @SerializedName("tel")
     String tel;

    @SerializedName("token")
     String token;

    @SerializedName("ponta_id")
     String pontaId;

    @SerializedName("coupon_id")
     String couponId;

    @SerializedName("coupon_code")
     String couponCode;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getName_sei() {
        return name_sei;
    }

    public void setName_sei(String name_sei) {
        this.name_sei = name_sei;
    }

    public String getName_mei() {
        return name_mei;
    }

    public void setName_mei(String name_mei) {
        this.name_mei = name_mei;
    }

    public String getName_kanasei() {
        return name_kanasei;
    }

    public void setName_kanasei(String name_kanasei) {
        this.name_kanasei = name_kanasei;
    }

    public String getName_kanamei() {
        return name_kanamei;
    }

    public void setName_kanamei(String name_kanamei) {
        this.name_kanamei = name_kanamei;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRole_value() {
        return role_value;
    }

    public void setRole_value(String role_value) {
        this.role_value = role_value;
    }

    public int getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(int prefecture) {
        this.prefecture = prefecture;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPremium() {
        return UserRole.factory(type) == UserRole.premium;
    }

    public boolean hasToken() {
        return token != null && !token.isEmpty();
    }

    public String getPontaId() {
        return pontaId;
    }
}
