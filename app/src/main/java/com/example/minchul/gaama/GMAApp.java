package com.example.minchul.gaama;

import android.app.Application;
import android.util.Log;

import com.example.minchul.gaama.ble.GamePadHelper;

/**
 * Created by minchul on 2017-05-22.
 */

public class GMAApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("GMAApp", "oncreate");
        GamePadHelper.getInstance().setContext(getApplicationContext());
    }
}
