package org.atmarkcafe.otocon;

import android.content.Context;

import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.parameter.AddDeviceParams;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.pref.BaseShareReferences;

public class AddDeviceNoLoginPresenter extends MVPPresenter<String, OnResponse> implements MVPPresenter.ExecuteListener<OnResponse> {
    public AddDeviceNoLoginPresenter(MVPExtension.View<OnResponse> view) {
        super(view);
    }

    public static final String TAG = "AddDeviceNoLoginPresenter";

    public static final String KEY_DEVICE_TOKKEN = "KEY_DEVICE_TOKKEN";

    @Override
    public void onExecute(Context context, int action, String device_tooken) {
        // save device tooken to db
        if (device_tooken != null && !device_tooken.isEmpty()) {
            BaseShareReferences references = new BaseShareReferences(context);
            references.set(KEY_DEVICE_TOKKEN, device_tooken);
        }

        new Thread(() -> {
            AddDeviceParams params = new AddDeviceParams(context);

            if (DBManager.isLogin(context)) {
                AddDeviceNoLoginPresenter.this.execute(InteractorManager.getApiInterface(context).addDeviceLogged(params), AddDeviceNoLoginPresenter.this);
            } else {
                AddDeviceNoLoginPresenter.this.execute(InteractorManager.getApiInterface(context).addDeviceNoLogin(params), AddDeviceNoLoginPresenter.this);
            }
        }).start();
    }

    @Override
    public void onNext(OnResponse response) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
