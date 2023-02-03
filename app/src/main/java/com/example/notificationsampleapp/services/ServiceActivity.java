package com.example.notificationsampleapp.services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.notificationsampleapp.R;

public class ServiceActivity extends AppCompatActivity {

    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        editTextInput = findViewById(R.id.edit_text_input);
    }

    public void startService(View view){
        String input = editTextInput.getText().toString();
        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService(View view){
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
    }
}