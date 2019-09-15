package com.example.apartment_finder2;

import android.location.Address;

import java.util.List;

public class LangiLati {

    private double longi,lat;
    Address address;
    LangiLati(List<Address> addressList)
    {
        address=addressList.get(0);
        lat=address.getLatitude();
        longi=address.getLongitude();
    }
    public double getLat()
    {
        return lat;
    }
    public double getLongi()
    {
        return longi;
    }


}
