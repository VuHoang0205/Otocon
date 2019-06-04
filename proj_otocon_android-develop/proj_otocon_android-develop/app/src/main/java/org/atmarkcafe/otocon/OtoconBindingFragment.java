package org.atmarkcafe.otocon;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.atmarkcafe.otocon.utils.BindingUtils;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyboardUtils;
import org.atmarkcafe.otocon.utils.LogUtils;

public abstract class OtoconBindingFragment<T extends ViewDataBinding> extends OtoconFragment {

    public void startReplace(FragmentActivity activity, OtoconFragmentListener listener, boolean isBack) {
        this.otoconFragmentListener = listener;
        FragmentUtils.replace(activity, this, isBack);
    }

    public void finish() {
        getActivity().onBackPressed();
    }

    public void finish(int status, Bundle extras) {
        getActivity().onBackPressed();
        otoconFragmentListener.onHandlerReult(status, extras);
    }

    public void finishParent() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void finishParent(int status, Bundle extras) {
        getActivity().getSupportFragmentManager().popBackStack();
        if(((OtoconBindingFragment) getParentFragment()) != null)
        ((OtoconBindingFragment) getParentFragment()).otoconFragmentListener.onHandlerReult(status, extras);
    }

    public void setEnableBack(boolean enable) {
        if (getActivity() != null) {
            ((ExtensionActivity) getActivity()).setEnableBack(enable);
        }
    }

    public T viewDataBinding;

    @Override
    public void onResume() {
        super.onResume();

        LogUtils.d("Fragment : " + this.getClass().getName(), null);
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewDataBinding == null) {
            viewDataBinding = DataBindingUtil.inflate(inflater, layout(), container, false);
            KeyboardUtils.hiddenKeyBoard(getActivity());
            onCreateView(viewDataBinding);
        }

        return viewDataBinding.getRoot();
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    private FragmentManager childFrgementManager;

    public void setStoreChildFrgementManager(FragmentManager childFrgementManager) {
        this.childFrgementManager = childFrgementManager;
    }


    public FragmentManager getStoreChildFrgementManager() {
        return childFrgementManager;
    }

    public void clearStoreChildFrgementManager() {
        if (childFrgementManager != null) {
            // FIXME
        } else if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            //FIXME
        }
    }

    public void onHideKeyboard() {
        KeyboardUtils.hiddenKeyBoard(getActivity());
    }

    public abstract int layout();

    public abstract void onCreateView(T viewDataBinding);


}
