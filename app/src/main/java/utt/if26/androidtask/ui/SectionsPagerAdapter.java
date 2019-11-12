package utt.if26.androidtask.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    TypeCategory typeCategory;
    ArrayList<String> tabName;

    public SectionsPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> tabName, TypeCategory typeCategory) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabName=tabName;
        this.typeCategory = typeCategory;
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(tabName.get(position),typeCategory);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
