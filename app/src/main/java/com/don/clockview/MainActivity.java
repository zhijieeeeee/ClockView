package com.don.clockview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.don.clockviewlibrary.ClockView;

public class MainActivity extends AppCompatActivity {

    private ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockView = (ClockView) findViewById(R.id.clockView);
        clockView.setOnCurrentTimeListener(new ClockView.OnCurrentTimeListener() {
            @Override
            public void currentTime(String time) {
                Log.i("MyLog", time);
            }
        });
    }
}
