package com.example.minchul.gaama.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by minchul on 2017-05-22.
 */

public class GameButton extends AppCompatButton {
    private GameButtonListener mGameButtonListener;



    public GameButton(Context context) {
        super(context);
    }

    public GameButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameButton(Context context, AttributeSet attrs, int defStyleAttr) {
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

    /**
     * Created by minchul on 2017-06-06.
     */
    public static interface GameButtonListener {
        void onChangeButtonPressed(View view, boolean pressed);
    }
}
