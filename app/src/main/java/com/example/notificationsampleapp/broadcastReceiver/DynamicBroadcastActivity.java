package com.example.notificationsampleapp.broadcastReceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.notificationsampleapp.R;

public class DynamicBroadcastActivity extends AppCompatActivity {

    DynamicBroadcastReceiver broadcastReceiver = new DynamicBroadcastReceiver();

    //When we register app in onStart and unregister it in onDestroy -> app won't receive broadcasts when in background
    // if we want to receive broadcasts in background as well register in OnCreate() and unregister in onDestroy().

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_broadcast);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Define an intent filter
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        //register receiver
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregister receiver
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}