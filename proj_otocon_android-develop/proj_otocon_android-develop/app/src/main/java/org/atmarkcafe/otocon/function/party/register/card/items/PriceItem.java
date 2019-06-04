package org.atmarkcafe.otocon.function.party.register.card.items;

import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemPriceBinding;

public class PriceItem extends BindableItem<ItemPriceBinding> {
    private int color;
    private String hint;
    private String price;
    private int count;

    public PriceItem setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public PriceItem setPrice(String price) {
        this.price = price;
        return this;
    }

    public PriceItem setColor(int color) {
        this.color = color;
        return this;
    }

    public PriceItem setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public void bind(@NonNull ItemPriceBinding viewBinding, int position) {
        viewBinding.priceHint.setText(hint);
        viewBinding.priceValue.setTextColor(color);
        viewBinding.taxIncluded.setTextColor(color);

//        if (position < count - 1) {
//            Spannable spannable = new SpannableString(price);
//            spannable.setSpan(new StrikethroughSpan(), 0, price.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            viewBinding.priceValue.setText(spannable);
//        } else {
        viewBinding.priceValue.setText(price);
//        }
    }

    @Override
    public int getLayout() {
        return R.layout.item_price;
    }
}
