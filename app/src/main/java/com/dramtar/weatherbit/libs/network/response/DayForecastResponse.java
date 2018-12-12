package com.dramtar.weatherbit.libs.network.response;

import com.dramtar.weatherbit.libs.db.entity.ForecastEntityDay;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dramtar on 2018-12-06
 */
public class DayForecastResponse extends Response {
    @SerializedName("data")
    private List<ForecastEntityDay> mForecasts;

    @SerializedName("city_name")
    private String mCityName;

    public ForecastEntityDay getForecast() {
        return mForecasts.get(0);
    }

    public List<ForecastEntityDay> getForecasts() {
        return mForecasts;
    }

    @Override
    public void success() {
        super.success();
        for (ForecastEntityDay forecastEntityDay : mForecasts) {
            forecastEntityDay.setCityName(mCityName);
        }
    }
}
