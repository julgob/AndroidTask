package utt.if26.androidtask.persistance.entity.TypeConverter;

import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @TypeConverter
    public static OffsetDateTime fromTimeStamp(String timeStamp){
        if(timeStamp!=null)
            return formatter.parse(timeStamp,OffsetDateTime::from);
        else
            return null;
    }

    @TypeConverter
    public static String dateToTimestamp(OffsetDateTime dateTime) {
        if(dateTime != null)
            return dateTime.format(formatter);
        else
            return null;
    }
}
