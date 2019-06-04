package org.atmarkcafe.otocon.function.mypage.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.dialog.multiple.MultipleModel;
import org.atmarkcafe.otocon.model.response.ListData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileNameModel extends BaseObservable {

    private String common_hobby;
    private String nickname;
    private String basic_housing;
    private String address;
    private String basic_residence;
    private String basic_height;
    private String basic_blood_type;
    private String family_composition_inroduce;
    private String presence_children_introduce;
    private String job_name;
    private String job_location;
    private String job_salary;
    private String final_education_introduce;
    private String last_educational_qualification;
    private String lifestyle_sake;
    private String smoking_introduce;
    private String car_ownership_introduce;
    private String ranking_data_introduce_1;
    private String ranking_data_introduce_2;
    private String ranking_data_introduce_3;
    private String lifestyle_holiday;
    private String marriage_partner_selection;
    private String marriage_holiday;
    private String marriage_ideal_wife;
    private String work_after_marriage_introduce;
    private String time_marriage;
    private String after_marriage_committed;
    private String after_marriage_job;
    private String lifestyle_have_car;
    private boolean father;
    private boolean mother;
    private boolean brother;
    private boolean sister;
    private boolean youngtSister;
    private boolean youngtBrother;
    private String type_of_preference;
    private boolean inCountry = true;
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean fore;
    private boolean five;
    private boolean six;
    private boolean seven;
    private boolean eight;
    private boolean nine;
    private String basic_marital_status;
    private List<MultipleModel> nameFamily;
    private List<MultipleModel> nameHoliday;
    private String prefecture;

    @Bindable
    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
        notifyPropertyChanged(BR.prefecture);
    }

    public List<MultipleModel> getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(List<MultipleModel> nameFamily) {
        this.nameFamily = nameFamily;
    }

    public List<MultipleModel> getNameHoliday() {
        return nameHoliday;
    }

    public void setNameHoliday(List<MultipleModel> nameHoliday) {
        this.nameHoliday = nameHoliday;
    }

    @Bindable
    public String getBasic_marital_status() {
        return basic_marital_status;
    }

    public void setBasic_marital_status(String basic_marital_status) {
        this.basic_marital_status = basic_marital_status;
        notifyPropertyChanged(BR.basic_marital_status);
    }

    @Bindable
    public boolean isOne() {
        return one;
    }

    public void setOne(boolean one) {
        this.one = one;
        notifyPropertyChanged(BR.one);
    }

    @Bindable
    public boolean isTwo() {
        return two;
    }

    public void setTwo(boolean two) {
        this.two = two;
        notifyPropertyChanged(BR.two);
    }

    @Bindable
    public boolean isThree() {
        return three;
    }

    public void setThree(boolean three) {
        this.three = three;
        notifyPropertyChanged(BR.three);
    }

    @Bindable
    public boolean isFore() {
        return fore;
    }

    public void setFore(boolean fore) {
        this.fore = fore;
        notifyPropertyChanged(BR.fore);
    }

    @Bindable
    public boolean isFive() {
        return five;
    }

    public void setFive(boolean five) {
        this.five = five;
        notifyPropertyChanged(BR.five);
    }

    @Bindable
    public boolean isSix() {
        return six;
    }

    public void setSix(boolean six) {
        this.six = six;
        notifyPropertyChanged(BR.six);
    }

    @Bindable
    public boolean isSeven() {
        return seven;
    }

    public void setSeven(boolean seven) {
        this.seven = seven;
        notifyPropertyChanged(BR.seven);
    }

    @Bindable
    public boolean isEight() {
        return eight;
    }

    public void setEight(boolean eight) {
        this.eight = eight;
        notifyPropertyChanged(BR.eight);
    }

    @Bindable
    public boolean isNine() {
        return nine;
    }

    public void setNine(boolean nine) {
        this.nine = nine;
        notifyPropertyChanged(BR.nine);
    }

    @Bindable
    public boolean isInCountry() {
        return inCountry;
    }

    public void setInCountry(boolean inCountry) {
        this.inCountry = inCountry;
        notifyPropertyChanged(BR.inCountry);
    }

    @Bindable
    public String getType_of_preference() {
        return type_of_preference;
    }

    public void setType_of_preference(String type_of_preference) {
        this.type_of_preference = type_of_preference;
        notifyPropertyChanged(BR.type_of_preference);
    }

    @Bindable
    public String getLifestyle_have_car() {
        return lifestyle_have_car;

    }

    @Bindable
    public boolean isFather() {
        return father;
    }

    public void setFather(boolean father) {
        this.father = father;
        notifyPropertyChanged(BR.father);
    }

    @Bindable
    public boolean isMother() {
        return mother;
    }

    public void setMother(boolean mother) {
        this.mother = mother;
        notifyPropertyChanged(BR.mother);
    }

    @Bindable
    public boolean isBrother() {
        return brother;
    }

    public void setBrother(boolean brother) {
        this.brother = brother;
        notifyPropertyChanged(BR.brother);
    }

    @Bindable
    public boolean isSister() {
        return sister;
    }

    public void setSister(boolean sister) {
        this.sister = sister;
        notifyPropertyChanged(BR.sister);
    }

    @Bindable
    public boolean isYoungtSister() {
        return youngtSister;
    }

    public void setYoungtSister(boolean youngtSister) {
        this.youngtSister = youngtSister;
        notifyPropertyChanged(BR.youngtSister);
    }

    @Bindable
    public boolean isYoungtBrother() {
        return youngtBrother;
    }

    public void setYoungtBrother(boolean youngtBrother) {
        this.youngtBrother = youngtBrother;
        notifyPropertyChanged(BR.youngtBrother);
    }

    public void setLifestyle_have_car(String lifestyle_have_car) {
        this.lifestyle_have_car = lifestyle_have_car;
        notifyPropertyChanged(BR.lifestyle_have_car);
    }

    @Bindable
    public String getAfter_marriage_job() {
        return after_marriage_job;
    }

    public void setAfter_marriage_job(String after_marriage_job) {
        this.after_marriage_job = after_marriage_job;
        notifyPropertyChanged(BR.after_marriage_job);
    }

    @Bindable
    public String getCommon_hobby() {
        return common_hobby;
    }

    public void setCommon_hobby(String common_hobby) {
        this.common_hobby = common_hobby;
        notifyPropertyChanged(BR.common_hobby);
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }

    @Bindable
    public String getBasic_housing() {
        return basic_housing;
    }

    public void setBasic_housing(String basic_housing) {
        this.basic_housing = basic_housing;
        notifyPropertyChanged(BR.basic_housing);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getBasic_residence() {
        return basic_residence;
    }

    public void setBasic_residence(String basic_residence) {
        this.basic_residence = basic_residence;
        notifyPropertyChanged(BR.basic_residence);
    }

    @Bindable
    public String getBasic_height() {
        return basic_height;
    }

    public void setBasic_height(String basic_height) {
        this.basic_height = basic_height;
        notifyPropertyChanged(BR.basic_height);
    }

    @Bindable
    public String getBasic_blood_type() {
        return basic_blood_type;
    }

    public void setBasic_blood_type(String basic_blood_type) {
        this.basic_blood_type = basic_blood_type;
        notifyPropertyChanged(BR.basic_blood_type);
    }

    @Bindable
    public String getFamily_composition_inroduce() {
        return family_composition_inroduce;
    }

    public void setFamily_composition_inroduce(String family_composition_inroduce) {
        this.family_composition_inroduce = family_composition_inroduce;
        notifyPropertyChanged(BR.family_composition_inroduce);
    }

    @Bindable
    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
        notifyPropertyChanged(BR.job_name);
    }

    @Bindable
    public String getPresence_children_introduce() {
        return presence_children_introduce;
    }

    public void setPresence_children_introduce(String presence_children_introduce) {
        this.presence_children_introduce = presence_children_introduce;
        notifyPropertyChanged(BR.presence_children_introduce);
    }

    @Bindable
    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
        notifyPropertyChanged(BR.job_location);
    }

    @Bindable
    public String getJob_salary() {
        return job_salary;
    }

    public void setJob_salary(String job_salary) {
        this.job_salary = job_salary;
        notifyPropertyChanged(BR.job_salary);
    }

    @Bindable
    public String getFinal_education_introduce() {
        return final_education_introduce;
    }

    public void setFinal_education_introduce(String final_education_introduce) {
        this.final_education_introduce = final_education_introduce;
        notifyPropertyChanged(BR.final_education_introduce);
    }

    @Bindable
    public String getLast_educational_qualification() {
        return last_educational_qualification;
    }

    public void setLast_educational_qualification(String last_educational_qualification) {
        this.last_educational_qualification = last_educational_qualification;
        notifyPropertyChanged(BR.last_educational_qualification);
    }

    @Bindable
    public String getLifestyle_sake() {
        return lifestyle_sake;
    }

    public void setLifestyle_sake(String lifestyle_sake) {
        this.lifestyle_sake = lifestyle_sake;
        notifyPropertyChanged(BR.lifestyle_sake);
    }

    @Bindable
    public String getSmoking_introduce() {
        return smoking_introduce;
    }

    public void setSmoking_introduce(String smoking_introduce) {
        this.smoking_introduce = smoking_introduce;
        notifyPropertyChanged(BR.smoking_introduce);
    }

    @Bindable
    public String getCar_ownership_introduce() {
        return car_ownership_introduce;
    }

    public void setCar_ownership_introduce(String car_ownership_introduce) {
        this.car_ownership_introduce = car_ownership_introduce;
        notifyPropertyChanged(BR.car_ownership_introduce);
    }

    @Bindable
    public String getRanking_data_introduce_1() {
        return ranking_data_introduce_1;
    }

    public void setRanking_data_introduce_1(String ranking_data_introduce_1) {
        this.ranking_data_introduce_1 = ranking_data_introduce_1;
        notifyPropertyChanged(BR.ranking_data_introduce_1);
    }

    @Bindable
    public String getRanking_data_introduce_2() {
        return ranking_data_introduce_2;
    }

    public void setRanking_data_introduce_2(String ranking_data_introduce_2) {
        this.ranking_data_introduce_2 = ranking_data_introduce_2;
        notifyPropertyChanged(BR.ranking_data_introduce_2);
    }

    @Bindable
    public String getRanking_data_introduce_3() {
        return ranking_data_introduce_3;
    }

    public void setRanking_data_introduce_3(String ranking_data_introduce_3) {
        this.ranking_data_introduce_3 = ranking_data_introduce_3;
        notifyPropertyChanged(BR.ranking_data_introduce_3);
    }

    @Bindable
    public String getMarriage_holiday() {
        return marriage_holiday;
    }

    public void setMarriage_holiday(String marriage_holiday) {
        this.marriage_holiday = marriage_holiday;
        notifyPropertyChanged(BR.marriage_holiday);
    }

    @Bindable
    public String getLifestyle_holiday() {
        return lifestyle_holiday;
    }

    public void setLifestyle_holiday(String lifestyle_holiday) {
        this.lifestyle_holiday = lifestyle_holiday;
        notifyPropertyChanged(BR.lifestyle_holiday);
    }

    @Bindable
    public String getMarriage_partner_selection() {
        return marriage_partner_selection;
    }

    public void setMarriage_partner_selection(String marriage_partner_selection) {
        this.marriage_partner_selection = marriage_partner_selection;
        notifyPropertyChanged(BR.marriage_partner_selection);
    }

    @Bindable
    public String getMarriage_ideal_wife() {
        return marriage_ideal_wife;
    }

    public void setMarriage_ideal_wife(String marriage_ideal_wife) {
        this.marriage_ideal_wife = marriage_ideal_wife;
        notifyPropertyChanged(BR.marriage_ideal_wife);
    }

    @Bindable
    public String getWork_after_marriage_introduce() {
        return work_after_marriage_introduce;
    }

    public void setWork_after_marriage_introduce(String work_after_marriage_introduce) {
        this.work_after_marriage_introduce = work_after_marriage_introduce;
        notifyPropertyChanged(BR.work_after_marriage_introduce);
    }

    @Bindable
    public String getTime_marriage() {
        return time_marriage;
    }

    public void setTime_marriage(String time_marriage) {
        this.time_marriage = time_marriage;
        notifyPropertyChanged(BR.time_marriage);
    }

    @Bindable
    public String getAfter_marriage_committed() {
        return after_marriage_committed;
    }

    public void setAfter_marriage_committed(String after_marriage_committed) {
        this.after_marriage_committed = after_marriage_committed;
        notifyPropertyChanged(BR.after_marriage_committed);
    }


    public void setDataName(Context context, ProfileNameModel model, UserProfileModel data) {

        getBasicFamily(model, data.basic_brother_sister);
        getBasicHoliday(model, data.lifestyle_holiday);
        getCountry(model, data.job_location);

        model.setCommon_hobby(data.common_hobby);
        model.setNickname(data.nickname);

        model.setBasic_housing(getNameData(data.basic_housing));
        model.setAddress(data.address);
        model.setBasic_residence(getNameData(data.basic_residence));
        model.setBasic_height(getNameData(data.basic_height));
        model.setBasic_blood_type(getNameData(data.basic_blood_type));
        model.setPresence_children_introduce(getNameData(data.basic_child_status));
        model.setJob_name(getNameData(data.job_name));
        model.setJob_location(getNameContry(context, model));

        model.setFinal_education_introduce(getNameData(data.job_final_education));
        model.setLast_educational_qualification(getNameData(data.job_last_educational_qualification));
        model.setLifestyle_sake(getNameData(data.lifestyle_sake));
        model.setSmoking_introduce(getNameData(data.lifestyle_smoking));
        model.setWork_after_marriage_introduce(getNameData(data.after_marriage_house_work));
        model.setFamily_composition_inroduce(getListNameData(data.basic_brother_sister));


        model.setCar_ownership_introduce(getNameData(data.job_name));

        model.setTime_marriage(getNameData(data.marriage_time));

        model.setRanking_data_introduce_1(data.lifestyle_sort_hobby1);
        model.setRanking_data_introduce_2(data.lifestyle_sort_hobby2);
        model.setRanking_data_introduce_3(data.lifestyle_sort_hobby3);
        model.setMarriage_partner_selection(getNameData(data.marriage_partner_selection));
        model.setMarriage_holiday(getNameData(data.marriage_holiday));
        model.setMarriage_ideal_wife(data.marriage_ideal_wife);
        model.setLifestyle_holiday(getListNameData(data.lifestyle_holiday));
        model.setAfter_marriage_committed(data.after_marriage_committed);
        model.setAfter_marriage_job(getNameData(data.after_marriage_job));
        model.setLifestyle_have_car(getNameData(data.lifestyle_have_car));
        model.setType_of_preference(data.type_of_preference);
        model.setBasic_marital_status(getNameData(data.basic_marital_status));

        model.setNameFamily(data.basic_brother_sister);
        model.setNameHoliday(data.lifestyle_holiday);
        model.setJob_salary(getNameData(data.job_salary));
        model.setPrefecture(getNameData(data.job_location_prefecture));
        model.setJob_location(getNameData(data.job_location).equals(context.getResources().getStringArray(R.array.country_list)[1]) ? model.job_location : getNameData(data.job_location_prefecture));
    }


    public String getNameData(MultipleModel model) {
        return model == null ? "" : model.getTitle();
    }

    public String getListNameData(List<MultipleModel> models) {
        String name = "";
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                name += models.get(i).getTitle() + ",";
            }
        }
        if (name.lastIndexOf(",") > 0) {
            return name.substring(0, name.length() - 1);
        }
        return name;
    }

    public String getNameContry(Context context, ProfileNameModel model) {
        return model.inCountry ? model.prefecture : context.getResources().getStringArray(R.array.country_list)[1];
    }

    public void getCountry(ProfileNameModel model, MultipleModel data) {
        if (data != null) model.setInCountry(data.getId() == 0);
    }

    public void getBasicFamily(ProfileNameModel model, List<MultipleModel> models) {
        String nameId = "";

        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                nameId += models.get(i).getId() + ",";
            }
        }

        model.setFather(nameId.contains("1"));
        model.setMother(nameId.contains("2"));
        model.setBrother(nameId.contains("3"));
        model.setSister(nameId.contains("4"));
        model.setYoungtBrother(nameId.contains("5"));
        model.setYoungtSister(nameId.contains("6"));
    }

    public String getNameFamilySelection(Context context, ProfileNameModel model) {
        boolean[] booleans = new boolean[]{
                model.father,
                model.mother,
                model.brother,
                model.sister,
                model.youngtBrother,
                model.youngtSister
        };

        String nameFamily = "";
        for (int i = 0; i < booleans.length; i++) {
            if (booleans[i]) {
                nameFamily = nameFamily + (nameFamily.isEmpty() ? "" : ",") + context.getResources().getStringArray(R.array.family_list)[i];
            }
        }
        return nameFamily;
    }

    public String getHolidaySelection(Context context, ProfileNameModel model) {
        boolean[] booleans = new boolean[]{
                model.one,
                model.two,
                model.three,
                model.fore,
                model.five,
                model.six,
                model.seven,
                model.eight,
                model.nine,
        };

        String nameHoliday = "";
        for (int i = 0; i < booleans.length; i++) {
            if (booleans[i]) {
                nameHoliday = nameHoliday + (nameHoliday.isEmpty() ? "" : ",") + context.getResources().getStringArray(R.array.holiday_list)[i];
            }
        }
        return nameHoliday;
    }

    public void getBasicHoliday(ProfileNameModel model, List<MultipleModel> models) {

        //Todo get Basic Holiday
        String nameId = "";

        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                nameId += models.get(i).getId() + " ,";
            }
        }

        model.setOne(nameId.contains("1"));
        model.setTwo(nameId.contains("2"));
        model.setThree(nameId.contains("3"));
        model.setFore(nameId.contains("4"));
        model.setFive(nameId.contains("5"));
        model.setSix(nameId.contains("6"));
        model.setSeven(nameId.contains("7"));
        model.setEight(nameId.contains("8"));
        model.setNine(nameId.contains("9"));

    }

    public void setField(String value, ListData key) {
        switch (key) {
            case basicHousings:
                setBasic_housing(value);
                break;
            case residences:
                setBasic_residence(value);
                break;
            case bloodTypes:
                setBasic_blood_type(value);
                break;
            case selectPrefectures:
                setAddress(value);
                break;
            case childStatus:
                setPresence_children_introduce(value);
                break;
            case jobNames:
                setJob_name(value);
                break;
            case jobSalarys:
                setJob_salary(value);
                break;
            case finalEducations:
                setFinal_education_introduce(value);
                break;
            case lastEducationalQualifications:
                setLast_educational_qualification(value);
                break;
            case lifestyleSakes:
                setLifestyle_sake(value);
                break;
            case lifestyleSmokings:
                setSmoking_introduce(value);
                break;
            case yesNos:
                setLifestyle_have_car(value);
                break;
            case marriageTimes:
                setTime_marriage(value);
                break;
            case marriagepartnerSelections:
                setMarriage_partner_selection(value);
                break;
            case marriageHolidays:
                setMarriage_holiday(value);
                break;
            case afterMarriageJobs:
                setAfter_marriage_job(value);
                break;
            case basic_marital_status:
                setBasic_marital_status(value);
                break;
            case height:
                setBasic_height(value);
                break;
            case prefecture:
                setPrefecture(value);
                break;


        }
    }

    public String getIdFamily() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("1", isFather());
        map.put("2", isMother());
        map.put("3", isBrother());
        map.put("4", isSister());
        map.put("5", isYoungtBrother());
        map.put("6", isYoungtSister());

        String idParams = "";
        for (String key : map.keySet()) {
            if (map.get(key)) {
                idParams = idParams + (idParams.isEmpty() ? "" : ",") + key;
            }
        }
        return "[" + idParams + "]";
    }

    public String getIdBasicHoliday() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("1", isOne());
        map.put("2", isTwo());
        map.put("3", isThree());
        map.put("4", isFore());
        map.put("5", isFive());
        map.put("6", isSix());
        map.put("7", isSeven());
        map.put("8", isEight());
        map.put("9", isNine());

        String idParams = "";
        for (String key : map.keySet()) {
            if (map.get(key)) {
                idParams = idParams + (idParams.isEmpty() ? "" : ",") + key;
            }
        }
        return "[" + idParams + "]";
    }

    public String getIdsField(ListData key) {
        switch (key) {
            case basicHousings:
                return basic_housing;
            case residences:
                return basic_residence;
            case height:
                return basic_height;
            case bloodTypes:
                return basic_blood_type;
            case basic_marital_status:
                return basic_marital_status;
            case childStatus:
                return presence_children_introduce;
            case jobNames:
                return job_name;
            case jobSalarys:
                return job_salary;
            case finalEducations:
                return final_education_introduce;
            case lastEducationalQualifications:
                return last_educational_qualification;
            case marriageTimes:
                return time_marriage;
            case marriagepartnerSelections:
                return marriage_partner_selection;
            case yesNos:
                return car_ownership_introduce;
            case lifestyleSmokings:
                return smoking_introduce;
            case afterMarriageJobs:
                return after_marriage_job;
            case prefecture:
                return prefecture;
        }
        return "";
    }
}
