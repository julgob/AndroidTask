package utt.if26.androidtask.receiver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Optional;

import utt.if26.androidtask.AlarmManagerUtility;
import utt.if26.androidtask.AsyncCallback;
import utt.if26.androidtask.R;
import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class ReminderReceiver extends BroadcastReceiver implements AsyncCallback {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context =context;
        archiveReminderAndSetNextAlarm(context);
    }

    private void archiveReminderAndSetNextAlarm(Context context){
        Repository repository = new Repository(context.getApplicationContext());
        repository.archiveReminderAndSetNext(this);
    }

    public void callback(Optional<ReminderEntity> reminderToSchedule, Optional<ReminderEntity> firedReminder){
        if(firedReminder.isPresent())
            makeNotif(context,firedReminder.get());
        if(reminderToSchedule.isPresent()){
            setNextAlarm(context,reminderToSchedule.get());
        }
    }


    private void makeNotif(Context context,ReminderEntity firedReminder){
        Notification notification = buildNotification(context,firedReminder);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(firedReminder.getReminderId(), notification);
    }

    private Notification buildNotification(Context context,ReminderEntity reminderEntity){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(reminderEntity.getTitre())
                .setContentText(reminderEntity.getComment())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

    private void setNextAlarm(Context context,ReminderEntity nextReminder){
        AlarmManagerUtility alarmManagerUtility = new AlarmManagerUtility();
        alarmManagerUtility.createAlarmForReminder(context,nextReminder.getTriggerDateTime());
    }
}
