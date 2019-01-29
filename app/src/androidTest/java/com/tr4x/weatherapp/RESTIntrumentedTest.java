package com.tr4x.weatherapp;

import android.support.test.runner.AndroidJUnit4;

import com.tr4x.weatherapp.webservice.WebserviceController;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RESTIntrumentedTest {

    private static final double latitude = 50.4671962;
    private static final double longitude = 3.9571922;

    @Test
    public void testFetchAndParse() {
        final ArrayList<WeatherInfo> weatherList = WebserviceController.getWeatherInfos(longitude, latitude);

        assertTrue(weatherList.size() > 0);

        for (int i = 0; i <= 5; i++) {
            assertNotNull(weatherList.get(i).getSummary());
            assertNotNull(weatherList.get(i).getIcon());
        }
    }
}
