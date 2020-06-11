package com.tkach.SchoolDiary.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tkach.SchoolDiary.R;
import com.tkach.SchoolDiary.adapters.FillScheduleAdapter;
import com.tkach.SchoolDiary.pojo.fillScheduleItems.FillDayItem;
import com.tkach.SchoolDiary.pojo.fillScheduleItems.FillSubjectItem;
import com.tkach.SchoolDiary.pojo.realmObjects.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;

public class FillScheduleActivity extends AppCompatActivity {

    public static final String prefName = "FillSchedulePref";

    public static final String isFilledExtra = "isFilled";

    private List<FillDayItem> testItems = new ArrayList<>();

    private FillScheduleAdapter adapter = new FillScheduleAdapter();

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_scheule);

        realm = Realm.getDefaultInstance();

        initRecyclerView();

        final Toolbar toolbar = findViewById(R.id.fill_week_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Fill Schedule");

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {

            saveSchedule();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            getSharedPreferences(prefName, MODE_PRIVATE)
                    .edit()
                    .putBoolean(isFilledExtra, true)
                    .apply();

            finish();
        });
    }

    private void saveSchedule() {
        realm.beginTransaction();
        for (final List<Subject> daySchedule : adapter.getSchedule()) {
            realm.insert(daySchedule);
        }
        realm.commitTransaction();
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.fill_subjects_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        final DividerItemDecoration divider = new DividerItemDecoration(this, new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(divider);
        makeItems();
        adapter.setData(testItems);
    }

    private void makeItems() {
        final List<String> weekDays = Arrays.asList("1. Monday", "2. Tuesday", "3. Wednesday", "4. Thursday", "5. Friday", "6. Saturday");
        final List<String> subjects = Arrays.asList("", "", "", "", "", "", "", "");

        for (int i = 0; i < 6; i++) {

            final List<FillSubjectItem> subjectItems = new ArrayList<>();
            for (int j = 1; j < 8; j++) {
                subjectItems.add(new FillSubjectItem(String.valueOf(j), subjects.get(j)));
            }

            testItems.add(new FillDayItem(weekDays.get(i), subjectItems));
        }
    }

}
