package com.dramtar.weatherbit.libs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dramtar.weatherbit.model.Forecast;

import java.util.ArrayList;
import java.util.List;

import static com.dramtar.weatherbit.libs.db.DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_CITY;
import static com.dramtar.weatherbit.libs.db.DB.ForecastReaderContract.ForecastEntryDay.TABLE_NAME;

/**
 * Created by Dramtar on 2018-12-09
 */

public class ForecastReaderDbHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES_DAY = "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    DB.ForecastReaderContract.ForecastEntryDay._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    COLUMN_NAME_CITY + " TEXT ," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TIME_STAMP + " DOUBLE," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TEMP + " DOUBLE," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_LATITUDE + " DOUBLE," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_LONGITUDE + " DOUBLE," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_RH + " INTEGER," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_WIND_SPD + " DOUBLE," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_WIND_DIR + " TEXT," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_ICON_LINK + " TEXT," +
                    DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_DAY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_WEEK = "CREATE TABLE " +
            DB.ForecastReaderContract.ForecastEntryWeek.TABLE_NAME + " (" +
            DB.ForecastReaderContract.ForecastEntryWeek._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_CITY + " TEXT ," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_TIME_STAMP + " DOUBLE," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_TEMP + " DOUBLE," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_LATITUDE + " DOUBLE," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_LONGITUDE + " DOUBLE," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_RH + " INTEGER," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_WIND_SPD + " DOUBLE," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_WIND_DIR + " TEXT," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_ICON_LINK + " TEXT," +
            DB.ForecastReaderContract.ForecastEntryWeek.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_WEEK = "DROP TABLE IF EXISTS " + DB.ForecastReaderContract.ForecastEntryWeek.TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ForecastReader.db";

    public ForecastReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_DAY);
        db.execSQL(SQL_CREATE_ENTRIES_WEEK);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_DAY);
        db.execSQL(SQL_DELETE_ENTRIES_WEEK);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void writeAllForecastDay(List<Forecast> forecasts) {
        writeAll(forecasts, TABLE_NAME);
    }

    public void writeAllForecastWeek(List<Forecast> forecasts) {
        writeAll(forecasts, DB.ForecastReaderContract.ForecastEntryWeek.TABLE_NAME);
    }

    private void writeAll(List<Forecast> forecasts, String tableName) {
        SQLiteDatabase db = getWritableDatabase();

        String cityName = forecasts.get(0).getCityName();
        if (checkIsExist(cityName, tableName)) {
            deleteByCity(cityName, tableName);
        }
        for (Forecast forecast : forecasts) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_CITY, forecast.getCityName());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TIME_STAMP, forecast.getTimeStamp());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TEMP, forecast.getTemperature());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_LATITUDE, forecast.getLatitude());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_LONGITUDE, forecast.getLongitude());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_RH, forecast.getRelativeHumidity());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_WIND_SPD, forecast.getWindSpeed());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_WIND_DIR, forecast.getWindDir());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_ICON_LINK, forecast.getIllustrationWeather().getIconCode());
            values.put(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_DESCRIPTION, forecast.getIllustrationWeather().getDescription());
            db.insert(tableName, null, values);
        }
    }

    public void deleteByCityForecastDay(String value) {
        deleteByCity(value, TABLE_NAME);
    }

    public void deleteByCityForecastWeek(String value) {
        deleteByCity(value, DB.ForecastReaderContract.ForecastEntryWeek.TABLE_NAME);
    }

    private void deleteByCity(String value, String tableName) {
        String selection = COLUMN_NAME_CITY + " LIKE ?";
        String[] selectionArgs = {value};
        getWritableDatabase().delete(tableName, selection, selectionArgs);
    }

    public boolean checkIsExistForecastDay(String value) {
        return checkIsExist(value, TABLE_NAME);
    }

    public boolean checkIsExistForecastWeek(String value) {
        return checkIsExist(value, DB.ForecastReaderContract.ForecastEntryWeek.TABLE_NAME);
    }


    private boolean checkIsExist(String value, String tableName) {
        Cursor cursor = getReadableDatabase().rawQuery("Select * from " + tableName + " where " + COLUMN_NAME_CITY + " = '" + value + "'", null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Forecast getClosestForecast(long timeStamp, String value) {
        Cursor cursor = getReadableDatabase().rawQuery("Select * from " + DB.ForecastReaderContract.ForecastEntryWeek.TABLE_NAME +
                " where " + COLUMN_NAME_CITY + " = '" + value + "'" +
                " ORDER BY ABS(" + (double) timeStamp + " - time_stamp) LIMIT 1", null);

        Forecast forecast = new Forecast();

        Forecast.Helper.setCityName(forecast, cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_CITY)));
        Forecast.Helper.setTimeStamp(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TIME_STAMP)));
        Forecast.Helper.setTemperature(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TEMP)));
        Forecast.Helper.setLatitude(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_LATITUDE)));
        Forecast.Helper.setLongitude(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_LONGITUDE)));
        Forecast.Helper.setRelativeHumidity(forecast, cursor.getInt(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_RH)));
        Forecast.Helper.setWindSpeed(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_WIND_SPD)));
        Forecast.Helper.setWindDir(forecast, cursor.getString(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_WIND_DIR)));

        Forecast.IllustrateWeather illustrateWeather = new Forecast.IllustrateWeather();
        illustrateWeather.setIconCode(cursor.getString(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_ICON_LINK)));
        illustrateWeather.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_DESCRIPTION)));

        Forecast.Helper.setIllustrationWeather(forecast, illustrateWeather);
        cursor.close();
        return forecast;
    }

    public List<Forecast> readAllForecastDay(String city) {
        return readAll(city, TABLE_NAME);
    }

    public List<Forecast> readAllForecastWeek(String city) {
        return readAll(city, DB.ForecastReaderContract.ForecastEntryWeek.TABLE_NAME);
    }

    private List<Forecast> readAll(String city, String tableName) {
        List<Forecast> forecasts = new ArrayList<>();

        String selection = COLUMN_NAME_CITY + " = ?";
        String[] selectionArgs = {city};
        String sortOrder = DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TIME_STAMP + " ASC";
        Cursor cursor = getReadableDatabase().query(tableName, null, selection, selectionArgs, null, null, sortOrder);

        while (cursor.moveToNext()) {
            Forecast forecast = new Forecast();

            Forecast.Helper.setCityName(forecast, cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_CITY)));
            Forecast.Helper.setTimeStamp(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TIME_STAMP)));
            Forecast.Helper.setTemperature(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_TEMP)));
            Forecast.Helper.setLatitude(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_LATITUDE)));
            Forecast.Helper.setLongitude(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_LONGITUDE)));
            Forecast.Helper.setRelativeHumidity(forecast, cursor.getInt(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_RH)));
            Forecast.Helper.setWindSpeed(forecast, cursor.getDouble(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_WIND_SPD)));
            Forecast.Helper.setWindDir(forecast, cursor.getString(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_WIND_DIR)));

            Forecast.IllustrateWeather illustrateWeather = new Forecast.IllustrateWeather();
            illustrateWeather.setIconCode(cursor.getString(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_ICON_LINK)));
            illustrateWeather.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DB.ForecastReaderContract.ForecastEntryDay.COLUMN_NAME_DESCRIPTION)));

            Forecast.Helper.setIllustrationWeather(forecast, illustrateWeather);

            forecasts.add(forecast);
        }
        cursor.close();
        return forecasts;
    }
}
