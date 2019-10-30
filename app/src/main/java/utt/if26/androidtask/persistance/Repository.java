package utt.if26.androidtask.persistance;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.CalendarContract;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import utt.if26.androidtask.AsyncCallback;
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

    public void setFired(long reminderId){
        new SetFiredAsyncTask(reminderDao).execute(reminderId);
    }

    public void archiveReminderAndGetNext(AsyncCallback receiver){
        new ArchiveAndGetNextAsyncTask(reminderDao,receiver).execute();
    }

    public void addNewReminder(ReminderEntity reminder,AsyncCallback callback){
        new AddNewReminder(reminderDao,callback).execute();
    }

    public void setReminderEnabled(Long reminderId,AsyncCallback callback){
        new SetReminderEnabled(reminderDao,callback);
    }

    public void setReminderDisabled(Long reminderId,AsyncCallback callback){
        new SetReminderDisabled(reminderDao,callback);
    }

    public void deleteReminder(Long reminderId,AsyncCallback callback){

    }

    private static class DeleteReminder extends AsyncTask<Long,Void,Optional<ReminderEntity>>{
        private ReminderDao dao;
        private AsyncCallback callback;
        public DeleteReminder(ReminderDao dao,AsyncCallback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Optional<ReminderEntity> doInBackground(Long... longs) {
            ReminderEntity current = this.dao.getNextReminder();
            this.dao.deleteById(longs[0]);
            //if we deleted the scheduled reminder we send backthe next one
            if(current.getReminderId()== longs[0]){
                return Optional.ofNullable(this.dao.getNextReminder());
            }
            return Optional.empty();
        }

        @Override
        protected void onPostExecute(Optional<ReminderEntity> reminderEntity) {
            List<Optional<ReminderEntity>> list = new ArrayList<>();
            list.add(reminderEntity);
            this.callback.callback(list);
        }
    }

    private static class SetReminderEnabled extends AsyncTask<Long,Void,Optional<ReminderEntity>>{
        private ReminderDao dao;
        private AsyncCallback callback;
        public SetReminderEnabled(ReminderDao dao,AsyncCallback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Optional<ReminderEntity> doInBackground(Long... longs) {
            ReminderEntity current = this.dao.getNextReminder();
            this.dao.setEnabled(longs[0],1);
            ReminderEntity next = this.dao.getNextReminder();
            //if it is the same it means we didnt toggle the next reminder so we dont have to do anything with alarmamanger
            //we have to check for null in the callback
            if(current.getReminderId() == next.getReminderId()){
                return Optional.empty();
            }
            //else we return the next enabled one to schedule at alarmmanager
            return Optional.ofNullable(next);
        }

        @Override
        protected void onPostExecute(Optional<ReminderEntity> reminderEntity) {
            List<Optional<ReminderEntity>> list = new ArrayList<>();
            list.add(reminderEntity);
            this.callback.callback(list);
        }
    }

    private static class SetReminderDisabled extends AsyncTask<Long,Void,Optional<ReminderEntity>>{
        private ReminderDao dao;
        private AsyncCallback callback;
        public SetReminderDisabled(ReminderDao dao,AsyncCallback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Optional<ReminderEntity> doInBackground(Long... longs) {
            ReminderEntity current = this.dao.getNextReminder();
            this.dao.setEnabled(longs[0],0);
            ReminderEntity next = this.dao.getNextReminder();
            //if it is the same it means we didnt toggle the next reminder so we dont have to do anything with alarmamanger
            //we have to check for null in the callback
            if(current.getReminderId() == next.getReminderId()){
                return Optional.empty();
            }
            //else we return the next enabled one to schedule at alarmmanager
            return Optional.ofNullable(next);
        }

        @Override
        protected void onPostExecute(Optional<ReminderEntity> reminderEntity) {
            List<Optional<ReminderEntity>> list = new ArrayList<>();
            list.add(reminderEntity);
            this.callback.callback(list);
        }
    }

    private static class AddNewReminder extends AsyncTask<ReminderEntity,Void,Void>{
        private ReminderDao dao;
        private AsyncCallback callback;
        public AddNewReminder(ReminderDao dao,AsyncCallback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(ReminderEntity... reminderEntities) {
            this.dao.insert(reminderEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.callback.callback(new ArrayList<Optional<ReminderEntity>>());
        }
    }

    private static class ArchiveAndGetNextAsyncTask extends AsyncTask<Void,Void,List<Optional<ReminderEntity>>>{
        private ReminderDao dao;
        private AsyncCallback receiver;

        private ArchiveAndGetNextAsyncTask(ReminderDao dao, AsyncCallback receiver) {
            this.dao = dao;
            this.receiver = receiver;
        }

        @Override
        protected List<Optional<ReminderEntity>> doInBackground(Void ... voids){
            ArrayList<Optional<ReminderEntity>> reminderList =new ArrayList<>();
            ReminderEntity firedReminder= dao.getNextReminder();
            reminderList.set(0,Optional.of(firedReminder));
            dao.setFired(firedReminder.getReminderId());
            reminderList.set(1,Optional.of(dao.getNextReminder()));
            return reminderList;
        }

        @Override
        protected void onPostExecute(List<Optional<ReminderEntity>> reminderEntities){

            receiver.callback(reminderEntities);
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
