package utt.if26.androidtask;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import utt.if26.androidtask.persistance.entity.ReminderEntity;

//pour le time faut faire gaffe au fait que le date picker renvoit entre 0 et 11 et pas 1 et 12 alors que je crois que les datetime prennent entre 1 et 12
public class Main2Activity extends AppCompatActivity {
    private int hour1;
    private int minute1;
    private int hour2;
    private int minute2;
    private int hour3;
    private int minute3;

    private Switch switch1;
    private Switch switch2;
    private Switch switch3;

    MainActivityViewModel viewModel;

    TextView textView1;
    TextView textView2;
    TextView textView3;

    List<TextView> textViewList;

    List<ReminderEntity> reminderEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        createNotificationChannel();

        textView1 =findViewById(R.id.textView1);
        textView2 =findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        switch1 = findViewById(R.id.switch1);
        switch3 = findViewById(R.id.switch3);
        switch2 = findViewById(R.id.switch2);

        switch1.setChecked(true);
        switch3.setChecked(true);
        switch2.setChecked(true);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.toggle(reminderEntityList.get(0).getReminderId(),isChecked);
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.toggle(reminderEntityList.get(1).getReminderId(),isChecked);
            }
        });
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.toggle(reminderEntityList.get(2).getReminderId(),isChecked);
            }
        });


        textViewList = Arrays.asList(textView1,textView2,textView3);
        setTextViewDebug();
        //setMinMaxDateTimeDebug();

    }

    private void setTextViewDebug(){
        viewModel.getAll().observe(this, new Observer<List<ReminderEntity>>() {
            @Override
            public void onChanged(List<ReminderEntity> reminderEntities) {
                reminderEntityList = reminderEntities;
                for (TextView tx:textViewList) {
                    tx.setText("textview");
                }
                for (int i = 0;i < reminderEntities.size();i++) {
                    ReminderEntity re = reminderEntities.get(i);
                    textViewList.get(i).setText("enabled: "+re.isEnabled()+" time: "+re.getDateTime()+" fired: "+re.isFired());
                }
            }
        });
    }

    private void setMinMaxDateTimeDebug(){
        viewModel.getMax().observe(this, x ->{
            if(x !=null)
                textView1.setText(x.getDateTime().toString());
        });

        viewModel.getMin().observe(this, x ->{
            if(x != null )
                textView2.setText(x.getDateTime().toString());
        });
    }

    public void delete1(View v){
        deleteReminder(0);
    }
    public void delete2(View v){deleteReminder(1);}
    public void delete3(View v){
        deleteReminder(2);
    }


    public void deleteReminder(int i){
        this.viewModel.deleteById(this.reminderEntityList.get(i).getReminderId(),this);
    }



    public void add1(View v){
        add(1);
    }public void add2(View v){
        add(2);
    }public void add3(View v){
        add(3);
    }

    private void add(int i){
        int mHour ;
        int mMinute;
        switch (i){
            case 1 : mHour = hour1;mMinute= minute1;break;
            case 2 : mHour = hour2;mMinute= minute2;break;
            case 3 : mHour = hour3;mMinute= minute3;break;
            default: mHour = 1; mMinute = 1;
        }

        OffsetDateTime offsetDateTime =  OffsetDateTime.of(2019,11,2,mHour,mMinute,0,0, OffsetDateTime.now().getOffset());

        viewModel.addReminder(offsetDateTime,this);
    }

    public void time1(View v){
        showTime(1);
    }
    public void time2(View v){
        showTime(2);
    }

    public void time3(View v){
        showTime(3);
    }

    private void showTime(final int i){
        // Get Current Time
        OffsetDateTime offsetDateTime =  OffsetDateTime.now();
        int mHour = offsetDateTime.getHour();
        int mMinute = offsetDateTime.getMinute();
        // Launch Time Picker Dialog

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        switch (i){
                            case 1:hour1 = hourOfDay;minute1 = minute;break;
                            case 2:hour2 = hourOfDay;minute2 = minute;break;
                            case 3:hour3 = hourOfDay;minute3 = minute;break;

                        }
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }




    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("241", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
