package com.dramtar.weatherbit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dramtar.weatherbit.MainActivity;
import com.dramtar.weatherbit.R;
import com.dramtar.weatherbit.libs.model.Forecast;
import com.dramtar.weatherbit.libs.network.Network;
import com.dramtar.weatherbit.libs.network.response.CurrentWeatherResponse;
import com.dramtar.weatherbit.libs.network.response.WeatherResponse;
import com.dramtar.weatherbit.widget.view.ImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.squareup.otto.Subscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit.RetrofitError;

/**
 * Created by Dramtar on 2018-12-09
 */
public class DetailForecastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String KEY_FORECAST = "market";

    @BindView(R.id.city_name)
    TextView mCityName;

    @BindView(R.id.temp_view)
    TextView mTempView;

    @BindView(R.id.description_view)
    TextView mDescriptionView;

    @BindView(R.id.weather_icon)
    ImageView mWeatherIcon;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefresher;

    @BindView(R.id.coordinator)
    CoordinatorLayout mContainer;

    private Unbinder mUnBinder;
    private Forecast mForecast;

    public static DetailForecastFragment newInstance(@NonNull Forecast forecast) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_FORECAST, forecast);

        DetailForecastFragment fragment = new DetailForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_forecast, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        mRefresher.setOnRefreshListener(this);

        getActivity().setTitle(R.string.title_detail_fragment);

        Bundle args = getArguments();
        if (args != null) {
            mForecast = (Forecast) getArguments().getSerializable(KEY_FORECAST);
            update();
        }

        mRefresher.setRefreshing(true);

        mViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), getActivity()));
        mTabLayout.setupWithViewPager(mViewPager);
        refresh();
        return view;
    }

    private void refresh() {
        mRefresher.setRefreshing(false);
        Network.getInstance().getCurrentWeather(mForecast.getLatitude(), mForecast.getLongitude());
    }

    @Subscribe
    public void onCurrentWeatherResponse(CurrentWeatherResponse response) {
        mRefresher.setRefreshing(false);
        mForecast = response.getForecast();
        MainActivity.saveCurrentForecast(response.getForecast());
        update();
    }

    @Subscribe
    public void onRetrofitError(RetrofitError error) {
        mRefresher.setRefreshing(false);
        Snackbar snackbar = Snackbar.make(mContainer, R.string.no_internet_connection, Snackbar.LENGTH_LONG);
        if (error.getKind() == RetrofitError.Kind.NETWORK && !snackbar.isShown()) {
            snackbar.show();
        }
    }

    private void update() {
        mWeatherIcon.setImageURL(mForecast.getIllustrateWeather().getIconLink());
        mTempView.setText(getString(R.string.temperature_place_holder, mForecast.getTemperatureString()));
        mDescriptionView.setText(mForecast.getIllustrateWeather().getDescription());
        mCityName.setText(mForecast.getCityName());
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @OnClick(R.id.to_map)
    public void onClickToMap() {
        getFragmentManager().beginTransaction().replace(R.id.container, new MapFragment()).commitAllowingStateLoss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        Network.unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Network.register(this);
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private static final int PAGE_DAY_FORECAST = 0;
        private static final int PAGE_WEEK_FORECAST = 1;
        private static final int PAGES_COUNT = 2;
        private Context mContext;

        private PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public int getCount() {
            return PAGES_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getString(R.string.day_tab);
                case 1:
                    return mContext.getString(R.string.week_tab);
                default:
                    return null;
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_DAY_FORECAST:
                    return new DayForecastFragment();
                case PAGE_WEEK_FORECAST:
                    return new WeekForecastFragment();
            }
            return null;
        }
    }
}