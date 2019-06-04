package org.atmarkcafe.otocon.function.menu.item;

import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemMenuViewBinding;

public class MenuItem extends BindableItem<ItemMenuViewBinding> {

    private int idMenu;
    private int resIcon;
    private String title;
    private int noti;

    public MenuItem(int idMenu, int resIcon, String title) {
        this.idMenu = idMenu;
        this.resIcon = resIcon;
        this.title = title;
    }

    @Override
    public void bind(@NonNull ItemMenuViewBinding viewBinding, int position) {
        viewBinding.setItem(this);
    }

    @Override
    public int getLayout() {
        return R.layout.item_menu_view;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNoti() {
        return noti;
    }

    public String getStringNoti() {
        if(noti > 99){
            return  "99+";
        }
        return noti + "";
    }

    public MenuItem setNoti(int noti) {
        this.noti = noti;
        return this;
    }
}
