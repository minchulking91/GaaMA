package com.example.minchul.gaama.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.blelib.GamePadPeripheral;
import com.example.minchul.gaama.GaaMaService;
import com.example.minchul.gaama.R;
import com.example.minchul.gaama.widget.GameButton;
import com.example.minchul.gaama.widget.JoystickPad;

public class GamePadActivity extends AbstractBLEActivity implements JoystickPad.JoyStickActionListener, GameButton.GameButtonListener {

    //VIEW
    private View mContentView;
    private JoystickPad mJoystickPad;
    private GameButton mGameButtonA;
    private GameButton mGameButtonB;
    private GameButton mGameButtonC;
    private GameButton mGameButtonD;

    boolean buttonA = false;
    boolean buttonB = false;
    boolean buttonC = false;
    boolean buttonD = false;

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private int dx = 0;
    private int dy = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_pad);

        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);
        mJoystickPad = (JoystickPad) findViewById(R.id.joystick_pad);
        mJoystickPad.setJoyStickActionListener(this);
        mGameButtonA = (GameButton) findViewById(R.id.game_button_a);
        mGameButtonB = (GameButton) findViewById(R.id.game_button_b);
        mGameButtonC = (GameButton) findViewById(R.id.game_button_c);
        mGameButtonD = (GameButton) findViewById(R.id.game_button_d);

        mGameButtonA.setGameButtonListener(this);
        mGameButtonB.setGameButtonListener(this);
        mGameButtonC.setGameButtonListener(this);
        mGameButtonD.setGameButtonListener(this);
    }


    @Override
    protected void startBlePeripheralService() {
        Intent intent = new Intent(this, GaaMaService.class);
        intent.putExtra(GaaMaService.GAA_MA_SERVICE_COMMAND, GaaMaService.START_ADVERTISING);
        startService(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.title_exit));
        alertDialog.setMessage(getString(R.string.message_exit));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(GamePadActivity.this, GaaMaService.class);
                        intent.putExtra(GaaMaService.GAA_MA_SERVICE_COMMAND, GaaMaService.STOP_ADVERTISING);
                        startService(intent);
                        finish();
                    }
                });
        alertDialog.show();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    //Joystick Action Listener
    @Override
    public void onStickUp(JoystickPad v) {
        dx = 0;
        dy = 0;
        onChangeButtonStatus();

    }

    private void onChangeButtonStatus() {
        Intent intent = new Intent(this, GaaMaService.class);
        intent.putExtra(GaaMaService.GAA_MA_SERVICE_COMMAND, GaaMaService.INPUT_BUTTON_STATUS);
        Bundle bundle = new Bundle();
        bundle.putInt("dx", dx);
        bundle.putInt("dy", dy);
        bundle.putBoolean("buttonA", buttonA);
        bundle.putBoolean("buttonB", buttonB);
        bundle.putBoolean("buttonC", buttonC);
        bundle.putBoolean("buttonD", buttonD);
        bundle.putBoolean("buttonE", false);
        bundle.putBoolean("buttonF", false);
        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    public void onStickMove(JoystickPad v, int x, int y) {
        this.dx = x;
        this.dy = y;
        onChangeButtonStatus();
    }

    //GameButton Listener
    @Override
    public void onChangeButtonPressed(View view, boolean pressed) {
        if (view == mGameButtonA) {
            this.buttonA = pressed;
        } else if (view == mGameButtonB) {
            this.buttonB = pressed;
        } else if (view == mGameButtonC) {
            this.buttonC = pressed;
        } else if (view == mGameButtonD) {
            this.buttonD = pressed;
        }
        onChangeButtonStatus();
    }

}
