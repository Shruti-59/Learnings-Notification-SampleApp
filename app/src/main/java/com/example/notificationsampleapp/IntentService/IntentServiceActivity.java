package com.example.notificationsampleapp.IntentService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.notificationsampleapp.R;

public class IntentServiceActivity extends AppCompatActivity {

    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        editTextInput = findViewById(R.id.edit_text_input);
    }
    public void startService(View v) {
        String input = editTextInput.getText().toString();

        Intent serviceIntent = new Intent(this, MyIntentService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    }
}