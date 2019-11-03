package utt.if26.androidtask;

import java.util.Optional;

import utt.if26.androidtask.persistance.entity.ReminderEntity;

public interface AsyncCallback {


    void callback(Optional<ReminderEntity> ReminderToSchedule,Optional<ReminderEntity> firedReminder);
}
