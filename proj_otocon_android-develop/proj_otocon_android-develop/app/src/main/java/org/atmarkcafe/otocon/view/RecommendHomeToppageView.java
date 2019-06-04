package org.atmarkcafe.otocon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendHomeToppageView extends ConstraintLayout {

    @BindView(R.id.ivImage)
    ImageView ivImage;

    @BindView(R.id.tvTitle)
     TextView tvTitle;

    public RecommendHomeToppageView(Context context) {
        super(context);
        init();
    }

    public RecommendHomeToppageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.layout_recommend_home_view, 0, 0);

        try {
            tvTitle.setText(a.getString(R.styleable.layout_recommend_home_view_custitle));
        } catch (Exception e) {

        }

        try {
            ivImage.setImageDrawable(a.getDrawable(R.styleable.layout_recommend_home_view_cusimage));
        } catch (Exception e) {

        }

        if (a != null) {
            a.recycle();
        }
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_recommend_home_view, this);
        ButterKnife.bind(this);
    }

}
