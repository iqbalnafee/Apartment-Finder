package com.example.apartment_finder2;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomizeSearch extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_search);

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.divisions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)  {
        String location = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        Geocoder gcd = new Geocoder(parent.getContext(), Locale.getDefault());

        List<Address> addressList = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(CustomizeSearch.this);
        double lat=0;
        double lng=0;
        if (location != null || !location.equals("")) {
            String errorMessage = "";
            //map.clear();

            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList == null || addressList.size() == 0) {
                Toast.makeText(CustomizeSearch.this, "The Location User provide is not Map Specified", Toast.LENGTH_LONG).show();
            }
            else {
                Address address = addressList.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressFragments.add(address.getAddressLine(i));
                }
                //Address address=addressList.get(0);
                //LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                lat=address.getLatitude();
                lng=address.getLongitude();
                addressList.clear();
            }

        }

        try
        {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 10);
            if (addresses.size() > 0)
            {
                Log.d("District",addresses.get(1).getLocality());
            }

        }
        catch (Exception e)
        {

        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
