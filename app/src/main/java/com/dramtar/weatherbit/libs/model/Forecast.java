package com.dramtar.weatherbit.libs.model;


import com.dramtar.weatherbit.libs.db.entity.ForecastEntityCurrent;

import java.io.Serializable;

/**
 * Created by Dramtar on 2018-12-12
 */
public interface Forecast extends Serializable {

    int getWindSpeedInt();

    int getRelativeHumidity();

    double getLatitude();

    double getLongitude();

    double getTimeStamp();

    String getWindDir();

    String getTemperatureString();

    String getCityName();

    String getTimeString();

    String getDateString();

    ForecastEntityCurrent.IllustrateWeather getIllustrateWeather();

}

