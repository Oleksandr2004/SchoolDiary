package com.tkach.SchoolDiary.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DayItem {

    private String weekDay;

    private List<SubjectItem> subjects = new ArrayList<>(7);

    public DayItem(final String weekDay, final Collection<SubjectItem> subjects) {
        this.weekDay = weekDay;
        this.subjects.addAll(subjects);
    }

    public List<SubjectItem> getSubjects() {
        return subjects;
    }

    public String getWeekDay() {
        return weekDay;
    }

}
