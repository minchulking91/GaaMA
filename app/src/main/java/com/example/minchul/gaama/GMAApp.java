package com.example.minchul.gaama;

import android.app.Application;
import android.util.Log;

import com.example.blelib.GamePadHelper;

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
