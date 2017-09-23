package com.alamin.profilemanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileManagerActivity extends Activity {
    
	Button startService;
	Button stopService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       // Toast.makeText(ProfileManagerActivity.this, "oncreate", Toast.LENGTH_SHORT).show();
        startService = (Button)findViewById(R.id.startButton);
        stopService = (Button)findViewById(R.id.stopButton);
        
        //final Intent profileManagerService = new Intent(this,ManagerService.class);
        //Toast.makeText(ProfileManagerActivity.this, "successfully on", Toast.LENGTH_SHORT).show();
    }


    public void startClicked(View view){
    	try{
    		Intent profileManagerService = new Intent(this,ManagerService.class);
    		startService(profileManagerService);
    	}
    	catch(Exception ex){
    		Toast.makeText(ProfileManagerActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
    	}
    	//startService(new Intent(getBaseContext(), ManagerService.class));
    }
    
    public void stopClicked(View view){
    	try{
    		Intent profileManagerService = new Intent(this,ManagerService.class);
    		stopService(profileManagerService);
    	}
    	catch(Exception ex){
    		Toast.makeText(ProfileManagerActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
    	}
    }

	protected void onStart() {
		// TODO Auto-generated method stub
        super.onStart();
    }
}