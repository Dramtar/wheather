package com.dramtar.weatherbit.libs.db;

import android.provider.BaseColumns;

/**
 * Created by Dramtar on 2018-12-09
 */
public class DB {
    public static final class ForecastReaderContract {
        private ForecastReaderContract() {
        }

        public static class ForecastEntryDay implements BaseColumns {
            public static final String TABLE_NAME = "forecasts_day";
            public static final String COLUMN_NAME_CITY = "city";
            public static final String COLUMN_NAME_TIME_STAMP = "time_stamp";
            public static final String COLUMN_NAME_TEMP = "temperature";
            public static final String COLUMN_NAME_LATITUDE = "latitude";
            public static final String COLUMN_NAME_LONGITUDE = "longitude";
            public static final String COLUMN_NAME_RH = "rh";
            public static final String COLUMN_NAME_WIND_SPD = "wind_spd";
            public static final String COLUMN_NAME_WIND_DIR = "wind_dir";
            public static final String COLUMN_NAME_ICON_LINK = "icon_link";
            public static final String COLUMN_NAME_DESCRIPTION = "description";
        }

        public static class ForecastEntryWeek implements BaseColumns {
            public static final String TABLE_NAME = "forecasts_week";
            public static final String COLUMN_NAME_CITY = "city";
            public static final String COLUMN_NAME_TIME_STAMP = "time_stamp";
            public static final String COLUMN_NAME_TEMP = "temperature";
            public static final String COLUMN_NAME_LATITUDE = "latitude";
            public static final String COLUMN_NAME_LONGITUDE = "longitude";
            public static final String COLUMN_NAME_RH = "rh";
            public static final String COLUMN_NAME_WIND_SPD = "wind_spd";
            public static final String COLUMN_NAME_WIND_DIR = "wind_dir";
            public static final String COLUMN_NAME_ICON_LINK = "icon_link";
            public static final String COLUMN_NAME_DESCRIPTION = "description";
        }
    }

}
