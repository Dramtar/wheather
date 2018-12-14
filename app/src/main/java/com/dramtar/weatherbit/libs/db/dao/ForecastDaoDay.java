package com.dramtar.weatherbit.libs.db.dao;

import com.dramtar.weatherbit.libs.db.entity.ForecastEntityDay;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by Dramtar on 2018-12-12
 */
@Dao
public interface ForecastDaoDay {
    @Query("SELECT * FROM forecast_day where mCityName = :city")
    List<ForecastEntityDay> getForecastsByCity(String city);

    @Insert
    void insertForecasts(List<ForecastEntityDay> forecasts);

    @Query("DELETE FROM forecast_day WHERE mCityName = :city")
    void deleteByCity(String city);
}
