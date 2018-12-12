package com.dramtar.weatherbit.libs.db.converter;

import com.dramtar.weatherbit.libs.Utils;
import com.dramtar.weatherbit.libs.db.entity.ForecastEntityCurrent;

import androidx.room.TypeConverter;

/**
 * Created by Dramtar on 2018-12-12
 */
public class IllustrationConverter {
    @TypeConverter
    public static ForecastEntityCurrent.IllustrateWeather toIllustration(String string) {
        return string == null ? null : Utils.fromJson(string, ForecastEntityCurrent.IllustrateWeather.class);
    }

    @TypeConverter
    public static String toString(ForecastEntityCurrent.IllustrateWeather illustrateWeather) {
        return illustrateWeather == null ? null : Utils.toJson(illustrateWeather);
    }
}
