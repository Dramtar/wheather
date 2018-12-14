package com.dramtar.weatherbit.libs.db;

import android.content.Context;

import com.dramtar.weatherbit.libs.db.converter.IllustrationConverter;
import com.dramtar.weatherbit.libs.db.dao.ForecastDaoDay;
import com.dramtar.weatherbit.libs.db.dao.ForecastDaoWeek;
import com.dramtar.weatherbit.libs.db.entity.ForecastEntityCurrent;
import com.dramtar.weatherbit.libs.db.entity.ForecastEntityDay;
import com.dramtar.weatherbit.libs.db.entity.ForecastEntityWeek;
import com.dramtar.weatherbit.libs.model.Forecast;

import java.util.List;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Created by Dramtar on 2018-12-12
 */
@Database(entities = {ForecastEntityCurrent.class, ForecastEntityDay.class, ForecastEntityWeek.class}, version = 1, exportSchema = false)
@TypeConverters(IllustrationConverter.class)
public abstract class AppDB extends RoomDatabase {
    public static final String DATABASE_NAME = "forecasts-database";
    private static AppDB mInstance;

    public static AppDB getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, DATABASE_NAME)
                    .allowMainThreadQueries() //TODO!!! implemented this in background!!!
                    .build();
        }
        return mInstance;
    }

    public abstract ForecastDaoDay forecastDayDao();

    public abstract ForecastDaoWeek forecastWeekDao();

    public void updateForecasts(List<ForecastEntityDay> forecasts) {
        forecastDayDao().deleteByCity(forecasts.get(0).getCityName());
        forecastDayDao().insertForecasts(forecasts);
    }

    public List<ForecastEntityDay> getForecastsDay(String city) {
        return forecastDayDao().getForecastsByCity(city);
    }

    public void updateForecastsWeek(List<ForecastEntityWeek> forecasts) {
        forecastWeekDao().deleteByCity(forecasts.get(0).getCityName());
        forecastWeekDao().insertForecasts(forecasts);
    }

    public List<ForecastEntityWeek> getForecastsWeek(String city) {
        return forecastWeekDao().getForecastsByCity(city);
    }

    public Forecast getLastCurrentForecast(String city, double currentTimeStamp) {
        return forecastWeekDao().getLastCurrentForecast(city, currentTimeStamp);
    }
}
