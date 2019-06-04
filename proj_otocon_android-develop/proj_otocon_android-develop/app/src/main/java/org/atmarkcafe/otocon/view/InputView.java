package org.atmarkcafe.otocon.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputView extends ConstraintLayout {

    public enum Type {
        email, //
        passworld, //
        username, //
        sex, //
    }

    private Context context;
    private Type type;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.edInput)
    EditText edInput;

    public InputView(Context context) {
        super (context);
        init (context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super (context, attrs);
        init (context);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        init (context);
    }

    private void init(Context context) {
        LayoutInflater.from (getContext ()).inflate (R.layout.view_input_custom, this);
        this.context = context;

        ButterKnife.bind (this);
    }

    public void setText(String title) {
        tvTitle.setText (title);
    }

    public void setType(Type type) {
        this.type = type;
        switch (type) {
            case sex:
                tvTitle.setText (context.getResources ().getString (R.string.gender));
                break;
            case username:
                tvTitle.setText (context.getResources ().getString (R.string.username));
                break;
            case email:
                tvTitle.setText (context.getResources ().getString (R.string.email));
                break;
            case passworld:
                tvTitle.setText (context.getResources ().getString (R.string.passworld));
                edInput.setInputType (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
    }

    public String getText() {
        return edInput.getText ().toString ();
    }

}
