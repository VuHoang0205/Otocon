package org.atmarkcafe.otocon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogGpsBinding;

public class MessageDialog extends Dialog {

    public interface PopupMessageErrorListener{
        void onPressed(boolean ok);
    }

    private PopupMessageErrorListener listener;
    private String message = null;

    private String title = null;
    private String buttonOk = null;
    private String buttonCancel = null;


    public void set(String title, String message){
        this.title = title;
        this.message = message;
    }

    public void setButtonOk(String ok){
        this.buttonOk = ok;
    }

    public void setButtonCancel(String cancel){
        this.buttonCancel = cancel;
    }

    public MessageDialog(@NonNull Context context, PopupMessageErrorListener listener) {
        super(context, R.style.AppTheme_Dialog);
        this.listener = listener;
    }

    public MessageDialog(@NonNull Context context, String title, String message,PopupMessageErrorListener listener) {
        super(context, R.style.AppTheme_Dialog);
        this.listener = listener;
        this.title = title;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        DialogGpsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_gps, null, false);
        binding.setDialog(this);

        if(message != null){
            binding.textView7.setText(message);
        }

        if(title != null){
            binding.textView6.setText(title);
        }

        if (buttonOk != null){
            binding.bnOk.setText(buttonOk);
        }

        if (buttonCancel != null){
            binding.cancel.setText(buttonCancel);
        }
        setContentView(binding.getRoot());
    }

    public void onPressedOk(){

        dismiss();
        listener.onPressed(true);
    }

    public void onPressedCancel(){

        dismiss();
        listener.onPressed(false);
    }
}
