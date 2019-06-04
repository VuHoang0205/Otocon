package org.atmarkcafe.otocon.model;

public class AgeRecomend {
    private String ageShow;

    private String age;

    public AgeRecomend(String ageShow, String age) {
        this.ageShow = ageShow;
        this.age = age;
    }

    public String getAgeShow() {
        return ageShow;
    }

    public void setAgeShow(String ageShow) {
        this.ageShow = ageShow;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
