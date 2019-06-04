package org.atmarkcafe.otocon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogRematchMessageBinding;

public class DialogRematchMessage extends Dialog {

    public interface DialogRematchListener {
        void onClickButtonDialog(boolean isOke, boolean isChecked);
    }

    private DialogRematchMessage.DialogRematchListener listener;
    private String message = "";
    private Boolean isChecked = false;
    private String nameButtonGreen = null;
    private String nameButtonBlack = null;
    private String nameCheckBox = null;

    public DialogRematchMessage(@NonNull Context context, String message, DialogRematchMessage.DialogRematchListener listener) {
        super(context, R.style.AppTheme_Dialog);
        this.listener = listener;
        this.message = message;
    }

    public DialogRematchMessage setTextButtonGreen(String nameGreen){
        this.nameButtonGreen = nameGreen;
        return this;
    }

    public DialogRematchMessage setTexButtonBlack(String nameBlack){
        this.nameButtonBlack = nameBlack;
        return this;
    }

    public DialogRematchMessage setTextCheckBox(String nameCheckBox){
        this.nameCheckBox = nameCheckBox;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);
        DialogRematchMessageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_rematch_message, null, false);
        binding.setDialog(this);

        setContentView(binding.getRoot());
    }

    public void onPressedOk() {
        dismiss();
        if (listener != null){
            listener.onClickButtonDialog(true, isChecked);
        }
    }

    public void onPressedCancel() {
        dismiss();
        if (listener != null){
            listener.onClickButtonDialog(false, isChecked);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getNameButtonGreen() {
        return nameButtonGreen;
    }

    public String getNameButtonBlack() {
        return nameButtonBlack;
    }

    public String getNameCheckBox() {
        return nameCheckBox;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
