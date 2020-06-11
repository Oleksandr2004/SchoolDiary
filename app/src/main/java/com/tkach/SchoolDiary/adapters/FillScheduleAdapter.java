package com.tkach.SchoolDiary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tkach.SchoolDiary.R;
import com.tkach.SchoolDiary.pojo.fillScheduleItems.FillDayItem;
import com.tkach.SchoolDiary.pojo.realmObjects.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FillScheduleAdapter extends RecyclerView.Adapter<FillScheduleAdapter.FillDaysViewHolder> {

    private List<FillDayItem> data = new ArrayList<>();

    private List<FillSubjectsAdapter> adapters = new ArrayList<>();

    private List<List<Subject>> schedule = new ArrayList<>();

    @NonNull
    @Override
    public FillScheduleAdapter.FillDaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_fill_day_item, parent, false);
        return new FillScheduleAdapter.FillDaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FillScheduleAdapter.FillDaysViewHolder holder, int position) {
        holder.applyData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(final Collection<FillDayItem> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<List<Subject>> getSchedule() {
        for (FillSubjectsAdapter adapter : adapters) {
            schedule.add(adapter.getDaySchedule());
        }
        return schedule;
    }

    class FillDaysViewHolder extends RecyclerView.ViewHolder {

        private TextView weekDay;

        private RecyclerView rv;

        private FillSubjectsAdapter adapter;

        public FillDaysViewHolder(@NonNull View itemView) {
            super(itemView);
            this.weekDay = itemView.findViewById(R.id.fill_weekday);
            this.rv = itemView.findViewById(R.id.fill_subjects_rv);
            initRecyclerView();
        }

        private void initRecyclerView() {
            rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            adapter = new FillSubjectsAdapter();
            adapters.add(adapter);
            rv.setAdapter(adapter);
        }

        public void applyData(final FillDayItem dayItem) {
            weekDay.setText(dayItem.getWeekDay());
            adapter.setData(dayItem.getSubjects());
            adapter.setWeekDay(dayItem.getWeekDay());
        }

    }

}
