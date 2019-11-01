package utt.if26.androidtask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import java.time.OffsetDateTime;

import utt.if26.androidtask.receiver.ReminderReceiver;

import static android.content.Context.ALARM_SERVICE;

public class ReminderUtility {

    public void createAlarmForReminder(Context context, OffsetDateTime dateTime){
        Intent myIntent = new Intent(context , ReminderReceiver.class ) ;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService( ALARM_SERVICE ) ;
        PendingIntent pendingIntent = PendingIntent.getBroadcast ( context, 0 , myIntent , 0 ) ;
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP , dateTime.toInstant().toEpochMilli() ,  pendingIntent);
    }
}
