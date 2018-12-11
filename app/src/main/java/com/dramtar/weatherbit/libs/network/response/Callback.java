package com.dramtar.weatherbit.libs.network.response;

public abstract class Callback<T extends Response> implements retrofit.Callback<T> {

    public abstract void success(T t);

    @Override
    public void success(T t, retrofit.client.Response response) {
        success(t);
    }
}
