package com.tkach.SchoolDiary.pojo.fillScheduleItems;

public class FillSubjectItem {

    private String number;

    private String subjectName;

    public FillSubjectItem(final String number, final String subjectName) {
        this.number = number;
        this.subjectName = subjectName;
    }

    public String getNumber() {
        return number;
    }

    public String getSubjectName() {
        return subjectName;
    }

}
