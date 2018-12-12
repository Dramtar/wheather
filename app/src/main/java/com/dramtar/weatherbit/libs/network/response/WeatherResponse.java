package com.dramtar.weatherbit.libs.network.response;

import com.dramtar.weatherbit.libs.db.entity.ForecastEntityCurrent;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dramtar on 2018-12-06
 */
public class WeatherResponse extends Response {
    @SerializedName("data")
    private List<ForecastEntityCurrent> mForecasts;

    public ForecastEntityCurrent getForecast() {
        return mForecasts.get(0);
    }

    public List<ForecastEntityCurrent> getForecasts() {
        return mForecasts;
    }
}
