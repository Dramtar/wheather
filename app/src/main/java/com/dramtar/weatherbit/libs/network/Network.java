package com.dramtar.weatherbit.libs.network;

import android.util.Log;

import com.dramtar.weatherbit.BuildConfig;
import com.dramtar.weatherbit.libs.network.response.Callback;
import com.dramtar.weatherbit.libs.network.response.CurrentWeatherResponse;
import com.dramtar.weatherbit.libs.network.response.DayForecastResponse;
import com.dramtar.weatherbit.libs.network.response.Response;
import com.dramtar.weatherbit.libs.network.response.WeekForecastResponse;
import com.squareup.otto.Bus;

import java.util.Locale;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Dramtar on 2018-12-06
 */
public class Network {
    private static final Bus BUS = new Bus();
    private static final String TAG = Network.class.getSimpleName();
    private static final int NUM_OF_DAY_IN_DAILY_FORECAST = 1;
    private static final int NUM_OF_DAY_IN_WEEKLY_FORECAST = 7;
    private static Network sInstance;
    private static String language = Locale.getDefault().getLanguage();

    private final RestAdapter mAdapter = new RestAdapter.Builder()
            .setEndpoint(BuildConfig.WEATHERBIT_URL)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();
    private final API mAPI = mAdapter.create(API.class);

    public static Network getInstance() {
        if (sInstance == null) {
            sInstance = new Network();
        }
        return sInstance;
    }

    public static void register(@NonNull Object object) {
        BUS.register(object);
    }

    public static void unregister(@NonNull Object object) {
        BUS.unregister(object);
    }

    public void getCurrentWeather(double latitude, double longitude) {
        mAPI.getCurrentForecast(BuildConfig.WEATHERBIT_KEY, language, latitude, longitude, new ResponseCallback<CurrentWeatherResponse>());
    }

    public void getDayForecast(double latitude, double longitude) {
        mAPI.getDayForecast(BuildConfig.WEATHERBIT_KEY, language, latitude, longitude, NUM_OF_DAY_IN_DAILY_FORECAST, new ResponseCallback<DayForecastResponse>());
    }

    public void getWeekForecast(double latitude, double longitude) {
        mAPI.getWeekForecast(BuildConfig.WEATHERBIT_KEY, language, latitude, longitude, NUM_OF_DAY_IN_WEEKLY_FORECAST, new ResponseCallback<WeekForecastResponse>());
    }

    private void post(Object event) {
        BUS.post(event);
    }

    private class ResponseCallback<T extends Response> extends Callback<T> {

        @Override
        @CallSuper
        public void success(T response) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "success");
            }
            response.success();
            post(response);
        }

        @Override
        @CallSuper
        public void failure(RetrofitError error) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "failure");
                Log.d(TAG, "kind " + error.getKind().name());
                Log.d(TAG, "error message: " + error.getMessage());
            }
            post(error);
        }
    }
}
