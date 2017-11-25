package hu.bme.mhorvath.newsapp.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import hu.bme.mhorvath.newsapp.MainActivity;
import hu.bme.mhorvath.newsapp.R;

public class Receiver extends BroadcastReceiver {

    private static final String ID = "my_channel_01";
    private static final String CHANNEL_NAME = ID;
    private static final String CHANNEL_DESCRIPTION = ID;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = initializeChannel(context);
        sendNotification(context, nm);
    }

    private NotificationManager initializeChannel(Context context) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESCRIPTION);
            nm.createNotificationChannel(channel);

        }

        return nm;

    }

    private void sendNotification(Context context, NotificationManager nm) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, ID)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(context.getString(R.string.notif_title))
                        .setContentText(context.getString(R.string.notif_text));

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        nm.notify(3, builder.build());

    }

}
