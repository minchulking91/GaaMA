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

    private NotificationManager notificationManager;
    private GamePadPeripheral mGamePadPeripheral;


    @Override
    public void onCreate() {
        Log.d(TAG, "Service Create!");

        mGamePadPeripheral = new GamePadPeripheral(this);
        mGamePadPeripheral.setDeviceName(getString(R.string.ble_joystick));
        mGamePadPeripheral.setManufacturer("Team 15");
        mGamePadPeripheral.setSerialNumber("0001");

        buildNotification();
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
        removeBlePeripheralProvider();
        stopNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "startId : " + startId);
        //show notification service is running
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentText(getResources().getText(R.string.service_is_running))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setOngoing(true);

        startForeground(NOTIFICATION_ID, builder.build());
        return START_STICKY;
    }

    private void onChangeButtonStatus(Bundle bundle) {
        int dx = bundle.getInt("dx", 0);
        int dy = bundle.getInt("dy", 0);
        boolean buttonA = bundle.getBoolean("buttonA");
        boolean buttonB = bundle.getBoolean("buttonB");
        boolean buttonC = bundle.getBoolean("buttonC");
        boolean buttonD = bundle.getBoolean("buttonD");
        boolean buttonE = bundle.getBoolean("buttonE");
        boolean buttonF = bundle.getBoolean("buttonF");
        mGamePadPeripheral.onChangeButtonStatus(dx, dy, buttonA, buttonB, buttonC, buttonD, buttonE, buttonF);
    }

    private void buildNotification() {

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
