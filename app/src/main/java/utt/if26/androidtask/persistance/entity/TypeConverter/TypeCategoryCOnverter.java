package utt.if26.androidtask.persistance.entity.TypeConverter;

import androidx.room.TypeConverter;

import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

public class TypeCategoryCOnverter {

    @TypeConverter
    public static TypeCategory fromTypeCategory(int value){
        return TypeCategory.valueOf(value);
    }

    @TypeConverter
    public static int categoryToValue(TypeCategory typeCategory) {
        if(typeCategory != null)
            return typeCategory.getValue();
        else return 0;
    }

}
