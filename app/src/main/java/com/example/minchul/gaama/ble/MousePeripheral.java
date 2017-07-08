package com.example.minchul.gaama.ble;

import android.content.Context;

/**
 * Created by minchul on 2017-07-08.
 */

public class MousePeripheral extends HidPeripheral {


    private static final byte[] REPORT_MAP = {
            USAGE_PAGE(1), 0x01, //    USAGE_PAGE (Generic Desktop)	05 01
            USAGE(1), 0x02, //    USAGE (Mouse)	09 02
            COLLECTION(1), 0x01,//    COLLECTION (Application)	A1 01
            USAGE(1), 0x01,//    USAGE (Pointer)	09 01
            COLLECTION(1), 0x00,//    COLLECTION (Physical)	A1 00
            USAGE_PAGE(1), 0x09,//    USAGE_PAGE (Button)	05 09
            USAGE_MINIMUM(1), 0x01,//    USAGE_MINIMUM (Button 1)	19 01
            USAGE_MAXIMUM(1), 0x03,//    USAGE_MAXIMUM (Button 3)	29 03
            LOGICAL_MINIMUM(1), 0x00,//    LOGICAL_MINIMUM (0)	15 00
            LOGICAL_MAXIMUM(1), 0x01, //    LOGICAL_MAXIMUM (1)	25 01
            REPORT_COUNT(1), 0x03, //    REPORT_COUNT (3)	95 03
            REPORT_SIZE(1), 0x01,//    REPORT_SIZE (1)	75 01
            INPUT(1), 0x02, //    INPUT (Data,Var,Abs)	81 02
            REPORT_COUNT(1), 0x01, //    REPORT_COUNT (1)	95 01
            REPORT_SIZE(1), 0x05, //    REPORT_SIZE (5)	75 05
            INPUT(1), 0x03, //    INPUT (Cnst,Var,Abs)	81 03
            USAGE_PAGE(1), 0x01, //    USAGE_PAGE (Generic Desktop)	05 01
            USAGE(1), 0x30,//    USAGE (X)	09 30
            USAGE(1), 0x31, //    USAGE (Y)	09 31
            LOGICAL_MINIMUM(1), (byte) 0x81,//    LOGICAL_MINIMUM (-127)	15 81
            LOGICAL_MAXIMUM(1), 0x7F,//    LOGICAL_MAXIMUM (127)	25 7F
            REPORT_SIZE(1), 0x08, //    REPORT_SIZE (8)	75 08
            REPORT_COUNT(1), 0x02, //    REPORT_COUNT (2)	95 02
            INPUT(1), 0x06,//    INPUT (Data,Var,Rel)	81 06
            END_COLLECTION(),//    END_COLLECTION	C0
            END_COLLECTION()//    END_COLLECTION	C0
    };

    public MousePeripheral(final Context context) throws UnsupportedOperationException {
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
