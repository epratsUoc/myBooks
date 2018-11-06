package cat.enricprats.mybooks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Método llamado cuando se recibe un mensaje remoto
     *
     * @param remoteMessage Mensaje recibido de Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.w(TAG, "@@@@@Receiving message.");
        // Mostrar una notificación al recibir un mensaje de Firebase
        sendNotification(remoteMessage);
    }

    /**
     * Crea y muestra una notificación al recibir un mensaje de Firebase
     *
     * @param remoteMessage message recibido de firebase
     */
    private void sendNotification(RemoteMessage remoteMessage) {

        // Get the data from the message
        long bookId = Long.parseLong(remoteMessage.getData().get("book_item"));

        // Get the notification insede the message
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        // Create intents for the notification actions
        Intent deleteIntent = new Intent(Intent.ACTION_DELETE);
        deleteIntent.putExtra("bookId", bookId);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), deleteIntent, 0);

        Intent detailIntent = new Intent(Intent.ACTION_GET_CONTENT);
        detailIntent.putExtra("bookId", bookId);
        PendingIntent detailPendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), detailIntent, 0);

//        Intent intent = new Intent(this, BookItemListActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(android.R.drawable.alert_light_frame)
                        .setContentTitle(notification.getTitle())
                        .setContentText(notification.getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Estos son los detalles expandidos de la notificación anterior, aquí se puede escribir más texto para que lo lea el usuario."))
                        .addAction(new NotificationCompat.Action(android.R.drawable.ic_menu_delete, "Borrar", deletePendingIntent))
                        .addAction(new NotificationCompat.Action(android.R.drawable.ic_menu_info_details, "Detalle", detailPendingIntent));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}