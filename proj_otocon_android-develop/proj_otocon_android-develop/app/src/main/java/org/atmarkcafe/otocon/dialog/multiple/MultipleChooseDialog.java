package org.atmarkcafe.otocon.dialog.multiple;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.DialogMultipleChooseBinding;
import org.atmarkcafe.otocon.databinding.DialogSelectionBinding;
import org.atmarkcafe.otocon.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.view.ViewGroup;

public class MultipleChooseDialog extends Dialog {
    public interface MultipleChooseDialogListener {
        public void onResult(List<MultipleModel> lis, String ids, String names);
    }

    MultipleAdapter adapter = new MultipleAdapter();
    private String title = "";

    DialogMultipleChooseBinding binding;
    MultipleChooseDialogListener listener;

    public MultipleChooseDialog(@NonNull Context context, String title, List<MultipleModel> list, MultipleChooseDialogListener listener) {
        super(context, R.style.AppTheme_Dialog);
        this.title = title;
        adapter.setDatas(list, null);
        this.listener = listener;
    }

    public MultipleChooseDialog(@NonNull Context context, String title, List<MultipleModel> list,String ids,  MultipleChooseDialogListener listener) {
        super(context, R.style.AppTheme_Dialog);
        this.title = title;
        adapter.setDatas(list, ids);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_multiple_choose, null, false);
        binding.title.setText(title);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerview.setAdapter(adapter);

        binding.setDialog(this);

        setContentView(binding.getRoot());
    }

    public void onReset() {
        // clear
        adapter.reset();
    }

    public void onSubmit() {
        dismiss();


        LogUtils.d("ddddd " + adapter.getIds() , null);
        listener.onResult(adapter.getResult(), adapter.getIds(), adapter.getNames());
    }

    public void onBack() {
        dismiss();
    }
}


class MultipleAdapter extends RecyclerView.Adapter<MultipleViewHolder> {

    private List<MultipleModel> list = new ArrayList<>();

    public void setDatas(List<MultipleModel> list, String ids) {
        this.list.clear();

        List<String> selectedIds = toList(ids);
        for (MultipleModel model : list) {

            MultipleModel newNmodel = new MultipleModel(model.getId(), model.getTitle(), model.isChecked());

            if(selectedIds.contains(newNmodel.getId() + "")){
                newNmodel.setChecked(true);
            }
            this.list.add(newNmodel);
        }


    }

    private List<String> toList(String ids){
        List<String> list = new ArrayList<>();
        if(ids != null && !ids.isEmpty()){
            String idArray[]  = ids.split(",");
            for(String str : idArray){
                list.add(str);
            }
        }

        return list;
    }

    public List<MultipleModel> getResult() {
        List<MultipleModel> list = new ArrayList<>();
        for (MultipleModel model : this.list) {
            if (model.isChecked()) {
                list.add(model);
            }
        }
        return list;
    }
    public String getNames() {
        String names = "";
        for (MultipleModel model : list) {
            if (model.isChecked()) {
                names = names + (names.isEmpty() ? "" : ",") + model.getTitle();
            }
        }

        return names;

    }
    public String getIds() {
        String ids = "";
        for (MultipleModel model : list) {
            if (model.isChecked()) {
                ids = ids + (ids.isEmpty() ? "" : ",") + model.getId();
            }
        }

        return ids;
    }
    public void reset() {
        for (MultipleModel model : list) {
            model.setChecked(false);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public MultipleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_multiple_choose, viewGroup, false);
        return new MultipleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleViewHolder multipleViewHolder, int i) {
        multipleViewHolder.bind(list.get(i));
    }


}

class MultipleViewHolder extends RecyclerView.ViewHolder {
    ViewDataBinding binding;

    public MultipleViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(MultipleModel model) {
        binding.setVariable(BR.item, model);
    }
}