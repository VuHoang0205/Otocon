package org.atmarkcafe.otocon.dialog.multiple;

import com.google.gson.annotations.SerializedName;

public class MultipleModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String title;

    private boolean checked;

    public MultipleModel(int id, String title, boolean checked) {
        this.id = id;
        this.title = title;
        this.checked = checked;
    }

    public MultipleModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public MultipleModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
