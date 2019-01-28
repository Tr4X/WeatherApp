package com.tr4x.weatherapp;

public class WeatherInfo {
    private String summary;
    private String icon;
    private long time;
    private double temperature;
    private double temperatureMin;
    private double temperatureMax;

    public WeatherInfo(String summary, String icon, long time, double temperature, double temperatureMin, double temperatureHigh) {
        this.summary = summary;
        this.icon = icon;
        this.time = time;
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureHigh;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", time=" + time +
                ", temperature=" + temperature +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                '}';
    }
}
