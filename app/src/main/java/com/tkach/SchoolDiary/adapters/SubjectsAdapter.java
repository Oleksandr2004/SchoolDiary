package com.tkach.SchoolDiary.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tkach.SchoolDiary.R;
import com.tkach.SchoolDiary.pojo.SubjectItem;
import com.tkach.SchoolDiary.pojo.realmObjects.Homework;
import com.tkach.SchoolDiary.pojo.realmObjects.Mark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private static final String TAG = "SubjectsAdapter";

    private List<SubjectItem> data = new ArrayList<>();

    private List<Homework> homeworkList;

    private List<Mark> marksList;

    private String weekDay = "";

    private final String weekNumber;

    {
        initHomeworkList();
        initMarksList();
    }

    private void initHomeworkList() {
        homeworkList = new ArrayList<>();
        for (int i = 0; i < 8; i++) homeworkList.add(i, new Homework());
    }

    private void initMarksList() {
        marksList = new ArrayList<>();
        for (int i = 0; i < 8; i++) marksList.add(i, new Mark());
    }

    public SubjectsAdapter(final String weekNumber) {
        this.weekNumber = weekNumber;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_subject_item, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.applyData(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(final Collection<SubjectItem> data) {
        this.data = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<Homework> getDayHomework() {
        final List<Homework> dayHomework = new ArrayList<>();
        for (final Homework homework : homeworkList)
            if (!homework.getHomework().trim().equals(""))
                dayHomework.add(homework);

        initHomeworkList();
        return dayHomework;
    }

    public void setWeekDay(final String weekDay) {
        this.weekDay = weekDay;
    }

    public List<Mark> getDayMarks() {
        final List<Mark> dayMarks = new ArrayList<>();
        for (final Mark mark : marksList)
            if (!mark.getMark().trim().equals(""))
                dayMarks.add(mark);

        initMarksList();
        return dayMarks;
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        private TextView number;

        private TextView subject;

        private EditText homework;

        private EditText mark;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.subject_number);
            subject = itemView.findViewById(R.id.subject_name);
            homework = itemView.findViewById(R.id.subject_task);
            mark = itemView.findViewById(R.id.subject_mark);
        }

        public void applyData(final SubjectItem subjectItem, final int position) {
            number.setText(subjectItem.getNumber());
            subject.setText(subjectItem.getSubject());
            homework.setText(subjectItem.getHomework());
            mark.setText(subjectItem.getMark());
            mark.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    final Mark mark = new Mark();
                    mark.setNumber(Integer.parseInt(subjectItem.getNumber()));
                    mark.setWeekDay(weekDay);
                    mark.setWeekNumber(weekNumber);
                    mark.setMark(s.toString());
                    marksList.set(position, mark);
                }
            });
            homework.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    final Homework homework = new Homework();
                    homework.setNumber(Integer.parseInt(subjectItem.getNumber()));
                    homework.setWeekDay(weekDay);
                    homework.setWeekNumber(weekNumber);
                    homework.setHomework(s.toString());
                    homeworkList.set(position, homework);
                }

            });
        }

    }

}
