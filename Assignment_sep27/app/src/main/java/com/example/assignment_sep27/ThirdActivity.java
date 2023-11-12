package com.example.assignment_sep27;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvNameApp;
    EditText edMeter,edCentimeter,edKilometer;
    Button btnReturn,btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initialize();
    }
    private void initialize(){
        tvNameApp= findViewById(R.id.tvNameApp);
        edMeter=findViewById(R.id.edMeter);
        edCentimeter=findViewById(R.id.edCentimeter);
        edKilometer= findViewById(R.id.edKilometer);
        btnCalculate=findViewById(R.id.btnCalculate);
        btnReturn=findViewById(R.id.btnReturn);
        btnCalculate.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        String name= getIntent().getStringExtra("name");
        tvNameApp.setText(name);

    }
    private void calculateMetrix(){
        String meterString= edMeter.getText().toString();
        double meter= Double.parseDouble(meterString);
        double centimeter= meter*100;
        double kilometer= meter /1000;

        String centimeterString= String.valueOf(centimeter);
        String kilometerString= String.valueOf(kilometer);
        edCentimeter.setText(centimeterString);
        edKilometer.setText(kilometerString);

    }

    @Override
    public void onClick(View view) {
        int calId= view.getId();
        if (calId==R.id.btnCalculate){
            calculateMetrix();
        }else finish();

    }
}