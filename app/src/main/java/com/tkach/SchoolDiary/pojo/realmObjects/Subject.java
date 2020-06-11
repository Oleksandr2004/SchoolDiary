package com.tkach.SchoolDiary.pojo.realmObjects;

import io.realm.RealmObject;

public class Subject extends RealmObject {

    private int number = 0;

    private String weekDay = "";

    private String subject = "";

    private String homework = "";

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

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

}
