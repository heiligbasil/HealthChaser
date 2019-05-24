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
    private static final String URL_FULL = URL_BASE + URL_MIDDLE + URL_ENDING;
    private static final String URL_IMAGE = "https://openweathermap.org/img/w/%s.png";

    public Weather getWeather() {
        return getWeather(null);
    }

    public Weather getWeather(String urlParameters) {
        final String completeUrl;
        if (urlParameters == null) {
            completeUrl = URL_FULL;
        } else {
            completeUrl = URL_BASE + urlParameters + URL_ENDING;
        }

        final Weather weather = new Weather();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = null;
                JSONArray jsonArray = null;

                jsonObject = new JSONObject();

                String returnedJsonAsString = NetworkAdapter.httpRequest(completeUrl);

                try {
                    jsonObject = new JSONObject(returnedJsonAsString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setCityName(jsonObject.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setCityId(jsonObject.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setVisibility(jsonObject.getInt("visibility"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setLatitude(jsonObject.getJSONObject("coord").getDouble("lat"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setLongitude(jsonObject.getJSONObject("coord").getDouble("lon"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setTemperature(jsonObject.getJSONObject("main").getDouble("temp"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setPressure(jsonObject.getJSONObject("main").getInt("pressure"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setHumidity(jsonObject.getJSONObject("main").getInt("humidity"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setWindSpeed(jsonObject.getJSONObject("wind").getDouble("speed"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setWindDegrees(jsonObject.getJSONObject("wind").getInt("deg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setCloudinessPercentage(jsonObject.getJSONObject("clouds").getInt("all"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setCountryCode(jsonObject.getJSONObject("sys").getString("country"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setSunriseTime(jsonObject.getJSONObject("sys").getLong("sunrise"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    weather.setSunsetTime(jsonObject.getJSONObject("sys").getLong("sunset"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    jsonArray = jsonObject.getJSONArray("weather");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            weather.setWeatherDescription(jsonObject.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            weather.setWeatherIconId(jsonObject.getString("icon"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
