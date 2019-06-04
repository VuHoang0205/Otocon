package org.atmarkcafe.otocon.model;

import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.Collections;

/**
 * class PartyDetailContent
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class PartyDetailContent extends CheckedExpandableGroup {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public PartyDetailContent(String title, String content) {
        super(title, Collections.singletonList(content));
    }

    @Override
    public void onChildClicked(int childIndex, boolean checked) {

    }
}
