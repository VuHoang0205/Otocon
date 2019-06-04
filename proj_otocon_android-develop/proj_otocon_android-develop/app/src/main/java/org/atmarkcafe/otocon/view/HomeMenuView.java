package org.atmarkcafe.otocon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.model.HorizontalCalandarWeek;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeMenuView extends LinearLayout {
    public interface HomeMenuListener {
        public void onCheckedChange(int index, boolean isChecked);

        public void onDateChange();

        public void onCurrentDay(String day);
    }

    private HomeMenuListener homeMenuListener;

    public void setHomeMenuListener(HomeMenuListener homeMenuListener) {
        this.homeMenuListener = homeMenuListener;
    }

    @BindView(R.id.horizontal_calandar_week)
    HorizontalCalandarView horizontal_calandar_week;

    @BindView(R.id.homeOptionToppage1)
    HomeOptionToppageView optionView1;

    @BindView(R.id.homeOptionToppage2)
    HomeOptionToppageView optionView2;

    @BindView(R.id.homeOptionToppage3)
    HomeOptionToppageView optionView3;

    @BindView(R.id.homeOptionToppage4)
    HomeOptionToppageView optionView4;

    @BindView(R.id.homeOptionToppage5)
    HomeOptionToppageView optionView5;

    @BindView(R.id.homeOptionToppage6)
    HomeOptionToppageView optionView6;

    @BindView(R.id.homeOptionToppage7)
    HomeOptionToppageView optionView7;

    @BindView(R.id.homeOptionToppage8)
    HomeOptionToppageView optionView8;

    public HomeMenuView(Context context) {
        super(context);

        init(R.layout.view_home_menu);
    }

    public HomeMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(R.layout.view_home_menu);
    }

    public void init(int res) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(res, this);

        ButterKnife.bind(this);

        // menu check
        optionView1.setOnCheckedChanged((CompoundButton compoundButton, boolean b) -> {
            if (homeMenuListener != null) {
                homeMenuListener.onCheckedChange(1, b);
            }
        });

        optionView2.setOnCheckedChanged((CompoundButton compoundButton, boolean b) -> {
            if (homeMenuListener != null) {
                homeMenuListener.onCheckedChange(2, b);
            }
        });

        optionView3.setOnCheckedChanged((CompoundButton compoundButton, boolean b) -> {
            if (homeMenuListener != null) {
                homeMenuListener.onCheckedChange(3, b);
            }
        });

        optionView4.setOnCheckedChanged((CompoundButton compoundButton, boolean b) -> {
            if (homeMenuListener != null) {
                homeMenuListener.onCheckedChange(4, b);
            }
        });

        optionView5.setOnCheckedChanged((CompoundButton compoundButton, boolean b) -> {
            if (homeMenuListener != null) {
                homeMenuListener.onCheckedChange(5, b);
            }
        });

        optionView5.setOnCheckedChanged((CompoundButton compoundButton, boolean b) -> {
            if (homeMenuListener != null) {
                homeMenuListener.onCheckedChange(5, b);
            }
        });

        optionView7.setOnCheckedChanged((CompoundButton compoundButton, boolean b) -> {
            if (homeMenuListener != null) {
                homeMenuListener.onCheckedChange(7, b);
            }
        });

        optionView8.setOnCheckedChanged((CompoundButton compoundButton, boolean b) -> {
            if (homeMenuListener != null) {
                homeMenuListener.onCheckedChange(8, b);
            }
        });
    }

    public boolean isCheckedOption1() {
        return optionView1.isChecked();
    }

    public boolean isCheckedOption2() {
        return optionView2.isChecked();
    }

    public boolean isCheckedOption3() {
        return optionView3.isChecked();
    }

    public boolean isCheckedOption4() {
        return optionView4.isChecked();
    }

    public boolean isCheckedOptio5() {
        return optionView5.isChecked();
    }

    public boolean isCheckedOption6() {
        return optionView6.isChecked();
    }

    public boolean isCheckedOption7() {
        return optionView7.isChecked();
    }

    public boolean isCheckedOption8() {
        return optionView8.isChecked();
    }

    public void setCurrentTime(String current) {

        // init data all week
//        initDataOnWeek();

        // number of page
        // start and end date
        HorizontalCalandarWeek horizontalCalandarWeek = new HorizontalCalandarWeek();
        horizontalCalandarWeek.initDataAllWeek();
        horizontal_calandar_week.addData(horizontalCalandarWeek.getCalandarWeeks());
//        horizontal_calandar_week.setOnItemClickListener((int position, Object data, int type) -> {
//            String dateTime = (String) data;
//            if (homeMenuListener != null) {
//                homeMenuListener.onCurrentDay(horizontalCalandarWeek.getDayOfWeek(dateTime));
//            }
//
//            horizontal_calandar_week.notifyDataSetChanged();
//        });
    }
}
