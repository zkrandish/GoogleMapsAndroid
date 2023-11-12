package com.example.placesprojectdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.MimeTypeFilter;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.placesprojectdemo.databinding.ActivityMapsBinding;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMapsBinding binding;
    private GoogleMap mMap;
    /*responsible for fetching the current location of the device*/
    private FusedLocationProviderClient mFusedLocationProviderClient;

    /*responsible for loading the suggestions as you see the user type*/
    private PlacesClient placesClient;

    /*as the solutions are recieved from the ggogle api we need an arraylist
    * to save those*/
    private List<AutocompletePrediction> predictionList;
    private Location mLastKnownLocation;

    //use for updating user request if the lastknowmlocation is null
    private LocationCallback locationCallback;


    //for the layout and design
    private MaterialSearchBar materialSearchBar;
    private View mapView;
    private Button btnFind;
    private RippleBackground ripple_bg;

    private final float DEFAULT_ZOOM=18;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        materialSearchBar= findViewById(R.id.searchBar);
        btnFind= findViewById(R.id.btnFind);
        ripple_bg= findViewById(R.id.ripple_bg);
        //id of the fragmanet in layout
        SupportMapFragment mapFragment= (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);
        //to manipulate the location button
        mapView = mapFragment.getView();
        //to initialize the fuse location

        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        //will enable places sdk
        Places.initialize(MapsActivity.this,"AIzaSyBBz3HT6FAtlv-BTVSX6nQ_KN0Bstk1bzs");
        placesClient = Places.createClient(this);
        AutocompleteSessionToken token=  AutocompleteSessionToken.newInstance();

        //to manipulate the search
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(),true,null,true);

            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if(buttonCode==MaterialSearchBar.BUTTON_NAVIGATION){
                    //OPENING OR CLOSING a navigation drawer

                }else if(buttonCode == MaterialSearchBar.BUTTON_BACK){
                    materialSearchBar.disableSearch();
                }

            }
        });

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setCountry("ca").setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token).setQuery(s.toString()).build();
                placesClient.findAutocompletePredictions(predictionsRequest)
                        .addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                            @Override
                            public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                                if (task.isSuccessful()) {
                                    FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                                    if (predictionsResponse != null) {
                                       predictionList = predictionsResponse.getAutocompletePredictions();
                                       List<String > suggestionList = new ArrayList<>();
                                       for(int i = 0; i<predictionList.size();i++){
                                           AutocompletePrediction prediction = predictionList.get(i);
                                           suggestionList.add(prediction.getFullText(null).toString());
                                       }
                                       materialSearchBar.updateLastSuggestions(suggestionList);
                                       if(!materialSearchBar.isSuggestionsVisible()){
                                           materialSearchBar.showSuggestionsList();
                                       }
                                    }
                                } else {
                                    Log.i("mytag","prediction fetching task unsuccessful");

                                }
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                /*we need the lat and long of that location but we dont have the lat and lng
                * of that location. we have the place id of that place
                * we take that place id, send it to google
                *  places api and request to send us the lat and lng
                * and then we move the camera to that place*/
                if(position>=predictionList.size()){
                    return;
                }
                //we need to fetch the id of the place that is selected by the user
                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                materialSearchBar.setText(suggestion);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialSearchBar.clearSuggestions();
                    }
                },1000 );

                //to close the keyboard
                InputMethodManager imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(imm !=null){
                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
                    String placeId= selectedPrediction.getPlaceId();
                    //we pass this place id to give us the lat and lng
                    //which fields are we intesrted, we are only intested in lgn and lat
                    List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);
                    FetchPlaceRequest fetchPlaceRequest= FetchPlaceRequest.builder(placeId,placeFields).build();
                    placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @Override
                        public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                            Place place= fetchPlaceResponse.getPlace();
                            Log.i("mytag","place found: "+ place.getName());
                            LatLng latLngOfPlace = place.getLatLng();
                            if(latLngOfPlace !=null){
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace,DEFAULT_ZOOM));
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof ApiException){
                                ApiException apiException = (ApiException) e;
                                apiException.printStackTrace();
                                int statusCode = apiException.getStatusCode();
                                Log.i("mytag","place not found: "+e.getMessage());
                                Log.i("mytag","status code "+ statusCode);
                            }
                        }
                    });

                }
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng currentMarkerLocation = mMap.getCameraPosition().target;
                ripple_bg.startRippleAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ripple_bg.stopRippleAnimation();
                        startActivity(new Intent(MapsActivity.this,MainActivity.class));
                        finish();
                    }
                },3000);
            }
        });
    }

    //this function will be called when the map is ready and loaded
    //so that we can perform the action like moving the map or enablning some buttons
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        /*this function is called when the map is ready
        * we take the map,load it to the mMap
        * we enable the location button
        * and we moved the location button to the required place*/
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        //so that my location button is shown
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (mapView !=null && mapView.findViewById(Integer.parseInt("1")) !=null){

            //we are fetching the layoutparams from the location button
            View locationButton = ((View)mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            //we are removing the align parent top rule of the location button
            //so it is no longer on the top of the screen
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,40,180);

        }
        //check if the GPS is enabled or not and then request the user to enable it

        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setInterval(10000); // set interval to 5000ms
        locationRequest.setFastestInterval(5000); // set the fastest interval to 5000ms
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder= new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient= LocationServices.getSettingsClient(MapsActivity.this);
        //this function will check for the location setting
        //the location settings will be sufficien the location will be on and you can perform the a task
        //or the locationsettings will not be on
        Task<LocationSettingsResponse> task= settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(MapsActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //when the gps is enabl we get the device location
                getDeviceLocation();

            }
        });
        //on failure we chaeck if the issue can be resolved
        task.addOnFailureListener(MapsActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    ResolvableApiException resolvable =(ResolvableApiException) e;
                    try {
                        //show the user the diaolog where the user can either enable the location or do not
                        resolvable.startResolutionForResult(MapsActivity.this,51);
                    } catch (IntentSender.SendIntentException ex) {
                       ex.printStackTrace();
                    }
                }

            }
        });
        //to clear the searchbar after i selct my location. reseting the searchbar
       mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
           @Override
           public boolean onMyLocationButtonClick() {
               if(materialSearchBar.isSuggestionsVisible()){
                   materialSearchBar.clearSuggestions();
               }
               if(materialSearchBar.isSearchEnabled()){
                   materialSearchBar.disableSearch();
               }
               return false;
           }
       });
    }
    //need to the ce\heck the result; what did the user do
    //did he accept the location or not

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==51){
            if(resultCode==RESULT_OK){
                //if they enable the gps option on their phone
                //we can now proceed to find the user current location
                //and then to move the map to that location
                getDeviceLocation();

            }
        }
    }

    /*in this function we asked the mFusedLocationProviderClient to
    * give us the last location
    * when the request is complete
    * we check if the task was succesful or not
    *if it was, it doess not still grantee that we could get the location
    * so we get the location and check if it s null or not
    * if it not null
    * we move the camera to the location that we recieve
    * if the last known is null
    * we have to create two things location request and location callback
    * location callback is a fuction that will be executed
    * when an updated location is recived
    * */
    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        //to fetch the users last known location so we need the fused provider
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                    mLastKnownLocation= task.getResult();
                    if(mLastKnownLocation !=null){
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()),DEFAULT_ZOOM));

                    }else{
                        //if itis null then we have to ask for the updated location
                        LocationRequest locationRequest= LocationRequest.create();
                        locationRequest.setInterval(100000);
                        locationRequest.setFastestInterval(5000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationCallback= new LocationCallback(){
                            //if it is null we send the request to get the location update
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                //if the location result is null
                                if(locationResult==null){
                                    return;
                                }
                                //if it is not null we update our last known locationvariable

                                mLastKnownLocation= locationResult.getLastLocation();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()),DEFAULT_ZOOM));
                                mFusedLocationProviderClient.removeLocationUpdates(locationCallback);

                            }
                        };
                        mFusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);

                    }
                }else {
                    //if the task was not successull
                    Toast.makeText(MapsActivity.this, "unable to get the last location", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}