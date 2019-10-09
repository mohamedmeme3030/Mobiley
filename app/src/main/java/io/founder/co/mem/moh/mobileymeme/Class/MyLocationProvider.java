package io.founder.co.mem.moh.mobileymeme.Class;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyLocationProvider implements LocationListener {

    double lat;
    double lng;
    Location location;
    boolean canGetLocation;
    LocationManager locationManager;
    Context context;
    int MIN_DISTANCE_BETWEEN_UPDATES = 10;
    long MIN_TIME_BETWEEN_UPDATES = 5 * 60 * 1000;

    public MyLocationProvider(Context context) {
        this.context = context;
        location = null;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        lat = 0.0;
        lng = 0.0;
        canGetLocation = false;
        getMyLocation();
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public void setCanGetLocation(boolean canGetLocation) {
        this.canGetLocation = canGetLocation;
    }

    private void getMyLocation() {
        String provider = null;


        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;

            Log.d("NETWORK_PROVIDER", "getMyLocation: "+provider);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                provider = LocationManager.GPS_PROVIDER;


                if (provider == null) {
                    canGetLocation = false;
                    lat = 0.0;
                    lng = 0.0;
                    location = null;
                    return;
                }

                Log.d("NETWORK_PROVIDER", "getMyLocation: "+provider);

                try {
                    locationManager.requestLocationUpdates(provider, MIN_TIME_BETWEEN_UPDATES, MIN_DISTANCE_BETWEEN_UPDATES, this);
                    location = locationManager.getLastKnownLocation(provider);


                    if (location != null) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        canGetLocation=true;


                    }else{
                        canGetLocation=false;
                        location=null;
                    }



                } catch (SecurityException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Missing User Permission", Toast.LENGTH_SHORT).show();

                }
            }
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        lat = location.getLatitude();
        lng = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

