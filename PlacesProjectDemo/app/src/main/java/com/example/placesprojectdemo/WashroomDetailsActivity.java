package com.example.placesprojectdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

public class WashroomDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washroom_details);

        // Get the Washroom object from Intent
        Washroom washroom = (Washroom) getIntent().getSerializableExtra("washroom");

        // Find TextViews and ImageView in the layout
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewAddress = findViewById(R.id.textViewAddress);
        TextView textViewLatitude = findViewById(R.id.textViewLatitude);
        TextView textViewLongitude = findViewById(R.id.textViewLongitude);
        TextView textViewRating = findViewById(R.id.textViewRating);
        TextView textViewOpenNow = findViewById(R.id.textViewOpenNow);
        LinearLayout photoLayout = findViewById(R.id.photoLayout);

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
                textViewAddress.setVisibility(View.GONE);
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

            // Set open now status
            Boolean isOpenNow = washroom.isOpenNow();
            if (isOpenNow != null) {
                textViewOpenNow.setText(isOpenNow ? "Open Now" : "Closed");
            } else {
                textViewOpenNow.setVisibility(View.GONE);
            }

            // Display photos if available
            displayPhotos(photoLayout, washroom.getPhotoReferences());
        } else {
            Toast.makeText(this, "Washroom details not available", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void displayPhotos(LinearLayout photoLayout, List<String> photoReferences) {
        for (String photoReference : photoReferences) {
            ImageView imageView = new ImageView(this);
            // Use Picasso to load images from URL
            Picasso.get().load(getPhotoUrl(photoReference)).into(imageView);
            // Set layout parameters as needed
            photoLayout.addView(imageView);
        }
    }

    // Method to construct the URL for the photo
    private String getPhotoUrl(String photoReference) {
        int maxWidth = 800; // Set your desired max width
        return "https://maps.googleapis.com/maps/api/place/photo?" +
                "maxwidth=" + maxWidth +
                "&photoreference=" + photoReference +
                "&key=" + getResources().getString(R.string.google_maps_key);
    }

    public void onBackButtonClick(View view) {
        finish();
    }
}
