package com.example.blelib;

import android.content.Context;

public class GamePadPeripheral extends HidPeripheral {

    private static final byte[] REPORT_MAP = {
            USAGE_PAGE(1), 0x01, //UsagePage (Generic Desktop)
            USAGE(1), 0x05, //Usage (Game Pad)
            COLLECTION(1), 0x01, //Collection (Application)

            USAGE(1), 0x01, //Usage (Pointer)
            COLLECTION(1), 0x00, //Collection (Physical)
            LOGICAL_MINIMUM(1), 0x00,
            LOGICAL_MAXIMUM(2), (byte) 0xFF, 0x00,
            PHYSICAL_MINIMUM(1), 0x00,
            PHYSICAL_MAXIMUM(2), (byte) 0xFF, 0x00,
            USAGE(1), 0x30, //Usage (X)
            USAGE(1), 0x31, //Usage (Y)
            USAGE(1), 0x33, //Usage (RX)
            USAGE(1), 0x34, //Usage (RY)
            REPORT_COUNT(1), 0x04, //Report Count (4)
            REPORT_SIZE(1), 0x08, //Report Size (8)
            INPUT(1), 0x02, //input (Data, Variable, Absolute, No Null)
            END_COLLECTION(1), //End Collection

            USAGE(1), 0x39, //Usage (hat switch)
            LOGICAL_MINIMUM(1), 0x00, LOGICAL_MAXIMUM(1), 0x07,
            PHYSICAL_MINIMUM(1), 0x00, PHYSICAL_MAXIMUM(1), 0x07,
            UNIT(1), 0x14, UNIT_EXPONENT(1), 0x00,
            REPORT_SIZE(1), 0x04, REPORT_COUNT(1), 0x01,
            INPUT(1), 0x02,

            REPORT_COUNT(1), 0x04, //Report Count (2)
            REPORT_SIZE(1), 0x01, //Report Size (1)
            INPUT(1), 0x03, //Input (Constant, Variable, Absolute)

            USAGE_PAGE(1), 0x09, //Usage Page (Buttons)
            USAGE_MINIMUM(1), 0x01, //Usage Minimum (Button 1)
            USAGE_MAXIMUM(1), 0x0A, //Usage Maximum (Button 10)
            LOGICAL_MINIMUM(1), 0x00, //Logical Minimum (0)
            LOGICAL_MAXIMUM(1), 0x01, //Logical Maximum (1)
            PHYSICAL_MINIMUM(1), 0x00,
            PHYSICAL_MAXIMUM(1), 0x01,
            REPORT_COUNT(1), 0x0A, //Report Count (10)
            REPORT_SIZE(1), 0x01, //Report Size (1)
            INPUT(1), 0x02, //Input (Data, Variable, Absolute)


            REPORT_COUNT(1), 0x06, //Report Count (6)
            REPORT_SIZE(1), 0x01, //Report Size (1)
            INPUT(1), 0x03, //Input (Constant, Variable, Absolute)

            END_COLLECTION(1), //End Collection
    };
    private static final String TAG = GamePadPeripheral.class.getSimpleName();

    public GamePadPeripheral(final Context context) throws UnsupportedOperationException {
        super(context, true, false, false, 10);
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
            if (abs_hat_switch < 23) {
                direction = 2;
            } else if (abs_hat_switch < 68) {
                direction = (byte) (hat_switch > 0 ? 1 : 3);
            } else if (abs_hat_switch < 113) {
                direction = (byte) (hat_switch > 0 ? 0 : 4);
            } else if (abs_hat_switch < 158) {
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

        this.addInputReport(report);

    }

    @Override
    protected byte[] getReportMap() {
        return REPORT_MAP;
    }

    @Override
    protected void onOutputReport(byte[] outputReport) {
        // do nothing
    }
}
