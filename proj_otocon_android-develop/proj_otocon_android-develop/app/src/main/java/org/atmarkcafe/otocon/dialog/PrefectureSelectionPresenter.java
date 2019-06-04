package org.atmarkcafe.otocon.dialog;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.response.Prefecture;
import org.atmarkcafe.otocon.model.response.PrefectureResponse;
import org.atmarkcafe.otocon.pref.BaseShareReferences;
import org.atmarkcafe.otocon.view.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PrefectureSelectionPresenter implements PrefectureSelectionContract.Presenter {
    private Context mContext;
    private PrefectureSelectionContract.View mView;

    List<Prefecture> dataList = new ArrayList<>();
    public final ObservableField<List<String>> itemList = new ObservableField<>(new ArrayList<String>());
    public OnItemSelectedListener onItemSelectedListener;
    private String initPrefecture;

    public final ObservableBoolean isLoading = new ObservableBoolean(true);
    public final ObservableInt currentPosition = new ObservableInt();

    public PrefectureSelectionPresenter(Context ctx, PrefectureSelectionContract.View view) {
        this.mContext = ctx;
        this.mView = view;

//        prefectureList.add("HELLO");
//        prefectureList.add("WORLD");
        this.onItemSelectedListener = index -> currentPosition.set(index);
    }

    public void setCurrentSelection(String init) {
        initPrefecture = init;
        for (int i = 0; i < itemList.get().size(); i++) {
            if (itemList.get().get(i).equals(init)) {
                currentPosition.set(i);
                break;
            }
        }
    }

    @Override
    public void onLoad() {
        isLoading.set(true);

        InteractorManager.getApiInterface(mContext).listPefectures()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrefectureResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PrefectureResponse response) {
                        isLoading.set(false);
                        dataList.clear();
                        if (response.isSuccess()) {
                            List<Prefecture> data = response.getList();
                            dataList.addAll(data);

                            // save list to db
                            Gson gson = new Gson();
                            String json = gson.toJson(data);

                            new BaseShareReferences(mContext).set( BaseShareReferences.KEY_PREFECTURE, json);
                        } else {
                            // load data from db
                            dataList.addAll(loadPrefecture());
                        }

                        showDataToView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        // load data from db
                        dataList.addAll(loadPrefecture());

                        showDataToView();

                        // fail (first time)
                        if(dataList.size() == 0){
//                            mView.showMessage();
                        }
                    }

                    private  List<Prefecture> loadPrefecture() {
                        // get from DB
                        String json =  new BaseShareReferences(mContext).get(BaseShareReferences.KEY_PREFECTURE);

                        // if first
                        if(json.isEmpty()){
                            json = "[]";
                        }

                        Gson gson = new Gson();
                        List<Prefecture> list  = gson.fromJson(json, Prefecture.typeList);
                        return list;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showDataToView() {

        int initPos = 0;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (initPrefecture.equals(dataList.get(i).getName())) {
                initPos = i;
            }
            list.add(dataList.get(i).getName());
        }

        itemList.set(list);
        currentPosition.set(initPos);
    }

    @Override
    public void onClose() {
        Prefecture prefecture = null;
        if (dataList != null && dataList.size() > 0) {
            prefecture = dataList.get(currentPosition.get());
        }
        mView.close(prefecture);
    }
}
