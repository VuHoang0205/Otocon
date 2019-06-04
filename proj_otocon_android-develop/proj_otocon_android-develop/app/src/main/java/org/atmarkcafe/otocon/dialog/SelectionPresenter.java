package org.atmarkcafe.otocon.dialog;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;

import org.atmarkcafe.otocon.utils.StringUtils;
import org.atmarkcafe.otocon.view.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class SelectionPresenter implements SelectionContract.Presenter {
    private Context mContext;
    private SelectionContract.View mView;
    public final List<String> itemList = new ArrayList<>();
    public OnItemSelectedListener onItemSelectedListener;


    public final ObservableInt currentPosition = new ObservableInt();

    public SelectionPresenter(Context ctx, SelectionContract.View view) {
        this.mContext = ctx;
        this.mView = view;

        this.onItemSelectedListener = index -> currentPosition.set(index);
    }

    public void setData(List<String> data) {
        this.itemList.clear();
        this.itemList.addAll(data);
    }

    public void setCurrentSelection(int index) {
        currentPosition.set(index);
    }

    public void setCurrentSelection(String gender) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).equals(gender)) {
                currentPosition.set(i);
                break;
            }
        }
    }

    @Override
    public void onClose() {
        if (itemList.size() == 0) {
            mView.close("");
            return;
        }
        mView.close(itemList.get(currentPosition.get()));
    }
}
