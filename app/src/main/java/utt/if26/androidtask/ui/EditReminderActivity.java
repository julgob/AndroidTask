package utt.if26.androidtask.ui;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
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

    private TextView reminderTitleTV;
    private String reminderTitle;
    private Switch notifSwitch;
    private EditText commentEditText;
    private EditText titleEditText;

    private Button notfiDateButton;
    private Button notifTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        this.notifSwitch = findViewById(R.id.edit_reminder_notif_switch);
        this.commentEditText = findViewById(R.id.edit_activity_comment_text);
        this.titleEditText = findViewById(R.id.activity_edit_reminder_task_name);
        this.notfiDateButton = findViewById(R.id.edit_reminder_notif_date);
        this.notifTimeButton = findViewById(R.id.edit_reminder_notif_time);

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

    // fonction appelé en async (livedata) donc il faut mettre a jour tous les champs dans cette fonction
    //cette fonction sera appelé quand le reminder est obtenu pour la première foix mais aussi quand on le met ajour depuis cetteactivité
    public void showReminder(ReminderEntity reminderEntity){
        if(reminderEntity!=null){
            this.reminderEntity = reminderEntity;
            reminderTitle = reminderEntity.getTitre();
            reminderTitleTV = (TextView) findViewById(R.id.activity_edit_reminder_task_name);
            reminderTitleTV.setText(reminderTitle);
            if(reminderEntity.isNotificationIsEnabled()){
                this.notifSwitch.setChecked(true);
            }
            this.commentEditText.setText(reminderEntity.getComment());
            EditReminderActivity self = this;
            notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    self.activateNotif(isChecked);
                }
            });
            if(reminderEntity.getTriggerDateTime()!=null){
                OffsetDateTime dateTime = reminderEntity.getTriggerDateTime();
                this.notifYear = dateTime.getYear();
                this.notifMonth= dateTime.getMonthValue();
                this.notifDay = dateTime.getDayOfMonth();
                this.notifHour= dateTime.getHour();
                this.notifMinute= dateTime.getMinute();

                String notifMinuteString = String.valueOf(notifMinute);
                String notifHourString = String.valueOf(notifHour);
                if(notifHour<10){
                    notifHourString = "0"+notifHour;
                }
                if(notifMinute< 10){
                    notifMinuteString = "0"+notifMinute;
                }
                notifTimeButton.setText(notifHourString + ":" + notifMinuteString);
                notfiDateButton.setText(notifDay + " / " + notifMonth + " / " + notifYear);
            }
        }
        //aficher sur lecran toute les bonnes valeur
    }

    public void activateNotif(boolean isActivate){

        if(isActivate){
            this.repository.setNotificationEnabled(reminderEntity.getReminderId(),this::callback);
        }else {
            this.repository.setNotificationDisabled(reminderEntity.getReminderId(),this::callback);
        }
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
                String notifMinuteString = String.valueOf(notifMinute);
                String notifHourString = String.valueOf(notifHour);
                if(notifHour<10){
                    notifHourString = "0"+notifHour;
                }
                if(notifMinute< 10){
                    notifMinuteString = "0"+notifMinute;
                }
                notifTimeButton.setText(notifHourString + ":" + notifMinuteString);
                //Toast.makeText(getApplicationContext(),notifHour + " h " + notifMinute,Toast.LENGTH_LONG).show();
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
                notfiDateButton.setText(notifDay + " / " + notifMonth + " / " + notifYear);
                //Toast.makeText(getApplicationContext(),notifDay + " / " + notifMonth + " / " + notifYear,Toast.LENGTH_LONG).show();
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
        Toast.makeText(this,"Notification created for the " + notifDay + " / " + notifMonth + " / " + notifYear + " at " + notifHour + " h " + notifMinute,Toast.LENGTH_LONG).show();
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


    public void saveReminderModif(View v){
        this.reminderEntity.setComment(this.commentEditText.getText().toString());
        this.reminderEntity.setTitre(this.titleEditText.getText().toString());
        this.repository.updateReminder(reminderEntity);
    }
}
