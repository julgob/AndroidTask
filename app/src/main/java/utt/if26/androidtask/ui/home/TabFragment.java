package utt.if26.androidtask.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import utt.if26.androidtask.R;

public class TabFragment extends Fragment {
   private TextView text_view_for_tab_selection;

    public TabFragment() {
        // Required empty public constructor
    }


    public static TabFragment newInstance(String tabSelected) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString("b", tabSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        text_view_for_tab_selection=(android.widget.TextView)view.findViewById(R.id.text_view_for_tab_selection);
        text_view_for_tab_selection.setText(getArguments().getString("b"));
        return view;
    }

}
