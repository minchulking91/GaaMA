package com.example.minchul.gaama.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.minchul.gaama.GaaMaService;
import com.example.minchul.gaama.GameButtonListener;
import com.example.minchul.gaama.R;
import com.example.minchul.gaama.utils.Text2Bitmap;
import com.example.minchul.gaama.widget.GameButton;
import com.example.minchul.gaama.widget.GameDotButton;
import com.example.minchul.gaama.widget.JoystickPad;

public class GamePadActivity extends AbstractBLEActivity implements JoystickPad.JoyStickActionListener, GameButtonListener {

    //VIEW
    private View mContentView;
    private JoystickPad leftStick;
    private JoystickPad rightStick;
    private JoystickPad hatSwitch;
    private GameDotButton btnA;
    private GameDotButton btnB;
    private GameDotButton btnX;
    private GameDotButton btnY;
    private GameButton btnLB;
    private GameButton btnRB;
    private GameButton btnLT;
    private GameButton btnRT;
    private GameButton btnBack;
    private GameButton btnStart;
    private AppCompatButton btnFullScreen;
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

    private int leftStickX = 0;
    private int leftStickY = 0;
    private int rightStickX = 0;
    private int rightStickY = 0;
    private int hatSwitchDirection = 0;
    private boolean btnPressedA = false;
    private boolean btnPressedB = false;
    private boolean btnPressedX = false;
    private boolean btnPressedY = false;
    private boolean btnPressedLB = false;
    private boolean btnPressedRB = false;
    private boolean btnPressedLT = false;
    private boolean btnPressedRT = false;
    private boolean btnPressedBack = false;
    private boolean btnPressedStart = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_pad);

        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);
        leftStick = (JoystickPad) findViewById(R.id.left_stick);
        rightStick = (JoystickPad) findViewById(R.id.right_stick);
        hatSwitch = (JoystickPad) findViewById(R.id.direction_pad);
        rightStick.setJoyStickActionListener(this);
        leftStick.setJoyStickActionListener(this);
        hatSwitch.setJoyStickActionListener(this);
        btnA = (GameDotButton) findViewById(R.id.btn_a);
        btnB = (GameDotButton) findViewById(R.id.btn_b);
        btnX = (GameDotButton) findViewById(R.id.btn_x);
        btnY = (GameDotButton) findViewById(R.id.btn_y);
        btnA.setImageBitmap(Text2Bitmap.textAsBitmap("A", 30, Color.WHITE));
        btnB.setImageBitmap(Text2Bitmap.textAsBitmap("B", 30, Color.WHITE));
        btnX.setImageBitmap(Text2Bitmap.textAsBitmap("X", 30, Color.WHITE));
        btnY.setImageBitmap(Text2Bitmap.textAsBitmap("Y", 30, Color.WHITE));
        btnLB = (GameButton) findViewById(R.id.btn_lb);
        btnLT = (GameButton) findViewById(R.id.btn_lt);
        btnRB = (GameButton) findViewById(R.id.btn_rb);
        btnRT = (GameButton) findViewById(R.id.btn_rt);
        btnBack = (GameButton) findViewById(R.id.btn_back);
        btnStart = (GameButton) findViewById(R.id.btn_start);
        btnFullScreen = (AppCompatButton) findViewById(R.id.btn_fullscreen);
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        btnA.setGameButtonListener(this);
        btnB.setGameButtonListener(this);
        btnX.setGameButtonListener(this);
        btnY.setGameButtonListener(this);
        btnBack.setGameButtonListener(this);
        btnStart.setGameButtonListener(this);
        btnLB.setGameButtonListener(this);
        btnLT.setGameButtonListener(this);
        btnRB.setGameButtonListener(this);
        btnRT.setGameButtonListener(this);
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
        if (v == leftStick) {
            leftStickX = 0;
            leftStickY = 0;
        } else if (v == rightStick) {
            rightStickX = 0;
            rightStickY = 0;
        } else if (v == hatSwitch) {
            hatSwitchDirection = Integer.MAX_VALUE;
        }
        onChangeButtonStatus();

    }

    private void onChangeButtonStatus() {
        Intent intent = new Intent(this, GaaMaService.class);
        intent.putExtra(GaaMaService.GAA_MA_SERVICE_COMMAND, GaaMaService.INPUT_BUTTON_STATUS);
        Bundle bundle = new Bundle();
        bundle.putInt(GaaMaService.STICK_X, leftStickX);
        bundle.putInt(GaaMaService.STICK_Y, leftStickY);
        bundle.putInt(GaaMaService.STICK_RX, rightStickX);
        bundle.putInt(GaaMaService.STICK_RY, rightStickY);
        bundle.putInt(GaaMaService.HAT_SWITCH, hatSwitchDirection);
        bundle.putBoolean(GaaMaService.BUTTON_A, btnPressedA);
        bundle.putBoolean(GaaMaService.BUTTON_B, btnPressedB);
        bundle.putBoolean(GaaMaService.BUTTON_X, btnPressedX);
        bundle.putBoolean(GaaMaService.BUTTON_Y, btnPressedY);
        bundle.putBoolean(GaaMaService.BUTTON_LB, btnPressedLB);
        bundle.putBoolean(GaaMaService.BUTTON_RB, btnPressedRB);
        bundle.putBoolean(GaaMaService.BUTTON_LT, btnPressedLT);
        bundle.putBoolean(GaaMaService.BUTTON_RT, btnPressedRT);
        bundle.putBoolean(GaaMaService.BUTTON_BACK, btnPressedBack);
        bundle.putBoolean(GaaMaService.BUTTON_START, btnPressedStart);

        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    public void onStickMove(JoystickPad v, int x, int y, float degrees) {
        if (v == leftStick) {
            this.leftStickX = x;
            this.leftStickY = y;
        } else if (v == rightStick) {
            this.rightStickX = x;
            this.rightStickY = y;
        } else if (v == hatSwitch) {
            hatSwitchDirection = (int) degrees;
        }
        onChangeButtonStatus();
    }

    //GameButton Listener
    @Override
    public void onChangeButtonPressed(View view, boolean pressed) {
        if (view == btnA) {
            this.btnPressedA = pressed;
        } else if (view == btnB) {
            this.btnPressedB = pressed;
        } else if (view == btnX) {
            this.btnPressedX = pressed;
        } else if (view == btnY) {
            this.btnPressedY = pressed;
        } else if (view == btnBack) {
            this.btnPressedBack = pressed;
        } else if (view == btnStart) {
            this.btnPressedStart = pressed;
        } else if (view == btnLB) {
            this.btnPressedLB = pressed;
        } else if (view == btnLT) {
            this.btnPressedLT = pressed;
        } else if (view == btnRB) {
            this.btnPressedRB = pressed;
        } else if (view == btnRT) {
            this.btnPressedRT = pressed;
        }

        onChangeButtonStatus();
    }

}
