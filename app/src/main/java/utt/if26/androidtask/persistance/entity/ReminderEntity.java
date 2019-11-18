package utt.if26.androidtask.persistance.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.OffsetDateTime;

import utt.if26.androidtask.persistance.entity.TypeConverter.DateTimeConverter;
import utt.if26.androidtask.persistance.entity.TypeConverter.RatingEnumConverter;
import utt.if26.androidtask.persistance.entity.TypeConverter.TypeCategoryCOnverter;
import utt.if26.androidtask.persistance.entity.categoryEnum.RatingCategory;
import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

@Entity(tableName = "reminder")
public class ReminderEntity {

    public ReminderEntity(String titre,OffsetDateTime deadline,TypeCategory typeCategory,RatingCategory rating) {
        this.titre = titre;
        this.notificationFired = false;
        this.notificationIsEnabled = false;
        this.deadline = deadline;
        this.typeCategory = typeCategory;
        this.rating = rating;
    }

    @PrimaryKey (autoGenerate = true)
    private int reminderId;

    @ColumnInfo(name = "titre")
    private String titre;

    @TypeConverters(DateTimeConverter.class)
    private OffsetDateTime triggerDateTime;

    @TypeConverters(DateTimeConverter.class)
    private OffsetDateTime deadline;

    @TypeConverters(RatingEnumConverter.class)
    private RatingCategory rating;

    private boolean notificationFired;
    private boolean notificationIsEnabled;

    @TypeConverters(TypeCategoryCOnverter.class)
    private TypeCategory typeCategory;

    public RatingCategory getRating() {
        return rating;
    }

    public void setRating(RatingCategory rating) {
        this.rating = rating;
    }

    public void setTypeCategory(TypeCategory typeCategory) {
        this.typeCategory = typeCategory;
    }

    public TypeCategory getTypeCategory() {
        return typeCategory;
    }

    public OffsetDateTime getTriggerDateTime() {
        return triggerDateTime;
    }

    public void setTriggerDateTime(OffsetDateTime triggerDateTime) {
        this.triggerDateTime = triggerDateTime;
    }

    public boolean isNotificationIsEnabled() {
        return notificationIsEnabled;
    }

    public void setNotificationIsEnabled(boolean notificationIsEnabled) {
        this.notificationIsEnabled = notificationIsEnabled;
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

    public boolean isNotificationFired() {
        return notificationFired;
    }

    public void setNotificationFired(boolean notificationFired) {
        this.notificationFired = notificationFired;
    }

    public OffsetDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(OffsetDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj ==this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        ReminderEntity reminderEntity = (ReminderEntity) obj;
        return reminderEntity.reminderId == this.reminderId;
    }


    @Override
    public String toString() {
        return "ReminderEntity{" +
                "reminderId=" + reminderId +
                ", titre='" + titre + '\'' +
                ", triggerDateTime=" + triggerDateTime +
                ", deadline=" + deadline +
                ", notificationFired=" + notificationFired +
                ", notificationIsEnabled=" + notificationIsEnabled +
                '}';
    }
}
