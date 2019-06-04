package org.atmarkcafe.otocon.function.registerparty;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * view EditTextRegisterParty
 *
 * @author acv-hoanv
 * @version 1.0
 */

public class EditTextRegisterParty extends LinearLayout {

    // Reference to Text View indicates the label of field
    @BindView(R.id.register_party_form_title)
    TextView title;

    // Reference to Edit Text indicates the text box of field
    @BindView(R.id.register_party_form_edit_text)
    EditText editText;

    public EditTextRegisterParty(Context context) {
        super(context);

        init(R.layout.view_register_party_edit_text);
    }

    public EditTextRegisterParty(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(R.layout.view_register_party_edit_text);
        title.setText("");

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.EditTextRegisterParty, 0, 0);

        // Set label text of non-required field
        try {
            title.setText(array.getString(R.styleable.EditTextRegisterParty_editTextRegisterParty));
        } catch (Exception e) {

        }

        // Set label text of required field
        try {
            setTitleWithAsterisk(array.getString(R.styleable.EditTextRegisterParty_registerPartyRequired));
        } catch (Exception e) {

        }

        // Set height of text box
        try {
            setEditTextHeight(array.getDimensionPixelSize(
                    R.styleable.EditTextRegisterParty_registerPartyHeight, 0));
        } catch (Exception e) {

        }

        // Sets the maximum of lines of the text box.
        try {
            editText.setMaxLines(array.getInt(
                    R.styleable.EditTextRegisterParty_android_maxLines, 1));
        } catch (Exception e) {

        }

        // Specify the input method action of the text box.
        try {
            editText.setImeOptions(array.getInt(
                    R.styleable.EditTextRegisterParty_android_imeOptions, EditorInfo.IME_ACTION_NEXT));
        } catch (Exception e) {

        }

        // Specify the input method type of the text box.
        try {
            editText.setInputType(array.getInt(
                    R.styleable.EditTextRegisterParty_android_inputType, InputType.TYPE_CLASS_TEXT));
        } catch (Exception e) {

        }


        // Sets the horizontal alignment of the text and the vertical gravity in the text box
        try {
            editText.setGravity(array.getInt(
                    R.styleable.EditTextRegisterParty_android_gravity, Gravity.CENTER));
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
     * @param str String:   String label followed by asterisk
     * @return Label with text followed by asterisk
     */
    public EditTextRegisterParty setTitleWithAsterisk(String str) {

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
     * Sets the height of the text box to be exactly pixels tall.
     * <p>
     *
     * @param height int:    The exact height of the text box in terms of pixels
     * @return Text box with height changed
     */
    public EditTextRegisterParty setEditTextHeight(int height) {

        if (height != editText.getHeight()) {
            ViewGroup.LayoutParams params = editText.getLayoutParams();
            params.height = height;
            editText.setLayoutParams(params);
        }
        return this;
    }

    // Provide a get text method from text box
    public String getTextFromEditText() {
        return editText.getText().toString();
    }
}
