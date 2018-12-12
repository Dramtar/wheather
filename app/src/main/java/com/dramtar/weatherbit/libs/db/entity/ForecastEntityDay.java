package com.dramtar.weatherbit.libs.db.entity;

import com.dramtar.weatherbit.libs.Utils;
import com.dramtar.weatherbit.libs.model.Forecast;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.dramtar.weatherbit.libs.Utils.ICON_LINK_POSTFIX;
import static com.dramtar.weatherbit.libs.Utils.ICON_LINK_PREFIX;


/**
 * Created by Dramtar on 2018-12-12
 */

@Entity(tableName = "forecast_day")
public class ForecastEntityDay extends ForecastEntityCurrent {
    public ForecastEntityDay() {
    }


}
