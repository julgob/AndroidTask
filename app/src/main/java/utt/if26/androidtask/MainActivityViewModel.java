package utt.if26.androidtask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.time.OffsetDateTime;

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


    //  public void addReminder()
}
