package utt.if26.androidtask.persistance.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.OffsetDateTime;

import utt.if26.androidtask.persistance.entity.TypeConverter.DateTimeConverter;

@Entity(tableName = "reminder")
public class ReminderEntity {

    public ReminderEntity(String titre, OffsetDateTime dateTime) {
        this.titre = titre;
        this.dateTime = dateTime;
        this.fired = false;
        this.enabled = true;
    }

    @PrimaryKey (autoGenerate = true)
    private int reminderId;

    @ColumnInfo(name = "titre")
    private String titre;

    @TypeConverters(DateTimeConverter.class)
    private OffsetDateTime dateTime;



    private boolean fired;

    private boolean enabled;

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }
}
