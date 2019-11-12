package utt.if26.androidtask.ui.other;

import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;
import utt.if26.androidtask.ui.TabContainerParentFragment;

public class OtherFragment extends TabContainerParentFragment {
    @Override
    public TypeCategory getTypeCategory() {
        return TypeCategory.PROJECT;
    }
}