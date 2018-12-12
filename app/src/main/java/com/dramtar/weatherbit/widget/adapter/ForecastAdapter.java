package com.dramtar.weatherbit.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dramtar.weatherbit.R;
import com.dramtar.weatherbit.libs.model.Forecast;
import com.dramtar.weatherbit.widget.view.ImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dramtar on 2018-12-09
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private List<? extends Forecast> mForecasts;
    private Context mContext;
    private boolean isDayForecast;

    public ForecastAdapter(Context context, List<? extends Forecast> forecasts, boolean isDayForecast) {
        mContext = context;
        this.isDayForecast = isDayForecast;
        this.mForecasts = forecasts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forecast forecast = mForecasts.get(position);

        if (isDayForecast) {
            holder.mDateView.setText(forecast.getTimeString());
        } else {
            holder.mDateView.setText(forecast.getDateString());
        }

        holder.mTempView.setText(mContext.getString(R.string.temperature_place_holder, forecast.getTemperatureString()));
        holder.mWeatherIcon.setImageURL(forecast.getIllustrateWeather().getIconLink());
        holder.mWindDirView.setText(forecast.getWindDir());
        holder.mWindSpeedView.setText(mContext.getString(R.string.mc_place_holder, forecast.getWindSpeedInt()));
        holder.mRelativeHumidity.setText(mContext.getString(R.string.percentage_place_holder, forecast.getRelativeHumidity()));

        if (position == 0) {
            holder.mTitleRelativeHumidity.setVisibility(View.VISIBLE);
            holder.mTitleWindDirView.setVisibility(View.VISIBLE);
            holder.mTitleWindSpeedView.setVisibility(View.VISIBLE);
        } else {
            holder.mTitleRelativeHumidity.setVisibility(View.INVISIBLE);
            holder.mTitleWindDirView.setVisibility(View.INVISIBLE);
            holder.mTitleWindSpeedView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date_view)
        TextView mDateView;

        @BindView(R.id.temp_view)
        TextView mTempView;

        @BindView(R.id.wind_dir_view)
        TextView mWindDirView;

        @BindView(R.id.wind_speed_view)
        TextView mWindSpeedView;

        @BindView(R.id.relative_humidity_view)
        TextView mRelativeHumidity;

        @BindView(R.id.title_relative_humidity)
        TextView mTitleRelativeHumidity;

        @BindView(R.id.title_wind_dir)
        TextView mTitleWindDirView;

        @BindView(R.id.title_wind_spd)
        TextView mTitleWindSpeedView;

        @BindView(R.id.weather_icon)
        ImageView mWeatherIcon;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
