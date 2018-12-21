package com.dramtar.weatherbit.fragments.forecast;

import android.os.Bundle;
import android.view.View;

import com.dramtar.weatherbit.R;
import com.dramtar.weatherbit.fragments.base.BaseNetFragment;
import com.dramtar.weatherbit.libs.db.AppDB;
import com.dramtar.weatherbit.libs.model.Forecast;
import com.dramtar.weatherbit.libs.network.response.WeekForecastResponse;
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
public class WeekForecastFragment extends BaseNetFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private AppDB mDBHelper;
    private Forecast mForecast;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_forecasts;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getActivity().getIntent().getExtras();
        if (args != null) {
            mForecast = (Forecast) args.getSerializable(KEY_FORECAST);

            mDBHelper = AppDB.getInstance(getContext());
            if (!mDBHelper.getForecastsWeek(mForecast.getCityName()).isEmpty()) {
                List<? extends Forecast> forecasts = mDBHelper.getForecastsWeek(mForecast.getCityName());
                mRecyclerView.setAdapter(new ForecastAdapter(getActivity(), forecasts, false));
            }
        }
    }

    @Subscribe
    public void onWeekForecastResponse(WeekForecastResponse response) {
        mRecyclerView.setAdapter(new ForecastAdapter(getActivity(), response.getForecasts(), false));
        mDBHelper.updateForecastsWeek(response.getForecasts());
    }

    @Override
    public void onResume() {
        super.onResume();
        mNetWork.getWeekForecast(mForecast.getLatitude(), mForecast.getLongitude());
    }
}
