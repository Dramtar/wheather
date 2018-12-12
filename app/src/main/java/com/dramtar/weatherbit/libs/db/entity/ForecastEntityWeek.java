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
@Entity(tableName = "forecast_week")
public class ForecastEntityWeek implements Forecast {
    public ForecastEntityWeek() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @SerializedName("wind_cdir")
    private String mWindDir;

    @SerializedName("temp")
    private double mTemperature;

    @SerializedName("ts")
    private double mTimeStamp;

    @SerializedName("lat")
    private double mLatitude;

    @SerializedName("lon")
    private double mLongitude;

    @SerializedName("rh")
    private int mRelativeHumidity;

    @SerializedName("wind_spd")
    private double mWindSpeed;

    @SerializedName("city_name")
    private String mCityName;

    public ForecastEntityCurrent.IllustrateWeather getIllustrationWeather() {
        return mIllustrationWeather;
    }

    @SerializedName("weather")
    private ForecastEntityCurrent.IllustrateWeather mIllustrationWeather;

    public static class IllustrateWeather implements Serializable {
        @SerializedName("icon")
        private String mIconCode;

        @SerializedName("description")
        private String mDescription;

        public String getIconCode() {
            return mIconCode;
        }

        public String getDescription() {
            return mDescription;
        }

        public String getIconLink() {
            return ICON_LINK_PREFIX + mIconCode + ICON_LINK_POSTFIX;
        }
    }

    @Override
    public int getWindSpeedInt() {
        return (int)mWindSpeed;
    }

    @Override
    public int getRelativeHumidity() {
        return mRelativeHumidity;
    }


    public double getWindSpeed() {
        return mWindSpeed;
    }

    @Override
    public double getLatitude() {
        return mLatitude;
    }

    @Override
    public double getLongitude() {
        return mLongitude;
    }

    @Override
    public double getTimeStamp() {
        return mTimeStamp;
    }

    public double getTemperature() {
        return mTemperature;
    }

    @Override
    public String getWindDir() {
        return mWindDir;
    }

    @Override
    public String getTemperatureString() {
        return String.valueOf(mTemperature);
    }

    @Override
    public String getCityName() {
        return mCityName;
    }

    @Override
    public String getTimeString() {
        return Utils.formatDate(Utils.TIME_PATTERN, (long) getTimeStamp() * 1000);
    }

    @Override
    public String getDateString() {
        return Utils.formatDate(Utils.DATE_TIME_PATTERN, (long) getTimeStamp() * 1000);
    }

    @Override
    public ForecastEntityCurrent.IllustrateWeather getIllustrateWeather() {
        return mIllustrationWeather;
    }

    public void setWindDir(String mWindDir) {
        this.mWindDir = mWindDir;
    }

    public void setTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    public void setTimeStamp(double mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public void setRelativeHumidity(int mRelativeHumidity) {
        this.mRelativeHumidity = mRelativeHumidity;
    }

    public void setWindSpeed(double mWindSpeed) {
        this.mWindSpeed = mWindSpeed;
    }

    public void setCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public void setIllustrationWeather(ForecastEntityCurrent.IllustrateWeather mIllustrationWeather) {
        this.mIllustrationWeather = mIllustrationWeather;
    }
}
