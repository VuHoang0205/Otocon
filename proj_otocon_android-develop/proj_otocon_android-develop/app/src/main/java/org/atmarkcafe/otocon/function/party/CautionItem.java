package org.atmarkcafe.otocon.function.party;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.Spanned;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemCautionBinding;


public class CautionItem extends BindableItem<ItemCautionBinding> {
    private Spanned content;
    private String title;
    private int res;


    public CautionItem(Spanned content, String title, int res) {
        this.content = content;
        this.title = title;
        this.res = res;
    }

    @Override
    public int getLayout() {
        return R.layout.item_caution;
    }

    @Override
    public void bind(@NonNull ItemCautionBinding viewBinding, int position) {
        viewBinding.textView19.setText(content);
        viewBinding.title.setText(title);
        viewBinding.imageView10.setImageResource(res);
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        if (position == 2) {
            viewBinding.textView19.setMinHeight(height);
        } else {
            viewBinding.textView19.setMinHeight(10);
        }
    }
}
