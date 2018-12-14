package com.dramtar.weatherbit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.dramtar.weatherbit.fragments.MapFragment;
import com.dramtar.weatherbit.fragments.forecast.DetailForecastFragment;
import com.dramtar.weatherbit.libs.db.AppDB;
import com.dramtar.weatherbit.libs.model.Forecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static com.dramtar.weatherbit.libs.Utils.KEY_CURRENT_CITY;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        double currentTimeMillis = (double) System.currentTimeMillis();
        String currentCityName = prefs.getString(KEY_CURRENT_CITY, "");

        Forecast forecast = AppDB.getInstance(this).getLastCurrentForecast(currentCityName, currentTimeMillis);

        Fragment fragment;
        if (forecast != null) {
            fragment = DetailForecastFragment.newInstance(forecast);
        } else {
            fragment = new MapFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
    }
}