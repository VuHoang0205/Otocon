package org.atmarkcafe.otocon.model.menunew;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.model.parameter.PartyParams;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MenuNewModel extends BaseObservable {

    public static final int ACTION_NONE = 0;
    public static final int ACTION_LIST_EVENT = 1;
    public static final int ACTION_EVENT_DETAIL = 2;

    public class AdvancedSearch {

        @SerializedName("link_label")
        private String link_label;

        @SerializedName("citys")
        private List<String> citys;

        @SerializedName("day_of_week")
        private List<String> day_of_week;

        @SerializedName("event_date_from")
        private String event_date_from;

        @SerializedName("event_date_to")
        private String event_date_to;

        @SerializedName("age_male")
        private List<String> age_male;

        @SerializedName("age_female")
        private List<String> age_female;

        @SerializedName("check_female_slot")
        private int check_female_slot;

        @SerializedName("check_male_slot")
        private int check_male_slot;

        public String getLink_label() {
            return link_label;
        }

        public void setLink_label(String link_label) {
            this.link_label = link_label;
        }

        public List<String> getCitys() {
            return citys;
        }

        public void setCitys(List<String> citys) {
            this.citys = citys;
        }

        public List<String> getDay_of_week() {
            return day_of_week;
        }

        public void setDay_of_week(List<String> day_of_week) {
            this.day_of_week = day_of_week;
        }

        public String getEvent_date_from() {
            return event_date_from;
        }

        public void setEvent_date_from(String event_date_from) {
            this.event_date_from = event_date_from;
        }

        public String getEvent_date_to() {
            return event_date_to;
        }

        public void setEvent_date_to(String event_date_to) {
            this.event_date_to = event_date_to;
        }

        public List<String> getAge_male() {
            return age_male;
        }

        public void setAge_male(List<String> age_male) {
            this.age_male = age_male;
        }

        public List<String> getAge_female() {
            return age_female;
        }

        public void setAge_female(List<String> age_female) {
            this.age_female = age_female;
        }

        public int getCheck_female_slot() {
            return check_female_slot;
        }

        public void setCheck_female_slot(int check_female_slot) {
            this.check_female_slot = check_female_slot;
        }

        public int getCheck_male_slot() {
            return check_male_slot;
        }

        public void setCheck_male_slot(int check_male_slot) {
            this.check_male_slot = check_male_slot;
        }
    }

    private final String DATE_FORMAT = "yyyy/M/d";
    private final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    @SerializedName("advanced_search")
    private PartyParams advancedSearch;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("status")
    private String status;

    @SerializedName("sub_content")
    private String sub_content;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("notify_id")
    private String notify_id;

    @SerializedName("action")
    private int action;

    @SerializedName("event_id")
    private String event_id;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("event_title")
    private String event_title;

    public Spannable getLinkAction(){
        SpannableString result = new SpannableString("");
        if (action == ACTION_LIST_EVENT){
            result = new SpannableString(advancedSearch.getLink_label());
        }
        if (action == ACTION_EVENT_DETAIL){
            result = new SpannableString(event_title);
        }
        result.setSpan(new UnderlineSpan(), 0, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }

    public String getTitleAction(){
        if (action == ACTION_LIST_EVENT){
            return  advancedSearch.getLink_label();
        }
        if (action == ACTION_EVENT_DETAIL){
            return  event_title;
        }
        return "";
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public PartyParams getAdvancedSearch() {
        return advancedSearch;
    }

    public void setAdvancedSearch(PartyParams advancedSearch) {
        this.advancedSearch = advancedSearch;
    }

    public String getSub_content() {
        return sub_content;
    }

    public void setSub_content(String sub_content) {
        this.sub_content = sub_content;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public boolean isReaded() {
        return !(notify_id != null && !notify_id.isEmpty());
    }


    @Bindable
    public String getCreated_at() {
        return created_at;

    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
        notifyPropertyChanged(BR.created_at);
    }

    public Spanned readHtml() {
        return Html.fromHtml(content.replace("&lt;", "<").replace("&gt;", ">"));
    }

    public String formatDate() {
        if (created_at.isEmpty()) {
            return "";
        }

        Date dateTime = formatToDateTime(created_at);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(dateTime);
    }

    public Date formatToDateTime(String dateEvent) {

        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
            Date date = dateFormat.parse(dateEvent);

            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean showTextSpannable(Spannable text){
        String ttt = text.toString();
        if (ttt != null && !ttt.equals("")) return true;
        return false;
    }

    public boolean showTextString(String text){
        if (text != null && !text.equals("")) return true;
        return false;
    }
}
