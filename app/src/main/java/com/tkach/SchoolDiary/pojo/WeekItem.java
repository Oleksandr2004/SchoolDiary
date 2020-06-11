package com.tkach.SchoolDiary.pojo;

import androidx.annotation.NonNull;

public class WeekItem {

    private final int number;

    private boolean current;

    public WeekItem(final int number, final boolean current) {
        this.number = number;
        this.current = current;
    }

    public int getNumber() {
        return number;
    }

    public boolean isCurrent() {
        return current;
    }

    public void makeCurrent(final boolean current) {
        this.current = current;
    }

    @NonNull
    @Override
    public String toString() {
        return "Week " + number;
    }

}
