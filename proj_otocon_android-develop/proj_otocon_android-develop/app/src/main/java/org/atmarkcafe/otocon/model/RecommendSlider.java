package org.atmarkcafe.otocon.model;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.parameter.PartyParams;

import java.io.Serializable;

public class RecommendSlider implements Serializable {
    public static final int STATUS_DISABLE = 0;
    public static final int STATUS_ENABLE = 1;

    public static final int TYPE_SLIDER = 1;
    public static final int TYPE_RECOMMEND = 2;


    public enum ActionEvent{

        //0: can't filter,
        // 1: link to list event,
        // 2: link to event detail,
        // 3: party next week,
        // 4: special discount,
        // 5: benefit early
        none,
        link_to_list_event,
        link_to_event_detail,
        party_next_week,
        special_discount,
        benefit_early;

        public static ActionEvent pactory(String action){
            try {
                return ActionEvent.values()[Integer.parseInt(action)];
            }catch (Exception e){
                return none;
            }
        }

        public static ActionEvent pactory(int action){
            try {
                return ActionEvent.values()[action];
            }catch (Exception e){
                return none;
            }
        }
    }

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("image")
    String image;

    @SerializedName("type")
    int type;

    @SerializedName("action")
    int action;

    @SerializedName("status")
    int status;

    @SerializedName("event_id")
    String eventId;

    @SerializedName("advanced_search")
    PartyParams advancedSearch;

    public RecommendSlider() {
        this.id = "";
        this.title = "";
        this.status = STATUS_ENABLE;
        this.type = TYPE_SLIDER;
        this.action = ActionEvent.none.ordinal();
    }

    public RecommendSlider(int type) {
        this.id = "";
        this.title = "";
        this.status = STATUS_ENABLE;
        this.type = type;
        this.action = ActionEvent.none.ordinal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PartyParams getAdvancedSearch() {
        return advancedSearch;
    }

    public void setAdvancedSearch(PartyParams advancedSearch) {
        this.advancedSearch = advancedSearch;
    }
}
