package com.example.placesprojectdemo;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
import java.util.ArrayList;
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



    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            // Iterate through the results and add markers
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject placeObject = jsonArray.getJSONObject(i);

                LatLng selectedPlaceLatLng = getLatLngFromPlace(placeObject);
                String name = placeObject.getString("name");
                Boolean isOpenNow = getOpenNow(placeObject);
                List<String> photoReferences = getPhotoReferences(placeObject);
                float rating = getRating(placeObject);
                String address = getAddressFromLatLng(context, selectedPlaceLatLng);

                if (!photoReferences.isEmpty()) {
                    Log.e("PhotoReferences", photoReferences.get(0));
                } else {
                    Log.e("PhotoReferences", "No photo references available");
                }
                Log.e("IsOpenNow", String.valueOf(isOpenNow));


                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(selectedPlaceLatLng);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlaceLatLng, 15));


                googleMap.setOnMarkerClickListener(marker -> {
                    // Check if the washroom is in the database based on its name
                    checkWashroomInDatabase(name, address, selectedPlaceLatLng, rating, photoReferences, isOpenNow);
                    return true;
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Boolean getOpenNow(JSONObject placeObject) {
        try {
            if (placeObject.has("opening_hours") && placeObject.getJSONObject("opening_hours").has("open_now")) {
                return placeObject.getJSONObject("opening_hours").getBoolean("open_now");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getPhotoReferences(JSONObject placeObject) {
        List<String> photoReferences = new ArrayList<>();
        try {
            if (placeObject.has("photos")) {
                JSONArray photosArray = placeObject.getJSONArray("photos");
                for (int i = 0; i < photosArray.length(); i++) {
                    JSONObject photoObject = photosArray.getJSONObject(i);
                    if (photoObject.has("photo_reference")) {
                        String photoReference = photoObject.getString("photo_reference");
                        photoReferences.add(photoReference);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoReferences;
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

    private LatLng getLatLngFromPlace(JSONObject placeObject) throws JSONException {
        JSONObject location = placeObject.getJSONObject("geometry").getJSONObject("location");
        double lat = location.getDouble("lat");
        double lng = location.getDouble("lng");
        return new LatLng(lat, lng);
    }

    private float getRating(JSONObject placeObject) {
        if (placeObject.has("rating")) {
            try {
                return (float) placeObject.getDouble("rating");
            } catch (JSONException e) {
                Log.e("RatingError", e.getMessage());
                e.printStackTrace();
            }
        }
        return 0;
    }

    private void checkWashroomInDatabase(String selectedPlaceName, String address, LatLng selectedPlaceLatLng, float rating, List<String> photoReferences, Boolean isOpenNow) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("washrooms");
        databaseReference.orderByChild("name").equalTo(selectedPlaceName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Get the first matching Washroom object (assuming unique names)
                            DataSnapshot firstChild = snapshot.getChildren().iterator().next();
                            Washroom washroom = firstChild.getValue(Washroom.class);

                            // Start the details activity and pass the washroom details
                            if (washroom != null) {
                                // Update the washroom object with the address, latitude, and longitude
                                washroom.setName(selectedPlaceName);
                                washroom.setAddress(address);
                                washroom.setLatitude(selectedPlaceLatLng.latitude);
                                washroom.setLongitude(selectedPlaceLatLng.longitude);
                                washroom.setRating(rating);
                                washroom.setOpenNow(isOpenNow);
                                washroom.setPhotoReferences(photoReferences);

                                // Save the washroom data to Firebase
                                addWashroomToFirebase(washroom);

                                Intent intent = new Intent(context, WashroomDetailsActivity.class);
                                intent.putExtra("washroom", washroom);
                                context.startActivity(intent);
                            }
                        } else {
                            // Washroom not found in the database
                            Toast.makeText(context, "Place details not found", Toast.LENGTH_SHORT).show();

                            // Create a new Washroom object with the selected details
                            Washroom newWashroom = new Washroom(selectedPlaceName, address, selectedPlaceLatLng.latitude, selectedPlaceLatLng.longitude, rating, isOpenNow, photoReferences);

                            // Add the new washroom to the database
                            addWashroomToFirebase(newWashroom);

                            // Start the details activity and pass the washroom details
                            Intent intent = new Intent(context, WashroomDetailsActivity.class);
                            intent.putExtra("washroom", newWashroom);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.e("FetchData", "Database error: " + error.getMessage());
                    }
                });
    }

    // Inside the method that adds a washroom to Firebase
    private void addWashroomToFirebase(Washroom washroom) {
        DatabaseReference washroomsRef = FirebaseDatabase.getInstance().getReference("washrooms");
        String washroomId = washroomsRef.push().getKey(); // Generate a unique key for the washroom
        washroomsRef.child(washroomId).setValue(washroom);
    }

//    private String getOpeningHours(JSONObject placeObject) {
//        try {
//            if (placeObject.has("opening_hours")) {
//                JSONArray weekdayTextArray = placeObject.getJSONObject("opening_hours").getJSONArray("weekday_text");
//
//                // Concatenate the weekday text into a single string
//                StringBuilder openingHoursStringBuilder = new StringBuilder();
//                for (int i = 0; i < weekdayTextArray.length(); i++) {
//                    openingHoursStringBuilder.append(weekdayTextArray.getString(i)).append("\n");
//                }
//
//                return openingHoursStringBuilder.toString();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}