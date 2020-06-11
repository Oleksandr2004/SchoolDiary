package com.tkach.SchoolDiary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tkach.SchoolDiary.R;
import com.tkach.SchoolDiary.pojo.DayItem;
import com.tkach.SchoolDiary.pojo.realmObjects.Homework;
import com.tkach.SchoolDiary.pojo.realmObjects.Mark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysViewHolder> {

    private List<DayItem> data = new ArrayList<>();

    private List<SubjectsAdapter> adapters = new ArrayList<>();

    private List<List<Homework>> homework = new ArrayList<>();

    private List<List<Mark>> marks = new ArrayList<>();

    private final String weekNumber;

    public DaysAdapter(final String weekNumber) {
        this.weekNumber = weekNumber;
    }

    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_day_item, parent, false);
        return new DaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {
        holder.applyData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(final Collection<DayItem> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<List<Homework>> getHomework() {
        for (final SubjectsAdapter adapter : adapters)
            homework.add(adapter.getDayHomework());
        return homework;
    }

    public void clearHomeworkList() {
        homework = new ArrayList<>();
    }

    public List<List<Mark>> getMarks() {
        for (final SubjectsAdapter adapter : adapters)
            marks.add(adapter.getDayMarks());
        return marks;
    }

    class DaysViewHolder extends RecyclerView.ViewHolder {

        private TextView weekDay;

        private RecyclerView rv;

        private SubjectsAdapter adapter;

        public DaysViewHolder(@NonNull View itemView) {
            super(itemView);
            this.weekDay = itemView.findViewById(R.id.weekday);
            this.rv = itemView.findViewById(R.id.subjects_rv);
            initRecyclerView();
        }

        private void initRecyclerView() {
            rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            adapter = new SubjectsAdapter(weekNumber);
            rv.setAdapter(adapter);
            adapters.add(adapter);
        }

        public void applyData(final DayItem dayItem) {
            weekDay.setText(dayItem.getWeekDay());
            adapter.setData(dayItem.getSubjects());
            adapter.setWeekDay(dayItem.getWeekDay());
        }

    }

}
