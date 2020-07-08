package com.example.miniproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHandler {

    Context context;
    Intent intent;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    public AlarmHandler(Context context) {
        this.context = context;
        intent=new Intent(context,ExecutableService.class);
        pendingIntent=PendingIntent.getBroadcast(context,2,intent,0);
        alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarmManager(){

        //Intent intent=new Intent(context,ExecutableService.class);
        //PendingIntent pendingIntent=PendingIntent.getBroadcast(context,2,intent,0);

        //AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager!=null) {
            long triggerAfter=20*60*1000;
            long triggerEvery=20*60*1000;
            alarmManager.setRepeating(AlarmManager.RTC, triggerAfter, triggerEvery, pendingIntent);
        }
    }

    public void cancelAlarmManager(){
        //Intent intent=new Intent(context,ExecutableService.class);
        //PendingIntent pendingIntent=PendingIntent.getBroadcast(context,2,intent,0);
       //AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager!=null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
