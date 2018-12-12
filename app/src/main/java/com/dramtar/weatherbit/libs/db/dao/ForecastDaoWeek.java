package com.dramtar.weatherbit.libs.db.dao;

import com.dramtar.weatherbit.libs.db.entity.ForecastEntityCurrent;
import com.dramtar.weatherbit.libs.db.entity.ForecastEntityWeek;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by Dramtar on 2018-12-12
 */
@Dao
public interface ForecastDaoWeek {
    @Query("SELECT * FROM forecast_week where mCityName = :city ORDER BY mTimeStamp ASC")
    List<ForecastEntityWeek> getForecastsByCity(String city);

    @Query("SELECT * FROM forecast_week where mCityName = :city ORDER BY ABS(mTimeStamp - :timeStamp) LIMIT 1")
    ForecastEntityWeek getLastCurrentForecast(String city, double timeStamp);

    @Insert
    void insertForecasts(List<ForecastEntityWeek> forecasts);

    @Query("DELETE FROM forecast_week WHERE mCityName = :city")
    void deleteByCity(String city);
}
