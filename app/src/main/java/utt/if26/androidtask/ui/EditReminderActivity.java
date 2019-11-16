package utt.if26.androidtask.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import utt.if26.androidtask.R;
import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class EditReminderActivity extends AppCompatActivity {
    Repository repository;
    ReminderEntity reminderEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.repository = new Repository(this);
        int reminderId;
        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey("reminder_id")){
                reminderId = getIntent().getIntExtra("reminder_id",0);
                repository.getReminderForId(reminderId).observe(this,this::showReminder);
            }

        }

    }

    private void showReminder(ReminderEntity reminderEntity){
        this.reminderEntity = reminderEntity;
        //aficher sur lecran toute les bonnes valeur
    }

    //faudra une fonction qui update le reminder quand on clique save
    //utiliser la methode updatereminder du repo, on a qua passer lobjet reminder entity modifié (faut que lid soit le meme evidemment

}
