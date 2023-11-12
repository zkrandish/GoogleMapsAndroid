package com.example.projectandroidstructure;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //2- declare the object that manipulate the widget
    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //3- to reference/find the widget

        tvHello=findViewById(R.id.tvHello);
        //4-manipulate the widget
        tvHello.setTextSize(30);
        tvHello.setTextColor(Color.RED);
        tvHello.setBackgroundColor(Color.WHITE);
    }
}