package org.atmarkcafe.otocon.function.rematch;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemMatchingBinding;
import org.atmarkcafe.otocon.model.MatchingModel;

public class MatchingItem extends BindableItem<ItemMatchingBinding> {
    private MatchingModel respone;
    public MatchingItem(MatchingModel respone) {
        this.respone=respone;
    }

    @Override
    public void bind(@NonNull ItemMatchingBinding viewBinding, int position) {
        respone.setContext(viewBinding.getRoot().getContext());
        viewBinding.setItem(this);
    }

    @Override
    public int getLayout() {
        return R.layout.layout_partner_party;
    }

    public MatchingModel getRespone() {
        return respone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  MatchingItem){
            return respone.getId().equals(((MatchingItem) obj).getRespone().getId());
        }
        return super.equals(obj);
    }
}
