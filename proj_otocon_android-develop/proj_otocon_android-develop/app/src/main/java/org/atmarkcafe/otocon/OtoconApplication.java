package org.atmarkcafe.otocon;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;

public class OtoconApplication extends Application {

    public static OtoconApplication getInstance(Context context) {
        return (OtoconApplication) context.getApplicationContext();
    }

    public static OtoconBluetoothManager getBluetoothManager(Context context) {
        return getInstance(context).bluetoothManager;
    }

    private OtoconBluetoothManager bluetoothManager = new OtoconBluetoothManager();

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }


    /**
     * Bluetooth
     */
    public static class OtoconBluetoothManager {

        public boolean isEnabled(Context context) {
            return isSupportBluetooth(context) && getAdapter(context).isEnabled();
        }

        public boolean isSupportBluetooth(Context context) {
            return getAdapter(context ) != null;
        }

        public enum BluetoothCommand {
            start, pause, end, no_support, bluetooth_enable, device_change;
            public static final String KEY = "Command";
        }

        public interface BluetoohServiceListener {
            public void onBluettooth(BluetoothCommand command);
        }

        private List<BluetoohServiceListener> listeners = new ArrayList<>();

        public void addBluetoohServiceListener(BluetoohServiceListener listener) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }

        public void removeAll() {
            listeners.clear();
        }

        public int startBluetoothSetting(Activity activity) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST);
            return REQUEST;
        }


        public void removeBluetoohServiceListener(BluetoohServiceListener listener) {
            listeners.remove(listener);
        }

        private void send(BluetoothCommand command, Object o) {
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onBluettooth(command);
            }
        }

        private void stopScan(Context context, long time) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getAdapter(context) != null) {
                        scaning = false;
                        getAdapter(context).stopLeScan(mLeScanCallback);
                    }
                }
            }, time);
        }

        public final static int REQUEST = 1019;

        // Stops scanning after a pre-defined scan period.
        public static final long TIME = 10000;

//        public int requestOpenBluetooth(final AppCompatActivity activity) {
//            startBluetoothSetting(activity);
//
//            MessageDialog dialog = new MessageDialog(activity, new MessageDialog.PopupMessageErrorListener() {
//                @Override
//                public void onPressed(boolean ok) {
//                    if (ok) {
//                        startBluetoothSetting(activity);
//                    } else {
//                        // TODO
//                    }
//                }
//            });
//            dialog.set(activity.getString(R.string.app_name), activity.getString(R.string.bluetooth_disable));
//            dialog.show();
//            return REQUEST;
//        }

        private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mLeDeviceListAdapter.addDevice(device);
//                            mLeDeviceListAdapter.notifyDataSetChanged();
//                        }
//                    });
            }
        };

        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        boolean isSupportBLE(Context context) {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        }

        public BluetoothAdapter getAdapter(Context context) {
            if (getBluetoothManager(context) != null) {
                return getBluetoothManager(context).getAdapter();
            }

            return null;
        }

        public BluetoothManager getBluetoothManager(Context context) {
            return (android.bluetooth.BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        }

        private boolean scaning = false;

        private void scan(Context context) {
            if (!scaning) {
                if (getAdapter(context) != null) {
                    scaning = true;
                    // TODO check deprecated
                    stopScan(context, TIME);
                    getAdapter(context).startLeScan(mLeScanCallback);
                }
            }
        }
    }
}