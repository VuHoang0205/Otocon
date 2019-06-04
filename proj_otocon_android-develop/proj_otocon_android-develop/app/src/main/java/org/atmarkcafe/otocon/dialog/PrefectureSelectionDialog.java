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

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogPrefectureSelectionBinding;
import org.atmarkcafe.otocon.model.response.Prefecture;

public class PrefectureSelectionDialog extends BottomSheetDialog implements PrefectureSelectionContract.View {

    private PrefectureSelectionPresenter mPresenter;
    private ObservableField<String> mPrefecture;
    private ObservableField<String> mPrefectureId;

    public PrefectureSelectionDialog(@NonNull Context context) {
        super(context, R.style.dialog_base_no_actionbar);
        mPresenter = new PrefectureSelectionPresenter(getContext(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        DialogPrefectureSelectionBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_prefecture_selection, null, false);
        binding.setPresenter(mPresenter);

        setContentView(binding.getRoot());

        mPresenter.onLoad();
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setObversablePrefecture(ObservableField<String> result) {
        this.mPrefecture = result;
        mPresenter.setCurrentSelection(result.get());
    }

    public void setObversablePrefectureId(ObservableField<String> result) {
        this.mPrefectureId = result;
    }

    @Override
    public void close(Prefecture prefecture) {
        if (prefecture != null) {
            if (mPrefectureId != null) mPrefectureId.set(prefecture.getId());
            if (mPrefecture != null) mPrefecture.set(prefecture.getName());
        }
        dismiss();
    }
}
