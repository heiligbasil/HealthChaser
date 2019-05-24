package com.lambdaschool.healthchaser;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.lambdaschool.healthchaser.connectivity.LoggedInUser;
import com.lambdaschool.healthchaser.connectivity.LoggedInUserDao;
import com.lambdaschool.healthchaser.connectivity.Weather;
import com.lambdaschool.healthchaser.connectivity.WeatherDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public enum Tracking {SLEEP, MEALS, MOOD, WATER, EXERCISE, RESTROOM, HYGIENE, MEDITATION}

    public static LoggedInUser currentLoggedInUser;
    private static final int REQUEST_CODE_SIGN_IN = 55;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.AnonymousBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                REQUEST_CODE_SIGN_IN);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing weather data!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 33);
                } else {

                    FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
                    locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                displayWeatherData(String.format(Locale.getDefault(), "lat=%f&lon=%f", location.getLatitude(), location.getLongitude()));
                            }
                        }
                    });
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                currentLoggedInUser = new LoggedInUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()));
                ((TextView) findViewById(R.id.main_activity_text_view_user_name)).append(" " + currentLoggedInUser.getDisplayName());
                ((TextView) findViewById(R.id.main_activity_text_view_user_email)).append(" " + currentLoggedInUser.getEmail());
                ((TextView) findViewById(R.id.navigation_header_text_view)).setText(" " + currentLoggedInUser.getDisplayName());
                LoggedInUserDao loggedInUserDao = new LoggedInUserDao();
                Bitmap currentLoggedInUserImage = loggedInUserDao.getImage(currentLoggedInUser.getPhotoUrl().toString());
                if (currentLoggedInUserImage != null)
                    ((ImageView) findViewById(R.id.main_activity_image_view_user_photo)).setImageBitmap(currentLoggedInUserImage);

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }

            displayWeatherData("lat=47.6&lon=-122.33");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.navigation_menu_sleep) {

            Intent intent = new Intent(this, GenericMasterActivity.class);
            intent.putExtra("tracking", Tracking.SLEEP);
            startActivity(intent);

        } else if (id == R.id.navigation_menu_meals) {

            Intent intent = new Intent(this, GenericMasterActivity.class);
            intent.putExtra("tracking", Tracking.MEALS);
            startActivity(intent);

        } else if (id == R.id.navigation_menu_mood) {

            Intent intent = new Intent(this, GenericMasterActivity.class);
            intent.putExtra("tracking", Tracking.MOOD);
            startActivity(intent);

        } else if (id == R.id.navigation_menu_water) {

            Intent intent = new Intent(this, GenericMasterActivity.class);
            intent.putExtra("tracking", Tracking.WATER);
            startActivity(intent);

        } else if (id == R.id.navigation_menu_exercise) {

            Intent intent = new Intent(this, GenericMasterActivity.class);
            intent.putExtra("tracking", Tracking.EXERCISE);
            startActivity(intent);

        } else if (id == R.id.navigation_menu_restroom) {

            Intent intent = new Intent(this, GenericMasterActivity.class);
            intent.putExtra("tracking", Tracking.RESTROOM);
            startActivity(intent);

        } else if (id == R.id.navigation_menu_hygiene) {

            Intent intent = new Intent(this, GenericMasterActivity.class);
            intent.putExtra("tracking", Tracking.HYGIENE);
            startActivity(intent);

        } else if (id == R.id.navigation_menu_meditation) {

            Intent intent = new Intent(this, GenericMasterActivity.class);
            intent.putExtra("tracking", Tracking.MEDITATION);
            startActivity(intent);

        } else if (id == R.id.navigation_menu_inspiration) {

            startActivity(new Intent(this, InspirationActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        FirebaseAuth.getInstance().signOut();
        super.onDestroy();
    }

    private void displayWeatherData(String coordinatesParameter) {

        WeatherDao weatherDao = new WeatherDao();
        Weather weather = weatherDao.getWeather(coordinatesParameter);
        ArrayList<Bitmap> weatherIcons = weatherDao.getImage(weather.getWeatherIconId());
        ((ImageView) findViewById(R.id.main_activity_image_view_weather_icon)).setImageBitmap(weatherIcons.get(0));
        if (weatherIcons.size() > 1) {
            for (int i = 1; i < weatherIcons.size(); ++i) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                imageView.setImageBitmap(weatherIcons.get(i));
                ((LinearLayout) findViewById(R.id.main_activity_linear_layout_weather)).addView(imageView, 1);
            }
        }
        ((TextView) findViewById(R.id.main_activity_text_view_weather_city)).setText("City: " + weather.getCityAndIdAndCountryCode());
        ((TextView) findViewById(R.id.main_activity_text_view_weather_cloudiness)).setText("Cloudiness: " + weather.getCloudinessPercentage() + "%");
        ((TextView) findViewById(R.id.main_activity_text_view_weather_humidity)).setText("Humidity: " + weather.getHumidity());
        ((TextView) findViewById(R.id.main_activity_text_view_weather_coordinates)).setText("Coordinates: " + weather.getCoordinates());
        ((TextView) findViewById(R.id.main_activity_text_view_weather_pressure)).setText("Pressure: " + weather.getPressure());
        ((TextView) findViewById(R.id.main_activity_text_view_weather_sunrise)).setText("Sunrise: " + convertEpochToDateTime(weather.getSunriseTime()));
        ((TextView) findViewById(R.id.main_activity_text_view_weather_sunset)).setText("Sunset: " + convertEpochToDateTime(weather.getSunsetTime()));
        ((TextView) findViewById(R.id.main_activity_text_view_weather_temperature)).setText("Temperature: " + weather.getTemperature() + "°");
        ((TextView) findViewById(R.id.main_activity_text_view_weather_visibility)).setText("Visibility: " + weather.getVisibility());
        ((TextView) findViewById(R.id.main_activity_text_view_weather_description)).setText("Description: " + weather.getWeatherDescription());
        ((TextView) findViewById(R.id.main_activity_text_view_weather_wind)).setText("Wind: " + weather.getWindSpeedAndDegrees());
        ((CardView) findViewById(R.id.main_activity_card_view_weather)).setVisibility(View.VISIBLE);
        ((CardView) findViewById(R.id.main_activity_card_view_reminder)).setVisibility(View.VISIBLE);
        ((CardView) findViewById(R.id.main_activity_card_view_user)).setVisibility(View.VISIBLE);

        convertEpochToDateTime(weather.getSunriseTime());
    }

    private String convertEpochToDateTime(long epoch) {
        Date date = new Date(epoch * 1000L);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
        //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-7"));

        return simpleDateFormat.format(date);
    }
}
