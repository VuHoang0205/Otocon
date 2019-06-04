package org.atmarkcafe.otocon.function.advancedsearch;

import android.support.annotation.NonNull;

import com.xwray.groupie.Group;
import com.xwray.groupie.NestedGroup;

import org.atmarkcafe.otocon.function.advancedsearch.items.AreaItem;
import org.atmarkcafe.otocon.function.advancedsearch.items.CityItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdvancedSearchCityExpandableGroup extends NestedGroup implements AreaItem.CheckArea, CityItem.CheckCity {

    private boolean isExpanded = false;
    private final Group parent;
    private final List<Group> children = new ArrayList<>();

    public AdvancedSearchCityExpandableGroup(Group expandableItem) {
        this.parent = expandableItem;
        ((AdvancedSearchCityExpandableItem) expandableItem).setExpandableGroup(this);
        ((AreaItem)parent).setCheckArea(this);
    }

    @Override
    public void addAll(@NonNull Collection<? extends Group> groups) {
        if (groups.isEmpty()) return;
        super.addAll(groups);
        if (isExpanded) {
            int itemCount = getItemCount();
            this.children.addAll(groups);
            notifyItemRangeInserted(itemCount, getItemCount(groups));
        } else {
            this.children.addAll(groups);
        }

        for (int i=0; i<children.size();i++){
            ((CityItem)children.get(i)).setCheckCity(this);
            if(((CityItem)children.get(i)).getCity().isCheck()){
                ((AreaItem)parent).getArea().setCheck(true);
                break;
            }
        }
    }
    
    @Override
    public void isCheckArea(boolean area) {
        if (area) {
            for (int i = 0; i < children.size(); i++) {
                ((CityItem)children.get(i)).getCity().setCheck(true);
            }
        }
        else {
            for (int i = 0; i < children.size(); i++) {
                ((CityItem)children.get(i)).getCity().setCheck(false);
            }
        }
    }

    @Override
    public void isCheckCity(boolean city) {
        if (city){
            if (((AreaItem)parent).getArea().isCheck())
                return;
            else ((AreaItem)parent).getArea().setCheck(true);
        }
        else {
            for (int i = 0; i < children.size(); i++){
                if (((CityItem)children.get(i)).getCity().isCheck())
                    return;
            }
            ((AreaItem)parent).getArea().setCheck(false);
        }
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    @NonNull
    public Group getGroup(int position) {
        if (position == 0) {
            return parent;
        } else {
            return children.get(position - 1);
        }
    }

    @Override
    public int getPosition(@NonNull Group group) {
        if (group == parent) {
            return 0;
        } else {
            return 1 + children.indexOf(group);
        }
    }

    public int getGroupCount() {
        return 1 + (isExpanded ? children.size() : 0);
    }

    public void onToggleExpanded() {
        int oldSize = getItemCount();
        isExpanded = !isExpanded;
        int newSize = getItemCount();
        if (oldSize > newSize) {
            notifyItemRangeRemoved(newSize, oldSize - newSize);
        } else {
            notifyItemRangeInserted(oldSize, newSize - oldSize);
        }
        notifyChanged();
    }
}
