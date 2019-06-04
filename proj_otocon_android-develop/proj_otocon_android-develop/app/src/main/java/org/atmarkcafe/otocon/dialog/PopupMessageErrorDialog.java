package org.atmarkcafe.otocon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogPopupMessageErrorBinding;

public class PopupMessageErrorDialog extends Dialog {
    public interface PopupMessageErrorListener {
        void onClickOke();
    }

    private String message;
    private String title;
    private PopupMessageErrorListener mListener;

    public PopupMessageErrorDialog(@NonNull Context context, String title, String message) {
        super(context, R.style.AppTheme_Dialog);
        this.message = message;
        this.title = title;
    }

    public PopupMessageErrorDialog setPopupMessageErrorListener(PopupMessageErrorListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public PopupMessageErrorDialog(@NonNull Context context, String message) {
        super(context, R.style.AppTheme_Dialog);
        this.message = message;
        this.title = getContext().getResources().getString(R.string.app_name);
    }

    public PopupMessageErrorDialog(@NonNull Context context) {
        super(context, R.style.AppTheme_Dialog);
        this.message = getContext().getResources().getString(R.string.network_error_content);
        this.title = getContext().getResources().getString(R.string.network_error_title);
    }

    public PopupMessageErrorDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.AppTheme_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        DialogPopupMessageErrorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_popup_message_error, null, false);
        binding.setDialog(this);
        binding.setTitle(title);
        binding.setMessage(message);

        setContentView(binding.getRoot());
    }

    public void finishDialog() {
        if (mListener != null) {
            mListener.onClickOke();
        }
        dismiss();
    }

    public static PopupMessageErrorDialog show(Context context, String title, String message, PopupMessageErrorListener listener) {
        PopupMessageErrorDialog dialog = null;
        if (context != null) {
            if (message != null && title != null) {
                dialog = new PopupMessageErrorDialog(context, title, message);
            } else if (title == null && message != null) {
                dialog = new PopupMessageErrorDialog(context, message);
            } else {
                dialog = new PopupMessageErrorDialog(context);
            }
        }

        if (dialog != null) {
            dialog.setPopupMessageErrorListener(listener);
            dialog.show();
        }
        return dialog;
    }

    public static PopupMessageErrorDialog show(Context context, int resTitle, int resMessage, PopupMessageErrorListener listener) {
        PopupMessageErrorDialog dialog = null;
        if (context != null) {

            String title = resTitle > 0 ? context.getString(resTitle) : null;
            String message = resMessage > 0 ? context.getString(resMessage) : null;

            if (message != null && title != null) {
                dialog = new PopupMessageErrorDialog(context, title, message);
            } else if (title == null && message != null) {
                dialog = new PopupMessageErrorDialog(context, message);
            } else {
                dialog = new PopupMessageErrorDialog(context);
            }
        }

        if (dialog != null) {
            dialog.setPopupMessageErrorListener(listener);
            dialog.show();
        }
        return dialog;
    }

    public static PopupMessageErrorDialog show(Context context, int resTitle, String message, PopupMessageErrorListener listener) {
        PopupMessageErrorDialog dialog = null;
        if (context != null) {

            String title = resTitle > 0 ? context.getString(resTitle) : null;

            if (message != null && title != null) {
                dialog = new PopupMessageErrorDialog(context, title, message);
            } else if (title == null && message != null) {
                dialog = new PopupMessageErrorDialog(context, message);
            } else {
                dialog = new PopupMessageErrorDialog(context);
            }
        }

        if (dialog != null) {
            dialog.setPopupMessageErrorListener(listener);
            dialog.show();
        }
        return dialog;
    }

}
