package com.example.notificationsampleapp.services;

import static com.example.notificationsampleapp.MyApplication.CHANEL_3_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.notificationsampleapp.R;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // return super.onStartCommand(intent, flags, startId);
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANEL_3_ID)
                .setContentTitle("My Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.daisy)
                .setContentIntent(pendingIntent)
                .build();
         startForeground(1, notification);

        //stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
