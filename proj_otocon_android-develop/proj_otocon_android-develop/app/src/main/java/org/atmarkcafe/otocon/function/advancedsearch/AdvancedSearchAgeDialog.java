package org.atmarkcafe.otocon.function.advancedsearch;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.dialog.SelectionDialog;
import java.util.ArrayList;
import java.util.List;


public class AdvancedSearchAgeDialog extends Dialog {


    AdvancedSearchDialog.OnAdvancedSearchActionListener dialogListener;
    ViewDataBinding binding;
    public int age;


    public AdvancedSearchAgeDialog(@NonNull Context context, int age, AdvancedSearchDialog.OnAdvancedSearchActionListener dialogListener) {
        super(context, R.style.AppTheme_Dialog);

        this.age = age;

        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_avanced_search_age, null, false);
        setContentView(binding.getRoot());

        binding.setVariable(BR.show, false);

        binding.setVariable(BR.dialog, this);
    }

    public void onBack() {
        dialogListener.onBackPress();
        this.dismiss();
    }

    public void onClickAge() {
        List<String> datas = new ArrayList<>();
        for (int i = 20; i <= 100; i++) {
            datas.add(i + getContext().getString(R.string.age));
        }


        binding.setVariable(BR.show, true);
        SelectionDialog selectionDialog = new SelectionDialog(getContext(), datas);
        // default is 30
        selectionDialog.setCurrentPosition(age == 0 ? 10 : age - 20);
        selectionDialog.setListener(new SelectionDialog.SelectionDialogListener() {
            @Override
            public void onResult(String str) {
                AdvancedSearchAgeDialog.this.binding.setVariable(BR.show, false);
                age = Integer.parseInt(str.replace(getContext().getString(R.string.age), ""));
                AdvancedSearchAgeDialog.this.binding.setVariable(BR.dialog, AdvancedSearchAgeDialog.this);
            }

            @Override
            public void onDismis() {
                
            }
        });

        selectionDialog.show();

    }

    public void onReset() {
        age = 0;
        binding.setVariable(BR.dialog, this);
    }

    public void onDismiss() {
        dismiss();
    }

    public void onConfrim() {
        dialogListener.onSendResult(AdvancedSearchDialog.KEY_AGE, age + "");

        this.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialogListener.onBackPress();
    }
}
