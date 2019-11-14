package utt.if26.androidtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import utt.if26.androidtask.persistance.entity.categoryEnum.RatingCategory;
import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

public class EntityActivity extends AppCompatActivity {

    Spinner spinnerType;
    Spinner spinnerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);


        // SPINNERS

        spinnerType = (Spinner) findViewById(R.id.task_spinner_type);
        spinnerType.setAdapter(new ArrayAdapter<TypeCategory>(this, android.R.layout.simple_spinner_item,TypeCategory.values()));

        spinnerRating = (Spinner) findViewById(R.id.task_spinner_rating);
        spinnerRating.setAdapter(new ArrayAdapter<RatingCategory>(this, android.R.layout.simple_spinner_item,RatingCategory.values()));
    }
}
