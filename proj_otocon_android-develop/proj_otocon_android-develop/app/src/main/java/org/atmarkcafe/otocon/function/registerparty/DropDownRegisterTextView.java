package org.atmarkcafe.otocon.function.registerparty;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * view DropDownRegisterTextView
 *
 * @author acv-hoanv
 * @version 1.0
 */
public class DropDownRegisterTextView extends LinearLayout {

    // Reference to Text View indicates the label of field
    @BindView(R.id.register_party_title)
    TextView title;

    // Reference to Text View indicates the dropdown of field
    @BindView(R.id.register_party_text_view)
    TextView dropDown;

    public DropDownRegisterTextView(Context context) {
        super(context);
        init(R.layout.view_register_party_textview);
    }

    public DropDownRegisterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(R.layout.view_register_party_textview);

        title.setText("");
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.DropDownRegisterTextView, 0, 0);


        // Set label text of required field
        try {
            setTitleWithAsterisk(array.getString(R.styleable.DropDownRegisterTextView_dropdownTitle));
        } catch (Exception e) {

        }

        if (array != null) {
            array.recycle();
        }
    }

    public void init(int res) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(res, this);
        ButterKnife.bind(this);
    }

    /**
     * Put a asterisk with predetermined color follow the label to notify users
     * this field is required
     * <p>
     *
     * @param str String:       String label followed by asterisk
     * @return Label with text followed by asterisk
     */
    public DropDownRegisterTextView setTitleWithAsterisk(String str) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(str);
        int start = builder.length();
        builder.append("*");
        int end = builder.length();

        if (getContext() != null) {
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorRequiredTitle)),
                    start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            title.setText(builder);
        }
        return this;
    }

    /**
     * Provide a onClick method to dropdown, which listens click event
     * <p>
     *
     * @param listener View.OnClickListener:   The handle of click event
     */
    public void setOnClickDropDown(View.OnClickListener listener) {
        dropDown.setOnClickListener(listener);
    }

    // Provide a get text method to dropdown
    public String getDropDownText() {
        return dropDown.getText().toString();
    }

    // Provide a set text method to dropdown
    public void setDropDownText(String string) {
        dropDown.setText(string);
    }
}
