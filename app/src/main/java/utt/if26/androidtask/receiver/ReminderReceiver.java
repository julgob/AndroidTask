package utt.if26.androidtask.receiver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import utt.if26.androidtask.R;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "Alarm Manager just ran", Toast.LENGTH_LONG).show();
        makeNotif(context);
        archiveReminder(reminderId);
        setNextAlarm();
        setNextReminderNotification();
        // faire avec un repository voir historique youtube
        // e
    }

    //IL FAUT CREER ET GERE LESCHANNEL QQ PART ET VIRER LES MAGIC NUMBER
    //UTILISER LE REPOSITORY POUR CHERCHER DONN2ES DU REMINDER
    private void makeNotif(Context context){

        ReminderEntity reminder = getFiredReminder();
        Notification notification = buildNotification(context,reminder);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(2, notification);



    }

    private Notification buildNotification(Context context,ReminderEntity reminderEntity){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "21")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("notif")
                .setContentText("notfi content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder.build();

    }



    private void setNextReminderNotification(){
        //use service to check database and set next alarmmanager
    }
}
