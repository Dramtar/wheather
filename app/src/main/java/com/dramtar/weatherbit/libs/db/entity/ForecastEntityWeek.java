package com.dramtar.weatherbit.libs.db.entity;

import androidx.room.Entity;

/**
 * Created by Dramtar on 2018-12-12
 */

@Entity(tableName = "forecast_week")
public class ForecastEntityWeek extends ForecastEntityCurrent {
    public ForecastEntityWeek() {
    }
}
