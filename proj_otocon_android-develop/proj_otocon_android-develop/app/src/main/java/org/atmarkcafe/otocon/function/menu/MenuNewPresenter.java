package org.atmarkcafe.otocon.function.menu;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.provider.Settings;
import android.util.Log;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.menunew.MenuNewModel;
import org.atmarkcafe.otocon.model.menunew.MenuNewRespone;
import org.atmarkcafe.otocon.model.response.MenuNotifyCountResponse;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.utils.ValidateUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MenuNewPresenter extends MVPPresenter<MenuNewModel, OnResponse> {
    public static final int MARK_READ = 1;
    public static final int LOAD_LIST_NEW = 0;

    private int page = 1;
    private int limit = 20;

    public MenuNewPresenter setPage(int page) {
        this.page = page;
        return this;
    }

    public MenuNewPresenter(MVPExtension.View<OnResponse> view) {
        super(view);
    }

    public ObservableBoolean isLoading = new ObservableBoolean(false);

    @Override
    public void onExecute(Context context, int action, MenuNewModel model) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        isLoading.set(true);
        switch (action) {
            case LOAD_LIST_NEW:
                execute(InteractorManager.getApiInterface(context).menuListNews(limit, page, deviceId), new ExecuteListener<MenuNewRespone>() {
                    @Override
                    public void onNext(MenuNewRespone respone) {
                        isLoading.set(false);

                        if(respone != null && respone.isSuccess()){
                            view.success(respone);
                        }else{
                            String message [] = OnResponse.getMessage(context, null, respone);
                            view.showPopup(message[0], message[1]);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);

                        String message [] = OnResponse.getMessage(context, e, null);
                        view.showPopup(message[0], message[1]);
                    }
                });
                break;
            case MARK_READ:
                execute(InteractorManager.getApiInterface(context).newMarkAsRead(deviceId, model.getNotify_id()), new ExecuteListener<OnResponse>() {
                    @Override
                    public void onNext(OnResponse respone) {
                        isLoading.set(false);

                        if(respone != null && respone.isSuccess()){
                            model.setNotify_id("");
                            view.success(respone);
                        }else{
                            String message [] = OnResponse.getMessage(context, null, respone);
                            view.showPopup(message[0], message[1]);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);

                        String message [] = OnResponse.getMessage(context, e, null);
                        view.showPopup(message[0], message[1]);
                    }
                });

                break;
        }
    }
}
