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

    //debug to delete
    @Query("SELECT * FROM reminder WHERE datetime(dateTime) = (SELECT MAX(datetime(re.dateTime)) FROM reminder as re ) LIMIT 1")
    LiveData<ReminderEntity> getMaxDate();

    //debug to delete
    @Query("SELECT * FROM reminder WHERE datetime(dateTime) = (SELECT MIN(datetime(re.dateTime)) FROM reminder as re ) LIMIT 1")
    LiveData<ReminderEntity> getMinDate();


    @Query("DELETE FROM reminder WHERE reminderId= :reminderId ")
    void deleteById(Integer reminderId);

    @Query("SELECT * FROM reminder")
    LiveData<List<ReminderEntity>> getAllReminder();

    //to show room datetime should be treated as datetime we use the datetime() fucntion
    @Query("SELECT * FROM reminder as reminder WHERE reminder.fired = 0 and reminder.enabled = 1 and datetime(reminder.dateTime)  =" +
            "  (SELECT MIN(datetime(re.dateTime)) FROM reminder as re WHERE re.fired = 0 AND re.enabled = 1) LIMIT 1")
    ReminderEntity getNextReminder();

    @Query("UPDATE reminder SET fired = 1 where reminderId = :id")
    void setFired(int id);

    @Query("UPDATE reminder SET enabled = :enabled where reminderId = :id")
    void setEnabled(int id,int enabled);

}
