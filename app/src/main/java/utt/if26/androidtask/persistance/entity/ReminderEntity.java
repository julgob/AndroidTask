package utt.if26.androidtask.persistance.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import utt.if26.androidtask.persistance.entity.TypeConverter.DateTimeConverter;

@Entity(tableName = "reminder")
public class ReminderEntity {

    @PrimaryKey (autoGenerate = true)
    public long reminderId;

    @ColumnInfo(name = "titre")
    public String titre;

    @TypeConverters(DateTimeConverter.class)
    public Date date;
}
