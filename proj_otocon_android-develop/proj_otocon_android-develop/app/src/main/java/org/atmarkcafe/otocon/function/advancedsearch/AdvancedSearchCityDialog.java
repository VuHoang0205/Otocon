package org.atmarkcafe.otocon.function.advancedsearch;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.xwray.groupie.GroupAdapter;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogAdvancedSearchChoiceCityOfAreaBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;

import java.util.List;

public class AdvancedSearchCityDialog extends Dialog implements AdvancedSearchCityContract.View {

    private AdvancedSearchDialog.OnAdvancedSearchActionListener mListener;
    private String mFirstValue = "";
    private DialogAdvancedSearchChoiceCityOfAreaBinding binding;
    private AdvancedSearchCityPresenter presenter;

    public AdvancedSearchCityDialog(@NonNull Context context, AdvancedSearchDialog.OnAdvancedSearchActionListener mListener) {
        super(context, R.style.AppTheme_Dialog);
        this.mListener = mListener;
    }

    public void setFirstValue(String value) {
        mFirstValue = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_advanced_search_choice_city_of_area, null, false);
        setContentView(binding.getRoot());

        presenter = new AdvancedSearchCityPresenter(this);
        presenter.onLoadArea(getContext());

        binding.back.setOnClickListener(v -> {
            mListener.onBackPress();
            dismiss();
        });

        binding.reset.setOnClickListener(view -> {
            presenter.onResetCheckCity();
        });

        binding.submit.setOnClickListener((view) -> {
            presenter.onGetCheckCity();
        });

    }

    @Override
    public void showProgress(boolean isShow) {
        binding.loadingLayout.getRoot().setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void success(List<AdvancedSearchCityExpandableGroup> groupList) {

        GroupAdapter groupAdapter = new GroupAdapter();
        groupAdapter.addAll(groupList);
        binding.recyclerview.setAdapter(groupAdapter);

        presenter.onSetCheckCity(mFirstValue);
    }

    @Override
    public void showDialogError() {
        new PopupMessageErrorDialog(getContext()).show();
    }

    @Override
    public void getCheckCity(String idList) {
            mListener.onSendResult(AdvancedSearchDialog.KEY_AREA ,idList);
            dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mListener.onBackPress();
    }
}
