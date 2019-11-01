package utt.if26.androidtask;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import utt.if26.androidtask.persistance.entity.ReminderEntity;
import utt.if26.androidtask.receiver.ReminderReceiver;

//pour le time faut faire gaffe au fait que le date picker renvoit entre 0 et 11 et pas 1 et 12 alors que je crois que les datetime prennent entre 1 et 12
public class Main2Activity extends AppCompatActivity implements AsyncCallback {
    private int hour1;
    private int minute1;
    private int hour2;
    private int minute2;
    private int hour3;
    private int minute3;
    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        createNotificationChannel();
    }

    public void add1(View v){
        add(1);
    }public void add2(View v){
        add(2);
    }public void add3(View v){
        add(3);
    }

    private void add(int i){
        int mHour ;
        int mMinute;
        switch (i){
            case 1 : mHour = hour1;mMinute= minute1;break;
            case 2 : mHour = hour2;mMinute= minute2;break;
            case 3 : mHour = hour3;mMinute= minute3;break;
            default: mHour = 1; mMinute = 1;
        }

        OffsetDateTime offsetDateTime =  OffsetDateTime.of(2019,11,1,mHour,mMinute,0,0, OffsetDateTime.now().getOffset());

        viewModel.addReminder(offsetDateTime,this);
    }

    public void time1(View v){
        showTime(1);
    }
    public void time2(View v){
        showTime(2);
    }

    public void time3(View v){
        showTime(3);
    }

    private void showTime(final int i){
        // Get Current Time
        OffsetDateTime offsetDateTime =  OffsetDateTime.now();
        int mHour = offsetDateTime.getHour();
        int mMinute = offsetDateTime.getMinute();
        // Launch Time Picker Dialog

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        switch (i){
                            case 1:hour1 = hourOfDay;minute1 = minute;break;
                            case 2:hour2 = hourOfDay;minute2 = minute;break;
                            case 3:hour3 = hourOfDay;minute3 = minute;break;

                        }
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    //callback après ajout, toggle , deletion
    //si un reminderentity est dans la liste en 0 alors faut delete l'alarm current et mettre nouveau pour ce reminder
    @Override
    public void callback(List<Optional<ReminderEntity>> entity) {
        //doit mettre lalarm si ya un truc
        if(entity.get(0).isPresent()){
            ReminderEntity reminder = entity.get(0).get();
            Intent myIntent = new Intent(this , ReminderReceiver.class ) ;
            AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE ) ;
            PendingIntent pendingIntent = PendingIntent.getBroadcast ( this, reminder.getReminderId() , myIntent , 0 ) ;
            //debugging
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP , reminder.getDateTime().toInstant().toEpochMilli(), pendingIntent);
        }
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("241", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
