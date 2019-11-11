package utt.if26.androidtask.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import utt.if26.androidtask.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    //private OnFragmentInteractionListener mListener;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    ArrayList<String> tabName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);
            }
        });
        tabLayout = root.findViewById(R.id.tabs);
        mViewPager = root.findViewById(R.id.container);
        tabName=new ArrayList<String>();
        for(int i=0;i<7;i++){
            tabLayout.addTab(tabLayout.newTab().setText(("tab "+ i)));
            tabName.add(String.valueOf(i));
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),tabName);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                getChildFragmentManager().beginTransaction().addToBackStack(null).commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return root;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Note that we are passing childFragmentManager, not FragmentManager
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),tabName);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }
}