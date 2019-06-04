package org.atmarkcafe.otocon.function.menu.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemMenuListNewsBinding;
import org.atmarkcafe.otocon.model.menunew.MenuNewModel;

public class MenuNewItem extends BindableItem<ItemMenuListNewsBinding> {

    private OnItemMenuNewListener itemMenuNewListener;
    private ObservableBoolean checked = new ObservableBoolean(true);
    private MenuNewModel menuNewModel;
    private Context context;

    public MenuNewItem(MenuNewModel menuNewModel, Context context, OnItemMenuNewListener itemMenuNewListener) {
        this.menuNewModel = menuNewModel;
        this.itemMenuNewListener = itemMenuNewListener;
        this.context = context;
    }

    @Override
    public int getLayout() {
        return R.layout.item_menu_list_news;
    }

    @Override
    public void bind(@NonNull ItemMenuListNewsBinding viewBinding, int position) {
        viewBinding.setMenuNewModel(menuNewModel);
        viewBinding.setIsCheck(checked);

        viewBinding.getRoot().setOnClickListener(v -> {
            checked.set(!checked.get());
            itemMenuNewListener.onReadNew(menuNewModel);
        });

        viewBinding.textView.setOnClickListener(v -> {
            checked.set(!checked.get());
            itemMenuNewListener.onReadNew(menuNewModel);

            itemMenuNewListener.gotoNewDetail(menuNewModel);
        });
    }

    public interface OnItemMenuNewListener {
        void gotoNewDetail(MenuNewModel model);
        void onReadNew(MenuNewModel model);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof MenuNewItem && menuNewModel != null && ((MenuNewItem)obj).menuNewModel != null ){
            return menuNewModel.getId()!= null && menuNewModel.getId().equals(((MenuNewItem)obj).menuNewModel.getId());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return menuNewModel != null && menuNewModel.getId() != null ?menuNewModel.getId().hashCode() : 0;
    }
}
