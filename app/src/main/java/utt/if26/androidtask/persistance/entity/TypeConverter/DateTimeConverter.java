package utt.if26.androidtask.persistance.entity.TypeConverter;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTimeConverter {

    @TypeConverter
    public static Date fromTimeStamp(String timeStamp){
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
