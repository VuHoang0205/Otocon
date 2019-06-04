package org.atmarkcafe.otocon.function.home.items;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemRecommendBinding;
import org.atmarkcafe.otocon.model.RecommendSlider;

public class RecommendItem extends BindableItem<ItemRecommendBinding> {
    private RecommendSlider item;

    public RecommendItem(RecommendSlider item) {
        this.item = item;
    }

    @Override
    public void bind(@NonNull ItemRecommendBinding viewBinding, int position) {
        viewBinding.setItem(item);
        int color[] = new int[]{
                ContextCompat.getColor(viewBinding.getRoot().getContext(), R.color.color_F7648F),
                ContextCompat.getColor(viewBinding.getRoot().getContext(), R.color.color17A5B8),
                ContextCompat.getColor(viewBinding.getRoot().getContext(), R.color.color3B73CC)
        };
        viewBinding.title.setBackgroundColor(color[position%3]);
    }

    @Override
    public int getLayout() {
        return R.layout.item_recommend;
    }

    public RecommendSlider getItem() {
        return item;
    }
}
