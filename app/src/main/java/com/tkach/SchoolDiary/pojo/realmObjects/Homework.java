package com.tkach.SchoolDiary.pojo.realmObjects;

import io.realm.RealmObject;

public class Homework extends RealmObject {

    private int number = 0;

    private String weekDay = " ";

    private String weekNumber = " ";

    private String homework = " ";

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

    public String getHomework() {
        return homework;
    }

    public void setHomework(final String homework) {
        this.homework = homework;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(final String weekNumber) {
        this.weekNumber = weekNumber;
    }

}
