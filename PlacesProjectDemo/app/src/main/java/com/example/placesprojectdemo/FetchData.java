package com.example.placesprojectdemo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FetchData extends AsyncTask<Object, String, String> {
    private String googleNearByPlacesData;
    private GoogleMap googleMap;
    private Context context;

    // Constructor to receive the context
    public FetchData(Context context) {
        this.context = context;
    }

    public FetchData() {

    }

    @Override
    protected String doInBackground(Object... objects) {
        try {
            googleMap = (GoogleMap) objects[0];
            String url = (String) objects[1];
            DownloadUrl downLoadUrl = new DownloadUrl();
            googleNearByPlacesData = downLoadUrl.retireveUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleNearByPlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            // Iterate through the results and add markers
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject getLocation = jsonObject1.getJSONObject("geometry")
                        .getJSONObject("location");
                String lat = getLocation.getString("lat");
                String lng = getLocation.getString("lng");
                JSONObject getName = jsonArray.getJSONObject(i);
                String name = getName.getString("name");
                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                // Check if the user clicked on a marker
                googleMap.setOnMarkerClickListener(marker -> {
                    // Assuming your marker's title is the place name
                    String selectedPlaceName = marker.getTitle();

                    // Query the Firebase database for the selected place
                    queryFirebaseDatabase(selectedPlaceName);

                    return false;
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void queryFirebaseDatabase(String selectedPlaceName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("washrooms");
        databaseReference.orderByChild("name").equalTo(selectedPlaceName)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Retrieve details and navigate to details activity
                            for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                                // Retrieve only the name
                                String selectedPlaceName = datasnapshot.child("name").getValue(String.class);

                                if (selectedPlaceName != null) {
                                    Log.d("FetchData", "Selected Place Name: " + selectedPlaceName);

                                    // Start the details activity and pass the selectedPlaceName
                                    if (context != null) {
                                        // Start the details activity and pass the selectedPlaceName
                                        Intent intent = new Intent(context, WashroomDetailsActivity.class);
                                        intent.putExtra("selectedPlaceName", selectedPlaceName);
                                        context.startActivity(intent);
                                    } else {
                                        Log.e("FetchData", "Context is null");
                                    }
                                }
                            }
                        } else {
                            // Handle the case where the selected place is not found in the database
                            Toast.makeText(context, "Place details not found", Toast.LENGTH_SHORT).show();
                        }
                    }


//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        // Check if the selected place exists in the database
//                        if (snapshot.exists()) {
//                            // Retrieve details and navigate to details activity
//                            for (DataSnapshot datasnapshot : snapshot.getChildren()) {
//                                // Retrieve only the name
//                                String selectedPlaceName = datasnapshot.child("name").getValue(String.class);
//
//                                if (selectedPlaceName != null) {
//                                    // Start the details activity and pass the selectedPlaceName
//                                    Intent intent = new Intent(context, WashroomDetailsActivity.class);
//                                    intent.putExtra("selectedPlaceName", selectedPlaceName);
//                                    context.startActivity(intent);
//                                }
//                            }
//                        } else {
//                            // Handle the case where the selected place is not found in the database
//                            Toast.makeText(context, "Place details not found", Toast.LENGTH_SHORT).show();
//                        }
//                    }



//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Log.d("FetchData", "onDataChange: Snapshot exists - " + snapshot.exists());
//                        // Check if the selected place exists in the database
//                        if (snapshot.exists()) {
//                            // Retrieve details and navigate to details activity
//                            for (DataSnapshot datasnapshot : snapshot.getChildren()) {
//                                Washroom washroom = datasnapshot.getValue(Washroom.class);
//                                if (washroom != null) {
//                                    Log.d("FetchData", "Washroom details: " + washroom.toString());
//                                    // Start the details activity and pass the washroom details
//                                    Intent intent = new Intent(context, WashroomDetailsActivity.class);
//                                    intent.putExtra("washroom", washroom);
//                                    context.startActivity(intent);
//                                }
//                            }
//                        } else {
//                            // Handle the case where the selected place is not found in the database
//                            Log.d("FetchData", "onDataChange: Place details not found in the database");
//                            Toast.makeText(context, "Place details not found", Toast.LENGTH_SHORT).show();
//                        }
//                    }


//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        // Check if the selected place exists in the database
//                        if (snapshot.exists()) {
//                            // Retrieve details and navigate to details activity
//                            for (DataSnapshot datasnapshot : snapshot.getChildren()) {
//                                Washroom washroom = snapshot.getValue(Washroom.class);
//                                if (washroom != null) {
//                                    // Start the details activity and pass the washroom details
//                                    Intent intent = new Intent(context, WashroomDetailsActivity.class);
//                                    intent.putExtra("washroom", washroom);
//                                    context.startActivity(intent);
//                                }
//                            }
//                        } else {
//                            // Handle the case where the selected place is not found in the database
//                            Toast.makeText(context, "Place details not found", Toast.LENGTH_SHORT).show();
//                        }
//                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                        Log.e("FetchData", "Database error: " + error.getMessage());
                    }


                });
    }
}
