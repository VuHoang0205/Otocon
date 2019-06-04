package org.atmarkcafe;

import android.view.View;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.FragmentTest2Binding;
import org.atmarkcafe.otocon.databinding.FragmentTestBinding;
import org.atmarkcafe.otocon.utils.FragmentUtils;

public class Test2Fragment extends OtoconBindingFragment<FragmentTest2Binding> {

    @Override
    public int layout() {
        return R.layout.fragment_test2;
    }

    @Override
    public void onCreateView(FragmentTest2Binding viewDataBinding) {
        viewDataBinding.btn.setText(System.currentTimeMillis() + "");

        viewDataBinding.btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, new Test2Fragment(), true);
            }
        });
    }

}
