package utt.if26.androidtask.persistance.entity.TypeConverter;

import androidx.room.Query;
import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class CalendarConverter {

    @TypeConverter
    public static Calendar fromTimeStamp(String timeStamp){
        if(timeStamp !=null){

            int year = Integer.parseInt(timeStamp.substring(0,4));
            int month = Integer.parseInt(timeStamp.substring(4,6));
            int day = Integer.parseInt(timeStamp.substring(6,8));
            int hour = Integer.parseInt(timeStamp.substring(8,10));
            int minute = Integer.parseInt(timeStamp.substring(10,12));

            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day,hour,minute);

            return calendar;
        } else
            return null;
    }

    @TypeConverter
    public static String dateToTimestamp(Calendar calendar) {
        if (calendar != null){
            StringBuilder builder = new StringBuilder();
            builder.append(calendar.get(Calendar.YEAR));
            if(calendar.get(Calendar.MONTH)<10)
                builder.append("0");
            builder.append(calendar.get(Calendar.MONTH));
            if(calendar.get(Calendar.DAY_OF_MONTH)<10)
                builder.append("0");
            builder.append(calendar.get(Calendar.DAY_OF_MONTH));
            if(calendar.get(Calendar.HOUR)<10)
                builder.append("0");
            builder.append(calendar.get(Calendar.HOUR));
            if(calendar.get(Calendar.MINUTE)<10)
                builder.append("0");
            builder.append(calendar.get(Calendar.MINUTE));

            return builder.toString();
        }else
            return null;
    }


}
