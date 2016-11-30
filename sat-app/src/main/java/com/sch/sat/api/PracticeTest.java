package com.sch.sat.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by rn250152 on 11/22/16.
 */
@Entity
@Table(name = "practice_tests")
public class PracticeTest {
    private long id;
    /*private Long createdBy;*/
    private User createdByUser;
    private Date date;
    private Course courses;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /*@Basic
    @Column(name = "created_by")
    @JsonProperty
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }*/

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="created_by")
    @JsonProperty
    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    @Basic
    @Column(name = "date")
    @JsonProperty
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="course_id")
    @JsonProperty
    public Course getCourses() {
        return courses;
    }

    public void setCourses(Course courses) {
        this.courses = courses;
    }
}
