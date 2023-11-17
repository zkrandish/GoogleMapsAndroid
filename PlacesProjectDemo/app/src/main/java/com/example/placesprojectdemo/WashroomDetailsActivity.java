package com.example.placesprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
        TextView textViewRating = findViewById(R.id.textViewRating);

        // Set values to TextViews
        if (washroom != null) {
            // Set the name
            String name = washroom.getName();
            textViewName.setText(name);

            // Set the address
            String address = washroom.getAddress();
            if (address != null && !address.isEmpty()) {
                textViewAddress.setText(address);
            } else {
                textViewAddress.setVisibility(View.GONE);  // Hide the TextView if address is empty
            }

            // Set the latitude
            double latitude = washroom.getLatitude();
            textViewLatitude.setText(String.valueOf(latitude));

            // Set the longitude
            double longitude = washroom.getLongitude();
            textViewLongitude.setText(String.valueOf(longitude));

            // Set the rating
            float rating = washroom.getRating();
            textViewRating.setText(String.valueOf(rating));
        } else {
            // Handle the case when washroom object is null
            Toast.makeText(this, "Washroom details not available", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity if washroom details are not available
        }
    }
}