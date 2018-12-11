package com.dramtar.weatherbit.model;

import com.dramtar.weatherbit.libs.Utils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Created by Dramtar on 2018-12-06
 */
public class Forecast implements Serializable {
    private static final String ICON_LINK_PREFIX = "https://www.weatherbit.io/static/img/icons/";
    private static final String ICON_LINK_POSTFIX = ".png";

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

    public String getWindDir() {
        return mWindDir;
    }

    public String getTemperature() {
        return String.valueOf(mTemperature);
    }

    public String getCityName() {
        return mCityName;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }


    public double getTimeStamp() {
        return mTimeStamp;
    }

    public int getWindSpeed() {
        return (int) mWindSpeed;
    }

    public String getTimeString() {
        return Utils.formatDate(Utils.TIME_PATTERN, (long) getTimeStamp() * 1000);
    }

    public String getDateString() {
        return Utils.formatDate(Utils.DATE_TIME_PATTERN, (long) getTimeStamp() * 1000);
    }

    public int getRelativeHumidity() {
        return mRelativeHumidity;
    }


    public IllustrateWeather getIllustrationWeather() {
        return mIllustrationWeather;
    }

    public static class IllustrateWeather implements Serializable {
        @SerializedName("icon")
        private String mIconCode;
        @SerializedName("description")
        private String mDescription;

        public String getIconLink() {
            return ICON_LINK_PREFIX + mIconCode + ICON_LINK_POSTFIX;
        }

        public String getIconCode() {
            return mIconCode;
        }

        public void setIconCode(String mIconLink) {
            this.mIconCode = mIconLink;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String mDescription) {
            this.mDescription = mDescription;
        }
    }

    public static final class Helper {

        public static void setWindDir(@NonNull Forecast forecast, String mWindDir) {
            forecast.mWindDir = mWindDir;
        }

        public static void setTemperature(@NonNull Forecast forecast, double mTemperature) {
            forecast.mTemperature = mTemperature;
        }

        public static void setTimeStamp(@NonNull Forecast forecast, double mTimeStamp) {
            forecast.mTimeStamp = mTimeStamp;
        }

        public static void setLatitude(@NonNull Forecast forecast, double mLatitude) {
            forecast.mLatitude = mLatitude;
        }

        public static void setLongitude(@NonNull Forecast forecast, double mLongitude) {
            forecast.mLongitude = mLongitude;
        }

        public static void setRelativeHumidity(@NonNull Forecast forecast, int mRelativeHumidity) {
            forecast.mRelativeHumidity = mRelativeHumidity;
        }

        public static void setWindSpeed(@NonNull Forecast forecast, double mWindSpeed) {
            forecast.mWindSpeed = mWindSpeed;
        }

        public static void setCityName(@NonNull Forecast forecast, String mCityName) {
            forecast.mCityName = mCityName;
        }

        public static void setIllustrationWeather(@NonNull Forecast forecast, IllustrateWeather mIllustrationWeather) {
            forecast.mIllustrationWeather = mIllustrationWeather;
        }
    }
}
