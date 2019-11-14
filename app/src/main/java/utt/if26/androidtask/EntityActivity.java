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


        //==========
        // SPINNERS
        //==========

        ArrayAdapter<TypeCategory> arrayType =  new ArrayAdapter<TypeCategory>( // create for the color of the text spinner
                this,
                R.layout.spinner_color_layout,
                TypeCategory.values()
        );

        arrayType.setDropDownViewResource(R.layout.spinner_dropdwon_layout);

        spinnerType = (Spinner) findViewById(R.id.task_spinner_type);
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
}
