package utt.if26.androidtask.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.graphics.Paint;

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
        public CheckBox checkBox;
        private OnReminderClickListener reminderClickListener;
        public MyViewHolder(View v,OnReminderClickListener reminderClickListener) {
            super(v);
            this.reminderClickListener= reminderClickListener;
            textView = v.findViewById(R.id.fragment_tab__recycler_item_titre);
            checkBox = v.findViewById(R.id.fragment_tab__recycler_item_checkBox);

            // tâche complétée ou non ?

            v.setOnClickListener(this);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // String text = (String) textView.getText();
                    if (checkBox.isChecked()){
                        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });
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
        //holder.setReminderEntity(reminderEntityList.get(position))

        if(reminderEntityList.get(position).getCompleted()){
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.checkBox.setChecked(true);
        }
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
