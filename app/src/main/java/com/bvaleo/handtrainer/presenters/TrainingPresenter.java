package com.bvaleo.handtrainer.presenters;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.bvaleo.handtrainer.ui.ITrainingView;

/**
 * Created by Valery on 19.03.2018.
 */

public class TrainingPresenter implements ITrainingPresenter {

    private ITrainingView mView;
    private Context mContext;

    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private Vibrator mVibrator;

    private int mCounter;

    public TrainingPresenter(Context context, ITrainingView view){
        mContext = context;
        mView = view;

        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        try {
            mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } catch (NullPointerException ex){
            Toast.makeText(context, "Акселерометр недоступен", Toast.LENGTH_SHORT).show();
        }

        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

    }

    private boolean flag = false;

    private SensorEventListener accelerometerListener = new SensorEventListener() {

        public void onAccuracyChanged(Sensor sensor, int acc) { }
        public void onSensorChanged(SensorEvent event) {
            float x =  event.values[0];
            int y = (int) event.values[1];
            int z = (int) event.values[2];

            if(x > -2 && x < 2) {
                Log.d("Sensor X", Float.toString(x));
                flag = true;
            }
            if(flag && y == 0 && z > -2 && z < 2) {
                //Log.d("Flag", Boolean.toString(flag));
                flag = false;
                mView.refreshCounter(++mCounter);
            }
        }
    };

    @Override
    public void startTraining() {
        mSensorManager.registerListener(accelerometerListener, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void finishTraining() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mVibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            mVibrator.vibrate(500);
        }
        mSensorManager.unregisterListener(accelerometerListener);
        mView.finishTraining(mCounter);
        mCounter = 0;
    }
}
