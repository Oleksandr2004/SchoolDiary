package com.tkach.SchoolDiary.pojo;

public class SubjectItem {

    private String number;

    private String subject;

    private String homework;

    private String mark;

    public SubjectItem(final String number, final String subject, final String homework, final String mark) {
        this.number = number;
        this.subject = subject;
        this.homework = homework;
        this.mark = mark;
    }

    public String getNumber() {
        return number;
    }

    public String getSubject() {
        return subject;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(final String homework) {
        this.homework = homework;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(final String mark) {
        this.mark = mark;
    }
}
