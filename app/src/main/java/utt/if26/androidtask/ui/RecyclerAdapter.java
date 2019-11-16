package utt.if26.androidtask.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import utt.if26.androidtask.R;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<ReminderEntity> reminderEntityList;
    private OnReminderClickListener reminderClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        private OnReminderClickListener reminderClickListener;
        public MyViewHolder(View v,OnReminderClickListener reminderClickListener) {
            super(v);
            this.reminderClickListener= reminderClickListener;
            //avoir tous les autre truc pour reminderentity
            textView = v.findViewById(R.id.fragment_tab__recycler_item_titre);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            reminderClickListener.onReminderClick(getAdapterPosition());
        }
    }

    public RecyclerAdapter(OnReminderClickListener reminderClickListener) {
        this.reminderEntityList = new ArrayList<>();
        this.reminderClickListener  = reminderClickListener;
    }

    public void setReminderEntityList(List<ReminderEntity> reminderEntityList) {
        this.reminderEntityList = reminderEntityList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tab_recycler_view_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v,reminderClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // changer pour mettre toute les data de reminderentity
        holder.textView.setText(reminderEntityList.get(position).getTitre());
    }

    @Override
    public int getItemCount() {
        return reminderEntityList.size();
    }

    public interface OnReminderClickListener{
        void onReminderClick(int position);
    }

    public ReminderEntity getItem(int position){
        return reminderEntityList.get(position);
    }

}
