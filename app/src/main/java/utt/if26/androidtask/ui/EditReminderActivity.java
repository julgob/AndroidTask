package utt.if26.androidtask.ui;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.OffsetDateTime;
import java.util.Optional;

import utt.if26.androidtask.AlarmManagerUtility;
import utt.if26.androidtask.AsyncCallback;
import utt.if26.androidtask.R;
import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class EditReminderActivity extends AppCompatActivity implements AsyncCallback {
    Repository repository;
    ReminderEntity reminderEntity;
    private int notifHour;
    private int notifMinute;
    private int notifMonth;
    private int notifDay;
    private int notifYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        this.repository = new Repository(this);
        int reminderId;
        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey("reminder_id")){
                reminderId = getIntent().getIntExtra("reminder_id",0);
                repository.getReminderForId(reminderId).observe(this,this::showReminder);
            }

        }

        createNotificationChannel();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder Notification";
            String description = "Notification for the reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("0", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void showReminder(ReminderEntity reminderEntity){
        this.reminderEntity = reminderEntity;
        //aficher sur lecran toute les bonnes valeur
    }

    //faudra une fonction qui update le reminder quand on clique save
    //utiliser la methode updatereminder du repo, on a qua passer lobjet reminder entity modifié (faut que lid soit le meme evidemment

    public void onDeleteClick(View v){
     if(this.reminderEntity != null){
         deleteReminder(this.reminderEntity.getReminderId());
     }
        this.onBackPressed();
    }

    public void deleteReminder(int id){
        this.repository.deleteReminder(id,this);
    }


    public void disableNotification(int reminderId){
        this.repository.setNotificationDisabled(reminderId,this);
    }

    public void enableNotification(int reminderId){
        this.repository.setNotificationEnabled(reminderId,this);
    }

    public void onTimeClick(View v){
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        TimePickerDialog picker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                notifHour    = hourOfDay;
                notifMinute  = minute;
            }
        },offsetDateTime.getHour(),offsetDateTime.getMinute(),true);
        picker.show();
    }

    public void onDateClick(View v){
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                notifYear   = year;
                notifMonth  = month+1;
                notifDay    = dayOfMonth;
            }
        },offsetDateTime.getYear(),offsetDateTime.getMonthValue()-1,offsetDateTime.getDayOfMonth());
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        picker.show();
    }


    public void onSaveNotifClick(View v){
        if(notifDateTimeIsValid()){
            createNotif();
        }else  {
            showCantCreateNotif();
        }
    }

    private boolean notifDateTimeIsValid(){
        return notifDay != 0 && notifMonth !=0 && notifYear != 0 && this.notifHour != 0 && this.notifDay != 0;
    }

    private void createNotif(){
        this.repository.scheduleNotification(this.reminderEntity.getReminderId(),makeNotifDateTime(),this);
    }

    private OffsetDateTime makeNotifDateTime(){
        return OffsetDateTime.of(this.notifYear,this.notifMonth,this.notifDay,this.notifHour,this.notifMinute,0,0,OffsetDateTime.now().getOffset());
    }

    private void showCantCreateNotif(){
        Toast toast = Toast.makeText(this,getString(R.string.cant_create_notif_toast),Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void callback(Optional<ReminderEntity> reminderToSchedule, Optional<ReminderEntity> firedReminder) {
        AlarmManagerUtility.cancelAlarm(getApplication().getApplicationContext());
        if(reminderToSchedule.isPresent()){
            ReminderEntity reminder = reminderToSchedule.get();
            AlarmManagerUtility.createAlarmForReminder(getApplication().getApplicationContext(),reminder.getTriggerDateTime());
        }
    }
}
