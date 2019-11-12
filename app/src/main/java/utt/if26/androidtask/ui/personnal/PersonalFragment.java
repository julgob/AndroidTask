package utt.if26.androidtask.ui.personnal;

import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;
import utt.if26.androidtask.ui.TabContainerParentFragment;

public class PersonalFragment extends TabContainerParentFragment {
    @Override
    public TypeCategory getTypeCategory() {
        return TypeCategory.PERSONAL;
    }
}