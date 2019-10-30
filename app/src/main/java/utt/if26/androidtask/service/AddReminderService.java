package utt.if26.androidtask.service;

import android.app.IntentService;
import android.content.Intent;



public class AddReminderService extends IntentService {


    public AddReminderService() {
        super("AddReminderService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            //supprimer le reminder de la db ou le mettre en done
        }
    }

}
