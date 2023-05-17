package com.cybercats.fitbharat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;

public class bmi extends AppCompatActivity {
    private Button button;
    private EditText height;
    private EditText weight;
    private TextView result;

    // BMI chart button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        getSupportActionBar().setTitle("BMI Calculator");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.btnbmichart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openbmichart();
            }

            public void openbmichart() {
                Intent i = new Intent(bmi.this,bmichart.class);
                startActivity(i);
            }
        });

        height=(EditText) findViewById(R.id.height);
        weight=(EditText) findViewById(R.id.weight);
        result=(TextView) findViewById(R.id.result);
    }

    public String calculateBMI(View V) {
        String h = height.getText().toString();
        String w = weight.getText().toString();
        String label = null;
        if (h != null && !"".equals(h) && w != null && !"".equals(w)) {
            float hf = Float.parseFloat(h);
            float wf = Float.parseFloat(w);
            float bmi = (wf / (hf * hf)) * 10000;

            if (bmi <= 18.5) {
                label = getString(R.string.under);
            } else if (bmi < 24.9) {
                label = getString(R.string.normal);
            } else if (bmi < 29.9) {
                label = getString(R.string.over);
            } else if (bmi < 34.9) {
                label = getString(R.string.obease);
            } else {
                label = getString(R.string.exobease);
            }
            result.setText(bmi+"\n\n"+label);

        }
        return label;
    }
}

