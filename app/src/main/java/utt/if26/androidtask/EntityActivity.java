package utt.if26.androidtask;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.OffsetDateTime;

import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;
import utt.if26.androidtask.persistance.entity.categoryEnum.RatingCategory;
import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

public class EntityActivity extends AppCompatActivity {

    Spinner spinnerType;
    Spinner spinnerRating;
    private int deadlineYear;
    private int deadlineMonth;
    private int deadlineDay;
    private String reminderTitle;
    private Repository repository;
    private TypeCategory typeCategory;
    private RatingCategory rating;
    private String comment;

    private EditText reminderTitleView;
    private EditText commentView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(this);
        setContentView(R.layout.activity_entity);
        reminderTitleView = findViewById(R.id.activity_entity_task_name);
        commentView = findViewById(R.id.activity_entity_task_comment);
        //==========
        // SPINNERS
        //==========

        ArrayAdapter<TypeCategory> arrayType =  new ArrayAdapter<TypeCategory>( // create for the color of the text spinner
                this,
                R.layout.spinner_color_layout,
                TypeCategory.values()
        );

        arrayType.setDropDownViewResource(R.layout.spinner_dropdwon_layout);

        spinnerType = (Spinner) findViewById(R.id.activity_entity_task_spinner_type);
        spinnerType.setAdapter(arrayType);


        ArrayAdapter<RatingCategory> arrayRating=  new ArrayAdapter<RatingCategory>(
                this,
                R.layout.spinner_color_layout,
                RatingCategory.values()
        );

        arrayRating.setDropDownViewResource(R.layout.spinner_dropdwon_layout);

        spinnerRating = (Spinner) findViewById(R.id.task_spinner_rating);
        spinnerRating.setAdapter(arrayRating);


    }

    public void onDeadlineDateClick(View v){
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                deadlineDay = dayOfMonth;
                deadlineMonth = month +1; // offset goes from 1 to 12, picker from 0 to 11
                deadlineYear = year;
            }
        },offsetDateTime.getYear(),offsetDateTime.getMonthValue() - 1/*offset goes from 1 to 12, picker from 0 to 11*/,offsetDateTime.getDayOfMonth());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void onSaveClick(View v){
        this.reminderTitle = this.reminderTitleView.getText().toString();
        this.typeCategory =(TypeCategory) this.spinnerType.getSelectedItem();
        this.rating = (RatingCategory) this.spinnerRating.getSelectedItem();
        this.comment = this.commentView.getText().toString();
        ReminderEntity reminder;
        if(canCreateReminder()){
            reminder = createReminder();
            this.repository.addNewReminder(reminder);
            this.onBackPressed();
        }
        else
            showCantCreateReminder();
    }

    private boolean canCreateReminder(){
        return  deadlineDay != 0 && deadlineMonth !=0 && deadlineYear != 0 &&
               typeCategory != null && reminderTitle != null && reminderTitle != "" && rating != null;
    }

    private ReminderEntity createReminder(){
        ReminderEntity reminderEntity = new ReminderEntity(this.reminderTitle,createDeadline(),this.typeCategory,this.rating,comment);
        return reminderEntity;
    }

    private void showCantCreateReminder(){
        Toast toast = Toast.makeText(this,getString(R.string.cant_create_reminder_toast),Toast.LENGTH_LONG);
        toast.show();
    }

    public OffsetDateTime createDeadline(){
        return  OffsetDateTime.of(this.deadlineYear,this.deadlineMonth,this.deadlineDay,0,0,0,0,OffsetDateTime.now().getOffset());
    }
}
