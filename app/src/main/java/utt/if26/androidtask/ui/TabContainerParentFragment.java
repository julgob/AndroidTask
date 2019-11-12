package utt.if26.androidtask.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import utt.if26.androidtask.R;
import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;


public abstract class TabContainerParentFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    //private OnFragmentInteractionListener mListener;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    ArrayList<String> tabName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal, container, false);
        tabLayout = root.findViewById(R.id.tabs);
        mViewPager = root.findViewById(R.id.container);
        tabName=new ArrayList<>();
        for(int i=0;i<4;i++){
            tabLayout.addTab(tabLayout.newTab().setText(("tab "+ i)));
            tabName.add(String.valueOf(i));
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),tabName,getTypeCategory());
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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),tabName,getTypeCategory());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public abstract TypeCategory getTypeCategory();
}
