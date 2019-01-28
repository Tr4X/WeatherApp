package com.tr4x.weatherapp.webservice;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWebserviceAsyncTask extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... urls) {
        URL url;
        HttpURLConnection urlConnection = null;
        String response = "";
        StringBuilder responseStrBuilder = new StringBuilder(2048);

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setDefaultUseCaches(false);
            InputStream in = urlConnection.getInputStream();

            if (urlConnection.getResponseCode() == 200 || urlConnection.getResponseCode() == 201) {
                InputStreamReader isw = new InputStreamReader(in);

                BufferedReader bufferedReader = new BufferedReader(isw);
                String inputStr;
                while ((inputStr = bufferedReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        response = responseStrBuilder.toString();
        responseStrBuilder = null;
        return response;
    }
}
