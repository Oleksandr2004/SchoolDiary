package com.tkach.SchoolDiary.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tkach.SchoolDiary.R;
import com.tkach.SchoolDiary.pojo.fillScheduleItems.FillSubjectItem;
import com.tkach.SchoolDiary.pojo.realmObjects.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FillSubjectsAdapter extends RecyclerView.Adapter<FillSubjectsAdapter.FillSubjectViewHolder> {

    private List<FillSubjectItem> data = new ArrayList<>();

    private List<Subject> daySchedule = new ArrayList<>(7);

    private String weekDay = "";

    private static final String TAG = "FillSubjectsAdapter";

    @NonNull
    @Override
    public FillSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_fill_subject_item, parent, false);
        return new FillSubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FillSubjectViewHolder holder, int position) {
        holder.applyData(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(final Collection<FillSubjectItem> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<Subject> getDaySchedule() {
        return daySchedule;
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void setWeekDay(final String weekDay) {
        this.weekDay = weekDay;
    }

    public class FillSubjectViewHolder extends RecyclerView.ViewHolder {

        private TextView number;

        private EditText subject;

        public FillSubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.fill_subject_number);
            subject = itemView.findViewById(R.id.fill_subject);
        }

        public void applyData(final FillSubjectItem fillSubjectItem, final int position) {
            number.setText(fillSubjectItem.getNumber());
            subject.setText(fillSubjectItem.getSubjectName());
            subject.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    final Subject subject = new Subject();
                    subject.setSubject(s.toString());
                    subject.setNumber(getLayoutPosition() + 1);
                    subject.setWeekDay(weekDay);
                    daySchedule.add(position, subject);

                    Log.i(TAG, "afterTextChanged: " + position + " : " + s.toString());
                }

            });
        }

    }

}
