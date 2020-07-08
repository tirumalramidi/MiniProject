package com.example.miniproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.widget.Toast;

public class ExecutableService extends BroadcastReceiver {

    LocationManager locationManager;
    LocationListener locationListener;
    Location currentWorkingLocation;

    public void update(){

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        
        final ExecutableService obj=new ExecutableService();


        new CountDownTimer(5*60*1000,10*1000){

            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(context,"10 seconds finished ",Toast.LENGTH_SHORT).show();

                obj.update();



            }

            @Override
            public void onFinish() {

                Toast.makeText(context,"finished", Toast.LENGTH_SHORT).show();

            }
        }.start();
        
        
    }



}
