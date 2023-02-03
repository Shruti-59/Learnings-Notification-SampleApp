package com.example.notificationsampleapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {

    public static final String CHANEL_1_ID = "chanel1";
    public static final String CHANEL_2_ID = "chanel2";
    public static final String CHANEL_3_ID = "chanel3";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel chanel1 = new NotificationChannel(CHANEL_1_ID,
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            chanel1.setDescription("This is channel 1");

            //This description defines the channel usuage
            NotificationChannel chanel2 = new NotificationChannel(CHANEL_2_ID,
                    "Channel 2", NotificationManager.IMPORTANCE_LOW);
            chanel2.setDescription("This is channel 2");

            //Notification channel for Services
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANEL_3_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chanel1);
            manager.createNotificationChannel(chanel2);
            manager.createNotificationChannel(serviceChannel);


        }
    }
}
