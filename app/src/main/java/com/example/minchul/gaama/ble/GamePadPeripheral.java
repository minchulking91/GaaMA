package com.example.minchul.gaama.ble;

import android.content.Context;

public class GamePadPeripheral extends HidPeripheral {

    private static final byte[] REPORT_MAP = {
            USAGE_PAGE(1), 0x01, //UsagePage (Generic Desktop)
            USAGE(1), 0x05, //Usage (Game Pad)
            COLLECTION(1), 0x01, //Collection (Application)
            REPORT_ID(1), 0x01,
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
            END_COLLECTION(), //End Collection

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

            END_COLLECTION(), //End Collection
    };
    private static final String TAG = GamePadPeripheral.class.getSimpleName();

    public GamePadPeripheral(final Context context) throws UnsupportedOperationException {
        super(context, true, false, false, 10);
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
