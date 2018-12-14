package com.dramtar.weatherbit.libs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;


/**
 * Created by Dramtar on 2018-12-09
 */
public final class Utils {

    public static final String KEY_FORECAST = "forecast";
    public static final String KEY_CURRENT_CITY = "city_name";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String DATE_TIME_PATTERN = "EEE, MMM d";
    public static final String ICON_LINK_PREFIX = "https://www.weatherbit.io/static/img/icons/";
    public static final String ICON_LINK_POSTFIX = ".png";
    private static final Gson GSON = new Gson();

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat();

    static {
        sDateFormat.setTimeZone(TimeZone.getDefault());
    }

    @PermissionChecker.PermissionResult
    public static boolean checkPermission(@NonNull Context context, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static String formatDate(String pattern, long milliSeconds) {
        sDateFormat.applyPattern(pattern);
        return sDateFormat.format(milliSeconds);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String string, Class<T> classOfT) {
        return GSON.fromJson(string, classOfT);
    }

    public static boolean isHasInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
