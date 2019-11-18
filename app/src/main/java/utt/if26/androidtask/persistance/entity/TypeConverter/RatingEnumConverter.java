package utt.if26.androidtask.persistance.entity.TypeConverter;

import androidx.room.TypeConverter;

import utt.if26.androidtask.persistance.entity.categoryEnum.RatingCategory;

public class RatingEnumConverter {

    @TypeConverter
    public static RatingCategory fromValue(int value){
        return RatingCategory.valueOf(value);
    }

    @TypeConverter
    public static int ratingToValue(RatingCategory ratingCategory) {
        if(ratingCategory != null)
            return ratingCategory.getValue();
        else return 0;
    }
}
