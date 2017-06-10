package com.example.minchul.gaama.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.minchul.gaama.R;

/**
 * Created by minchul on 2017-06-10.
 */

public abstract class BaseHIDService extends Service {

    public static final String SERVICE_COMMAND = "ServiceCommand";
    public static final int START_ADVERTISING = 1;
    public static final int STOP_ADVERTISING = 3;
    public static final int INPUT_BUTTON_STATUS = 2;

    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 11371;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int command = intent.getIntExtra(SERVICE_COMMAND, -1);
        if(command == START_ADVERTISING){
            onStartAdvertising();
        }else if (command == INPUT_BUTTON_STATUS){
            Bundle bundle = intent.getExtras();
            assert bundle != null;
            onChangeButtonStatus(bundle);
        }else if (command == STOP_ADVERTISING){
            onStopAdvertising();

        }
        return START_STICKY;
    }

    protected void buildNotification() {
        //show notification service is running
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentText(getResources().getText(R.string.service_is_running))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setOngoing(true);

        startForeground(NOTIFICATION_ID, builder.build());
    }

    protected void stopNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }

    protected abstract void onStartAdvertising();

    protected abstract void onStopAdvertising();

    protected abstract void onChangeButtonStatus(Bundle bundle);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
