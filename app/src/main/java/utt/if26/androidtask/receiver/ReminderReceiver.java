package utt.if26.androidtask.receiver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
        Toast.makeText(context.getApplicationContext(), "Alarm Manager just ran", Toast.LENGTH_LONG).show();
        archiveReminderAndSetNextAlarm(context);
    }

    private void archiveReminderAndSetNextAlarm(Context context){
        Repository repository = new Repository(context.getApplicationContext());
        repository.archiveReminderAndGetNext(this);
    }

    //callbak depuis repository , reminderentites contient en 0 le reminder qui vient detre executé et en 1 le next
    public void callback(Optional<ReminderEntity> reminderToSchedule, Optional<ReminderEntity> firedReminder){
        if(firedReminder.isPresent())
            makeNotif(context,firedReminder.get());
        //peut etre null si le reminder qui vient de sexecuter etait le dernier (a verifier)
        if(reminderToSchedule.isPresent()){
            setNextAlarm(context,reminderToSchedule.get());
        }
    }

    //IL FAUT CREER ET GERE LESCHANNEL QQ PART ET VIRER LES MAGIC NUMBER
    //UTILISER LE REPOSITORY POUR CHERCHER DONN2ES DU REMINDER
    private void makeNotif(Context context,ReminderEntity firedReminder){
        Notification notification = buildNotification(context,firedReminder);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(firedReminder.getReminderId(), notification);
    }

    private Notification buildNotification(Context context,ReminderEntity reminderEntity){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "241")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(reminderEntity.getTitre())
                .setContentText(reminderEntity.getTitre())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

    private void setNextAlarm(Context context,ReminderEntity nextReminder){
        AlarmManagerUtility alarmManagerUtility = new AlarmManagerUtility();
        alarmManagerUtility.createAlarmForReminder(context,nextReminder.getDateTime());
    }
}
