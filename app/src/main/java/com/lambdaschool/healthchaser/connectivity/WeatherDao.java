package com.lambdaschool.healthchaser.connectivity;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class WeatherDao {
    private static final String URL_BASE = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String URL_MIDDLE = "q=jefferson";
    private static final String URL_ENDING = "&units=imperial&APPID=9c8359f38c54ef6dec64ac6d3dc0b438";
    private static final String URL_READ_ALL = URL_BASE + URL_MIDDLE + URL_ENDING;
    private static final String URL_IMAGE = "https://openweathermap.org/img/w/%s.png";


    public Weather getWeather() {
        final Weather weather = new Weather();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = null;
                JSONArray jsonArray = null;

                try {
                    jsonObject = new JSONObject();

                    String returnedJsonAsString = NetworkAdapter.httpRequest(URL_READ_ALL);

                    jsonObject = new JSONObject(returnedJsonAsString);

                    weather.setCityName(jsonObject.getString("name"));
                    weather.setCityId(jsonObject.getInt("id"));
                    weather.setVisibility(jsonObject.getInt("visibility"));

                    weather.setLatitude(jsonObject.getJSONObject("coord").getDouble("lat"));
                    weather.setLongitude(jsonObject.getJSONObject("coord").getDouble("lon"));

                    weather.setTemperature(jsonObject.getJSONObject("main").getDouble("temp"));
                    weather.setPressure(jsonObject.getJSONObject("main").getInt("pressure"));
                    weather.setHumidity(jsonObject.getJSONObject("main").getInt("humidity"));

                    weather.setWindSpeed(jsonObject.getJSONObject("wind").getDouble("speed"));
                    weather.setWindDegrees(jsonObject.getJSONObject("wind").getInt("deg"));

                    weather.setCloudinessPercentage(jsonObject.getJSONObject("clouds").getInt("all"));

                    weather.setCountryCode(jsonObject.getJSONObject("sys").getString("country"));
                    weather.setSunriseTime(jsonObject.getJSONObject("sys").getLong("sunrise"));
                    weather.setSunsetTime(jsonObject.getJSONObject("sys").getLong("sunset"));

                    jsonArray = jsonObject.getJSONArray("weather");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);

                        weather.setWeatherDescription(jsonObject.getString("description"));
                        weather.setWeatherIconId(jsonObject.getString("icon"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return weather;
    }

    public ArrayList<Bitmap> getImage(final ArrayList<String> imageIds) {
        final ArrayList<Bitmap> bitmaps = new ArrayList<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (String id : imageIds)
                    bitmaps.add(NetworkAdapter.httpImageRequest(String.format(Locale.getDefault(), URL_IMAGE, id)));
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try {
            thread.join();
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }

        return bitmaps;
    }
}
