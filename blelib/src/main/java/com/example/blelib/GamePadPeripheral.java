package com.example.blelib;

import android.content.Context;

public class GamePadPeripheral extends HidPeripheral {

    private static final byte[] REPORT_MAP = {
            0x05, 0x01, //	Usage Page (Generic Desktop)
            0x09, 0x05, //	Usage (Game Pad)
            (byte)0xA1, 0x01, //	Collection (Application)

            0x05, 0x01, //		Usage Page (Generic Desktop)
            0x09, 0x3A, //		Usage (Counted Buffer)		;XXX
            (byte)0xA1, 0x02, //		Collection (Logical)

            //; padding
            0x75, 0x08, //			Report Size (8)
            (byte)0x95, 0x01, //			Report Count (1)
            (byte)0x81, 0x01, //			Input (Constant)

            //; byte count
            0x75, 0x08, //			Report Size (8)
            (byte)0x95, 0x01, //			Report Count (1)
            0x05, 0x01, //			Usage Page (Generic Desktop)
            0x09, 0x3B, //			Usage (Byte Count)		;XXX
            (byte)0x81, 0x01, //			Input (Constant)

//			; D-pad
            0x05, 0x01, //			Usage Page (Generic Desktop)
            0x09, 0x01, //			Usage (Pointer)
            (byte)0xA1, 0x00, //			Collection (Physical)
            0x75, 0x01, //				Report Size (1)
            0x15, 0x00, //				Logical Minimum (0)
            0x25, 0x01, //				Logical Maximum (1)
            0x35, 0x00, //				Physical Minimum (0)
            0x45, 0x01, //				Physical Maximum (1)
            (byte)0x95, 0x04, //				Report Count (4)
            0x05, 0x01, //				Usage Page (Generic Desktop)
            0x09, (byte)0x90, //				Usage (D-pad Up)
            0x09, (byte)0x91, //				Usage (D-pad Down)
            0x09, (byte)0x93, //				Usage (D-pad Left)
            0x09, (byte)0x92, //				Usage (D-pad Right)
            (byte)0x81, 0x02, //				Input (Data,Variable,Absolute)
            (byte)0xC0,//	End Collection

            //		; start, back, stick press
            0x75, 0x01, //			Report Size (1)
            0x15, 0x00, //			Logical Minimum (0)
            0x25, 0x01, //			Logical Maximum (1)
            0x35, 0x00, //			Physical Minimum (0)
            0x45, 0x01, //			Physical Maximum (1)
            (byte)0x95, 0x04, //			Report Count (4)
            0x05, 0x09, //			Usage Page (Button)
            0x19, 0x07, //			Usage Minimum (Button 7)
            0x29, 0x0A, //			Usage Maximum (Button 10)
            (byte)0x81, 0x02, //			Input (Data,Variable,Absolute)

            //; reserved
            0x75, 0x01, //			Report Size (1)
            (byte)0x95, 0x08, //			Report Count (8)
            (byte)0x81, 0x01, //			Input (Constant)

            //; analog buttons
            0x75, 0x08, //			Report Size (8)
            0x15, 0x00, //			Logical Minimum (0)
            0x26, (byte)0xFF, 0x00,	//		Logical Maximum (255)
            0x35, 0x00, //			Physical Minimum (0)
            0x46, (byte)0xFF, 0x00,	//		Physical Maximum (255)
            (byte)0x95, 0x06, //			Report Count (6)
            0x05, 0x09, //			Usage Page (Button)
            0x19, 0x01, //			Usage Minimum (Button 1)
            0x29, 0x06, //			Usage Minimum (Button 6)
            (byte)0x81, 0x02, //			Input (Data,Variable,Absolute)

            //; triggers
            0x75, 0x08, //			Report Size (8)
            0x15, 0x00, //			Logical Minimum (0)
            0x26, (byte)0xFF, 0x00,	//		Logical Maximum (255)
            0x35, 0x00, //			Physical Minimum (0)
            0x46, (byte)0xFF, 0x00,	//		Physical Maximum (255)
            (byte)0x95, 0x02, //			Report Count (2)
            0x05, 0x01, //			Usage Page (Generic Desktop)
            0x09, 0x32, //			Usage (Z)
            0x09, 0x35, //			Usage (Rz)
            (byte)0x81, 0x02, //			Input (Data,Variable,Absolute)

            //; sticks
            0x75, 0x10, //			Report Size (16)
            0x16, 0x00, (byte)0x80,	//		Logical Minimum (-32768)
            0x26, (byte)0xFF, 0x7F,	//		Logical Maximum (32767)
            0x36, 0x00, (byte)0x80,	//		Physical Minimum (-32768)
            0x46, (byte)0xFF, 0x7F,	//		Physical Maximum (32767)

            0x05, 0x01, //			Usage Page (Generic Desktop)
            0x09, 0x01, //			Usage (Pointer)
            (byte)0xA1, 0x00, //			Collection (Physical)
            (byte)0x95, 0x02, //				Report Count (2)
            0x05, 0x01, //				Usage Page (Generic Desktop)
            0x09, 0x30, //				Usage (X)
            0x09, 0x31, //				Usage (Y)			;north positive
            (byte)0x81, 0x02, //				Input (Data,Variable,Absolute)
            (byte)0xC0, //	End Collection

            0x05, 0x01, //			Usage Page (Generic Desktop)
            0x09, 0x01, //			Usage (Pointer)
            (byte)0xA1, 0x00, //			Collection (Physical)
            (byte)0x95, 0x02, //				Report Count (2)
            0x05, 0x01, //				Usage Page (Generic Desktop)
            0x09, 0x33, //				Usage (Rx)
            0x09, 0x34, //				Usage (Ry)			;north positive
            (byte)0x81, 0x02, //				Input (Data,Variable,Absolute)
            (byte)0xC0,			//End Collection

            (byte)0xC0,		//End Collection

            0x05, 0x01, //		Usage Page (Generic Desktop)
            0x09, 0x3A, //		Usage (Counted Buffer)		;XXX
            (byte)0xA1, 0x02, //		Collection (Logical)

            //; padding
            0x75, 0x08, //			Report Size (8)
            (byte)0x95, 0x01, //			Report Count (1)
            (byte)0x91, 0x01, //			Output (Constant)

            //; byte count
            0x75, 0x08, //			Report Size (8)
            (byte)0x95, 0x01, //			Report Count (1)
            0x05, 0x01, //			Usage Page (Generic Desktop)
            0x09, 0x3B, //			Usage (Byte Count)		;XXX
            (byte)0x91, 0x01, //			Output (Constant)

//			; padding
            0x75, 0x08, //			Report Size (8)
            (byte)0x95, 0x01, //			Report Count (1)
            (byte)0x91, 0x01, //			Output (Constant)

            //	; left actuator
            0x75, 0x08, //			Report Size (8)
            0x15, 0x00, //			Logical Minimum (0)
            0x26, (byte)0xFF, 0x00,	//		Logical Maximum (255)
            0x35, 0x00, //			Physical Minimum (0)
            0x46, (byte)0xFF, 0x00,	//		Physical Maximum (255)
            (byte)0x95, 0x01, //			Report Count (1)
            0x06, 0x00, (byte)0xFF,	//		Usage Page (vendor-defined)
            0x09, 0x01, //			Usage (1)
            (byte)0x91, 0x02, //			Output (Data,Variable,Absolute)

            //; padding
            0x75, 0x08, //			Report Size (8)
            (byte)0x95, 0x01, //			Report Count (1)
            (byte)0x91, 0x01, //			Output (Constant)

            //; right actuator
            0x75, 0x08, //			Report Size (8)
            0x15, 0x00, //			Logical Minimum (0)
            0x26, (byte)0xFF, 0x00, //			Logical Maximum (255)
            0x35, 0x00, //			Physical Minimum (0)
            0x46, (byte)0xFF, 0x00, //			Physical Maximum (255)
            (byte)0x95, 0x01, //			Report Count (1)
            0x06, 0x00, (byte)0xFF, //			Usage Page (vendor-defined)
            0x09, 0x02, //			Usage (2)
            (byte)0x91, 0x02, //			Output (Data,Variable,Absolute)

            (byte)0xC0,//		End Collection

            (byte)0xC0, //	End Collection
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

        final byte[] report = new byte[20];
        report[0] = 0x00; //constant
        report[1] = 0x14; //byte count
        report[2] = 0x00; //d-pad & start, back, stick press
        report[3] = 0x00; //reserved
        report[4] = 0x00; //button1 a
        report[5] = 0x00; //button2 b
        report[6] = 0x00; //button3 x
        report[7] = 0x00; //button4 y
        report[8] = 0x00; //button5 black
        report[9] = 0x00; //button6 white
        report[10] = 0x00; //L trigger
        report[11] = 0x00; //R trigger
        report[12] = 0x00; //left stick x
        report[13] = (byte)dx; //left stick x
        report[14] = 0x00; //left stick y
        report[15] = (byte)dy; //left stick y
        report[16] = 0x00; //right stick x
        report[17] = 0x00; //right stick x
        report[18] = 0x00; //right stick y
        report[19] = 0x00; //right stick y
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
