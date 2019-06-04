package org.atmarkcafe.otocon.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.model.HorizontalCalandarWeek;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * class HorizontalCalandarView
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class HorizontalCalandarView extends LinearLayout {

    // Adapter of list party
;
    @BindView(R.id.rv_horizontal_calandar)
    public RecyclerView rv_horizontal_calandar;

    // Action for Onclick item

    public HorizontalCalandarView(Context context) {
        super(context);

        init(R.layout.view_horizontal_calandar);
    }

    public HorizontalCalandarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(R.layout.view_horizontal_calandar);
    }

    public void notifyDataSetChanged() {

    }

    public void init(int res) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(res, this);
        ButterKnife.bind(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_horizontal_calandar.setLayoutManager(linearLayoutManager);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rv_horizontal_calandar);

    }

    public void addData(List<HorizontalCalandarWeek.Week> horizontalCalandarWeeks) {

    }
}
