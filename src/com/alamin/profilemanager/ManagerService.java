package com.alamin.profilemanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerService extends Service implements SensorEventListener{
	
	SensorManager sensorManager;
	AudioManager audioManager;
	
	Sensor ProximitySensor;
	Sensor Accelerometer;
	Sensor Light;
	
	boolean object=false, screenup=false, light=false;
	String vertical="none";
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int onStartCommand(Intent intent, int flags, int startId){
		//Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		
		
		ProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		Accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		
		audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		//Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();
		sensorManager.registerListener(this, ProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, Accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, Light, SensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        return START_STICKY;
	}
	
	public void onSensorChanged(SensorEvent event){
		if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
				float data = event.values[0];
				if(data<5.0){
					object=true;
				}
				else{
					object=false;
				}
			}
		
	
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			//float datax = event.values[0];
			float datay = event.values[1];
			float dataz = event.values[2];
			vertical="none";
				
				if(datay > 7.0){
					vertical="up";
				}else if(datay < -7.0){
					vertical="down";
				}
				
				if(dataz > -6.0){
					screenup=true;
				}
				else{
					screenup=false;
				}
			}
			
		if(event.sensor.getType() == Sensor.TYPE_LIGHT){
			float data = event.values[0];
				
				if(data>=6.3){
					light=true;
				}
				else{
					light=false;
				}
		}
			
		if(screenup && !object && vertical=="none"){
			if(light){
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			}else{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			}
		}	
		else if(!light && !screenup){
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		}
		else if(object && vertical=="up"){
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		}	
		else if(vertical=="down"){
			audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}		
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Toast.makeText(getBaseContext(), "Stopped", Toast.LENGTH_SHORT).show();
        sensorManager.unregisterListener(this);
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
