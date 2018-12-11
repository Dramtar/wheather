package com.dramtar.weatherbit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dramtar.weatherbit.MainActivity;
import com.dramtar.weatherbit.R;
import com.dramtar.weatherbit.libs.db.ForecastReaderDbHelper;
import com.dramtar.weatherbit.libs.network.Network;
import com.dramtar.weatherbit.libs.network.response.DayForecastResponse;
import com.dramtar.weatherbit.model.Forecast;
import com.dramtar.weatherbit.widget.adapter.ForecastAdapter;
import com.squareup.otto.Subscribe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dramtar on 2018-12-09
 */
public class DayForecastFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Unbinder mUnBinder;
    private ForecastReaderDbHelper mDBHelper;
    private Forecast mForecast;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_forecasts, container, false);
        mUnBinder = ButterKnife.bind(this, view);

        mDBHelper = new ForecastReaderDbHelper(getContext());
        mForecast = MainActivity.getForecast();

        if (mDBHelper.checkIsExistForecastDay(mForecast.getCityName())) {
            List<Forecast> forecasts = mDBHelper.readAllForecastDay(mForecast.getCityName());
            mRecyclerView.setAdapter(new ForecastAdapter(getActivity(), forecasts, true));
        }
        return view;
    }

    @Subscribe
    public void onDayForecastResponse(DayForecastResponse response) {
        mRecyclerView.setAdapter(new ForecastAdapter(getActivity(), response.getForecasts(), true));
        mDBHelper.writeAllForecastDay(response.getForecasts());
    }

    @Override
    public void onResume() {
        super.onResume();
        Network.getInstance().getDayForecast(mForecast.getLatitude(), mForecast.getLongitude());
    }

    @Override
    public void onDestroy() {
        mUnBinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        Network.register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        Network.unregister(this);
        super.onStop();
    }
}
