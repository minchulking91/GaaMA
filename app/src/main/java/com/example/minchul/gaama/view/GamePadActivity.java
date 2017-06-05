package com.example.minchul.gaama.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.example.minchul.gaama.GaaMaService;
import com.example.minchul.gaama.R;
import com.example.minchul.gaama.widget.GameButton;
import com.example.minchul.gaama.widget.JoystickPad;

public class GamePadActivity extends AbstractBLEActivity implements JoystickPad.JoyStickActionListener, GameButton.GameButtonListener {

    //VIEW
    private View mContentView;
    private JoystickPad mLeftStick;
    private JoystickPad mRightStick;
    private JoystickPad mHatSwitch;
    private GameButton mGameButtonA;
    private GameButton mGameButtonB;
    private GameButton mGameButtonC;
    private GameButton mGameButtonD;
    private GameButton mGameButtonLB;
    private GameButton mGameButtonRB;
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
    private int hatswitch = 0;
    private boolean buttonA = false;
    private boolean buttonB = false;
    private boolean buttonC = false;
    private boolean buttonD = false;
    private boolean buttonLB = false;
    private boolean buttonRB = false;
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
        mGameButtonA = (GameButton) findViewById(R.id.game_button_a);
        mGameButtonB = (GameButton) findViewById(R.id.game_button_b);
        mGameButtonC = (GameButton) findViewById(R.id.game_button_c);
        mGameButtonD = (GameButton) findViewById(R.id.game_button_d);
        mGameButtonBack = (GameButton) findViewById(R.id.btn_back);
        mGameButtonStart = (GameButton) findViewById(R.id.btn_start);
        mGameButtonA.setGameButtonListener(this);
        mGameButtonB.setGameButtonListener(this);
        mGameButtonC.setGameButtonListener(this);
        mGameButtonD.setGameButtonListener(this);
        mGameButtonBack.setGameButtonListener(this);
        mGameButtonStart.setGameButtonListener(this);
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
        }else if(v == mRightStick){
            stick_rx = 0;
            stick_ry = 0;
        }else if(v == mHatSwitch){
            hatswitch = Integer.MAX_VALUE;
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
        bundle.putInt(GaaMaService.HAT_SWITCH, hatswitch);
        bundle.putBoolean(GaaMaService.BUTTON_A, buttonA);
        bundle.putBoolean(GaaMaService.BUTTON_B, buttonB);
        bundle.putBoolean(GaaMaService.BUTTON_C, buttonC);
        bundle.putBoolean(GaaMaService.BUTTON_D, buttonD);
        bundle.putBoolean(GaaMaService.BUTTON_BACK, buttonBack);
        bundle.putBoolean(GaaMaService.BUTTON_START, buttonStart);

        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    public void onStickMove(JoystickPad v, int x, int y, float degrees) {
        if(v == mLeftStick) {
            this.stick_x = x;
            this.stick_y = y;
        }else if(v == mRightStick){
            this.stick_rx = x;
            this.stick_ry = y;
        }else if(v == mHatSwitch){
            hatswitch = (int) degrees;
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
        } else if (view == mGameButtonC) {
            this.buttonC = pressed;
        } else if (view == mGameButtonD) {
            this.buttonD = pressed;
        }else if (view == mGameButtonBack){
            this.buttonBack = pressed;
        }else if(view == mGameButtonStart){
            this.buttonStart = pressed;
        }
        onChangeButtonStatus();
    }

}
