package com.lambdaschool.healthchaser.connectivity;

import java.util.ArrayList;

public class Weather {

    private long sunriseTime, sunsetTime;
    private int cityId, cloudinessPercentage, windDegrees, humidity, visibility;
    private double latitude, longitude, temperature, pressure, windSpeed;
    private String cityName, countryCode;
    private ArrayList<String> weatherDescription = new ArrayList<>();
    private ArrayList<String> weatherIconId = new ArrayList<>();

    public long getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(long sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public long getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(long sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCloudinessPercentage() {
        return cloudinessPercentage;
    }

    public void setCloudinessPercentage(int cloudinessPercentage) {
        this.cloudinessPercentage = cloudinessPercentage;
    }

    public int getWindDegrees() {
        return windDegrees;
    }

    public void setWindDegrees(int windDegrees) {
        this.windDegrees = windDegrees;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getWeatherDescription() {
        StringBuilder weatherBuilder = new StringBuilder();

        for (String description : this.weatherDescription)
            weatherBuilder.append(description).append(", ");

        return weatherBuilder.toString().substring(0, weatherBuilder.length() - 2);
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription.add(weatherDescription);
    }

    public ArrayList<String> getWeatherIconId() {
        return weatherIconId;
    }

    public void setWeatherIconId(String weatherIconId) {
        this.weatherIconId.add(weatherIconId);
    }

    public String getCoordinates() {
        return this.latitude + ", " + this.longitude;
    }

    public String getCityAndIdAndCountryCode() {
        return this.cityName + " (" + this.cityId + ") in " + this.countryCode;
    }

    public String getWindSpeedAndDegrees() {
        return this.windSpeed + "mph, blowing " + this.windDegrees + "°";
    }
}
