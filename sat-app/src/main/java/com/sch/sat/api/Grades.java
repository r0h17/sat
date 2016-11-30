package com.sch.sat.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by rn250152 on 11/22/16.
 */
@Entity
@Table(name="grades")
public class Grades {
    private long id;
    private Double grade;
    private PracticeTest test;
    private User usersByUserId;

    @Id
    @Column(name = "id")
    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   /* @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }*/

    @Basic
    @Column(name = "grade")
    @JsonProperty
    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="test_id")
    @JsonProperty
    public PracticeTest getTest() {
        return test;
    }

    public void setTest(PracticeTest test) {
        this.test = test;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(User usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
