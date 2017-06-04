package com.example.blelib;

import android.content.Context;

public class GamePadPeripheral extends HidPeripheral {

    private static final byte[] REPORT_MAP = {
            USAGE_PAGE, 0x01, //UsagePage (Generic Desktop)
            USAGE, 0x04, //Usage (Game Pad)
            COLLECTION, 0x01, //Collection (Application)
            USAGE, 0x01, //Usage (Pointer)
            COLLECTION, 0x00, //Collection (Physical)

            USAGE, 0x30, //Usage (X)
            USAGE, 0x31, //Usage (Y)
            LOGICAL_MINIMUM, (byte)0xFF, //Logical Minimum (-1)
            LOGICAL_MAXIMUM, 0x01, //Logical Maximum (1)
            REPORT_COUNT, 0x02, //Report Count (2)
            REPORT_SIZE, 0x02, //Report Size (2)
            INPUT, 0x02, //nput (Data, Variable, Absolute, No Null)

            END_COLLECTION, //End Collectionhr

            REPORT_COUNT, 0x04, //Report Count (4)
            REPORT_SIZE, 0x01, //Report Size (1)
            INPUT, 0x03, //Input (Constant, Variable, Absolute)

            USAGE_PAGE, 0x09, //Usage Page (Buttons)
            USAGE_MINIMUM, 0x01, //Usage Minimum (Button 1)
            USAGE_MAXIMUM, 0x06, //Usage Maximum (Button 6)
            LOGICAL_MINIMUM, 0x00, //Logical Minimum (0)
            LOGICAL_MAXIMUM, 0x01, //Logical Maximum (1)
            REPORT_COUNT, 0x06, //Report Count (6)
            REPORT_SIZE, 0x01, //Report Size (1)
            INPUT, 0x02, //Input (Data, Variable, Absolute)

            REPORT_COUNT, 0x02, //Report Count (2)
            REPORT_SIZE, 0x01, //Report Size (1)
            INPUT, 0x03, //Input (Constant, Variable, Absolute)
            END_COLLECTION, //End Collection
    };

    /**
     *|   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
     *|               PAD             |     Y-axis    |     X-axis    |
     *|      PAD      |  Btn6 |  Btn5 |  Btn4 |  Btn3 |  Btn2 |  Btn1 |
     *
     * axis = 00 [0], 01 [1], 11 [-1]
     */

    public GamePadPeripheral(final Context context) throws UnsupportedOperationException {
        super(context, true, false, false, 10);
    }

    public void onChangeButtonStatus(final int dx, final int dy, final boolean btn1, final boolean btn2, final boolean btn3, final boolean btn4, final boolean btn5, final boolean btn6) {

        byte buttons = 0;
        if(btn1){
            buttons = 0x1;
        }
        if(btn2){
            buttons = (byte)(buttons | 0b10);
        }
        if(btn3){
            buttons = (byte)(buttons | 0b100);
        }
        if(btn4){
            buttons = (byte)(buttons | 0b1000);
        }
        if(btn5){
            buttons = (byte)(buttons | 0b10000);
        }
        if(btn6){
            buttons = (byte)(buttons | 0b100000);
        }

        final byte[] report = new byte[3];
        report[0] = (byte)dx;
        report[1] = (byte)dy;
        report[2] = buttons;

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
