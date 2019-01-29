package com.tr4x.weatherapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class WeatherInfoDialog extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.weather_info_dialog, null);

        Picasso.get().load(Utils.getUrlForIcon(getArguments().getString("icon"))).into((ImageView) view.findViewById(R.id.icon));

        ((TextView) view.findViewById(R.id.summary)).setText(getArguments().getString("summary"));

        String date = getArguments().getString("date");

        ((TextView) view.findViewById(R.id.date)).setText(date);

        String temp = getArguments().getString("temp");

        ((TextView) view.findViewById(R.id.temperatures)).setText(temp);

        builder.setView(view);
        return builder.create();
    }
}
