package com.tr4x.weatherapp;

public class WeatherInfo {
    private final String summary;
    private final String icon;
    private final long time;
    private final double temperature;
    private final double temperatureMin;
    private final double temperatureMax;
    private final String timezone;

    public WeatherInfo(String summary, String icon, long time, double temperature, double temperatureMin, double temperatureMax, String timezone) {
        this.summary = summary;
        this.icon = icon;
        this.time = time;
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.timezone = timezone;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public long getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public String getTimezone() {
        return timezone;
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
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
