package org.atmarkcafe.otocon.function.home.items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemPartyBinding;
import org.atmarkcafe.otocon.databinding.ItemPartyGridViewBinding;
import org.atmarkcafe.otocon.extesion.PartyListAdapter;
import org.atmarkcafe.otocon.model.Party;


public class ItemParty extends BindableItem<ViewDataBinding> {
    public enum ShowType {
        list, grid
    }

    public interface ItemPartyListener {
        public void onClickLike(Party party);

        public void onClickItem(Party party);
    }

    public ItemPartyListener listener;
    private PartyListAdapter adapter;
    public ShowType typeShow = ShowType.list;

    public void setTypeShow(ShowType typeShow) {
        this.typeShow = typeShow;
    }

    public Party respone;

    public ItemParty(PartyListAdapter adapter, Party response, ItemPartyListener listener) {
        this.respone = response;

        this.listener = listener;

        this.adapter = adapter;
    }

    @SuppressLint("ResourceType")
    @Override
    public void bind(@NonNull ViewDataBinding viewBinding, int position) {
        respone.setContext(viewBinding.getRoot().getContext());

        if (viewBinding instanceof ItemPartyBinding) {
            ItemPartyBinding item = (ItemPartyBinding) viewBinding;
            item.setItem(this);
        } else {
            ItemPartyGridViewBinding item = (ItemPartyGridViewBinding) viewBinding;
            item.setItem(this);
        }
    }


    @Override
    public int getLayout() {


        if (adapter.getShowType() == ShowType.grid) {
            return R.layout.item_party_gird_view;
        }
        return R.layout.item_party;
    }

    public Party getRespone() {
        return respone;
    }


    @Override
    public int hashCode() {
        return respone != null ? super.hashCode() : Integer.parseInt(respone.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ItemParty) {
            ItemParty itemParty = (ItemParty) obj;

            if (itemParty.getRespone() != null && getRespone() != null) {
                return itemParty.getRespone().getId() == getRespone().getId();
            }
        }
        return super.equals(obj);
    }
}
