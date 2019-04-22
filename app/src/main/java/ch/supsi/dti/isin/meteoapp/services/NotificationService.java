package ch.supsi.dti.isin.meteoapp.services;



import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import ch.supsi.dti.isin.meteoapp.model.Weather;


public class NotificationService extends IntentService {
    private static final String TAG = "TestService";
    private static Weather wea;

    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1); // min. is 1 minute!

    public NotificationService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NotificationService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn, Weather w) {
        Intent i = NotificationService.newIntent(context);
        wea = w;
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn)
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), POLL_INTERVAL_MS, pi);
        else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    private static int sI = 0;

    @Override
    protected void onHandleIntent(Intent intent) {
        if(wea.getTemperature()>30)
            sendNotification("temperature is > of 30");
        if(wea.getTemperature()<29)
            sendNotification("temperature is < of 10");
        Log.i(TAG, "Received an intent: " + intent);

    }

    private void sendNotification(String s) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "TEST_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Test Channel Description");
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Temperature is out of range")
                .setContentText(s)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        mNotificationManager.notify(0, mBuilder.build());
    }
}