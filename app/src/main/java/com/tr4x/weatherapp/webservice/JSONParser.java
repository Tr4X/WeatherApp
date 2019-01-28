package com.tr4x.weatherapp.webservice;

import android.util.Log;

import com.tr4x.weatherapp.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class JSONParser {

    static ArrayList<WeatherInfo> parseResponseForWeather(String response) {
        ArrayList<WeatherInfo> weatherList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(response);

            JSONObject currentWeatherObject = jsonObject.getJSONObject("currently");

            weatherList.add(parseObjectForWeather(currentWeatherObject));

            JSONArray dailyWeatherArray = jsonObject.getJSONObject("daily").getJSONArray("data");

            for (int i = 0; i < dailyWeatherArray.length(); i++) {
                JSONObject weatherObject = dailyWeatherArray.getJSONObject(i);
                weatherList.add(parseObjectForWeather(weatherObject));
            }

        } catch (JSONException e) {
            Log.e("WeatherApp", "Can t parse JSON ", e);
        }

        return weatherList;
    }

    private static WeatherInfo parseObjectForWeather(JSONObject weatherObject) {
        long time = getJsonTime(weatherObject);
        String summary = getJsonString(weatherObject, "summary");
        String icon = getJsonString(weatherObject, "icon");

        double temperature = 0d;
        double temperatureMin = 0d;
        double temperatureMax = 0d;

        try {
             temperature = weatherObject.getLong("temperature");
        } catch (JSONException e) {
            try {
                temperatureMin = weatherObject.getLong("temperatureLow");
                temperatureMax = weatherObject.getLong("temperatureHigh");
            } catch (JSONException er) {
                er.printStackTrace();
            }
        }


        return new WeatherInfo(summary, icon, time, temperature, temperatureMin, temperatureMax);
    }

    private static long getJsonTime(JSONObject jsonObject) {
        try {
            return jsonObject.getLong("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static String getJsonString(JSONObject jsonObject, String name) {
        try {
            return jsonObject.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

}
