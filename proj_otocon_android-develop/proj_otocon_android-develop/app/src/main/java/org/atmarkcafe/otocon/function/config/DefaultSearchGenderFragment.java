package org.atmarkcafe.otocon.function.config;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.IntroFragment;
import org.atmarkcafe.otocon.MainFragment;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogDefaultSearchGenderBinding;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.FragmentUtils;


public class DefaultSearchGenderFragment extends OtoconBindingFragment<DialogDefaultSearchGenderBinding> {

    int gender = 0;

    @Override
    public int layout() {
        return R.layout.dialog_default_search_gender;
    }

    @Override
    public void onCreateView(DialogDefaultSearchGenderBinding binding) {

        binding.setVariable(BR.dialog, this);
        binding.setVariable(BR.hasError, false);
        binding.setVariable(BR.gender, gender);

    }

    public void onBack() {
        getStoreChildFrgementManager().popBackStack();
    }

    public void onConfrim() {
        viewDataBinding.setVariable(BR.hasError, false);

        if (gender == 0) {
            viewDataBinding.setVariable(BR.hasError, true);
            return;
        }

        SearchDefault.getInstance().setGender(gender);
        SearchDefault.getInstance().save();

        // set first time is false
        SearchDefault.getInstance().setFirst(false);
        SearchDefault.getInstance().save();

        ExtensionActivity.setEnableBack(getActivity(), true);

        ((IntroFragment)getParentFragment()).onSusscess();

        //FragmentUtils.replace(getActivity(), new MainFragment(), false);
    }

    public void onReset() {
        gender = 0;
        viewDataBinding.setVariable(BR.gender, gender);
    }

    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        gender = id == R.id.male ? 1 : id == R.id.female ? 2 : 0;
    }
}