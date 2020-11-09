package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {



// Variables for phone's button.
private MaterialButton main_BTN_click;

    private SensorManager sensorManager;
    private Sensor tiltSensor;
    private SensorEventListener sensorEventListener;

    // Variables for phone's brightness case.
    private int curBrigthLight;
    private final int MAX_LIGHT = 255;

    // Variable for tilt sensor.
    private double xTilt = 0;
    private float[] gData = new float[3], mData = new float[3],
            rMat = new float[9], iMat = new float[9], orientation = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        main_BTN_click = findViewById(R.id.main_BTN_click);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tiltSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    xTilt = sensorEvent.values[0];
                    gData = sensorEvent.values.clone();
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };

main_BTN_click.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        registerTheTilt();

        // If the bright or the  is on the max level it will sign in.
        try {
            curBrigthLight = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if(curBrigthLight == MAX_LIGHT|| (xTilt > 3 && xTilt < 10)){
            Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, LoginSuccessful.class);
            startActivity(intent);

        }

        else{
            Toast.makeText(MainActivity.this,"Login failed!", Toast.LENGTH_LONG).show();

        }

    }
});

    }
    private void registerTheTilt(){
        sensorManager.registerListener(sensorEventListener, tiltSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

}