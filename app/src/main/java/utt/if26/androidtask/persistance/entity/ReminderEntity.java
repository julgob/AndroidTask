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
    public long reminderId;

    @ColumnInfo(name = "titre")
    public String titre;

    @TypeConverters(CalendarConverter.class)
    public Calendar date;

    public boolean fired;
}
