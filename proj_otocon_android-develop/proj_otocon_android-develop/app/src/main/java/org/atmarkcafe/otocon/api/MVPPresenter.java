package org.atmarkcafe.otocon.api;

import android.content.Context;

import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.CityByAreaResponse;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.UserRespose;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class MVPPresenter<Params, Response extends OnResponse> {
    public MVPExtension.View<Response> view;

    public MVPPresenter(MVPExtension.View<Response> view) {
        this.view = view;
    }

    public abstract void onExecute(Context context, int action, Params params);

    public void loadInfor(final Context context, final ExecuteListener<UserRespose> listener) {
        execute(InteractorManager.getApiInterface(context).getUserInfo(), new ExecuteListener<UserRespose>() {
            @Override
            public void onNext(UserRespose response) {
                view.showProgress(false);
                if (response != null && response.isSuccess()) {
                    // save

                    if (!response.getAcoount().hasToken()) {
                        response.getAcoount().setToken(DBManager.getToken(context));
                    }

                    DBManager.save(context, response.getAcoount());
                }

                if (listener != null) {
                    listener.onNext(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    listener.onError(e);
                }
            }
        });
    }

    public interface ExecuteListener<Response> {

        public void onNext(Response response);


        public void onError(Throwable e);
    }

    public final <T> void execute(Observable<T> observable, final ExecuteListener<T> listener) {
        if (view != null)
            view.showProgress(true);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T response) {
                        if (view != null)
                            view.showProgress(false);

                        if( ((Response)response).hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(((Response)response).getMessage());
                            return;
                        }
                        if (listener != null) {
                            listener.onNext(response);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.showProgress(false);
                        if (listener != null) {
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public final <T> void executeToView(Context context, Observable<T> observable, final ExecuteListener<T> listener) {
        if (view != null)
            view.showProgress(true);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T response) {
                        if (response != null && ((OnResponse)response).hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(((OnResponse)response).getMessage());
                            return;
                        }
                        if (view != null)
                            view.showProgress(false);
                        if (listener != null)
                            listener.onNext(response);

                        if (view != null) {
                            if (response != null && (((Response) response).isSuccess())) {
                                view.success((Response) response);
                            } else {
                                String[] message = OnResponse.getMessage(context, null, (Response) response);
                                view.showPopup(message[0], message[1]);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.showProgress(false);
                        if (listener != null)
                            listener.onError(e);

                        if (view != null) {
                            String[] message = OnResponse.getMessage(context, e, null);
                            view.showPopup(message[0], message[1]);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public final static <T> void executeApi(Observable<T> observable, final ExecuteListener<T> listener) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T response) {

                        if (response != null && ((OnResponse)response).hasAuthenticationError()){
                            AuthenticationUtils.Companion.show(((OnResponse)response).getMessage());
                            return;
                        }
                        listener.onNext(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
