package com.lambdaschool.healthchaser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.lambdaschool.healthchaser.connectivity.Weather;
import com.lambdaschool.healthchaser.connectivity.WeatherDao;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public enum Tracking {SLEEP, MEALS, MOOD, WATER, EXERCISE, RESTROOM, HYGIENE, MEDITATION}

    public static LoggedInUser currentLoggedInUser;
    private static final int REQUEST_CODE_SIGN_IN = 55;


    TextView defaultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultTextView = findViewById(R.id.main_text_view);

        WeatherDao weatherDao = new WeatherDao();
        Weather weather = weatherDao.getWeather();

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

                defaultTextView.append(" " + currentLoggedInUser.getDisplayName());

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.navigation_menu_summary) {

        } else if (id == R.id.navigation_menu_sleep) {

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

        } else if (id == R.id.navigation_menu_share) {

        } else if (id == R.id.navigation_menu_send) {

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

}
