package com.sch.sat.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;


/**
 * Created by rn250152 on 11/27/16.
 */
public class Schedule {
    @JsonProperty
    private Long examType;
    @JsonProperty
    private Long courseName;
    @JsonProperty
    private Date examDate;

    public Long getExamType() {
        return examType;
    }

    public void setExamType(Long examType) {
        this.examType = examType;
    }

    public Long getCourseName() {
        return courseName;
    }

    public void setCourseName(Long courseName) {
        this.courseName = courseName;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }
}
