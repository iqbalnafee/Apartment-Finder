package com.example.apartment_finder2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Map extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {
    GoogleMap map;
    GoogleApiClient mGoogleApiClient;
    private TextView textView;
    private GoogleApiClient client;
    //private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    public static final int REQUEST_CODE = 101;
    int PROXIMITY_RADIUS = 1000;
    SupportMapFragment supportMapFragment;
    SearchView mSv;
    TextView tv;
    String ss;
    public static String GlobalPlaceName = "";
    double latitude, longitude;
    private FusedLocationProviderClient mClient;
    Location curLocation;
    private MarkerOptions curplace,searchedPlace;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mClient = LocationServices.getFusedLocationProviderClient(this);

        //fetchLastLocation();
        //mSv=findViewById(R.id.sv_location);
        tv = findViewById(R.id.tv_location);
        textView = findViewById(R.id.viewRS);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        final String GetLocation = getIntent().getStringExtra("ViewLocation");
        ss = GetLocation;
        tv.setText(ss);
        supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        this.SeeMap(ss);
    }

    public void SeeMap(String s) {
        Log.d("aschii", "here i am");
        String location = "dhaka " + s;
        List<Address> addressList = new ArrayList<Address>();
        if (location != null || !location.equals("")) {
            String errorMessage = "";
            map.clear();
            Geocoder geocoder = new Geocoder(Map.this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList == null || addressList.size() == 0) {
                Toast.makeText(Map.this, "The Location User provide is not Map Specified", Toast.LENGTH_LONG).show();
            } else {
                Address address = addressList.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressFragments.add(address.getAddressLine(i));
                }
                //Address address=addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                latitude = address.getLatitude();
                longitude = address.getLongitude();

                map.addMarker(new MarkerOptions().position(latLng).title(location));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                addressList.clear();
            }

        }
    }


    public void onClick(View v) {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

        switch (v.getId()) {
            case R.id.B_hopistals:
                //map.clear();
                String hospital = "hospital";
                String url = getUrl(latitude, longitude, hospital);
                dataTransfer[0] = map;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(Map.this, "Showing Nearby Hospitals", Toast.LENGTH_LONG).show();
                textView.setText(GlobalPlaceName);
                GlobalPlaceName = "";
                break;


            case R.id.B_schools:
                //map.clear();
                String school = "school";
                url = getUrl(latitude, longitude, school);
                dataTransfer[0] = map;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(Map.this, "Showing Nearby Schools", Toast.LENGTH_LONG).show();
                textView.setText(GlobalPlaceName);
                Log.d("placeName", GlobalPlaceName);
                GlobalPlaceName = "";
                break;
            case R.id.B_directions:
                Toast.makeText(Map.this,"Showing direction",Toast.LENGTH_LONG).show();;
                map.clear();
                curplace = new MarkerOptions().position(new LatLng(23.726674, 90.388028)).title("Your Location");
                searchedPlace = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Searched Location");
                map.addMarker(curplace);
                map.addMarker(searchedPlace);
                //map.animateCamera();
                String URL=getUrl2(curplace.getPosition(),searchedPlace.getPosition(), "driving");

                //new FetchURL(Map.this).execute(URL, "driving");

        }
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=" + "AIzaSyBLEPBRfw7sMb73Mr88L91Jqh3tuE4mKsE");

        Log.d("MapsActivity", "url = " + googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    private String getUrl2(LatLng origin, LatLng dest, String directionMode) {
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
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyBLEPBRfw7sMb73Mr88L91Jqh3tuE4mKsE";
        return url;
    }
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }

    public void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = mClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location loc) {
                //Log.d("Nafees: ",curLocation.toString());
                if (loc != null) {
                    //curLocation=loc;
                    //Log.d("Nafees: ",curLocation.toString());
                }
            }
        });
    }


}


