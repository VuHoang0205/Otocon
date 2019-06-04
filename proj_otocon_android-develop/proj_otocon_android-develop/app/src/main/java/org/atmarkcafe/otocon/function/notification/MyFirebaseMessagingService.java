package org.atmarkcafe.otocon.function.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.atmarkcafe.otocon.AddDeviceNoLoginPresenter;

import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;
import org.atmarkcafe.otocon.R;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService implements KeyExtensionUtils {

    @Override
    public void onNewToken(String device_token) {
        super.onNewToken(device_token);
        // Add device tooken
        new AddDeviceNoLoginPresenter(null).onExecute(this, 0, device_token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle(); //title message  
            String content = remoteMessage.getNotification().getBody(); // body message

            String appName = getString(R.string.app_name); // name application
            String chanelId = getString(R.string.default_notification_channel_id); // Chanel id
            // Push notification
            sendNotification(this, title, content, remoteMessage.getData(), chanelId, appName, ExtensionActivity.class, R.drawable.ic_push_notification);
        }
    }

    /**
     * @param context
     * @param title
     * @param messgage
     * @param channelId
     * @param appname
     * @param aClass
     */
    public static void sendNotification(Context context, String title, String messgage, Map<String, String> data, String channelId, String appname, Class aClass, int logo) {
        Intent intent = new Intent(context, aClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(KEY_TITLE, title == null ? "" : title);
        intent.putExtra(KEY_CONTENT, messgage == null ? "" : messgage);
        for (String key : data.keySet()) {
            String value = data.get(key);
            intent.putExtra(key, value);
        }


        PushType type = PushType.factory(data.containsKey(KEY_TYPE) ? data.get(KEY_TYPE) : "");
        if (type == PushType.rematchListRequest || type == PushType.rematchTopPage){
            context.sendBroadcast(new Intent(ACTION_NOTI_MY_REMATCH));
        }

        int id = new Random().nextInt(1000) + 1;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, appname, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.BigTextStyle bigxtstyle = new NotificationCompat.BigTextStyle();

        bigxtstyle.bigText(messgage);
        bigxtstyle.setBigContentTitle(title);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(logo)

                        .setStyle(bigxtstyle)
                        .setContentTitle(title)
                        .setContentText(messgage)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
    }
}
