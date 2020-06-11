package com.tkach.SchoolDiary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tkach.SchoolDiary.R;
import com.tkach.SchoolDiary.adapters.WeeksAdapter;
import com.tkach.SchoolDiary.pojo.WeekItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String prefName = "current_week";

    public static final String weekNumberExtra = "number";

    private List<WeekItem> weeks;

    private WeeksAdapter adapter;

    private ActionMode actionMode;

    private WeekItem currentWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        initRecyclerView();

        if (!(getSharedPreferences(FillScheduleActivity.prefName, MODE_PRIVATE).getBoolean(FillScheduleActivity.isFilledExtra, false)))
            goToFillScheduleActivity();
        else
            goToWeekActivity();

    }

    private void initToolbar() {
        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SchoolDiary");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.weeks_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        final WeeksAdapter.OnWeekClickListener onWeekClickListener = new WeeksAdapter.OnWeekClickListener() {

            @Override
            public void onWeekClick(final WeekItem week) {
                if (actionMode != null) {
                    actionMode.finish();
                    return;
                }
                final Intent intent = new Intent(MainActivity.this, WeekActivity.class);
                intent.putExtra(weekNumberExtra, week.getNumber());
                startActivity(intent);
            }

            @Override
            public void onWeekLongClick(final WeekItem week) {
                if (actionMode != null) {
                    actionMode.finish();
                    return;
                }
                actionMode = startSupportActionMode(new ActionMode.Callback() {

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        MainActivity.this.actionMode = mode;
                        final MenuInflater inflater = new MenuInflater(MainActivity.this);
                        inflater.inflate(R.menu.weeks_menu, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        if (currentWeek != null) {
                            getSharedPreferences(prefName, MODE_PRIVATE)
                                    .edit()
                                    .remove(currentWeek.toString())
                                    .apply();
                            currentWeek.makeCurrent(false);
                            adapter.notifyItemChanged(currentWeek.getNumber() - 1);
                        }
                        currentWeek = week;
                        currentWeek.makeCurrent(true);
                        getSharedPreferences(prefName, MODE_PRIVATE)
                                .edit()
                                .putBoolean(week.toString(), true)
                                .apply();
                        adapter.notifyItemChanged(currentWeek.getNumber() - 1);
                        actionMode.finish();
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        actionMode = null;
                    }

                });
            }

        };

        adapter = new WeeksAdapter(onWeekClickListener);
        recyclerView.setAdapter(adapter);
        makeWeeks();
        adapter.setData(weeks);
    }

    private void makeWeeks() {
        weeks = new ArrayList<>();
        for (int i = 1; i <= 34; i++) {
            final WeekItem weekItem = new WeekItem(i, false);
            if (getSharedPreferences(prefName, MODE_PRIVATE).getBoolean(weekItem.toString(), false)) {
                weekItem.makeCurrent(true);
                weeks.add(weekItem);
                currentWeek = weekItem;
            } else {
                weekItem.makeCurrent(false);
                weeks.add(weekItem);
            }
        }
        if (currentWeek == null) {
            getSharedPreferences(prefName, MODE_PRIVATE)
                    .edit()
                    .putBoolean(weeks.get(0).toString(), true)
                    .apply();
            currentWeek = weeks.get(0);
            makeWeeks();
        }
    }

    private void goToFillScheduleActivity() {
        final Intent intent = new Intent(this, FillScheduleActivity.class);
        startActivity(intent);
    }

    public void goToWeekActivity() {
        final Intent intent = new Intent(this, WeekActivity.class);
        intent.putExtra(weekNumberExtra, currentWeek.getNumber());
        startActivity(intent);
    }

}
