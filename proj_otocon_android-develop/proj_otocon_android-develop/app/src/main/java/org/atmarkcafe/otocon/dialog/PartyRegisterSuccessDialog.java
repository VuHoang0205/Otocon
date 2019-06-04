package org.atmarkcafe.otocon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogPopupMessageErrorBinding;
import org.atmarkcafe.otocon.ktextension.OnItemClickListener;

import static org.atmarkcafe.otocon.utils.KeyExtensionUtils.RESULT.RESULT_REGISTER_PARTY_SUCCESS_INTRO;

public class PartyRegisterSuccessDialog extends Dialog {

    OtoconFragment.OtoconFragmentListener onItemClickListener;
    Bundle bundle;
    public PartyRegisterSuccessDialog(@NonNull Context context,OtoconFragment.OtoconFragmentListener onItemClickListener,Bundle bundle) {
        super(context, R.style.AppTheme_Dialog);
        this.onItemClickListener=onItemClickListener;
        this.bundle=bundle;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_party_register_success, null, false);
        binding.setVariable(BR.dialog,this);
        setContentView(binding.getRoot());
    }

    public void onSubmit(){
        dismiss();
        onItemClickListener.onHandlerReult(RESULT_REGISTER_PARTY_SUCCESS_INTRO,bundle);
    }
}
