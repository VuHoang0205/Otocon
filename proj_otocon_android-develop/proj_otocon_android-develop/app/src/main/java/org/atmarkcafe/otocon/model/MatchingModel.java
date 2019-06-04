package org.atmarkcafe.otocon.model;

public class MatchingModel extends Party {

    String read_at;
    int is_highlighted;

    public String getRead_at() {
        return read_at;
    }

    public int getIs_highlighted() {
        return is_highlighted;
    }

    public boolean isHighLighted(){
        if (is_highlighted == 0 && read_at != null) return false;
        return true;
    }
}
