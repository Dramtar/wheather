package com.dramtar.weatherbit.fragments.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dramtar.weatherbit.libs.network.Network;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dramtar on 2018-12-13
 */
public abstract class BaseNetFragment extends Fragment {
    protected Network mNetWork = Network.getInstance();
    private Unbinder mUnBinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getLayoutId();

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
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
}
