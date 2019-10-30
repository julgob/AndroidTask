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
    void deleteById(Long reminderId);

    @Query("SELECT * FROM reminder")
    LiveData<List<ReminderEntity>> getAllReminder();

    @Query("SELECT * FROM reminder WHERE fired = 'FALSE' and  date  =  (SELECT MAX(re.date) FROM reminder as re ) LIMIT 1")
    LiveData<ReminderEntity> getNextReminder();

    @Query("UPDATE reminder SET fired = 'true' where reminderId = :id")
    void setFired(long id);

}
