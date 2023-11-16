package com.example.placesprojectdemo;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import java.util.List;
import java.util.Locale;

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

    private LatLng getLatLngFromPlace(JSONObject placeObject) throws JSONException {
        JSONObject location = placeObject.getJSONObject("geometry").getJSONObject("location");
        double lat = location.getDouble("lat");
        double lng = location.getDouble("lng");
        return new LatLng(lat, lng);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            // Iterate through the results and add markers
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject placeObject = jsonArray.getJSONObject(i);
                LatLng latLng = getLatLngFromPlace(placeObject);

                String name = placeObject.getString("name");

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }

            // Set marker click listener
            googleMap.setOnMarkerClickListener(marker -> {
                // Assuming your marker's title is the place name
                String selectedPlaceName = marker.getTitle();

                // Get the LatLng object from the marker
                LatLng selectedPlaceLatLng = marker.getPosition();


                // Query the Firebase database for the selected place
                queryFirebaseDatabase(selectedPlaceName, selectedPlaceLatLng);

                return true;
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void queryFirebaseDatabase(String selectedPlaceName, LatLng selectedPlaceLatLng) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("washrooms");
        databaseReference.orderByChild("name").equalTo(selectedPlaceName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Get the first matching Washroom object (assuming unique names)
                            DataSnapshot firstChild = snapshot.getChildren().iterator().next();
                            Washroom washroom = firstChild.getValue(Washroom.class);

                            // Start the details activity and pass the washroom details
                            if (washroom != null) {
                                String address = getAddressFromLatLng(context, selectedPlaceLatLng);
                                // Update the washroom object with the address, latitude, and longitude
                                washroom.setAddress(address);
                                washroom.setLatitude(selectedPlaceLatLng.latitude);
                                washroom.setLongitude(selectedPlaceLatLng.longitude);

                                // Save the washroom data to Firebase
                                addWashroomToFirebase(washroom);

                                Intent intent = new Intent(context, WashroomDetailsActivity.class);
                                intent.putExtra("washroom", washroom);
                                context.startActivity(intent);
                            }
                        } else {
                            Toast.makeText(context, "Place details not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FetchData", "Database error: " + error.getMessage());
                    }
                });
    }

    private String getAddressFromLatLng(Context context, LatLng latLng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                Log.d("FetchData", "Selected Place Address: " + address.getAddressLine(0));
                return address.getAddressLine(0); // You can modify this to get more details if needed

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Address not found";
    }

    // Inside the method that adds a washroom to Firebase
    private void addWashroomToFirebase(Washroom washroom) {
        DatabaseReference washroomsRef = FirebaseDatabase.getInstance().getReference("washrooms");
        String washroomId = washroomsRef.push().getKey(); // Generate a unique key for the washroom
        washroomsRef.child(washroomId).setValue(washroom);
    }

}