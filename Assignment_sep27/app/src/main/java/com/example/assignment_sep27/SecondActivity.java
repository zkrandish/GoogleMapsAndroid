package com.example.assignment_sep27;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    TextView  tvNameApp;
    EditText edTempCel,edTemfah;
    Button btnCalculate, btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialize();
    }
    private void initialize(){
        tvNameApp=findViewById(R.id.tvNameApp);
        edTempCel=findViewById(R.id.edTempCel);
        edTemfah= findViewById(R.id.edTemfah);
        btnCalculate=findViewById(R.id.btnCalculate);
        btnReturn=findViewById(R.id.btnReturn);
        btnCalculate.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        String name= getIntent().getStringExtra("name");
        tvNameApp.setText(name);
    }
    private void calculateTemperature(){
        String tempString= edTempCel.getText().toString();
        double tempCelsius= Double.parseDouble(tempString);
        double tempFahrenheit = (tempCelsius *9/5) +32;
        String tempFahrenheitString= String.valueOf(tempFahrenheit);
        edTemfah.setText(tempFahrenheitString);
    }

    @Override
    public void onClick(View view) {
        int calId= view.getId();
        if (calId==R.id.btnCalculate){
            calculateTemperature();
        }else finish();
    }
}