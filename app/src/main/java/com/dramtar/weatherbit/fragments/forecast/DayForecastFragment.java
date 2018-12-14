package com.dramtar.weatherbit.fragments.forecast;

import android.os.Bundle;
import android.view.View;

import com.dramtar.weatherbit.R;
import com.dramtar.weatherbit.fragments.base.BaseNetFragment;
import com.dramtar.weatherbit.libs.db.AppDB;
import com.dramtar.weatherbit.libs.model.Forecast;
import com.dramtar.weatherbit.libs.network.response.DayForecastResponse;
import com.dramtar.weatherbit.widget.adapter.ForecastAdapter;
import com.squareup.otto.Subscribe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.dramtar.weatherbit.libs.Utils.KEY_FORECAST;

/**
 * Created by Dramtar on 2018-12-09
 */
public class DayForecastFragment extends BaseNetFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private AppDB mDBHelper;
    private Forecast mForecast;

    public static DayForecastFragment newInstance(@NonNull Forecast forecast) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_FORECAST, forecast);

        DayForecastFragment fragment = new DayForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_forecasts;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDBHelper = AppDB.getInstance(getContext());

        Bundle args = getArguments();
        if (args != null) {
            mForecast = (Forecast) args.getSerializable(KEY_FORECAST);

            if (!mDBHelper.forecastDayDao().getForecastsByCity(mForecast.getCityName()).isEmpty()) {
                List<? extends Forecast> forecasts = mDBHelper.getForecastsDay(mForecast.getCityName());
                mRecyclerView.setAdapter(new ForecastAdapter(getActivity(), forecasts, true));
            }
        }
    }

    @Subscribe
    public void onDayForecastResponse(DayForecastResponse response) {
        mRecyclerView.setAdapter(new ForecastAdapter(getActivity(), response.getForecasts(), true));
        mDBHelper.updateForecasts(response.getForecasts());
    }

    @Override
    public void onResume() {
        super.onResume();
        mNetWork.getDayForecast(mForecast.getLatitude(), mForecast.getLongitude());
    }
}
