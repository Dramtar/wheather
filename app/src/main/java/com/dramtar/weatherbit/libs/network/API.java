package com.dramtar.weatherbit.libs.network;

import com.dramtar.weatherbit.libs.network.response.Callback;
import com.dramtar.weatherbit.libs.network.response.CurrentWeatherResponse;
import com.dramtar.weatherbit.libs.network.response.DayForecastResponse;
import com.dramtar.weatherbit.libs.network.response.WeekForecastResponse;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Dramtar on 2018-12-06
 */
interface API {
    @GET("/current")
    void getCurrentForecast(@Query("key") String key, @Query("lang") String lang, @Query("lat") double latitude, @Query("lon") double longitude, Callback<CurrentWeatherResponse> callback);

    @GET("/forecast/daily")
    void getWeekForecast(@Query("key") String key, @Query("lang") String lang, @Query("lat") double latitude, @Query("lon") double longitude, @Query("days") int days, Callback<WeekForecastResponse> callback);

    @GET("/forecast/3hourly")
    void getDayForecast(@Query("key") String key, @Query("lang") String lang, @Query("lat") double latitude, @Query("lon") double longitude, @Query("days") int days, Callback<DayForecastResponse> callback);

}
