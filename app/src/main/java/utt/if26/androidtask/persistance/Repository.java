package utt.if26.androidtask.persistance;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import utt.if26.androidtask.persistance.dao.ReminderDao;
import utt.if26.androidtask.persistance.database.AppDatabase;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class Repository {
    private ReminderDao reminderDao;
    private LiveData<List<ReminderEntity>> allReminder;
    private LiveData<ReminderEntity> nextReminder;

    public Repository(Application application) {
        AppDatabase appDatabase = AppDatabase.getINSTANCE(application);
        reminderDao = appDatabase.getReminderDao();
        allReminder =reminderDao.getAllReminder();
        nextReminder = reminderDao.getNextReminder();
    }

    public void insert(ReminderEntity entity){
        new InsertReminderAsyncTask(reminderDao).execute(entity);
    }

    public void deleteById(long reminderId){
        new DeleteByIdAsyncTask(reminderDao).execute(reminderId);
    }

    public void setFired(long reminderId){
        new SetFiredAsyncTask(reminderDao).execute(reminderId);
    }

    @Nullable
    public ReminderEntity getNextReminder(){
        return nextReminder.getValue();
    }

    private static class InsertReminderAsyncTask extends AsyncTask<ReminderEntity,Void,Void>{
        private ReminderDao dao;

        private InsertReminderAsyncTask(ReminderDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ReminderEntity ... reminderEntities){
            ReminderEntity reminderEntity = reminderEntities[0];
            dao.insert(reminderEntity);
            return null;
        }
    }

    private static class DeleteByIdAsyncTask extends AsyncTask<Long,Void,Void>{
        private ReminderDao dao;

        private DeleteByIdAsyncTask(ReminderDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Long ... longs){
            Long reminderId = longs[0];
            dao.deleteById(reminderId.longValue());
            return null;
        }
    }

    private static class SetFiredAsyncTask extends AsyncTask<Long,Void,Void>{
        private ReminderDao dao;

        private SetFiredAsyncTask(ReminderDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Long...longs){
            dao.setFired(longs[0]);
            return null;
        }
    }
}
