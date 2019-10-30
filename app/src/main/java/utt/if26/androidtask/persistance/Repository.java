package utt.if26.androidtask.persistance;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import utt.if26.androidtask.persistance.dao.ReminderDao;
import utt.if26.androidtask.persistance.database.AppDatabase;
import utt.if26.androidtask.persistance.entity.ReminderEntity;
import utt.if26.androidtask.receiver.ReminderReceiver;

public class Repository {
    private ReminderDao reminderDao;
    private LiveData<List<ReminderEntity>> allReminder;


    public Repository(Context context) {
        AppDatabase appDatabase = AppDatabase.getINSTANCE(context);
        reminderDao = appDatabase.getReminderDao();
        allReminder =reminderDao.getAllReminder();
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

    public void archiveReminderAndGetNext(ReminderReceiver receiver){
        new ArchiveAndGetNextAsyncTask(reminderDao,receiver).execute();
    }

    private static class ArchiveAndGetNextAsyncTask extends AsyncTask<Void,Void,List<ReminderEntity>>{
        private ReminderDao dao;
        private ReminderReceiver receiver;

        private ArchiveAndGetNextAsyncTask(ReminderDao dao,ReminderReceiver receiver) {
            this.dao = dao;
            this.receiver = receiver;
        }

        @Override
        protected List<ReminderEntity> doInBackground(Void ... voids){
            ArrayList<ReminderEntity> reminderList =new ArrayList<>();
            ReminderEntity firedReminder= dao.getNextReminder();
            reminderList.set(0,firedReminder);
            dao.setFired(firedReminder.getReminderId());
            reminderList.set(1,dao.getNextReminder());
            return reminderList;
        }

        @Override
        protected void onPostExecute(List<ReminderEntity> reminderEntities){
            receiver.callBack(reminderEntities);
        }
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

    private static class GetNextReminderAsyncTask extends AsyncTask<Void,Void,ReminderEntity>{
        private ReminderDao dao;

        private GetNextReminderAsyncTask(ReminderDao dao) {
            this.dao = dao;
        }

        @Override
        protected ReminderEntity doInBackground(Void...longs){
            return dao.getNextReminder();
        }

        @Override
        protected void onPostExecute(ReminderEntity reminderEntity){

        }
    }
}
