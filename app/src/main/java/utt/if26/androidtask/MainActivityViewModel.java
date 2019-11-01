package utt.if26.androidtask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;
import java.util.List;

import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class MainActivityViewModel extends AndroidViewModel{

    Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void addReminder(OffsetDateTime dateTime, Main2Activity activity){
       this.repository.addNewReminder(new ReminderEntity("test",dateTime),activity);
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
        this.repository.deleteReminder(id,activity);
    }


        //  public void addReminder()
}
