package com.voice.bridgejsjava;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerSensor implements SensorEventListener {

    private SensorManager sensorManager;
    private Context context;
    private AccelerometerSensorChangedListener listener;

    AccelerometerSensor(Context context){
        this.context = context;
        setSettings();
    }

    private void setSettings()
    {
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null)
        {
            Log.e("ACCELEROMETER SENSOR", "not found");
            return;
        }
        Log.e("ACCELEROMETER SENSOR", "found");
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    void setListener(AccelerometerSensorChangedListener listener){
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        try
        {
            listener.onAccelerometerSensorChanged(event.values[1], event.values[0], event.values[2]);
        }
        catch (Exception ex)
        {
            Log.e("ACCELEROMETER SENSOR", ex.getMessage());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

     void close(){
        Log.e("ACCELEROMETER SENSOR", "sensor unregistered");
        sensorManager.unregisterListener(this);
     }

    public interface AccelerometerSensorChangedListener
    {
        void onAccelerometerSensorChanged(float x, float y, float z);
    }
}
