package org.atmarkcafe.otocon.model;

import java.io.Serializable;

public class ChooseItem implements Serializable {

    private String id;
    private String title;

    public ChooseItem() {
    }

    public ChooseItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
