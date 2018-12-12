package com.dramtar.weatherbit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.dramtar.weatherbit.fragments.DetailForecastFragment;
import com.dramtar.weatherbit.fragments.MapFragment;
import com.dramtar.weatherbit.libs.Utils;
import com.dramtar.weatherbit.libs.db.entity.ForecastEntityCurrent;
import com.dramtar.weatherbit.libs.model.Forecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_FORECAST = "forecast";
    private static Forecast mForecast;
    private static SharedPreferences mPrefs;

    public static Forecast getForecast() {
        return mForecast;
    }

    public static void setForecast(Forecast forecast) {
        mForecast = forecast;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mForecast = Utils.fromJson(mPrefs.getString(KEY_FORECAST, null), ForecastEntityCurrent.class);

        Fragment fragment;
        if (mForecast != null) {
            fragment = DetailForecastFragment.newInstance(mForecast);
        } else {
            fragment = new MapFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void saveCurrentForecast(ForecastEntityCurrent mForecast) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(KEY_FORECAST, Utils.toJson(mForecast));
        editor.apply();
    }
}
