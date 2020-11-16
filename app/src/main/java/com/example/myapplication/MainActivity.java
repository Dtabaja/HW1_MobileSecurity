package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    // Variables for phone's button.
    private MaterialButton main_BTN_click;

    // Variable for sensors.
    private SensorManager sensorManager;
    private Sensor tiltSensor;
    private Sensor stepCounterSensor;
    private SensorEventListener sensorEventListener;

    // Variables for phone's brightness case.
    private int currentBrigthLight;
    private final int MAX_LIGHT = 255;

    // Variable for tilt sensor.
    private double xTilt = 0;

    // Variables for step counter sensor.
    private float stepCounter = 0, initialStepCounter = 0;
    private boolean firstStep = true;

    // Variables for phone's Volume sound's case.
    private AudioManager audio;
    private int currentVolume;
    private int maxVolume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        main_BTN_click = findViewById(R.id.main_BTN_click);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tiltSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    xTilt = sensorEvent.values[0];
                }
                if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                    if (firstStep) {
                        initialStepCounter = sensorEvent.values[0];
                        firstStep = false;
                    }
                    stepCounter = sensorEvent.values[0] - initialStepCounter;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };

        main_BTN_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerTheSensors();
                audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
                maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

                try {
                    currentBrigthLight = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                //If the bright or the  is on the max level it will sign in.
                if (currentBrigthLight == MAX_LIGHT) {
                    Toast.makeText(MainActivity.this, "Login Successfully-MaxLight!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, LoginSuccessful.class);
                    startActivity(intent);
                }

                //If  the phone in tilt to the left.
                else if (xTilt > 3 && xTilt < 10) {
                    Toast.makeText(MainActivity.this, "Login Successfully- tilt!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, LoginSuccessful.class);
                    startActivity(intent);
                }

                //If you make more then 5 steps.
                else if (stepCounter > 5) {
                    Toast.makeText(MainActivity.this, "Login Successfully-steps!", Toast.LENGTH_LONG).show();
                    stepCounter = 0;
                    Intent intent = new Intent(MainActivity.this, LoginSuccessful.class);
                    startActivity(intent);
                }

                //If your volume sound level is at max.
                else if (currentVolume == maxVolume) {
                    Toast.makeText(MainActivity.this, "Login Successfully-MaxVolume!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, LoginSuccessful.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Login failed!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void registerTheSensors() {
        sensorManager.registerListener(sensorEventListener, tiltSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

}