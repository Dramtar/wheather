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

@Entity()
public class ForecastEntityCurrent implements Forecast {
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

    @SerializedName("weather")
    private IllustrateWeather mIllustrationWeather;

    public ForecastEntityCurrent() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public IllustrateWeather getIllustrationWeather() {
        return mIllustrationWeather;
    }

    public void setIllustrationWeather(IllustrateWeather mIllustrationWeather) {
        this.mIllustrationWeather = mIllustrationWeather;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double mWindSpeed) {
        this.mWindSpeed = mWindSpeed;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    @Override
    public String getWindDir() {
        return mWindDir;
    }

    public void setWindDir(String mWindDir) {
        this.mWindDir = mWindDir;
    }

    @Override
    public String getTemperatureString() {
        return String.valueOf(mTemperature);
    }

    @Override
    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String mCityName) {
        this.mCityName = mCityName;
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
    public IllustrateWeather getIllustrateWeather() {
        return mIllustrationWeather;
    }

    @Override
    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    @Override
    public int getWindSpeedInt() {
        return (int) mWindSpeed;
    }

    @Override
    public int getRelativeHumidity() {
        return mRelativeHumidity;
    }

    public void setRelativeHumidity(int mRelativeHumidity) {
        this.mRelativeHumidity = mRelativeHumidity;
    }

    @Override
    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    @Override
    public double getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(double mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

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
}
