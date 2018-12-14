package com.dramtar.weatherbit.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dramtar.weatherbit.R;
import com.dramtar.weatherbit.fragments.base.BaseNetFragment;
import com.dramtar.weatherbit.fragments.forecast.DetailForecastFragment;
import com.dramtar.weatherbit.libs.Utils;
import com.dramtar.weatherbit.libs.model.Forecast;
import com.dramtar.weatherbit.libs.network.response.CurrentWeatherResponse;
import com.dramtar.weatherbit.widget.view.ConnectionErrorDialog;
import com.dramtar.weatherbit.widget.view.ImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.otto.Subscribe;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit.RetrofitError;

import static com.dramtar.weatherbit.libs.Utils.KEY_FORECAST;

/**
 * Created by Dramtar on 2018-12-06
 */
public class MapFragment extends BaseNetFragment implements OnMapReadyCallback {
    @BindView(R.id.city_name)
    TextView mCityName;

    @BindView(R.id.temp_view)
    TextView mTempView;

    @BindView(R.id.description_view)
    TextView mDescriptionView;

    @BindView(R.id.weather_icon)
    ImageView mWeatherIcon;

    @BindView(R.id.bubble_container)
    LinearLayout mBubbleContainer;

    @BindView(R.id.container)

    CoordinatorLayout mContainer;
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    private Forecast mForecast;
    private PopupWindow mPopupWindow;

    public static MapFragment newInstance(@NonNull Forecast forecast) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_FORECAST, forecast);

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_activity_maps);

        Bundle args = getArguments();
        if (args != null) {
            mForecast = (Forecast) args.getSerializable(KEY_FORECAST);
        }

        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mMapFragment.getMapAsync(this);
    }

    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        if (mMapFragment != null) {
            getChildFragmentManager().beginTransaction().remove(mMapFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mForecast != null) {
            update();

            LatLng latLng = new LatLng(mForecast.getLatitude(), mForecast.getLongitude());
            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            mMap.addMarker(new MarkerOptions().position(latLng).icon(Utils.bitmapDescriptorFromVector(getActivity(), R.drawable.ic_pin)));

            mNetWork.getCurrentWeather(latLng.latitude, latLng.longitude);
        } else if (!Utils.isHasInternetConnection(getActivity())) {
            showErrorDialog();
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).icon(Utils.bitmapDescriptorFromVector(getActivity(), R.drawable.ic_pin)));
                mNetWork.getCurrentWeather(latLng.latitude, latLng.longitude);
            }
        });
    }

    @Subscribe
    public void onCurrentWeatherResponse(CurrentWeatherResponse response) {
        mForecast = response.getForecast();
        update();
    }

    @Subscribe
    public void onRetrofitError(RetrofitError error) {
        mBubbleContainer.setVisibility(View.GONE);
        switch (error.getKind()) {
            case NETWORK:
                if (mForecast != null) {
                    Snackbar snackbar = Snackbar.make(mContainer, R.string.no_internet_connection, Snackbar.LENGTH_LONG);
                    if (error.getKind() == RetrofitError.Kind.NETWORK && !snackbar.isShown()) {
                        snackbar.show();
                    }
                } else if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    showErrorDialog();
                }
                break;
        }
    }

    private void showErrorDialog() {
        mContainer.postDelayed(new Runnable() { //mega hack ><
            @Override
            public void run() {
                mPopupWindow = new PopupWindow();
                mPopupWindow.setOutsideTouchable(false);
                mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                mPopupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                mPopupWindow.setContentView(new ConnectionErrorDialog(getActivity()));
                mPopupWindow.showAsDropDown(mContainer);
            }
        }, 300);
    }

    private void update() {
        if (mBubbleContainer.getVisibility() == View.GONE) {
            mBubbleContainer.setVisibility(View.VISIBLE);
            mBubbleContainer.animate().setInterpolator(new OvershootInterpolator()).translationY(0).setDuration(300).start();
        }
        mWeatherIcon.setImageURL(mForecast.getIllustrateWeather().getIconLink());
        mDescriptionView.setText(mForecast.getIllustrateWeather().getDescription());
        mTempView.setText(getString(R.string.temperature_place_holder, mForecast.getTemperatureString()));
        mCityName.setText(mForecast.getCityName());
    }

    @OnClick(R.id.bubble_container)
    public void onClickMoreDetail() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, DetailForecastFragment.newInstance(mForecast)).addToBackStack("map").commitAllowingStateLoss();
    }
}
