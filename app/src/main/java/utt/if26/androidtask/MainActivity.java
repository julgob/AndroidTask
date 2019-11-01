package utt.if26.androidtask;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;

import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.receiver.ReminderReceiver;

public class MainActivity extends AppCompatActivity {

    private int hour1;
    private int minute1;
    private int hour2;
    private int minute2;
    private int hour3;
    private int minute3;

    private AndroidViewModel viewModel;
    private Repository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        createNotificationChannel();
    }


    public void schedule1(View v){
        schedule(1);
    }public void schedule2(View v){
        schedule(2);
    }public void schedule3(View v){
        schedule(3);
    }

    private void schedule(int i){
        Intent myIntent = new Intent(this , ReminderReceiver.class ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE ) ;
        PendingIntent pendingIntent = PendingIntent.getBroadcast ( this, i , myIntent , 0 ) ;
        Calendar calendar = Calendar.getInstance() ;
        int mHour ;
        int mMinute;
        switch (i){
            case 1 : mHour = hour1;mMinute= minute1;break;
            case 2 : mHour = hour2;mMinute= minute2;break;
            case 3 : mHour = hour3;mMinute= minute3;break;
            default: mHour = 1; mMinute = 1;
        }
        calendar.set(2019,10,30,mHour,mMinute);
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis() ,  pendingIntent);
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
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

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
                }, mHour, mMinute, false);
        timePickerDialog.show();
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
