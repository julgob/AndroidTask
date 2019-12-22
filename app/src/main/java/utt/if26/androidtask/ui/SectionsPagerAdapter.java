package utt.if26.androidtask.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import utt.if26.androidtask.persistance.entity.categoryEnum.TimeCategory;
import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    TypeCategory typeCategory;

    public SectionsPagerAdapter(FragmentManager fm, int NumOfTabs, TypeCategory typeCategory) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.typeCategory = typeCategory;
    }

    //faufra changer si on veut tout voir en mm temps
    @Override
    public Fragment getItem(int position) {
        TimeCategory timeCategory;
        switch (position){
            case 0 : timeCategory = TimeCategory.TODAY;break;
            case 1 : timeCategory = TimeCategory.TOMORROW;break;
            case 2 : timeCategory = TimeCategory.NEXT_SEVEN_DAYS;break;
            case 3 : timeCategory = TimeCategory.AFTER_NEXT_SEVEN_DAYS;break;
            default: timeCategory = TimeCategory.ALL;
        }
        return TabFragment.newInstance(typeCategory,timeCategory);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
