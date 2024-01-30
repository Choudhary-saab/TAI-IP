package com.madhur.todolist.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.madhur.todolist.R;

public class NotificationHelper {

    public static void showNotification(Context context, String title, String content) {
        createNotificationChannel(context);
        NotificationChannel channel = new NotificationChannel("12345", "jatt", NotificationManager.IMPORTANCE_DEFAULT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "12345")
                .setContentTitle("Notification Title")
                .setContentText("Notification Text")
                .setSmallIcon(R.drawable.ic_notification);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(12345, builder.build());
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "jatt";
            String description = "Hlo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("12345", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

