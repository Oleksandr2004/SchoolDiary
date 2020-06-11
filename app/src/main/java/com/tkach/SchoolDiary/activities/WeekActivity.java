package com.tkach.SchoolDiary.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tkach.SchoolDiary.R;
import com.tkach.SchoolDiary.adapters.DaysAdapter;
import com.tkach.SchoolDiary.pojo.DayItem;
import com.tkach.SchoolDiary.pojo.SubjectItem;
import com.tkach.SchoolDiary.pojo.realmObjects.Homework;
import com.tkach.SchoolDiary.pojo.realmObjects.Mark;
import com.tkach.SchoolDiary.pojo.realmObjects.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class WeekActivity extends AppCompatActivity {

    private DaysAdapter adapter;

    private List<DayItem> items = new ArrayList<>();

    private String weekNumber;

    public static List<String> weekDays;

    final Realm realm = Realm.getDefaultInstance();

    static {
        weekDays = Arrays.asList("1. Monday", "2. Tuesday", "3. Wednesday", "4. Thursday", "5. Friday", "6. Saturday");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        weekNumber = "Week " + getIntent().getIntExtra(MainActivity.weekNumberExtra, 0);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            final Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (final InterruptedException ignored) {
                }
                swipeRefreshLayout.setRefreshing(false);
            });
            thread.start();
            items = new ArrayList<>();
            initRecyclerView();
        });

        initToolbar();

        initRecyclerView();

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            saveHomework();
            saveMarks();
            Toast.makeText(WeekActivity.this, "Saved", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        final Toolbar toolbar = findViewById(R.id.week_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(weekNumber);
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.days_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DaysAdapter(weekNumber);
        recyclerView.setAdapter(adapter);
        final DividerItemDecoration divider = new DividerItemDecoration(this, new LinearLayoutManager(this).getOrientation());
        addItemDecoration(recyclerView, divider);
        setSchedule();
        setHomework();
        setMarks();
        adapter.setData(items);
    }

    private void setMarks() {
        final RealmResults<Mark> marksRealmResult = Realm.getDefaultInstance()
                .where(Mark.class)
                .findAll();

        for (int dayIndex = 0; dayIndex < weekDays.size(); dayIndex++)
            for (int subjectNumber = 1; subjectNumber < 8; subjectNumber++)
                setMark(marksRealmResult, dayIndex, subjectNumber);
    }

    private void setMark(final RealmResults<Mark> markRealmResult, final int dayIndex, final int subjectNumber) {
        for (final Mark mark : markRealmResult)
            if (mark.getWeekNumber() != null
                    && mark.getWeekNumber().equals(weekNumber)
                    && mark.getWeekDay().equals(weekDays.get(dayIndex))
                    && mark.getNumber() == subjectNumber)
                for (final DayItem day : items)
                    if (day.getWeekDay().equals(weekDays.get(dayIndex)))
                        for (final SubjectItem subject : day.getSubjects())
                            if (Integer.parseInt(subject.getNumber()) == subjectNumber)
                                subject.setMark(mark.getMark());
    }

    private void saveMarks() {
        hideKeyboard(this);

        final List<List<Mark>> marks = adapter.getMarks();
        adapter.clearHomeworkList();

        boolean save = false;
        for (final List<Mark> marksList : marks) {
            if (!marksList.isEmpty()) {
                save = true;

                for (final Mark markToDelete : marksList) {
                    realm.executeTransaction(realm -> {
                        final Mark item = realm.where(Mark.class)
                                .equalTo("weekNumber", markToDelete.getWeekNumber())
                                .equalTo("weekDay", markToDelete.getWeekDay())
                                .equalTo("number", markToDelete.getNumber())
                                .findFirst();
                        if (item != null) {
                            item.deleteFromRealm();
                        }
                    });
                }

            }
        }
        if (save) saveMarksInRealm(marks);
    }

    private void saveHomework() {
        hideKeyboard(this);

        final List<List<Homework>> homework = adapter.getHomework();
        adapter.clearHomeworkList();

        boolean save = false;
        for (final List<Homework> homeworkList : homework) {
            if (!homeworkList.isEmpty()) {
                save = true;

                for (final Homework homeworkToDelete : homeworkList) {
                    realm.executeTransaction(realm -> {
                        Homework item = realm.where(Homework.class)
                                .equalTo("weekNumber", homeworkToDelete.getWeekNumber())
                                .equalTo("weekDay", homeworkToDelete.getWeekDay())
                                .equalTo("number", homeworkToDelete.getNumber())
                                .findFirst();
                        if (item != null) {
                            item.deleteFromRealm();
                        }
                    });
                }

            }
        }
        if (save) saveHomeworkInRealm(homework);
    }

    private void saveHomeworkInRealm(final List<List<Homework>> homework) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            for (final List<Homework> homeworkList : homework) r.insert(homeworkList);
        });
    }

    private void saveMarksInRealm(final List<List<Mark>> marks) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            for (final List<Mark> marksList : marks) r.insert(marksList);
        });
    }

    public static void hideKeyboard(final Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void addItemDecoration(final RecyclerView recyclerView, final DividerItemDecoration divider) {
        if (recyclerView.getItemDecorationCount() >= 1) {
            recyclerView.removeItemDecoration(divider);
        } else {
            recyclerView.addItemDecoration(divider);
        }
    }

    private void setSchedule() {
        final RealmResults<Subject> subjectRealmResults = Realm.getDefaultInstance().where(Subject.class).findAll();

        final List<SubjectItem> subjects = new ArrayList<>();

        for (int dayIndex = 0; dayIndex < weekDays.size(); dayIndex++) {
            if (getSubject(subjectRealmResults, dayIndex, 1) == null) continue;

            for (int subjectNumber = 1; subjectNumber < 8; subjectNumber++) {
                final SubjectItem subjectItem = getSubject(subjectRealmResults, dayIndex, subjectNumber);
                if (subjectItem != null)
                    subjects.add(subjectItem);
            }
            items.add(new DayItem(weekDays.get(dayIndex), subjects));
            subjects.clear();
        }

    }

    private SubjectItem getSubject(final RealmResults<Subject> subjectRealmResults, final int dayIndex, final int subjectNumber) {
        for (Subject subject : subjectRealmResults)
            if (subject.getWeekDay().equals(weekDays.get(dayIndex)))
                if (subject.getNumber() == subjectNumber)
                    return new SubjectItem(String.valueOf(subject.getNumber()), subject.getSubject(), "", "");
        return null;
    }

    private void setHomework() {
        final RealmResults<Homework> homeworkRealmResult = Realm.getDefaultInstance()
                .where(Homework.class)
                .findAll();

        for (int dayIndex = 0; dayIndex < weekDays.size(); dayIndex++)
            for (int subjectNumber = 1; subjectNumber < 8; subjectNumber++)
                setTask(homeworkRealmResult, dayIndex, subjectNumber);
    }

    private void setTask(final RealmResults<Homework> homeworkRealmResults, final int dayIndex, final int subjectNumber) {
        for (final Homework homework : homeworkRealmResults)
            if (homework.getWeekNumber() != null
                    && homework.getWeekNumber().equals(weekNumber)
                    && homework.getWeekDay().equals(weekDays.get(dayIndex))
                    && homework.getNumber() == subjectNumber)
                for (final DayItem day : items)
                    if (day.getWeekDay().equals(weekDays.get(dayIndex)))
                        for (final SubjectItem subject : day.getSubjects())
                            if (Integer.parseInt(subject.getNumber()) == subjectNumber)
                                subject.setHomework(homework.getHomework());
    }

}
