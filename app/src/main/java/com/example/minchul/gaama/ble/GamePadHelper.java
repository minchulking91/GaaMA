package com.example.minchul.gaama.ble;

import android.content.Context;

/**
 * Created by minchul on 2017-06-07.
 */

public class GamePadHelper {
    private static final GamePadHelper instance = new GamePadHelper();

    public static GamePadHelper getInstance() {
        return instance;
    }

    GamePadPeripheral mGamePadPeripheral;
    private GamePadHelper() {
    }

    public void setContext(Context applicationContext) {
        if(mGamePadPeripheral == null) {
            mGamePadPeripheral = new GamePadPeripheral(applicationContext);
        }
    }


    public void onChangeButtonStatus(final int stick_x, final int stick_y, final int stick_rx, final int stick_ry, final int hat_switch,
                                     final boolean btn1, final boolean btn2, final boolean btn3, final boolean btn4,
                                     final boolean btnLB, final boolean btnRB, final boolean btnLT, final boolean btnRT,
                                     boolean btnBack, boolean btnStart) {

        byte direction = 0;
        if (hat_switch == Integer.MAX_VALUE) {
            direction = 15;
        } else {
            int abs_hat_switch = Math.abs(hat_switch);
            if (abs_hat_switch < 30) {
                direction = 2;
            } else if (abs_hat_switch < 60) {
                direction = (byte) (hat_switch > 0 ? 1 : 3);
            } else if (abs_hat_switch < 120) {
                direction = (byte) (hat_switch > 0 ? 0 : 4);
            } else if (abs_hat_switch < 150) {
                direction = (byte) (hat_switch > 0 ? 7 : 5);
            } else {
                direction = 6;
            }
        }
        byte buttons = 0;
        byte menu_buttons = 0;
        if (btn1) {
            buttons = 0x01;
        }
        if (btn2) {
            buttons = (byte) (buttons | 0x02);
        }
        if (btn3) {
            buttons = (byte) (buttons | 0x04);
        }
        if (btn4) {
            buttons = (byte) (buttons | 0x08);
        }
        if (btnLB) {
            buttons = (byte) (buttons | 0x10);
        }
        if (btnRB) {
            buttons = (byte) (buttons | 0x20);
        }
        if (btnLT) {
            buttons = (byte) (buttons | 0x40);
        }
        if (btnRT) {
            buttons = (byte) (buttons | 0x80);
        }
        if (btnBack) {
            menu_buttons = (byte) (buttons | 0x01);
        }
        if (btnStart) {
            menu_buttons = (byte) (buttons | 0x02);
        }
        final byte[] report = new byte[7];
        report[0] = (byte) (stick_x + 128);
        report[1] = (byte) (stick_y + 128);
        report[2] = (byte) (stick_rx + 128);
        report[3] = (byte) (stick_ry + 128);
        report[4] = direction;
        report[5] = buttons;
        report[6] = menu_buttons;

        mGamePadPeripheral.addInputReport(report);

    }

    public void startAdvertising() {
        mGamePadPeripheral.startAdvertising();
    }

    public void stopAdvertising() {
        mGamePadPeripheral.stopAdvertising();
    }
}
