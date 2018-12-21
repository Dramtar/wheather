package com.dramtar.weatherbit.libs.network.response;

import com.dramtar.weatherbit.libs.db.entity.ForecastEntityWeek;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dramtar on 2018-12-06
 */
public class WeekForecastResponse extends Response {
    @SerializedName("data")
    private List<ForecastEntityWeek> mForecasts;

    @SerializedName("city_name")
    private String mCityName;

    @SerializedName("lat")
    private double mLatitude;

    @SerializedName("lon")
    private double mLongitude;

    public ForecastEntityWeek getForecast() {
        return mForecasts.get(0);
    }

    public List<ForecastEntityWeek> getForecasts() {
        return mForecasts;
    }

    @Override
    public void success() {
        super.success();
        for (ForecastEntityWeek forecastEntityDay : mForecasts) {
            forecastEntityDay.setCityName(mCityName);
            forecastEntityDay.setLatitude(mLatitude);
            forecastEntityDay.setLongitude(mLongitude);
        }
    }
}
