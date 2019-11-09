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

    //to show room datetime should be treated as datetime we use the datetime() fucntion
    @Query("SELECT * FROM reminder as reminder WHERE reminder.notificationFired = 0 and reminder.notificationIsEnabled = 1 and datetime(reminder.triggerDateTime)  =" +
            "  (SELECT MIN(datetime(re.triggerDateTime)) FROM reminder as re WHERE re.notificationFired = 0 AND re.notificationIsEnabled = 1) LIMIT 1")
    ReminderEntity getNextScheduledReminder();

    @Query("UPDATE reminder SET notificationFired = 1 where reminderId = :id")
    void setFired(int id);

    @Query("UPDATE reminder SET notificationIsEnabled = :notificationIsEnabled where reminderId = :id")
    void enableNotification(int id, int notificationIsEnabled);

}
