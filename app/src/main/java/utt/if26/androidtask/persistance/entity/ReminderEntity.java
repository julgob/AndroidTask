package utt.if26.androidtask.persistance.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;


import utt.if26.androidtask.persistance.entity.TypeConverter.CalendarConverter;

@Entity(tableName = "reminder")
public class ReminderEntity {

    @PrimaryKey (autoGenerate = true)
    private long reminderId;

    @ColumnInfo(name = "titre")
    private String titre;

    @TypeConverters(CalendarConverter.class)
    private Calendar calendar;

    private boolean fired;

    private boolean enabled;

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getReminderId() {
        return reminderId;
    }

    public void setReminderId(long reminderId) {
        this.reminderId = reminderId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setDate(Calendar calendar) {
        this.calendar = calendar;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }
}
