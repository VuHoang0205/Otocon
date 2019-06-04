package org.atmarkcafe.otocon.function.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabIndivicatorView extends LinearLayout {

    @BindView (R.id.icon)
    ImageView imageView;

    @BindView (R.id.text)
    TextView title;

    private int icon;
    private String titleIndivicator;

    public TabIndivicatorView(Context context) {
        super (context);

        init (R.layout.tab_indivicator);
    }

    public TabIndivicatorView(Context context, AttributeSet attrs) {
        super (context, attrs);
        init (R.layout.tab_indivicator);
    }

    public void init(int res) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(res, this);

        ButterKnife.bind (this);

        imageView.setImageResource (icon);

        title.setText (titleIndivicator);
    }

    public TabIndivicatorView config(int icon, String title){
        this.icon = icon;
        this.titleIndivicator = title;

        return this;
    }
}
