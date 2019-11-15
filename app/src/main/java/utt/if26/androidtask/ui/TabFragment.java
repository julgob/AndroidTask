package utt.if26.androidtask.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import utt.if26.androidtask.R;
import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;
import utt.if26.androidtask.persistance.entity.categoryEnum.TimeCategory;
import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

public class TabFragment extends Fragment {
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
        repository.getReminderForTimeAndTypeCategory(timeCategory,typeCategory).observe(this,x -> {
                    mAdapter.setReminderEntityList(x);
                    mAdapter.notifyDataSetChanged();
                }
        );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.reminder_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.recyclerView = null;
    }

}
