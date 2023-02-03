package com.example.notificationsampleapp.JobIntentService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.notificationsampleapp.R;

public class JobIntentServiceActivity extends AppCompatActivity {

    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_intent_service);

        editTextInput = findViewById(R.id.edit_text_input);
    }

    public void enqueueWork(View v) {
        String input = editTextInput.getText().toString();

        Intent serviceIntent = new Intent(this, JobIntentService.class);
        serviceIntent.putExtra("inputExtra", input);

        JobIntentService.enqueueWork(this, serviceIntent);
    }
}