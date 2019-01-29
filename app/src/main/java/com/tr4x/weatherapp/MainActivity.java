package com.tr4x.weatherapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
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

    private ImageView currentImageView;
    private TextView currentSummaryTextView, currentTimezoneTxtView, currentDateTextView;
    private ArrayList<ImageView> forecastImageViews = new ArrayList<>();
    private ArrayList<TextView> forecastTextViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.permissionBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.permissionBtn).setVisibility(View.INVISIBLE);
                findViewById(R.id.permissionText).setVisibility(View.INVISIBLE);
                askForPermission();
            }
        });

        findViewById(R.id.errorBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.errorBtn).setVisibility(View.INVISIBLE);
                findViewById(R.id.errorText).setVisibility(View.INVISIBLE);
                getLastKnownLocation();
            }
        });

        findViewById(R.id.infoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://darksky.net"));
                startActivity(browserIntent);
            }
        });

        forecastImageViews.add((ImageView) findViewById(R.id.forecast1));
        forecastImageViews.add((ImageView) findViewById(R.id.forecast2));
        forecastImageViews.add((ImageView) findViewById(R.id.forecast3));
        forecastImageViews.add((ImageView) findViewById(R.id.forecast4));
        forecastImageViews.add((ImageView) findViewById(R.id.forecast5));

        forecastTextViews.add((TextView) findViewById(R.id.forecastText1));
        forecastTextViews.add((TextView) findViewById(R.id.forecastText2));
        forecastTextViews.add((TextView) findViewById(R.id.forecastText3));
        forecastTextViews.add((TextView) findViewById(R.id.forecastText4));
        forecastTextViews.add((TextView) findViewById(R.id.forecastText5));

        currentImageView = findViewById(R.id.currentImageView);
        currentSummaryTextView = findViewById(R.id.currentSummaryTextView);
        currentTimezoneTxtView = findViewById(R.id.cityTextView);
        currentDateTextView = findViewById(R.id.currentDateTextView);

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
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

                        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                        if (weatherList.size() > 0) {

                            findViewById(R.id.separator).setVisibility(View.VISIBLE);

                            currentTimezoneTxtView.setText(weatherList.get(0).getTimezone());


                            String summaryTxt = weatherList.get(0).getTemperature() + "\u2103" + " : " + weatherList.get(0).getSummary();
                            currentSummaryTextView.setText(summaryTxt);

                            String currentDate = DateFormat.format("EEEE, d MMM", new Date(weatherList.get(0).getTime() * 1000)).toString();
                            currentDateTextView.setText(currentDate);

                            Picasso.get().load(Utils.getUrlForIcon(weatherList.get(0).getIcon())).into(currentImageView);

                            for (int i = 0; i < 5; i++) {
                                Picasso.get().load(Utils.getUrlForIcon(weatherList.get(i + 1).getIcon())).into(forecastImageViews.get(i));

                                String forecast1Txt = DateFormat.format("EEEE, d MMM", new Date(weatherList.get(i + 1).getTime() * 1000)).toString() + ": " + weatherList.get(i + 1).getSummary() + "\nMin: " + weatherList.get(i + 1).getTemperatureMin() + "\u2103" + " - Max: " + weatherList.get(i + 1).getTemperatureMax() + "\u2103";
                                forecastTextViews.get(i).setText(forecast1Txt);

                                final int index = i;
                                View.OnClickListener clickListener = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        WeatherInfoDialog dialog = new WeatherInfoDialog();

                                        Bundle args = new Bundle();
                                        args.putString("icon", weatherList.get(index + 1).getIcon());
                                        args.putString("temp", "Min: " + weatherList.get(index + 1).getTemperatureMin() + "\u2103" + " - Max: " + weatherList.get(index + 1).getTemperatureMax() + "\u2103");
                                        args.putString("date", DateFormat.format("EEEE, d MMM", new Date(weatherList.get(index + 1).getTime() * 1000)).toString());
                                        args.putString("summary", weatherList.get(index + 1).getSummary());
                                        dialog.setArguments(args);

                                        dialog.show(getSupportFragmentManager(), "WeatherDialog");

                                    }
                                };
                                forecastTextViews.get(i).setOnClickListener(clickListener);
                                forecastImageViews.get(i).setOnClickListener(clickListener);
                            }
                        } else {
                            findViewById(R.id.errorBtn).setVisibility(View.VISIBLE);
                            findViewById(R.id.errorText).setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }

    private void getLastKnownLocation() {
        LocationManager locationManager;

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location optimalLocation = null;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            for (String provider : providers) {
                Location providerLocation = locationManager.getLastKnownLocation(provider);
                if (providerLocation == null) {
                    continue;
                }
                if (optimalLocation == null || providerLocation.getAccuracy() < optimalLocation.getAccuracy()) {
                    optimalLocation = providerLocation;
                }
            }

            if (optimalLocation != null) {
                latitude = optimalLocation.getLatitude();
                longitude = optimalLocation.getLongitude();
                fetchWeather();
            } else {
                findViewById(R.id.errorBtn).setVisibility(View.VISIBLE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }
    }
}
