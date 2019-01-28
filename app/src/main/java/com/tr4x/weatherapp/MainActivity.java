package com.tr4x.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tr4x.weatherapp.webservice.WebserviceController;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private final int MY_LOCATION_REQUEST_CODE = 42;
    private double latitude = 0d;
    private double longitude = 0d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager;
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if(locationManager != null)
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        } else {
            askForPermission();
        }
    }

    private void askForPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, MY_LOCATION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager;
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if(locationManager != null)
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                }
            } else {
                // TODO Permission was denied. Display an error message.
            }
        }
    }

    private void fetchWeather() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<WeatherInfo> weatherList = WebserviceController.getWeatherInfos(longitude, latitude);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO update UI
                        for(WeatherInfo weather : weatherList){
                            Log.d("WEATHER", weather.toString());
                        }
                    }
                });
            }
        }).start();
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("Latitude", location.getLatitude() + "");
        Log.d("Longitude", location.getLongitude() + "");

        if(latitude == 0d && longitude == 0d) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            fetchWeather();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }
}
