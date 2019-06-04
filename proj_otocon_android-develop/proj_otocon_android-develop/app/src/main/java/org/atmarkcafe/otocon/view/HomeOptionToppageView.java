package org.atmarkcafe.otocon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeOptionToppageView extends LinearLayout {

    public CompoundButton.OnCheckedChangeListener onCheckedChanged;

    public void setOnCheckedChanged(CompoundButton.OnCheckedChangeListener onCheckedChanged) {
        this.onCheckedChanged = onCheckedChanged;
    }

    private boolean flag;
    @BindView(R.id.lnContainer)
    LinearLayout lnContainer;

    private int image;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.ivCheckbox)
    ImageView ivCheckbox;

    private Context context;

    public HomeOptionToppageView(Context context) {
        super(context);
        init(context);
    }

    public HomeOptionToppageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_layout_option_toppage, 0, 0);

        try {
            tvTitle.setText(a.getString(R.styleable.custom_layout_option_toppage_textTitle));
        } catch (Exception e) {

        }

        try {
            ivCheckbox.setImageDrawable(a.getDrawable(R.styleable.custom_layout_option_toppage_srcImage));
        } catch (Exception e) {

        }

        if (a != null) {
            a.recycle();
        }
    }

    public void init(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.item_option_home_view, this);
        this.context = context;
        ButterKnife.bind(this);
    }

    @OnClick(R.id.lnContainer)
    public void onClick() {
        flag = !flag;
        int color = flag ? R.color.colorEnableOption : R.color.colorDefaultOption;
        ivCheckbox.setColorFilter(context.getResources().getColor(color));
        tvTitle.setTextColor(context.getResources().getColor(color));

        if (this.onCheckedChanged != null) {
            this.onCheckedChanged.onCheckedChanged(null, flag);
        }
    }

    public boolean isChecked() {
        return flag;
    }
}
