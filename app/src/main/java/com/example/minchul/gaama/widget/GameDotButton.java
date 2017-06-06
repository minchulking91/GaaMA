package com.example.minchul.gaama.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.minchul.gaama.GameButtonListener;

/**
 * Created by minchul on 2017-06-06.
 */

public class GameDotButton extends FloatingActionButton implements GameButtonListener {
    private GameButtonListener mGameButtonListener;

    public GameDotButton(Context context) {
        super(context);
    }

    public GameDotButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameDotButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            setPressed(true);
            if (mGameButtonListener != null) {
                mGameButtonListener.onChangeButtonPressed(this, true);
            }
        } else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_CANCEL) {
            setPressed(false);
            if (mGameButtonListener != null) {
                mGameButtonListener.onChangeButtonPressed(this, false);
            }
        }
        return true;
    }

    @Deprecated
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    @Deprecated
    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    public void setGameButtonListener(GameButtonListener listener){
        this.mGameButtonListener = listener;
    }

    @Override
    public void onChangeButtonPressed(View view, boolean pressed) {

    }
}
