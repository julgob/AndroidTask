package utt.if26.androidtask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class MainActivityViewModel extends AndroidViewModel implements AsyncCallback{

    Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void addReminder(OffsetDateTime dateTime){
       this.repository.addNewReminder(new ReminderEntity("test",dateTime),this);
    }

    public LiveData<List<ReminderEntity>> getAll(){
        return repository.getAllReminder();
    }

    public void deleteById(int id){
        this.repository.deleteReminder(id,this);
    }

    @Override
    public void callback(Optional<ReminderEntity> reminderToSchedule,Optional<ReminderEntity> firedReminder) {
        AlarmManagerUtility.cancelAlarm(getApplication().getApplicationContext());
        if(reminderToSchedule.isPresent()){
            ReminderEntity reminder = reminderToSchedule.get();
            AlarmManagerUtility.createAlarmForReminder(getApplication().getApplicationContext(),reminder.getTriggerDateTime());
        }
    }

    public void toggle(int reminderId, boolean reminderActive){
        if(reminderActive)
            this.repository.setReminderEnabled(reminderId,this);
        else
            this.repository.setReminderDisabled(reminderId,this);
    }
}
