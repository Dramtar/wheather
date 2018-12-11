package com.dramtar.weatherbit.libs.network.response;

import com.dramtar.weatherbit.model.Forecast;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dramtar on 2018-12-06
 */
public class CurrentWeatherResponse extends Response {
    @SerializedName("data")
    private List<Forecast> mForecasts;

    public Forecast getForecast() {
        return mForecasts.get(0);
    }
}
