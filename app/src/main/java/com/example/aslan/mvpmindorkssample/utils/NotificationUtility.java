package com.example.aslan.mvpmindorkssample.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.app.NotificationCompat.MediaStyle;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.audio.AudioPlayService;
import com.example.aslan.mvpmindorkssample.ui.listening.ListeningActivity;
import com.example.aslan.mvpmindorkssample.ui.tasks.TaskChoiceActivity;

/**
 * Created by MAO on 7/28/2017.
 */

public class NotificationUtility {

    private static final String[] PROJECTION = {
            "Listening part",
            "Listen and answer the question"
    };

    private static final int TITLE_INDEX = 0;
    private static final int DESCRIPTION_INDEX = 1;

    private static final String FOREGROUND_CHANNEL_ID = "channel_id_0";
    private static final String NOTIFICATION_CHANNEL_ID = "channel_id_1";

    @Nullable
    public static Notification buildAudioServiceNotification(Context context,
                                                             Uri uriWithTimeStamp,
                                                             String action) {

        /*Cursor queryCursor = context.getContentResolver().query(uriWithTimeStamp,
                PROJECTION,
                null,
                null,
                null);

        if (queryCursor == null || !queryCursor.moveToFirst()) return null;
        String title = queryCursor.getString(TITLE_INDEX);
        String description = queryCursor.getString(DESCRIPTION_INDEX);
        queryCursor.close();*/

        String title = PROJECTION[0];
        String description = PROJECTION[1];


        Intent intentActivity = new Intent(context, ListeningActivity.class)
                .setData(uriWithTimeStamp);
        PendingIntent pendingIntentActivity =
                PendingIntent.getActivity(context, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, FOREGROUND_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setShowWhen(false)
                .setContentTitle(title)
                .setContentText(description)
                .setContentIntent(pendingIntentActivity)
                .addAction(createAction(context, AudioPlayService.ACTION_REPLAY))
                .addAction(createAction(context, action))
                .addAction(createAction(context, AudioPlayService.ACTION_FORWARD))
                .setSmallIcon(R.drawable.ic_headset)
                .setStyle(new MediaStyle().setShowActionsInCompactView(0, 1, 2));

        return notificationBuilder.build();
    }

    private static int getDrawable(String action) {
        switch (action) {
            case AudioPlayService.ACTION_PLAY:
                return R.drawable.ic_play_arrow;
            case AudioPlayService.ACTION_PAUSE:
                return R.drawable.ic_pause;
            case AudioPlayService.ACTION_FORWARD:
                return R.drawable.ic_forward_5;
            case AudioPlayService.ACTION_REPLAY:
                return R.drawable.ic_replay_5;
            default:
                return R.drawable.ic_play_arrow;
        }
    }

    @NonNull
    private static String getActionName(Context context, String action) {
        switch (action) {
            case AudioPlayService.ACTION_PLAY:
                return context.getString(R.string.play);
            case AudioPlayService.ACTION_PAUSE:
                return context.getString(R.string.pause);
            case AudioPlayService.ACTION_FORWARD:
                return context.getString(R.string.forward);
            case AudioPlayService.ACTION_REPLAY:
                return context.getString(R.string.replay);
            default:
                return context.getString(R.string.play);
        }
    }

    private static NotificationCompat.Action createAction(Context context, String action) {
        Intent intentService = new Intent(context, AudioPlayService.class)
                .setAction(action);
        PendingIntent pendingIntentService = PendingIntent.getService(context, 0, intentService, 0);
        return new NotificationCompat.Action(
                getDrawable(action), getActionName(context, action), pendingIntentService);
    }

    public static void showNewContentNotification(Context context, String category, String contentText) {
        String contentTitle = context.getString(R.string.notification_new_content) + " "
                + context.getString(R.string.app_name);
        /***
         * ToSet ViewPager Position
         */
        Intent intent = new Intent(context, ListeningActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("A", category);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_headset)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(12345, notification);
    }

}
