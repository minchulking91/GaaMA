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
