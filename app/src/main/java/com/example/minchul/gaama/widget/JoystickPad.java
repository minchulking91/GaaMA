package com.example.minchul.gaama.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.jmedeisis.bugstick.Joystick;
import com.jmedeisis.bugstick.JoystickListener;

/**
 * Created by minchul on 2017-05-21.
 */

public class JoystickPad extends Joystick implements JoystickListener {
    public static final float SCALE_127 = 127f;
    public static final float SCALE_32767 = 32767f;
    public static final String TAG = JoystickPad.class.getSimpleName();
    private JoyStickActionListener joyStickActionListener;
    private long repeatTouchEventDelay = 0;
    private long lastTouchEventMillis = 0;

    public JoystickPad(Context context) {
        super(context);
    }

    public JoystickPad(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JoystickPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("deprecation")
    public void setJoyStickActionListener(JoyStickActionListener listener) {
        this.joyStickActionListener = listener;
        setJoystickListener(this);
    }

    public void setRepeatTouchEventDelay(long delay) {
        this.repeatTouchEventDelay = delay;
    }

    @Override
    public void onDown() {
    }

    @Override
    public void onDrag(float degrees, float offset) {
        if (joyStickActionListener != null && System.currentTimeMillis() - lastTouchEventMillis >= repeatTouchEventDelay) {
            int x = getXAxisValue(degrees, offset);
            int y = getYAxisValue(degrees, offset);
            joyStickActionListener.onStickMove(this, x, y, degrees);
            lastTouchEventMillis = System.currentTimeMillis();
        }
    }

    @Deprecated
    @Override
    public void setJoystickListener(JoystickListener listener) {
        super.setJoystickListener(listener);
    }

    @Override
    public void onUp() {
        if (joyStickActionListener != null) {
            joyStickActionListener.onStickUp(this);
        }
    }

    public interface JoyStickActionListener {
        void onStickUp(JoystickPad v);

        void onStickMove(JoystickPad v, int x, int y, float degrees);
    }


    private int getXAxisValue(float degree, float offset) {
        float xRatio = 0;
        if (degree >= 0 && degree <= 90) {
            xRatio = (degree < 45) ? 1 : (90 - Math.abs(degree)) / 45;
        } else if (degree > 90 && degree <= 180) {
            xRatio = -((degree >= 135) ? 1 : (Math.abs(degree) - 90) / 45);
        } else if (degree < 0 && degree >= -90) {
            xRatio = (degree > -45) ? 1 : (90 - Math.abs(degree)) / 45;
        } else if (degree < -90 && degree >= -180) {
            xRatio = -((degree <= -135) ? 1 : (Math.abs(degree) - 90) / 45);
        }
        return Math.round((xRatio * SCALE_127) * offset);
    }

    private int getYAxisValue(float degree, float offset) {
        float yRatio = 0;
        if (degree >= 0 && degree <= 90) {
            yRatio = -((degree > 45) ? 1 : Math.abs(degree) / 45);
        } else if (degree > 90 && degree <= 180) {
            yRatio = -((degree <= 135) ? 1 : (180 - Math.abs(degree)) / 45);
        } else if (degree < 0 && degree >= -90) {
            yRatio = (degree < -45) ? 1 : Math.abs(degree) / 45;
        } else if (degree < -90 && degree >= -180) {
            yRatio = (degree >= -135) ? 1 : (180 - Math.abs(degree)) / 45;
        }
        return Math.round((yRatio * SCALE_127) * offset);
    }

}
