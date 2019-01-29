package com.tr4x.weatherapp;

class Utils {

    // Mapping of the dark sky icon definition with the openweathermap icons url
    public static String getUrlForIcon(String icon) {
        String url = "http://openweathermap.org/img/w/";
        switch (icon) {
            case "clear-day":
                url += "01d";
                break;
            case "clear-night":
                url += "01n";
                break;
            case "rain":
                url += "10d";
                break;
            case "snow":
            case "sleet":
                url += "13d";
                break;
            case "wind":
                url += "04d";
                break;
            case "fog":
                url += "50d";
                break;
            case "cloudy":
                url += "03d";
                break;
            case "partly-cloudy-day":
                url += "02d";
                break;
            case "partly-cloudy-night":
                url += "02n";
                break;
            case "hail":
            case "thunderstorm":
            case "tornado":
                url += "11d";
                break;
        }
        url += ".png";

        return url;
    }
}