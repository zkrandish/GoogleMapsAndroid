package com.example.drawroute;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import model.FetchURL;
import model.TaskLoadedCallback;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback {

    GoogleMap map;
    Button btngetDirection;
    MarkerOptions place1,place2;
    Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btngetDirection= findViewById(R.id.btnGetDirection);
        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
        place1= new MarkerOptions().position(new LatLng(27.658143,85.3199503)).title("Location 1");
        place2= new MarkerOptions().position(new LatLng(27.667491,85.3208583)).title("Location 2");

        btngetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url= getUrl(place1.getPosition(),place2.getPosition(),"driving");
                new FetchURL (MainActivity.this).execute(url,"driving");
            }
        });


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(place1);
        map.addMarker(place2);
    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}