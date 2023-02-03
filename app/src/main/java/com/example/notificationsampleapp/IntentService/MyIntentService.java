package com.example.notificationsampleapp.IntentService;

import static com.example.notificationsampleapp.MyApplication.CHANEL_1_ID;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.notificationsampleapp.R;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate() {
        super.onCreate();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "NotificationSample:WakeLock");

        Log.d(TAG, "onCreate: WakeLock Acquired "+wakeLock);

        wakeLock.acquire();
        //wakeLock.acquire(10*60*1000L /*10 minutes*/);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Notification notification = new NotificationCompat.Builder(this, CHANEL_1_ID)
                    .setSmallIcon(R.drawable.daisy)
                    .setContentTitle("MyIntentService")
                    .setContentText("Running..")
                    .build();

            startForeground(1, notification);
        }
    }

    public MyIntentService() {
        super("MyIntentService");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG, "onHandleIntent: "+intent);
        String input = intent.getStringExtra("inputExtra");
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, input+" - "+i);
            SystemClock.sleep(1000);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: WakeLock Released "+wakeLock);
    }
}
