package utt.if26.androidtask.persistance.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import utt.if26.androidtask.persistance.entity.ReminderEntity;

@Dao
public interface ReminderDao {

    @Insert
    void insert(ReminderEntity reminder);

    @Delete
    void delete(ReminderEntity reminder);

    @Query("DELETE FROM reminder WHERE reminderId= :reminderId ")
    void deleteById(Integer reminderId);

    @Query("SELECT * FROM reminder")
    LiveData<List<ReminderEntity>> getAllReminder();

    @Query("SELECT * FROM reminder WHERE fired = 0 and enabled = 1 and dateTime  =  (SELECT MAX(re.dateTime) FROM reminder as re ) LIMIT 1")
    ReminderEntity getNextReminder();

    @Query("UPDATE reminder SET fired = 1 where reminderId = :id")
    void setFired(int id);

    @Query("UPDATE reminder SET enabled = :enabled where reminderId = :id")
    void setEnabled(int id,int enabled);

}
