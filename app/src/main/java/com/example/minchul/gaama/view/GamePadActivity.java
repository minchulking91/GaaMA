package com.example.minchul.gaama.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.minchul.gaama.GaaMaService;
import com.example.minchul.gaama.GameButtonListener;
import com.example.minchul.gaama.R;
import com.example.minchul.gaama.widget.GameButton;
import com.example.minchul.gaama.widget.GameDotButton;
import com.example.minchul.gaama.widget.JoystickPad;

public class GamePadActivity extends AbstractBLEActivity implements JoystickPad.JoyStickActionListener, GameButtonListener {

    //VIEW
    private View mContentView;
    private JoystickPad mLeftStick;
    private JoystickPad mRightStick;
    private JoystickPad mHatSwitch;
    private GameDotButton mGameButtonA;
    private GameDotButton mGameButtonB;
    private GameDotButton mGameButtonX;
    private GameDotButton mGameButtonY;
    private GameButton mGameButtonLB;
    private GameButton mGameButtonRB;
    private GameButton mGameButtonLT;
    private GameButton mGameButtonRT;
    private GameButton mGameButtonBack;
    private GameButton mGameButtonStart;

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

    private int stick_x = 0;
    private int stick_y = 0;
    private int stick_rx = 0;
    private int stick_ry = 0;
    private int hat_switch = 0;
    private boolean buttonA = false;
    private boolean buttonB = false;
    private boolean buttonX = false;
    private boolean buttonY = false;
    private boolean buttonLB = false;
    private boolean buttonRB = false;
    private boolean buttonLT = false;
    private boolean buttonRT = false;
    private boolean buttonBack = false;
    private boolean buttonStart = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_pad);

        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);
        mLeftStick = (JoystickPad) findViewById(R.id.left_stick);
        mRightStick = (JoystickPad) findViewById(R.id.right_stick);
        mHatSwitch = (JoystickPad) findViewById(R.id.direction_pad);
        mRightStick.setJoyStickActionListener(this);
        mLeftStick.setJoyStickActionListener(this);
        mHatSwitch.setJoyStickActionListener(this);
        mGameButtonA = (GameDotButton) findViewById(R.id.btn_a);
        mGameButtonB = (GameDotButton) findViewById(R.id.btn_b);
        mGameButtonX = (GameDotButton) findViewById(R.id.btn_x);
        mGameButtonY = (GameDotButton) findViewById(R.id.btn_y);
        mGameButtonLB = (GameButton) findViewById(R.id.btn_lb);
        mGameButtonLT = (GameButton) findViewById(R.id.btn_lt);
        mGameButtonRB = (GameButton) findViewById(R.id.btn_rb);
        mGameButtonRT = (GameButton) findViewById(R.id.btn_rt);
        mGameButtonBack = (GameButton) findViewById(R.id.btn_back);
        mGameButtonStart = (GameButton) findViewById(R.id.btn_start);
        mGameButtonA.setGameButtonListener(this);
        mGameButtonB.setGameButtonListener(this);
        mGameButtonX.setGameButtonListener(this);
        mGameButtonY.setGameButtonListener(this);
        mGameButtonBack.setGameButtonListener(this);
        mGameButtonStart.setGameButtonListener(this);
        mGameButtonLB.setGameButtonListener(this);
        mGameButtonLT.setGameButtonListener(this);
        mGameButtonRB.setGameButtonListener(this);
        mGameButtonRT.setGameButtonListener(this);
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
        if (v == mLeftStick) {
            stick_x = 0;
            stick_y = 0;
        } else if (v == mRightStick) {
            stick_rx = 0;
            stick_ry = 0;
        } else if (v == mHatSwitch) {
            hat_switch = Integer.MAX_VALUE;
        }
        onChangeButtonStatus();

    }

    private void onChangeButtonStatus() {
        Intent intent = new Intent(this, GaaMaService.class);
        intent.putExtra(GaaMaService.GAA_MA_SERVICE_COMMAND, GaaMaService.INPUT_BUTTON_STATUS);
        Bundle bundle = new Bundle();
        bundle.putInt(GaaMaService.STICK_X, stick_x);
        bundle.putInt(GaaMaService.STICK_Y, stick_y);
        bundle.putInt(GaaMaService.STICK_RX, stick_rx);
        bundle.putInt(GaaMaService.STICK_RY, stick_ry);
        bundle.putInt(GaaMaService.HAT_SWITCH, hat_switch);
        bundle.putBoolean(GaaMaService.BUTTON_A, buttonA);
        bundle.putBoolean(GaaMaService.BUTTON_B, buttonB);
        bundle.putBoolean(GaaMaService.BUTTON_X, buttonX);
        bundle.putBoolean(GaaMaService.BUTTON_Y, buttonY);
        bundle.putBoolean(GaaMaService.BUTTON_LB, buttonLB);
        bundle.putBoolean(GaaMaService.BUTTON_RB, buttonRB);
        bundle.putBoolean(GaaMaService.BUTTON_LT, buttonLT);
        bundle.putBoolean(GaaMaService.BUTTON_RT, buttonRT);
        bundle.putBoolean(GaaMaService.BUTTON_BACK, buttonBack);
        bundle.putBoolean(GaaMaService.BUTTON_START, buttonStart);

        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    public void onStickMove(JoystickPad v, int x, int y, float degrees) {
        if (v == mLeftStick) {
            this.stick_x = x;
            this.stick_y = y;
        } else if (v == mRightStick) {
            this.stick_rx = x;
            this.stick_ry = y;
        } else if (v == mHatSwitch) {
            hat_switch = (int) degrees;
        }
        onChangeButtonStatus();
    }

    //GameButton Listener
    @Override
    public void onChangeButtonPressed(View view, boolean pressed) {
        if (view == mGameButtonA) {
            this.buttonA = pressed;
        } else if (view == mGameButtonB) {
            this.buttonB = pressed;
        } else if (view == mGameButtonX) {
            this.buttonX = pressed;
        } else if (view == mGameButtonY) {
            this.buttonY = pressed;
        } else if (view == mGameButtonBack) {
            this.buttonBack = pressed;
        } else if (view == mGameButtonStart) {
            this.buttonStart = pressed;
        } else if (view == mGameButtonLB) {
            this.buttonLB = pressed;
        } else if (view == mGameButtonLT) {
            this.buttonLT = pressed;
        } else if (view == mGameButtonRB) {
            this.buttonRB = pressed;
        } else if (view == mGameButtonRT) {
            this.buttonRT = pressed;
        }

        onChangeButtonStatus();
    }

}
