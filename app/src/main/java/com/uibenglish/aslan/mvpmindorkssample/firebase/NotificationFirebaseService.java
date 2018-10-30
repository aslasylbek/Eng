package com.uibenglish.aslan.mvpmindorkssample.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.Main2Activity;
import com.uibenglish.aslan.mvpmindorkssample.ui.splash.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Response;

public class NotificationFirebaseService extends FirebaseMessagingService {

    private static final String TAG = "asd";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());



        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
                Log.d(TAG, "onMessageReceived: "+remoteMessage.getNotification().getIcon());
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

        }

        /*Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        String cappello = remoteMessage.getNotification().getBody();
        Intent intent = new Intent();
        intent.putExtra("extra", cappello);
        intent.setAction("com.example.aslan.mvpmindorkssample.onMessageReceived");
        sendBroadcast(intent);*/

        showNewContentNotification(remoteMessage);


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public void showNewContentNotification(RemoteMessage remoteMessage) {
        /***
         * ToSet ViewPager Position
         */
        String CHANNEL_ONE_NAME = "Channel One";
        String CHANNEL_ID = "uibenglish.kz";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_LOW);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, Main2Activity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_stat_logo)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 3000, 3000)
                .setSound(defaultSoundUri)
                .setWhen(System.currentTimeMillis())
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(12345, notification1);
    }
    public void handleNow(){

    }
    public void scheduleJob(){

    }
}
