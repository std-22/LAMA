package io.github.studio22.lama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Melikbekyan Ashot & Tolokonov Ilya
 * @version 1.0
 * Стартовый экран с автопереключением, реализованным через таймер
 */

public class MainActivity extends AppCompatActivity {
    private final Timer timer = new Timer();
    SharedPreferences sharedPreferences;
    Boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        state = sharedPreferences.loadNightModeState();

        if (state){
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView lama = findViewById(R.id.lama_image);
        if(state){
            lama.setImageResource(R.drawable.logo_new_dark);
        } else {
            lama.setImageResource(R.drawable.logo_new);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MenuNavActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1800);
    }
}