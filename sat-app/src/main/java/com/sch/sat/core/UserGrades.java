package com.sch.sat.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

/**
 * Created by rn250152 on 11/29/16.
 */
public class UserGrades {

    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String courseName;
    @JsonProperty
    private String examName;
    @JsonProperty
    private Date date;
    @JsonProperty
    private Double grade;
    @JsonProperty
    private String userName;
    @JsonProperty
    private Long testId;

    public UserGrades() {
    }

    public UserGrades(String firstName, String lastName, String courseName, String examName, Date date, Double grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseName = courseName;
        this.examName = examName;
        this.date = date;
        this.grade = grade;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }
}
