package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

// Variables for phone's button.
private MaterialButton main_BTN_click;

 // Variables for phone's brightness case.
    private int curBrigthLight;
    private final int MAX_LIGHT = 255;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        main_BTN_click = findViewById(R.id.main_BTN_click);



main_BTN_click.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // If the bright is on the max level it will sign in.
        try {
            curBrigthLight = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if(curBrigthLight == MAX_LIGHT){
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
}