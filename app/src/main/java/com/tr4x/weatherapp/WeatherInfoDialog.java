package com.tr4x.weatherapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;


public class WeatherInfoDialog extends DialogFragment {
    private WeatherInfo weatherInfo;

    public WeatherInfoDialog(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.weather_info_dialog, null);

        Picasso.get().load(Utils.getUrlForIcon(weatherInfo.getIcon())).into((ImageView)view.findViewById(R.id.icon));

        ((TextView)view.findViewById(R.id.summary)).setText(weatherInfo.getSummary());

        String date = DateFormat.format("EEEE, d MMM", new Date(weatherInfo.getTime() * 1000)).toString();

        ((TextView)view.findViewById(R.id.date)).setText(date);

        String temp = "Min: " + weatherInfo.getTemperatureMin() + "\u2103" + " - Max: " + weatherInfo.getTemperatureMax() + "\u2103";

        ((TextView)view.findViewById(R.id.temperatures)).setText(temp);

        builder.setView(view);
        return builder.create();
    }
}
