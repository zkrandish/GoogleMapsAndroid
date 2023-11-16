package com.example.placesprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class WashroomDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washroom_details);

        // Get the Washroom object from Intent
        Washroom washroom = (Washroom) getIntent().getSerializableExtra("washroom");

        // Find TextViews in the layout
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewAddress = findViewById(R.id.textViewAddress);
        TextView textViewLatitude = findViewById(R.id.textViewLatitude);
        TextView textViewLongitude = findViewById(R.id.textViewLongitude);

        // Set values to TextViews
        if (washroom != null) {
            textViewName.setText(washroom.getName());
            textViewAddress.setText(washroom.getAddress());
            textViewLatitude.setText(String.valueOf(washroom.getLatitude()));
            textViewLongitude.setText(String.valueOf(washroom.getLongitude()));
        }
    }
}