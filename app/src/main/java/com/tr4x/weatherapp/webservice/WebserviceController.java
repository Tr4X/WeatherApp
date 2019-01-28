package com.tr4x.weatherapp.webservice;

import android.util.Log;

import com.tr4x.weatherapp.WeatherInfo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class WebserviceController {
    private static final String API_KEY = "04b059b9ead591f473fec4e719bba12c";

    public static ArrayList<WeatherInfo> getWeatherInfos(double longitude, double latitude) {
        String weatherUrl = "https://api.darksky.net/forecast/" + API_KEY + "/" + longitude + "," + latitude + "?units=si";
        try {
            String response = new GetWebserviceAsyncTask().execute(weatherUrl).get();
            return JSONParser.parseResponseForWeather(response);
        } catch (ExecutionException e) {
            Log.e("WeatherApp", "Webservice call execution exception ", e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("WeatherApp", "Webservice call interrupted exception ", e);
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
