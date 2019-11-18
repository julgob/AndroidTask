package utt.if26.androidtask.ui;

import android.os.Bundle;

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

    }

    public void showReminder(ReminderEntity reminderEntity){
        this.reminderEntity = reminderEntity;
        //aficher sur lecran toute les bonnes valeur
    }

    //faudra une fonction qui update le reminder quand on clique save
    //utiliser la methode updatereminder du repo, on a qua passer lobjet reminder entity modifié (faut que lid soit le meme evidemment


    public void deleteReminder(int id){
        this.repository.deleteReminder(id,this);
        this.onBackPressed();
    }

    public void scheduleNotification(int reminderId,OffsetDateTime triggerDateTime){
        this.repository.scheduleNotification(reminderId,triggerDateTime,this);
    }

    public void disableNotification(int reminderId){
        this.repository.setNotificationDisabled(reminderId,this);
    }

    public void enableNotification(int reminderId){
        this.repository.setNotificationEnabled(reminderId,this);
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
