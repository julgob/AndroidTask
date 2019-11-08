package utt.if26.androidtask.persistance;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import utt.if26.androidtask.AsyncCallback;
import utt.if26.androidtask.persistance.dao.ReminderDao;
import utt.if26.androidtask.persistance.database.AppDatabase;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class Repository {
    private ReminderDao reminderDao;
    private LiveData<List<ReminderEntity>> allReminder;
    public LiveData<ReminderEntity> maxdate;
    public LiveData<ReminderEntity> mindate;
   // public LiveData<R>
    //ON POURRAIT POTENTIELLEMENT VIRER LES CALLBACK POUR FAIR AVEC LIVEDATA
    //ON SABONNE A CHAQUE TRUC ADAPT2 ET DES QUE YA UN CHNAGEMENT ON REMPLACE LALARMANAGER AVEC LE FLAG QUI REMPLACE SI EXISTE D2JA
    //met ca fait que quand un est declanché ya le receiver et lactivité qui recoivent mais est ce que cest grave ?

    //dans quel cas le comm suivant s'applique ? que quand on peut ne plus avoir de reminder actif suite a loperation (delete,toggleoff)
    //car dans le cas ou on rajoute, cest unschedule grace au flag du pendingintent donc pas besoin de cancel
//faire gaffe a ce que lalarm soit bien supprimé ou quand le reminder est suppr
    //mais faire aussi gaffe a supprimer uniquement si besoin,: si on delete un reminder sassurer dannuler que si cest celui qui est next
    //pour ca on peut toujours utiliser le callback qui annule mais renvoyer le next meme si il a pas changé, comme ca si cetait le next le prochain est mis en place , si non le next est unscheduled et reschedules
    public Repository(Context context) {
        AppDatabase appDatabase = AppDatabase.getINSTANCE(context);
        reminderDao = appDatabase.getReminderDao();
        allReminder =reminderDao.getAllReminder();
        maxdate = reminderDao.getMaxDate();
        mindate  = reminderDao.getMinDate();
    }

    public LiveData<List<ReminderEntity>> getAllReminder(){
        return allReminder;
    }

    public void archiveReminderAndSetNext(AsyncCallback receiver){
        new SetFiredAndGetSextAsyncTask(reminderDao,receiver).execute();
    }

    public void addNewReminder(ReminderEntity reminder,AsyncCallback callback){
        new AddNewReminder(reminderDao,callback).execute(reminder);
    }

    public void setReminderEnabled(Integer reminderId,AsyncCallback callback){
        new SetReminderEnabled(reminderDao,callback).execute(reminderId);
    }

    public void setReminderDisabled(Integer reminderId,AsyncCallback callback){
        new SetReminderDisabled(reminderDao,callback).execute(reminderId);
    }

    public void deleteReminder(Integer reminderId,AsyncCallback callback){
        new DeleteReminder(reminderDao,callback).execute(reminderId);

    }

    private static class DeleteReminder extends AsyncTask<Integer,Void,Optional<ReminderEntity>>{
        private ReminderDao dao;
        private AsyncCallback callback;
        public DeleteReminder(ReminderDao dao,AsyncCallback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Optional<ReminderEntity> doInBackground(Integer... ints) {
            ReminderEntity current = this.dao.getNextReminder();
            this.dao.deleteById(ints[0]);
            //even if we didnt deleted the scheduled reminder we send it back ,
            // so it will be scheduled again after alarmdesactivationcallback unschedule it
            return Optional.ofNullable(this.dao.getNextReminder());

        }

        @Override
        protected void onPostExecute(Optional<ReminderEntity> reminderEntity) {
            this.callback.callback(reminderEntity,Optional.empty());
        }
    }


    private static class SetReminderEnabled extends AsyncTask<Integer,Void,Optional<ReminderEntity>>{
        private ReminderDao dao;
        private AsyncCallback callback;
        public SetReminderEnabled(ReminderDao dao,AsyncCallback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Optional<ReminderEntity> doInBackground(Integer... ints) {
            ReminderEntity current = this.dao.getNextReminder();
            this.dao.enableNotification(ints[0],1);
            ReminderEntity next = this.dao.getNextReminder();
            return Optional.ofNullable(next);
        }

        @Override
        protected void onPostExecute(Optional<ReminderEntity> reminderEntity) {
            this.callback.callback(reminderEntity,Optional.empty());
        }
    }

    private static class SetReminderDisabled extends AsyncTask<Integer,Void,Optional<ReminderEntity>>{
        private ReminderDao dao;
        private AsyncCallback callback;
        public SetReminderDisabled(ReminderDao dao,AsyncCallback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Optional<ReminderEntity> doInBackground(Integer... ints) {
            this.dao.enableNotification(ints[0],0);
            ReminderEntity next = this.dao.getNextReminder();
            return Optional.ofNullable(next);
        }

        @Override
        protected void onPostExecute(Optional<ReminderEntity> reminderEntity) {
            this.callback.callback(reminderEntity,Optional.empty());
        }
    }

    private static class AddNewReminder extends AsyncTask<ReminderEntity,Void,Optional<ReminderEntity>>{
        private ReminderDao dao;
        private AsyncCallback callback;
        public AddNewReminder(ReminderDao dao,AsyncCallback callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Optional<ReminderEntity> doInBackground(ReminderEntity... reminderEntities) {
            this.dao.insert(reminderEntities[0]);
            ReminderEntity next = dao.getNextReminder();
            return Optional.ofNullable(next);
        }

        @Override
        protected void onPostExecute(Optional<ReminderEntity> reminderEntity) {
            this.callback.callback(reminderEntity,Optional.empty());
      }
    }

    private static class SetFiredAndGetSextAsyncTask extends AsyncTask<Void,Void,List<Optional<ReminderEntity>>>{
        private ReminderDao dao;
        private AsyncCallback receiver;

        private SetFiredAndGetSextAsyncTask(ReminderDao dao, AsyncCallback receiver) {
            this.dao = dao;
            this.receiver = receiver;
        }

        @Override
        protected List<Optional<ReminderEntity>> doInBackground(Void ... voids){
            ArrayList<Optional<ReminderEntity>> reminderList = new ArrayList<>(2);
            ReminderEntity firedReminder= dao.getNextReminder();
            if (firedReminder != null) {
                dao.setFired(firedReminder.getReminderId());
            }
            reminderList.add(0,Optional.ofNullable(dao.getNextReminder()));
            reminderList.add(1,Optional.ofNullable(firedReminder));
            return reminderList;
        }

        @Override
        protected void onPostExecute(List<Optional<ReminderEntity>> reminderEntities){
            receiver.callback(reminderEntities.get(0),reminderEntities.get(1));
        }
    }

    private static class GetNextReminderAsyncTask extends AsyncTask<Void,Void,ReminderEntity>{
        private ReminderDao dao;

        private GetNextReminderAsyncTask(ReminderDao dao) {
            this.dao = dao;
        }

        @Override
        protected ReminderEntity doInBackground(Void...ints){
            return dao.getNextReminder();
        }

        @Override
        protected void onPostExecute(ReminderEntity reminderEntity){
        }
    }
}
