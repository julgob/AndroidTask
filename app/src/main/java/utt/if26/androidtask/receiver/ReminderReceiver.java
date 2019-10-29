package utt.if26.androidtask.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import utt.if26.androidtask.R;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "Alarm Manager just ran", Toast.LENGTH_LONG).show();
        makeNotif(context);
        setNextReminder();
    }

    //IL FAUT CREER ET GERE LESCHANNEL QQ PART ET VIRER LES MAGIC NUMBER
    private void makeNotif(Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "21")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("notif")
                .setContentText("notfi content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(2, builder.build());
    }

    private void setNextReminder(){
        //use service to check database and set next alarmmanager
    }
}
