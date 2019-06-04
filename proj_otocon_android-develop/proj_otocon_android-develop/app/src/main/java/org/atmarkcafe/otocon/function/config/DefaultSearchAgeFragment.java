package org.atmarkcafe.otocon.function.config;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.atmarkcafe.otocon.BR;

import org.atmarkcafe.otocon.IntroFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;

import org.atmarkcafe.otocon.databinding.DialogDefaultSearchAgeBinding;
import org.atmarkcafe.otocon.dialog.SelectionDialog;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchAgeDialog;
import org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchDialog;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

public class DefaultSearchAgeFragment extends OtoconBindingFragment<DialogDefaultSearchAgeBinding> {

    public enum ErrorType {
        none, blank, outofthreshold
    }

    DefaultSeachListener listener;

    public void setListener(DefaultSeachListener listener) {
        this.listener = listener;
    }

    public String age;

    @Override
    public int layout() {
        return R.layout.dialog_default_search_age;
    }

    @Override
    public void onCreateView(DialogDefaultSearchAgeBinding binding) {

        binding.setVariable(BR.dialog, this);
        binding.setErrorType(ErrorType.none);
        binding.setShow(false);
    }
    public void onBack() {
        getStoreChildFrgementManager().popBackStack();
        // ((IntroActivity) getActivity()).onFinishFragment();
        listener.onBack();
    }

    public void onClickAge() {
        int ageInt = age == null ? 0 : age.isEmpty() ? 0 : Integer.parseInt(age);
        viewDataBinding.setShow(true);
        SelectionDialog.showAge(getContext(), ageInt, new SelectionDialog.SelectionDialogListener() {
            @Override
            public void onResult(String str) {
                viewDataBinding.setVariable(BR.show, false);
                age = str.replace(getContext().getString(R.string.age), "");
                viewDataBinding.edtAge.setText(str);
            }

            @Override
            public void onDismis() {
                viewDataBinding.setShow(false);
            }
        });
    }

    public void onConfrim() {
        viewDataBinding.setErrorType(ErrorType.none);
        if (age == null || age != null && age.isEmpty()) {
            viewDataBinding.setErrorType(ErrorType.blank);
        } else {
            int ageInt = Integer.parseInt(age);

            if (ageInt < 20 || ageInt > 100) {
                viewDataBinding.setErrorType(ErrorType.outofthreshold);
            } else {
                // save to db
                SearchDefault.getInstance().setAge(ageInt);
                SearchDefault.getInstance().save();

                DefaultSearchGenderFragment genderDialog = new DefaultSearchGenderFragment();
                FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, genderDialog, true);
            }
        }
    }

    public void onReset() {
        age = null;
        viewDataBinding.setVariable(BR.dialog, this);
    }
}