package utt.if26.androidtask.persistance.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import utt.if26.androidtask.persistance.dao.ReminderDao;
import utt.if26.androidtask.persistance.entity.ReminderEntity;
import utt.if26.androidtask.persistance.entity.TypeConverter.TimeCategoryConverter;
import utt.if26.androidtask.persistance.entity.TypeConverter.TypeCategoryCOnverter;


@Database(entities = {ReminderEntity.class},version = 1)
@TypeConverters({TypeCategoryCOnverter.class, TimeCategoryConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ReminderDao getReminderDao();

    public static AppDatabase getINSTANCE(Context context){
        if(INSTANCE== null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"db").build();
        }
        return INSTANCE;
    }

}
