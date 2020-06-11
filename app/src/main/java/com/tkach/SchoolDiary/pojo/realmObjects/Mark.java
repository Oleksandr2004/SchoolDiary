package com.tkach.SchoolDiary.pojo.realmObjects;

import io.realm.RealmObject;

public class Mark extends RealmObject {

    private int number = 0;

    private String weekDay = " ";

    private String weekNumber = " ";

    private String mark = " ";

    public int getNumber() {
        return number;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public void setWeekDay(final String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(final String mark) {
        this.mark = mark;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(final String weekNumber) {
        this.weekNumber = weekNumber;
    }

}
