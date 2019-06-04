package org.atmarkcafe.otocon.ktextension;

public interface OnItemClickListener<Action, Item>  {
    void onItemClick(int position, Action action, Item data);
}
