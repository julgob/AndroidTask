package utt.if26.androidtask.persistance.entity.TypeConverter;

import androidx.room.TypeConverter;

import utt.if26.androidtask.persistance.entity.categoryEnum.TimeCategory;

public class TimeCategoryConverter {
    @TypeConverter
    public static TimeCategory fromTimeCategory(int value){
             return TimeCategory.valueOf(value);
    }

    @TypeConverter
    public static int categoryToValue(TimeCategory timeCategory) {
        if(timeCategory != null)
            return timeCategory.getValue();
        else return 0;
    }
}
