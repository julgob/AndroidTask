package utt.if26.androidtask;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.time.OffsetDateTime;

import utt.if26.androidtask.persistance.Repository;
import utt.if26.androidtask.persistance.entity.ReminderEntity;
import utt.if26.androidtask.persistance.entity.categoryEnum.TypeCategory;

public class NavigationDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_personal, R.id.nav_project, R.id.nav_other,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // a suppr
    public void fabClick(View v){
        Repository repository = new Repository(this);
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime t = now.plusDays(1);
        OffsetDateTime n7d = now.plusDays(4);
        OffsetDateTime a7d = now.plusDays(8);
        OffsetDateTime in7d = now.plusDays(7);

        now = OffsetDateTime.of(now.getYear(),now.getMonthValue(),now.getDayOfMonth(),0,0,0,0,now.getOffset());
        t = OffsetDateTime.of(t.getYear(),t.getMonthValue(),t.getDayOfMonth(),0,0,0,0,t.getOffset());
        n7d = OffsetDateTime.of(n7d.getYear(),n7d.getMonthValue(),n7d.getDayOfMonth(),0,0,0,0,t.getOffset());

        a7d = OffsetDateTime.of(a7d.getYear(),a7d.getMonthValue(),a7d.getDayOfMonth(),0,0,0,0,t.getOffset());
        in7d = OffsetDateTime.of(in7d.getYear(),in7d.getMonthValue(),in7d.getDayOfMonth(),0,0,0,0,t.getOffset());


        ReminderEntity reminderEntity = new ReminderEntity("personel today", now, TypeCategory.PERSONAL);
        ReminderEntity reminderEntity01 = new ReminderEntity("personel tomorow", t, TypeCategory.PERSONAL);
        ReminderEntity r = new ReminderEntity("personel in n 7 days",n7d, TypeCategory.PERSONAL);
        ReminderEntity r2 = new ReminderEntity("person apres 7 jours",a7d,TypeCategory.PERSONAL);
        ReminderEntity r3 = new ReminderEntity("person dans 7 jours",in7d,TypeCategory.PERSONAL);


        ReminderEntity reminderEntity1 = new ReminderEntity("projet", OffsetDateTime.now(), TypeCategory.PROJECT);
        ReminderEntity reminderEntity2 = new ReminderEntity("other", OffsetDateTime.now(), TypeCategory.OTHER);

        repository.addNewReminder(reminderEntity);
        repository.addNewReminder(reminderEntity1);
        repository.addNewReminder(reminderEntity2);
        repository.addNewReminder(reminderEntity01);
        repository.addNewReminder(r);
        repository.addNewReminder(r3);

        repository.addNewReminder(r2);
        Toast.makeText(this,"michel",Toast.LENGTH_LONG);
    }
}
