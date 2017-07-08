package com.example.minchul.gaama.ble;

import android.content.Context;

/**
 * Created by minchul on 2017-07-08.
 */

public class KeyboardPeripheral extends HidPeripheral {

    final private static byte[] REPORT_MAP = {
            USAGE_PAGE(1), 0x01, //            USAGE_PAGE (Generic Desktop)	05 01
            USAGE(1), 0x06, //            USAGE (Keyboard)	09 06
            COLLECTION(1), 0x01, //            COLLECTION (Application)	A1 01
            USAGE_PAGE(1), 0x07, //            USAGE_PAGE (Keyboard)	05 07
            USAGE_MINIMUM(1), (byte) 0xE0, //            USAGE_MINIMUM (Keyboard LeftControl)	19 E0
            USAGE_MAXIMUM(1), (byte) 0xE7, //            USAGE_MAXIMUM (Keyboard Right GUI)	29 E7
            LOGICAL_MINIMUM(1), 0x00, //            LOGICAL_MINIMUM (0)	15 00
            LOGICAL_MAXIMUM(1), 0x01, //            LOGICAL_MAXIMUM (1)	25 01
            REPORT_SIZE(1), 0x01, //            REPORT_SIZE (1)	75 01
            REPORT_COUNT(1), 0x08, //            REPORT_COUNT (8)	95 08
            INPUT(1), 0x02, //            INPUT (Data,Var,Abs)	81 02
            REPORT_COUNT(1), 0x01, //            REPORT_COUNT (1)	95 01
            REPORT_SIZE(1), 0x08, //            REPORT_SIZE (8)	75 08
            INPUT(1), 0x03, //            INPUT (Cnst,Var,Abs)	81 03
            REPORT_COUNT(1), 0x05, //            RE;PORT_COUNT (5)	95 05
            REPORT_SIZE(1), 0x01, //            REPORT_SIZE (1)	75 01
            USAGE_PAGE(1), 0x08, //            USAGE_PAGE (LEDs)	05 08
            USAGE_MINIMUM(1), 0x01, //            USAGE_MINIMUM (Num Lock)	19 01
            USAGE_MAXIMUM(1), 0x05, //            USAGE_MAXIMUM (Kana)	29 05
            OUTPUT(1), 0x02, //            OUTPUT (Data,Var,Abs)	91 02
            REPORT_COUNT(1), 0x01, //            REPORT_COUNT (1)	95 01
            REPORT_SIZE(1), 0x03, //            REPORT_SIZE (3)	75 03
            OUTPUT(1), 0x03, //            OUTPUT (Cnst,Var,Abs)	91 03
            REPORT_COUNT(1), 0x06,//            REPORT_COUNT (6)	95 06
            REPORT_SIZE(1), 0x08, //            REPORT_SIZE (8)	75 08
            LOGICAL_MINIMUM(1), 0x00, //            LOGICAL_MINIMUM (0)	15 00
            LOGICAL_MAXIMUM(1), 0x65, //            LOGICAL_MAXIMUM (101)	25 65
            USAGE_PAGE(1), 0x07, //            USAGE_PAGE (Keyboard)	05 07
            USAGE_MINIMUM(1), 0x00, //            USAGE_MINIMUM (Reserved (no event indicated))	19 00
            USAGE_MAXIMUM(1), 0x65, //            USAGE_MAXIMUM (Keyboard Application)	29 65
            INPUT(1), 0x00, //            INPUT (Data,Ary,Abs)	81 00
            END_COLLECTION()//            END_COLLECTION	C0
    };

    public KeyboardPeripheral(final Context context) throws UnsupportedOperationException {
        super(context, true, false, false, 10);
    }

    @Override
    protected byte[] getReportMap() {
        return REPORT_MAP;
    }

    @Override
    protected void onOutputReport(byte[] outputReport) {

    }
}
