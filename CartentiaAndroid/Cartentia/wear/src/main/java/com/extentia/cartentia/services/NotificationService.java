package com.extentia.cartentia.services;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.extentia.cartentia.Constants;
import com.extentia.cartentia.R;
import com.extentia.cartentia.views.activities.DashboardWearActivity;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Extentia on 8/29/2015.
 */
public class NotificationService extends WearableListenerService {

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                if (Constants.NOTIFICATION_PATH.equals(dataEvent.getDataItem().getUri().getPath())) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(dataEvent.getDataItem());
                    String title = dataMapItem.getDataMap().getString(Constants.NOTIFICATION_TITLE);
                    String content = dataMapItem.getDataMap().getString(Constants.NOTIFICATION_CONTENT);
                    sendNotification(title, content);
                }
            }
        }
    }

    private static int a = 100;

    private void sendNotification(String title, String content) {

        // this intent will open the activity when the user taps the "open" action on the notification
        Intent viewIntent = new Intent(this, DashboardWearActivity.class);
        PendingIntent pendingViewIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

        // this intent will be sent when the user swipes the notification to dismiss it
        Intent dismissIntent = new Intent(Constants.ACTION_DISMISS);
        PendingIntent pendingDeleteIntent = PendingIntent.getService(this, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setDeleteIntent(pendingDeleteIntent)
                .setContentIntent(pendingViewIntent);

        Notification notification = builder.build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(a++, notification);
    }

}

