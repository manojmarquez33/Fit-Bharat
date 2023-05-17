package com.cybercats.fitbharat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class bmichart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmichart);

        getSupportActionBar().setTitle("BMI chart");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

    }
}