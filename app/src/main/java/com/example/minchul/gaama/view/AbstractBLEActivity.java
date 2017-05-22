package com.example.minchul.gaama.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.blelib.util.BLEUtils;
import com.example.minchul.gaama.R;

/**
 * Created by minchul on 2017-05-22.
 */

public abstract class AbstractBLEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!BLEUtils.isBluetoothEnabled(this)) {
            BLEUtils.enableBluetooth(this);
            return;
        }

        if (!BLEUtils.isBleSupported(this) || !BLEUtils.isBlePeripheralSupported(this)) {
            // display alert and exit
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.not_supported));
            alertDialog.setMessage(getString(R.string.ble_perip_not_supported));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(final DialogInterface dialog) {
                    finish();
                }
            });
            alertDialog.show();
        } else {
            setupBlePeripheralProvider();
        }
    }

    abstract void setupBlePeripheralProvider();

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BLEUtils.REQUEST_CODE_BLUETOOTH_ENABLE) {
            if (!BLEUtils.isBluetoothEnabled(this)) {
                // User selected NOT to use Bluetooth.
                // do nothing
                Toast.makeText(this, R.string.requires_bl_enabled, Toast.LENGTH_LONG).show();
                return;
            }

            if (!BLEUtils.isBleSupported(this) || !BLEUtils.isBlePeripheralSupported(this)) {
                // display alert and exit
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(getString(R.string.not_supported));
                alertDialog.setMessage(getString(R.string.ble_perip_not_supported));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(final DialogInterface dialog) {
                        finish();
                    }
                });
                alertDialog.show();
            } else {
                setupBlePeripheralProvider();
            }
        }
    }

}
