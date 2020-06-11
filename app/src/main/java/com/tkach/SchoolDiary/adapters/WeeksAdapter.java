package com.tkach.SchoolDiary.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tkach.SchoolDiary.R;
import com.tkach.SchoolDiary.pojo.WeekItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.WeekViewHolder> {

    private List<WeekItem> data;

    private OnWeekClickListener onWeekClickListener;

    public WeeksAdapter(final OnWeekClickListener onWeekClickListener) {
        this.onWeekClickListener = onWeekClickListener;
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_week_item, parent, false);
        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {
        final WeekItem item = data.get(position);
        if (item.isCurrent())
            holder.applyData(item, Color.parseColor("#008577"));
        else
            holder.applyData(item, Color.parseColor("#00574B"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(final Collection<WeekItem> data) {
        this.data = new ArrayList<>();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    class WeekViewHolder extends RecyclerView.ViewHolder {

        private TextView weekNumber;

        private WeekViewHolder(@NonNull final View itemView) {
            super(itemView);
            weekNumber = itemView.findViewById(R.id.week);

            itemView.setOnClickListener(v -> {
                final WeekItem item = data.get(getLayoutPosition());
                onWeekClickListener.onWeekClick(item);
            });

            itemView.setOnLongClickListener(v -> {
                final WeekItem item = data.get(getLayoutPosition());
                onWeekClickListener.onWeekLongClick(item);
                return true;
            });
        }

        private void applyData(final WeekItem item, final int color) {
            weekNumber.setText(item.toString());
            weekNumber.setBackgroundColor(color);
        }

    }

    public interface OnWeekClickListener {

        void onWeekClick(final WeekItem week);

        void onWeekLongClick(final WeekItem week);

    }

}
