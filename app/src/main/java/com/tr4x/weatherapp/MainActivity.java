package com.tr4x.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tr4x.weatherapp.webservice.WebserviceController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int MY_LOCATION_REQUEST_CODE = 42;
    private double latitude = 0d;
    private double longitude = 0d;

    private ImageView currentImageView, forecast1ImageView, forecast2ImageView, forecast3ImageView, forecast4ImageView, forecast5ImageView;
    private TextView currentSummaryTextView, currentTimezoneTxtView, currentDateTextView, forecast1TextView, forecast2TextView, forecast3TextView, forecast4TextView, forecast5TextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.permissionBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForPermission();
                findViewById(R.id.permissionBtn).setVisibility(View.INVISIBLE);
                findViewById(R.id.permissionText).setVisibility(View.INVISIBLE);
            }
        });

        currentImageView = findViewById(R.id.currentImageView);
        forecast1ImageView = findViewById(R.id.forecast1);
        forecast2ImageView = findViewById(R.id.forecast2);
        forecast3ImageView = findViewById(R.id.forecast3);
        forecast4ImageView = findViewById(R.id.forecast4);
        forecast5ImageView = findViewById(R.id.forecast5);

        currentSummaryTextView = findViewById(R.id.currentSummaryTextView);
        currentTimezoneTxtView = findViewById(R.id.cityTextView);
        currentDateTextView = findViewById(R.id.currentDateTextView);
        forecast1TextView = findViewById(R.id.forecastText1);
        forecast2TextView = findViewById(R.id.forecastText2);
        forecast3TextView = findViewById(R.id.forecastText3);
        forecast4TextView = findViewById(R.id.forecastText4);
        forecast5TextView = findViewById(R.id.forecastText5);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLastKnownLocation();
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

                getLastKnownLocation();
            } else {
                // TODO Permission was denied. Display an error message.
                findViewById(R.id.permissionBtn).setVisibility(View.VISIBLE);
                findViewById(R.id.permissionText).setVisibility(View.VISIBLE);
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
                        /*for (WeatherInfo weather : weatherList) {
                            Log.d("WEATHER", weather.toString());
                        }*/

                        Picasso.get().load(Utils.getUrlForIcon(weatherList.get(0).getIcon())).into(currentImageView);
                        Picasso.get().load(Utils.getUrlForIcon(weatherList.get(1).getIcon())).into(forecast1ImageView);
                        Picasso.get().load(Utils.getUrlForIcon(weatherList.get(2).getIcon())).into(forecast2ImageView);
                        Picasso.get().load(Utils.getUrlForIcon(weatherList.get(3).getIcon())).into(forecast3ImageView);
                        Picasso.get().load(Utils.getUrlForIcon(weatherList.get(4).getIcon())).into(forecast4ImageView);
                        Picasso.get().load(Utils.getUrlForIcon(weatherList.get(5).getIcon())).into(forecast5ImageView);

                        currentTimezoneTxtView.setText(weatherList.get(0).getTimezone());


                        String summaryTxt = weatherList.get(0).getTemperature() + "\u2103" + " : " + weatherList.get(0).getSummary();
                        currentSummaryTextView.setText(summaryTxt);

                        String currentDate = DateFormat.format("EEEE, d MMM", new Date(weatherList.get(0).getTime() * 1000)).toString();
                        currentDateTextView.setText(currentDate);

                        String forecast1Txt = DateFormat.format("EEEE, d MMM", new Date(weatherList.get(1).getTime() * 1000)).toString() + ": " + weatherList.get(1).getSummary() + "\nMin: " + weatherList.get(1).getTemperatureMin() + "\u2103" + " - Max: " + weatherList.get(1).getTemperatureMax() + "\u2103";
                        forecast1TextView.setText(forecast1Txt);

                        String forecast2Txt = DateFormat.format("EEEE, d MMM", new Date(weatherList.get(2).getTime() * 1000)).toString() + ": " + weatherList.get(2).getSummary() + "\nMin: " + weatherList.get(2).getTemperatureMin() + "\u2103" + " - Max: " + weatherList.get(2).getTemperatureMax() + "\u2103";
                        forecast2TextView.setText(forecast2Txt);

                        String forecast3Txt = DateFormat.format("EEEE, d MMM", new Date(weatherList.get(3).getTime() * 1000)).toString() + ": " + weatherList.get(3).getSummary() + "\nMin: " + weatherList.get(3).getTemperatureMin() + "\u2103" + " - Max: " + weatherList.get(3).getTemperatureMax() + "\u2103";
                        forecast3TextView.setText(forecast3Txt);

                        String forecast4Txt = DateFormat.format("EEEE, d MMM", new Date(weatherList.get(4).getTime() * 1000)).toString() + ": " + weatherList.get(4).getSummary() + "\nMin: " + weatherList.get(4).getTemperatureMin() + "\u2103" + " - Max: " + weatherList.get(4).getTemperatureMax() + "\u2103";
                        forecast4TextView.setText(forecast4Txt);

                        String forecast5Txt = DateFormat.format("EEEE, d MMM", new Date(weatherList.get(5).getTime() * 1000)).toString() + ": " + weatherList.get(5).getSummary() + "\nMin: " + weatherList.get(5).getTemperatureMin() + "\u2103" + " - Max: " + weatherList.get(5).getTemperatureMax() + "\u2103";
                        forecast5TextView.setText(forecast5Txt);
                    }
                });
            }
        }).start();
    }

    private Location getLastKnownLocation() {
        LocationManager locationManager;

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location optimalLocation = null;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (optimalLocation == null || l.getAccuracy() < optimalLocation.getAccuracy()) {
                    optimalLocation = l;
                }
            }
            latitude = optimalLocation.getLatitude();
            longitude = optimalLocation.getLongitude();
            fetchWeather();
        }
        return optimalLocation;
    }
}
