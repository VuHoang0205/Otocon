package org.atmarkcafe.otocon.model;

import java.io.Serializable;

/**
 * class PartyRegister
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class PartyRegister implements Serializable {

    private String content;
    private String shortContent;
    private String email;
    private String passworld;
    private String phoneNumber;
    private String lastName;
    private String firstName;
    private String say;
    private String mei;
    private String gender;
    private String birthday;
    private String prefectures;
    private String numberPeople;
    private String friendOptional;
    private String pontaId;
    private String payment;

    public String getContent() {
        return content;
    }

    public String getShortContent() {
        return shortContent;
    }

    public String getEmail() {
        return email;
    }

    public String getPassworld() {
        return passworld;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSay() {
        return say;
    }

    public String getMei() {
        return mei;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPrefectures() {
        return prefectures;
    }

    public String getNumberPeople() {
        return numberPeople;
    }

    public String getFriendOptional() {
        return friendOptional;
    }

    public String getPontaId() {
        return pontaId;
    }

    public String getPayment() {
        return payment;
    }

    private PartyRegister(Builder builder) {
        this.content = builder.content;
        this.shortContent = builder.shortContent;
        this.email = builder.email;
        this.passworld = builder.passworld;
        this.phoneNumber = builder.phoneNumber;
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.say = builder.say;
        this.mei = builder.mei;
        this.gender = builder.gender;
        this.birthday = builder.birthday;
        this.prefectures = builder.prefectures;
        this.numberPeople = builder.numberPeople;
        this.friendOptional = builder.friendOptional;
        this.pontaId = builder.pontaId;
        this.payment = builder.payment;

    }

    public static class Builder {
        private String content;
        private String shortContent;
        private String email;
        private String passworld;
        private String phoneNumber;
        private String lastName;
        private String firstName;
        private String say;
        private String mei;
        private String gender;
        private String birthday;
        private String prefectures;
        private String numberPeople;
        private String friendOptional;
        private String pontaId;
        private String payment;

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setShortContent(String shortContent) {
            this.shortContent = shortContent;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassworld(String passworld) {
            this.passworld = passworld;
            return this;
        }

        public Builder setphoneNumber(String repeatPassworld) {
            this.phoneNumber = repeatPassworld;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setSay(String say) {
            this.say = say;
            return this;
        }

        public Builder setMei(String mei) {
            this.mei = mei;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder setPrefectures(String prefectures) {
            this.prefectures = prefectures;
            return this;
        }

        public Builder setNumberPeople(String numberPeople) {
            this.numberPeople = numberPeople;
            return this;
        }

        public Builder setFriendOptional(String friendOptional) {
            this.friendOptional = friendOptional;
            return this;
        }

        public Builder setPontaId(String pontaId) {
            this.pontaId = pontaId;
            return this;
        }

        public Builder setPayment(String payment) {
            this.payment = payment;
            return this;
        }

        public PartyRegister builder() {
            return new PartyRegister(this);
        }
    }
}
