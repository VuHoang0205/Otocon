package org.atmarkcafe.otocon.function.advancedsearch;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.xwray.groupie.GroupAdapter;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogAdvancedSearchSearchByAgeBinding;
import org.atmarkcafe.otocon.function.advancedsearch.items.SearchByAeItem;
import org.atmarkcafe.otocon.utils.AgeUtils;

import java.util.List;

import static org.atmarkcafe.otocon.function.advancedsearch.AdvancedSearchDialog.KEY_AGE_OF_OPPONENT;

public class AdvancedSearchAroundAgeDialog extends Dialog {
    private DialogAdvancedSearchSearchByAgeBinding mBinding;
    private AdvancedSearchDialog.OnAdvancedSearchActionListener mListener;
    GroupAdapter advancedSearchAdapter = new GroupAdapter();
    private List<String> firstValues;
    private String mFirstValue;

    public AdvancedSearchAroundAgeDialog(@NonNull Context context, String mFirstValue, AdvancedSearchDialog.OnAdvancedSearchActionListener mListener) {
        super(context, R.style.AppTheme_Dialog);
        this.mListener = mListener;
        this.mFirstValue = mFirstValue;
    }

    public void initData() {
        String[] array = getContext().getResources().getStringArray(R.array.advanced_search_ages);
        for (int i = 0; i < array.length; i++) {
            int id = i + 1;

            advancedSearchAdapter.add(new SearchByAeItem(id, array[i], firstValues.contains(String.valueOf(id))));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_advanced_search_search_by_age, null, false);

        setContentView(mBinding.getRoot());

        mBinding.reset.setOnClickListener(v -> {
            if (advancedSearchAdapter != null) {
                advancedSearchAdapter.clear();
                firstValues.clear();
                initData();
            }
        });

        mBinding.back.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onBackPress();
            }
            AdvancedSearchAroundAgeDialog.this.dismiss();
        });

        firstValues = AgeUtils.getHashMap(mFirstValue);

        initData();

        mBinding.recyclerview.setAdapter(advancedSearchAdapter);

        mBinding.submit.setOnClickListener(v -> {
            if (mListener != null) {
                StringBuilder str = new StringBuilder();

                int size = advancedSearchAdapter.getItemCount();
                for (int i = 0; i < size; i++) {

                    if (((SearchByAeItem) advancedSearchAdapter.getItem(i)).isChecked.get()) {
                        if (!str.toString().isEmpty()) {
                            str.append(",");
                        }
                        str.append(i + 1);
                    }
                }

                mListener.onSendResult(KEY_AGE_OF_OPPONENT, str.toString());
                dismiss();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mListener != null) mListener.onBackPress();
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mListener.onBackPress();
    }
}
