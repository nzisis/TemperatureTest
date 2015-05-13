package com.nick.temperatureoutdoors;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vromia on 13/5/2015.
 */

    public class WeatherFragment extends Fragment {

        TextView cityField;
        TextView updatedField;
        TextView detailsField;
        TextView currentTemperatureField;
        TextView weatherIcon;

        Handler handler;

        public WeatherFragment(){
            handler = new Handler();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
            cityField = (TextView)rootView.findViewById(R.id.city_field);
            updatedField = (TextView)rootView.findViewById(R.id.updated_field);
            detailsField = (TextView)rootView.findViewById(R.id.details_field);
            currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
            weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);

            //weatherIcon.setTypeface(weatherFont);
            return rootView;
        }



    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ " ?");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);


        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    }

