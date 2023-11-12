package com.example.assignment_oct25;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
  TextView tvStatusData,tvBrandData,tvIdData;
    Button btnReturn;
    String id,brand,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialize();
    }
    public void initialize(){

        tvStatusData=findViewById(R.id.tvStatusData);
        tvBrandData=findViewById(R.id.tvBrandData);
        tvIdData=findViewById(R.id.tvIdData);
        btnReturn=findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);
        //id=getIntent().getStringExtra.
        brand=getIntent().getStringExtra("brands");
        status= getIntent().getStringExtra("status");
        id=getIntent().getStringExtra("id");
        tvStatusData.setText(status);
        tvBrandData.setText(brand);
        tvIdData.setText(id);

    }

    @Override
    public void onClick(View v) {

    }
}