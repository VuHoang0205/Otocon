package org.atmarkcafe.otocon.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekView extends LinearLayout {

    @BindView(R.id.tvDay)
    TextView tvDay;

    @BindView(R.id.tvNameDay)
    TextView tvNameDay;

    public WeekView(Context context) {
        super(context);

        init(context);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_week_page_view, this);
        ButterKnife.bind(this);

    }

    public void setText(String day, String nameDay) {
        tvDay.setText(day);
        tvNameDay.setText(nameDay);
    }

    public String getNameDay() {
        return tvNameDay.getText().toString();
    }

    public String getDay() {
        return tvDay.getText().toString();
    }

}
