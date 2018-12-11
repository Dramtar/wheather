package com.dramtar.weatherbit.libs.network.response;

import com.dramtar.weatherbit.model.Forecast;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dramtar on 2018-12-06
 */
public class DayForecastResponse extends Response {
    @SerializedName("data")
    private List<Forecast> mForecasts;

    @SerializedName("city_name")
    private String mCityName;

    @Override
    public void success() {
        super.success();
        for (Forecast forecast : mForecasts) {
            Forecast.Helper.setCityName(forecast, mCityName);
        }
    }

    public List<Forecast> getForecasts() {
        return mForecasts;
    }
}
