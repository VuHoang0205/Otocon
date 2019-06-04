package org.atmarkcafe.otocon.function.beacon;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.dialog.MessageDialog;

public class GpsManager {

    public static final String permission = Manifest.permission.ACCESS_FINE_LOCATION;

    public static final int REQUEST_PERMISSTION_GPS = 1101;
    public static final int REQUEST_CHECK_LOCATION_SETTINGS = 1102;

    private GpsManagerListener listener;

    FusedLocationProviderClient mLocationProviderClient;
    LocationCallback mLocationCallback;
    LocationRequest mLocationRequest = LocationRequest.create()
            .setInterval(10000)
            .setFastestInterval(5000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    public GpsManager(GpsManagerListener listener) {
        this.listener = listener;
    }

    public void requestFollow(final FragmentActivity activity) {
        if (!isGranted(activity)) {
            // not granted perms
            listener.onDenied();
            MessageDialog dialog = new MessageDialog(activity, new MessageDialog.PopupMessageErrorListener() {
                @Override
                public void onPressed(boolean ok) {
                    if (ok) {
                        ExtensionActivity.openAppSettings(activity);
                    }
                }
            });
            dialog.set(activity.getString(R.string.gps_user_denied_title), activity.getString(R.string.gps_user_denied_message));
            dialog.setButtonOk(activity.getString(R.string.ok_jp));
            dialog.show();
        } else {
            // granted perms -> setting location
            mLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
            checkLocationSettings(activity);
        }
    }

    private void checkLocationSettings(Activity activity) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(activity, locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            listener.onOpenGps(true);
        });

        task.addOnFailureListener(activity, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                MessageDialog dialog = new MessageDialog(activity, ok -> {
                    if (ok) {
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            if (activity instanceof ExtensionActivity) {
                                ((ExtensionActivity)activity).setGpsManagerListener(listener);
                            } else {
                                Log.e(">>>>>>>>>>>>>>>", "Failed");
                            }
                            resolvable.startResolutionForResult(activity, REQUEST_CHECK_LOCATION_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.

                        }
                    } else {
                        listener.onOpenGps(false);
                    }
                });
                dialog.setButtonOk(activity.getString(R.string.ok_jp));
                dialog.show();
            } else {
                listener.onOpenGps(false);
            }
        });
    }


    public interface GpsManagerListener {
        void onRequestPermissionSuccess(boolean isGraned);

        void onDenied();

        void onOpenGps(boolean open);
    }


    public interface LocationCallbackListener {
        void locationChange(double latitude, double longitude);
    }

    private LocationCallbackListener locationCallbackListener;

    public void addLocationCallbackListener(LocationCallbackListener locationCallbackListener) {
        this.locationCallbackListener = locationCallbackListener;
    }

    public void removeLocationCallbackListener() {
        locationCallbackListener = null;
    }

    public void requestLocation(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        mLocationProviderClient.removeLocationUpdates(mLocationCallback);

                        if (locationCallbackListener != null) {
                            locationCallbackListener.locationChange(location.getLatitude(), location.getLongitude());
                        }
                    }
                }
            };

            mLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
    }


    public boolean isCalled(FragmentActivity activity) {
        return activity.getSharedPreferences(permission, 0).getBoolean(permission, false);
    }

    public boolean isGranted(FragmentActivity activity) {
        return checkSelfPermission(activity) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean shouldShowRequestPermissionRationale(FragmentActivity activity) {
        return activity.shouldShowRequestPermissionRationale(permission);
    }

    private int checkSelfPermission(FragmentActivity activity) {
        return activity.checkSelfPermission(permission);
    }

    public boolean isDenied(FragmentActivity activity) {
        return activity.shouldShowRequestPermissionRationale(permission) && !isGranted(activity);
    }

    public void request(final FragmentActivity activity) {
        activity.getSharedPreferences(permission, 0).edit().putBoolean(permission, true).commit();

        if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            // denied
            if (activity.shouldShowRequestPermissionRationale(permission)) {
                listener.onDenied();
            } else {
                activity.requestPermissions(new String[]{permission, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSTION_GPS);
            }
        } else {
            listener.onRequestPermissionSuccess(true);
        }
    }

    public void request(final Fragment fragment) {
        fragment.getActivity().getSharedPreferences(permission, 0).edit().putBoolean(permission, true).commit();

        if (fragment.getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            // denied
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                listener.onDenied();
            } else {
                fragment.requestPermissions(new String[]{permission, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSTION_GPS);
            }
        } else {
            listener.onRequestPermissionSuccess(true);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_PERMISSTION_GPS) {
            listener.onRequestPermissionSuccess(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }
}