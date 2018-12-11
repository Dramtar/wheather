package com.dramtar.weatherbit.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.dramtar.weatherbit.R;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dramtar on 2018-12-06
 */
public class ConnectionErrorDialog extends FrameLayout {
    public ConnectionErrorDialog(Context context) {
        this(context, null);
    }

    public ConnectionErrorDialog(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConnectionErrorDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.dialog_connection_error, this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.exit_button)
    public void onExitButton() {
        System.exit(0);
    }
}
