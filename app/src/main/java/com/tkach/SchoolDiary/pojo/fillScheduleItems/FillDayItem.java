package com.tkach.SchoolDiary.pojo.fillScheduleItems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FillDayItem {

    private String weekDay;

    private List<FillSubjectItem> subjects = new ArrayList<>(7);

    public FillDayItem(final String weekDay, final Collection<FillSubjectItem> subjects) {
        this.weekDay = weekDay;
        this.subjects.addAll(subjects);
    }

//    public void setWeekDay(String weekDay) {
//        this.weekDay = weekDay;
//    }
//
//    public void setSubjects(List<FillSubjectItem> subjects) {
//        this.subjects = subjects;
//    }

    public List<FillSubjectItem> getSubjects() {
        return subjects;
    }

    public String getWeekDay() {
        return weekDay;
    }

}
