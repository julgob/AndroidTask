package utt.if26.androidtask.persistance.entity.TypeConverter;

import androidx.room.Query;
import androidx.room.TypeConverter;

import java.util.Date;

import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class DateTimeConverter {

    @TypeConverter
    public static Date fromTimeStamp(Long timeStamp){
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


}
