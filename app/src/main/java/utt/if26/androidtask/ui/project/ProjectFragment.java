package utt.if26.androidtask.ui.project;


import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;
import utt.if26.androidtask.ui.TabContainerParentFragment;

public class ProjectFragment extends TabContainerParentFragment {
    @Override
    public TypeCategory getTypeCategory() {
        return TypeCategory.PROJECT;
    }
}