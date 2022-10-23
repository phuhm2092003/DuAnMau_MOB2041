package fpt.edu.projectlibmanna.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

import fpt.edu.projectlibmanna.R;

public class MyNotification {

    public static final String NOTIFICATION_ID = "libmana.notification";
    public static void checkSDK(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID, "Libmana", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager  = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    public static void getNotification(Context context, String strNotification){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_ID);
        builder.setContentTitle("Libmana");
        builder.setContentText(strNotification);
        builder.setColor(context.getResources().getColor(R.color.red));
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        int numberNotifi = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        managerCompat.notify(numberNotifi, builder.build());
    }
}
