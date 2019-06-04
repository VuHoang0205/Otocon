package org.atmarkcafe.otocon.function.menu;

import android.content.Context;
import android.provider.Settings;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.response.MenuNotifyCountResponse;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.UnreadNotificationResponse;

public class MenuFragmentPresenter extends MVPPresenter<String, OnResponse> implements MVPPresenter.ExecuteListener<OnResponse> {
    public final static int ACTION_MENU_NOTIFY_COUNT = 0;
    public final static int ACTION_NEW_EVENT_COUNT = 1;

    public MenuFragmentPresenter(MVPExtension.View<OnResponse> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, String s) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (action == ACTION_MENU_NOTIFY_COUNT) {
            execute(InteractorManager.getApiInterface(context).getNotifyCount(deviceId), new ExecuteListener<MenuNotifyCountResponse>() {
                @Override
                public void onNext(MenuNotifyCountResponse menuNotifyCountResponse) {
                    if (menuNotifyCountResponse != null && menuNotifyCountResponse.isSuccess()) {
                        view.success(menuNotifyCountResponse);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        } else {
            execute(InteractorManager.getApiInterface(context).getUnreadNotification(deviceId), new ExecuteListener<UnreadNotificationResponse>() {
                @Override
                public void onNext(UnreadNotificationResponse response) {
                    if (response != null && response.isSuccess()) {
                        view.success(response);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }

    @Override
    public void onNext(OnResponse menuNotifyCountResponse) {

    }

    @Override
    public void onError(Throwable e) {

    }

//    @Override
//    public void onLoadNotice(final Context context) {
//        view.showProgessbar(true);
//
//        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        InteractorManager.getApiInterface(context).getNotifyCount(deviceId)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<MenuNotifyCountResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(MenuNotifyCountResponse menuNewRespone) {
//                        view.showProgessbar(false);
//                        if (menuNewRespone == null) {
//                            view.showDialog(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail));
//                        } else if (menuNewRespone.isSuccess()) {
//                            view.success(menuNewRespone.getNewCount());
//                        } else {
//                            view.showDialog(context.getString(R.string.error_title_Connect_server_fail), menuNewRespone.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.showProgessbar(false);
//                        if (e instanceof HttpException) {
//                            view.showDialog(context.getString(R.string.error_title_Connect_server_fail), context.getString(R.string.error_content_Connect_server_fail));
//                        } else {
//                            view.showDialog(context.getString(R.string.network_error_title), context.getString(R.string.network_error_content));
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
