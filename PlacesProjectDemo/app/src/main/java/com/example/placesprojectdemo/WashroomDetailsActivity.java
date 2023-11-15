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

        // Retrieve the Washroom object from the Intent
        Washroom washroom = getIntent().getParcelableExtra("washroom");

        // Now you can use the details of the washroom as needed
        if (washroom != null) {
            // Example: Display washroom details in a TextView
            TextView textView = findViewById(R.id.textView);
            textView.setText("Washroom Name: " + washroom.getName());
        } else {
            // Handle the case where the washroom object is null
            Toast.makeText(this, "Washroom details not available", Toast.LENGTH_SHORT).show();
            //finish(); // Close the activity if no washroom details are available
        }
    }
}