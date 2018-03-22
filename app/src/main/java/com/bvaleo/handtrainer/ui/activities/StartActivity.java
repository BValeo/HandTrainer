package com.bvaleo.handtrainer.ui.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bvaleo.handtrainer.R;

public class StartActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_start);
        setTheme(R.style.CustomTheme);
        mHandler.postDelayed(process, 5000);
    }

    private final Runnable process = () -> {

        final Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        StartActivity.this.startActivity(intent);
        StartActivity.this.finish();
    };

}
