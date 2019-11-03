package utt.if26.androidtask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import java.time.OffsetDateTime;

import utt.if26.androidtask.receiver.ReminderReceiver;

import static android.content.Context.ALARM_SERVICE;

public class AlarmManagerUtility {

    public  static void createAlarmForReminder(Context context, OffsetDateTime dateTime){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService( ALARM_SERVICE ) ;
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dateTime.toInstant().toEpochMilli(), createPendingIntent(context));
    }

    public static PendingIntent createPendingIntent(Context context){
        Intent myIntent = new Intent(context, ReminderReceiver.class ) ;
        PendingIntent pendingIntent = PendingIntent.getBroadcast (context, 0 , myIntent ,PendingIntent.FLAG_CANCEL_CURRENT) ;
        return pendingIntent;
    }

    public static void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createPendingIntent(context));
    }
}
