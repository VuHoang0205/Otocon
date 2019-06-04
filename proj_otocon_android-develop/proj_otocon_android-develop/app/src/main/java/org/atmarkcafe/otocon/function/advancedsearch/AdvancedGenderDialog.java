package org.atmarkcafe.otocon.function.advancedsearch;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.databinding.DialogAdvancedGenderBinding;
import org.atmarkcafe.otocon.R;


public class AdvancedGenderDialog extends Dialog {

    private AdvancedSearchDialog.OnAdvancedSearchActionListener mListener;

    private DialogAdvancedGenderBinding binding;

    int gender = 0;

    public AdvancedGenderDialog(@NonNull Context context, int gender, AdvancedSearchDialog.OnAdvancedSearchActionListener listener) {
        super (context, R.style.AppTheme_Dialog);
        this.gender = gender;

        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from (getContext ());

        binding = DataBindingUtil.inflate (inflater, R.layout.dialog_advanced_gender, null, false);
        setContentView (binding.getRoot ());

        binding.setVariable (BR.dialog, this);
        binding.setVariable (BR.gender, gender);
    }

    public void onBack() {
        mListener.onBackPress();
        this.dismiss ();
    }

    public void onConfrim() {
        mListener.onSendResult(AdvancedSearchDialog.KEY_GENDER, gender + "");
        this.dismiss ();
    }

    public void onReset() {
        gender = 0;
        binding.setVariable (BR.gender, gender);
    }

    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        gender = id == R.id.male ? 1 : id == R.id.female ? 2 : 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mListener.onBackPress();
    }
}