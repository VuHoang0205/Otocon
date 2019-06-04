package org.atmarkcafe.otocon.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogSelectionBinding;
import org.atmarkcafe.otocon.function.config.DefaultSearchAgeFragment;
import org.atmarkcafe.otocon.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectionDialog extends BottomSheetDialog implements SelectionContract.View {

    public static void showAge(Context context, int ageInt, SelectionDialogListener listener) {
        List<String> datas = new ArrayList<>();
        for (int i = 20; i <= 100; i++) {
            datas.add(i + context.getString(R.string.age));
        }

        SelectionDialog selectionDialog = new SelectionDialog(context, datas);
        // default is 30
        selectionDialog.setCurrentPosition(ageInt == 0 ? 10 : ageInt - 20);
        selectionDialog.setListener(listener);

        selectionDialog.show();
    }

    public interface SelectionDialogListener {
        public void onResult(String str);

        void onDismis();
    }

    private SelectionDialogListener listener;


    private int currentPosition = -1;

    public void setListener(SelectionDialogListener listener) {
        this.listener = listener;
    }


    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    private SelectionPresenter mPresenter;
    private ObservableField<String> mResult;

    public SelectionDialog(@NonNull Context context, List<String> list) {
        super(context, R.style.dialog_base_no_actionbar);
        mPresenter = new SelectionPresenter(getContext(), this);
        mPresenter.setData(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        DialogSelectionBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_selection, null, false);
        binding.setPresenter(mPresenter);

        setContentView(binding.getRoot());

        if (currentPosition >= 0) {
            mPresenter.setCurrentSelection(currentPosition);
        }
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(listener!= null){
                    listener.onDismis();
                }
            }
        });
    }

    public void setObversableResult(ObservableField<String> result) {
        this.mResult = result;
        mPresenter.setCurrentSelection(result.get());
    }

    @Override
    public void close(String selected) {

        if (listener != null) {
            listener.onResult(selected);
        }

        if (mResult != null) mResult.set(selected);

        dismiss();
    }
}
