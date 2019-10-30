package utt.if26.androidtask;

import java.util.List;
import java.util.Optional;

import utt.if26.androidtask.persistance.entity.ReminderEntity;

public interface AsyncCallback {

    public void callback(List<Optional<ReminderEntity>> entity);
}
