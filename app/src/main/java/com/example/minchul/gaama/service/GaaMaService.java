package com.example.minchul.gaama.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.minchul.gaama.ble.GamePadHelper;


public class GaaMaService extends BaseHIDService {


    private static final String TAG = GaaMaService.class.getSimpleName();

    public static final String STICK_X = "stick_x";
    public static final String STICK_Y = "stick_y";
    public static final String STICK_RX = "stick_rx";
    public static final String STICK_RY = "stick_ry";
    public static final String BUTTON_A = "buttonA";
    public static final String BUTTON_B = "buttonB";
    public static final String BUTTON_X = "buttonX";
    public static final String BUTTON_Y = "buttonY";
    public static final String BUTTON_LB = "buttonLB";
    public static final String BUTTON_RB = "buttonRB";
    public static final String BUTTON_BACK = "buttonBACK";
    public static final String BUTTON_START = "buttonSTART";
    public static final String HAT_SWITCH = "hat_switch";
    public static final String BUTTON_LT = "button_lt";
    public static final String BUTTON_RT = "button_rt";


    private GamePadHelper gamePadHelper;

    @Override
    public void onCreate() {
        Log.d(TAG, "Service Create!");
        gamePadHelper = GamePadHelper.getInstance();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        gamePadHelper.stopAdvertising();
        stopNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gamePadHelper.stopAdvertising();
    }


    @Override
    protected void onStopAdvertising() {
        gamePadHelper.stopAdvertising();
        stopSelf();
    }

    @Override
    protected void onChangeButtonStatus(Bundle bundle) {
        final int stick_x = bundle.getInt(STICK_X, 0);
        final int stick_y = bundle.getInt(STICK_Y, 0);
        final int stick_rx = bundle.getInt(STICK_RX, 0);
        final int stick_ry = bundle.getInt(STICK_RY, 0);
        final int hat_switch = bundle.getInt(HAT_SWITCH, 0);
        final boolean buttonA = bundle.getBoolean(BUTTON_A);
        final boolean buttonB = bundle.getBoolean(BUTTON_B);
        final boolean buttonX = bundle.getBoolean(BUTTON_X);
        final boolean buttonY = bundle.getBoolean(BUTTON_Y);
        final boolean buttonLB = bundle.getBoolean(BUTTON_LB);
        final boolean buttonRB = bundle.getBoolean(BUTTON_RB);
        final boolean buttonLT = bundle.getBoolean(BUTTON_LT);
        final boolean buttonRT = bundle.getBoolean(BUTTON_RT);
        final boolean buttonSTART = bundle.getBoolean(BUTTON_START);
        final boolean buttonBACK = bundle.getBoolean(BUTTON_BACK);
        gamePadHelper.onChangeButtonStatus(stick_x, stick_y, stick_rx, stick_ry, hat_switch, buttonA, buttonB, buttonX, buttonY, buttonLB, buttonRB, buttonLT, buttonRT, buttonBACK, buttonSTART);
    }

    @Override
    protected void onStartAdvertising() {
        gamePadHelper.startAdvertising();
        buildNotification();
    }


}
