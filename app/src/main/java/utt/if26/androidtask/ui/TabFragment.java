package utt.if26.androidtask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import utt.if26.androidtask.R;
import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;
import utt.if26.androidtask.persistance.entity.categoryEnum.TimeCategory;
import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

public class TabFragment extends Fragment implements RecyclerAdapter.OnReminderClickListener {
    TimeCategory timeCategory;

    TypeCategory typeCategory;
    List<ReminderEntity> all;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter mAdapter;
    public TabFragment() {
    }

    public static TabFragment newInstance(TypeCategory typeCategory,TimeCategory timeCategory){
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt("typeCategory",typeCategory.getValue());
        args.putInt("timeCategory",timeCategory.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int typeCategoryInt = getArguments().getInt("typeCategory",0);
            this.typeCategory = TypeCategory.valueOf(typeCategoryInt);
            int timeCategoryInt = getArguments().getInt("timeCategory",0);
            this.timeCategory = TimeCategory.valueOf(timeCategoryInt);


            //utiliser un viexmodel ?? utile ??

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        Repository repository= new Repository(getActivity());
        repository.getAllReminder().observe(this,x ->{all = x;});
        repository.getReminderForTypeCategory(typeCategory).observe(this,x -> {
                    //we get reminder for all time category so we need to filter to only have the good ones
                    List<ReminderEntity> reminderEntities = getReminderListForTimecategory(this.timeCategory,x);
                    mAdapter.setReminderEntityList(reminderEntities);
                    mAdapter.notifyDataSetChanged();
                }
        );
        return view;
    }

    public List<ReminderEntity> getReminderListForTimecategory(TimeCategory timeCategory,List<ReminderEntity> allTimeCategoryReminderList){
        List<ReminderEntity> reminderEntityList;
        switch (timeCategory){
            case TODAY:                 reminderEntityList = getReminderForToday(allTimeCategoryReminderList);break;
            case TOMORROW:              reminderEntityList = getReminderForTomorrow(allTimeCategoryReminderList);break;
            case NEXT_SEVEN_DAYS:       reminderEntityList = getReminderForNSDays(allTimeCategoryReminderList);break;
            case AFTER_NEXT_SEVEN_DAYS: reminderEntityList = getReminderForANSDays(allTimeCategoryReminderList);break;
            case ALL:                   reminderEntityList = allTimeCategoryReminderList;break;
            default: reminderEntityList = allTimeCategoryReminderList;
        }
        return reminderEntityList;
    }

    private List<ReminderEntity> getReminderForToday(List<ReminderEntity> allTimeReminders){
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        offsetDateTime = OffsetDateTime.of(offsetDateTime.getYear(),offsetDateTime.getMonthValue(),offsetDateTime.getDayOfMonth(),0,0,0,0,offsetDateTime.getOffset());

        List<ReminderEntity> todayReminder =new ArrayList<>();
        for(ReminderEntity reminder : allTimeReminders){
            OffsetDateTime deadline = reminder.getDeadline();
            if(offsetDateTime.compareTo(deadline) == 0 ){
                todayReminder.add(reminder);
            }
        }
        return todayReminder;
    }

    private List<ReminderEntity> getReminderForTomorrow(List<ReminderEntity> allTimeReminders){
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        offsetDateTime = offsetDateTime.plusDays(1);
        offsetDateTime = OffsetDateTime.of(offsetDateTime.getYear(),offsetDateTime.getMonthValue(),offsetDateTime.getDayOfMonth(),0,0,0,0,offsetDateTime.getOffset());
        List<ReminderEntity> todayReminder =new ArrayList<>();
        for(ReminderEntity reminder : allTimeReminders){
            OffsetDateTime deadline = reminder.getDeadline();
            if(deadline.compareTo(offsetDateTime) == 0){
                todayReminder.add(reminder);
            }
        }
        return todayReminder;
    }

    private List<ReminderEntity> getReminderForNSDays(List<ReminderEntity> allTimeReminders){
        OffsetDateTime dateTimeTomorrow = OffsetDateTime.now().plusDays(1);
        OffsetDateTime dateTimeInSevenDays = dateTimeTomorrow.plusDays(7);
        dateTimeTomorrow = OffsetDateTime.of(dateTimeTomorrow.getYear(),dateTimeTomorrow.getMonthValue(),dateTimeTomorrow.getDayOfMonth(),0,0,0,0,dateTimeTomorrow.getOffset());
        dateTimeInSevenDays = OffsetDateTime.of(dateTimeInSevenDays.getYear(),dateTimeInSevenDays.getMonthValue(),dateTimeInSevenDays.getDayOfMonth(),0,0,0,0,dateTimeInSevenDays.getOffset());

        List<ReminderEntity> todayReminder =new ArrayList<>();
        for(ReminderEntity reminder : allTimeReminders){
            OffsetDateTime deadline = reminder.getDeadline();
            if(deadline.compareTo(dateTimeTomorrow)> 0 && deadline.compareTo(dateTimeInSevenDays) < 0){
                todayReminder.add(reminder);
            }
        }
        return todayReminder;
    }


    private List<ReminderEntity> getReminderForANSDays(List<ReminderEntity> allTimeReminder){
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        offsetDateTime = offsetDateTime.plusDays(7);
        offsetDateTime = OffsetDateTime.of(offsetDateTime.getYear(),offsetDateTime.getMonthValue(),offsetDateTime.getDayOfMonth(),0,0,0,0,offsetDateTime.getOffset());
        List<ReminderEntity> todayReminder =new ArrayList<>();
        for(ReminderEntity reminder : allTimeReminder){
            OffsetDateTime deadline = reminder.getDeadline();
            if(deadline.compareTo(offsetDateTime) == 1){
                todayReminder.add(reminder);
            }
        }
        return todayReminder;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.reminder_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.recyclerView = null;
    }

    @Override
    public void onReminderClick(int position) {
        Intent intent = new Intent(getActivity(),EditReminderActivity.class);
        intent.putExtra("reminder_id",mAdapter.getItem(position).getReminderId());
        startActivity(intent);
    }
}
