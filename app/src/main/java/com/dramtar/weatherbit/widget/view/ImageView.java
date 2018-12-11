package com.dramtar.weatherbit.widget.view;

/**
 * Created by Dramtar on 2018-12-07
 */

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import androidx.appcompat.widget.AppCompatImageView;

public class ImageView extends AppCompatImageView {

    public ImageView(Context context) {
        this(context, null);
    }

    public ImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageURL(String url) {
        Glide.with(this).load(url).into(this);
    }
}