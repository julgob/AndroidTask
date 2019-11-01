package utt.if26.androidtask;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import utt.if26.androidtask.persistance.dao.ReminderDao;
import utt.if26.androidtask.persistance.database.AppDatabase;
import utt.if26.androidtask.persistance.entity.ReminderEntity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest {
    private ReminderDao dao;
    private AppDatabase db;
    ReminderEntity reminderEntity;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.getReminderDao();
         reminderEntity = new ReminderEntity("entity 1",OffsetDateTime.now());
         dao.insert(reminderEntity);

    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        List<ReminderEntity> rem =(List<ReminderEntity>) LiveDataTestUtil.getValue(dao.getMaxDate());
        assert rem.get(0) == reminderEntity;
    }
}