package com.ayesha.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView timer;
    Button start, reset;
    static int flag = -1;
    static int counter = -1;
    private int seconds = 0;
    private boolean isRunning;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timer);
        start = findViewById(R.id.startBtn);
        reset = findViewById(R.id.resetBtn);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");

        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;

                if( flag == -1 && counter % 2 == 0) {
                    flag++;
                    onClickStart();
                }
                else if( counter % 2 != 0)
                    onPause();
                else
                    onResume();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStop();
            }
        });

        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("isRunning", isRunning);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStart()
    {
        start.setText("Pause");
        isRunning = true;

    }

    @Override
    public void onPause()
    {
            super.onPause();
            wasRunning = isRunning;
            isRunning = false;
            start.setText("Resume");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if( wasRunning )
        {
            start.setText("Pause");
            isRunning = true;
        }
    }

    public void onClickStop(){
        isRunning = false;
        timer.setText("00:00:00");
        flag = -1;
        counter = -1;
        seconds = 0;
        start.setText("Start");
    }

    public void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d ", hours, minutes, secs);
                timer.setText(time);

                if (isRunning)
                    seconds++;

                handler.postDelayed(this, 1000);
            }
        });
    }
}