package org.atmarkcafe.otocon.function.registerparty;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * view ConfirmInformationFragment
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class ConfirmInformationTextView extends LinearLayout {

    @BindView(R.id.tvTileInformation)
    TextView tvTileInformation;

    @BindView(R.id.tvValue)
    TextView tvValue;

    @BindView(R.id.lineBottom)
    View lineBottom;

    public ConfirmInformationTextView(Context context) {
        super(context);
        init(R.layout.view_confirm_information_text);
    }

    public ConfirmInformationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.view_confirm_information_text);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ConfirmInformationTextView, 0, 0);

        try {
            tvValue.setInputType(array.getInt(
                    R.styleable.ConfirmInformationTextView_android_inputType, InputType.TYPE_CLASS_TEXT));
        } catch (Exception e) {

        }

        try {
            lineBottom.setVisibility(array.getInt(
                    R.styleable.ConfirmInformationTextView_android_visibility, VISIBLE));
        } catch (Exception e) {

        }

    }

    public void init(int res) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(res, this);

        ButterKnife.bind(this);
    }


    public void setTitleAndValue(String title, String value) {

        tvTileInformation.setText(title);
        tvValue.setText(value);
    }
}
