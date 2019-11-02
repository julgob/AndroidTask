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

    public void addReminder(OffsetDateTime dateTime, Main2Activity activity){
       this.repository.addNewReminder(new ReminderEntity("test",dateTime),this);
    }

    public LiveData<List<ReminderEntity>> getAll(){
        return repository.getAllReminder();
    }

    public LiveData<ReminderEntity> getMax(){
        return repository.maxdate;
    }
    public LiveData<ReminderEntity> getMin() {
        return repository.mindate;
    }

    public void deleteById(int id,Main2Activity activity){
        this.repository.deleteReminder(id,this);
    }

    @Override
    public void alarmDesactivationCallback(List<Optional<ReminderEntity>> entityList) {
        AlarmManagerUtility.cancelAlarm(getApplication().getApplicationContext());
        callback(entityList);
    }

    //callback après ajout, toggle , deletion
    //si un reminderentity est dans la liste en 0 alors faut delete l'alarm current et mettre nouveau pour ce reminder
    @Override
    public void callback(List<Optional<ReminderEntity>> entity) {
        //doit mettre lalarm si ya un truc
        if(entity.get(0).isPresent()){
            ReminderEntity reminder = entity.get(0).get();
            AlarmManagerUtility.createAlarmForReminder(getApplication().getApplicationContext(),reminder.getDateTime());
        }
    }

    public void toggle(int reminderId, boolean reminderActive){
        if(reminderActive)
            this.repository.setReminderEnabled(reminderId,this);
        else
            this.repository.setReminderDisabled(reminderId,this);
    }
        //  public void addReminder()
}
