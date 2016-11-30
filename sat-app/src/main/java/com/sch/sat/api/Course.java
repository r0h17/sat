package com.sch.sat.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by rn250152 on 11/22/16.
 */
@Entity
@Table(name="courses")
public class Course {
    private long id;
    private Exam exam;
    private String courseName;

    @Id
    @Column(name = "id")
    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_name")
    @JsonProperty
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="exam_id")
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
