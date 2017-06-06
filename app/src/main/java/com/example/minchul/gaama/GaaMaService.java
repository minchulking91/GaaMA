package com.example.minchul.gaama;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.blelib.GamePadPeripheral;


public class GaaMaService extends Service {

    private static final int NOTIFICATION_ID = 11371;
    private static final String TAG = GaaMaService.class.getSimpleName();
    public static final String GAA_MA_SERVICE_COMMAND = "GaaMaServiceCommand";
    public static final int START_ADVERTISING = 1;
    public static final int STOP_ADVERTISING = 3;
    public static final int INPUT_BUTTON_STATUS = 2;
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


    private NotificationManager notificationManager;
    private GamePadPeripheral mGamePadPeripheral;

    @Override
    public void onCreate() {
        Log.d(TAG, "Service Create!");
        mGamePadPeripheral = new GamePadPeripheral(this);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        removeBlePeripheralProvider();
        stopNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int command = intent.getIntExtra(GAA_MA_SERVICE_COMMAND, -1);
        if(command == START_ADVERTISING){
            setupBlePeripheralProvider();
            buildNotification();
        }else if (command == INPUT_BUTTON_STATUS){
            Bundle bundle = intent.getExtras();
            assert bundle != null;
            onChangeButtonStatus(bundle);
        }else if (command == STOP_ADVERTISING){
            removeBlePeripheralProvider();
            stopSelf();
        }
        return START_STICKY;
    }

    private void onChangeButtonStatus(Bundle bundle) {
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
        mGamePadPeripheral.onChangeButtonStatus(stick_x, stick_y, stick_rx, stick_ry, hat_switch, buttonY, buttonB, buttonA, buttonX, buttonLB, buttonRB, buttonLT, buttonRT, buttonBACK, buttonSTART);
    }

    private void buildNotification() {
        //show notification service is running
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentText(getResources().getText(R.string.service_is_running))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setOngoing(true);

        startForeground(NOTIFICATION_ID, builder.build());
    }

    private void stopNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }

    boolean setupBlePeripheralProvider() {
        return mGamePadPeripheral.startAdvertising();
    }

    void removeBlePeripheralProvider() {
        mGamePadPeripheral.stopAdvertising();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


}
