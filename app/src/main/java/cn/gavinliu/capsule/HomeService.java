package cn.gavinliu.capsule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Gavin on 17-5-10.
 */

public class HomeService extends Service {

    private static final String TAG = HomeService.class.getSimpleName();

    HomeWatcher mHomeWatcher = null;

    NotificationManager mNotificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomeWatcher.stopWatch();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("xx")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("XX")
                .setContentIntent(contentIntent)
                .build();

        startForeground(1, notification);
        return START_STICKY;
    }
}
