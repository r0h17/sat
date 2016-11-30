package com.sch.sat.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by rn250152 on 11/22/16.
 */
@Entity
@Table(name = "exam")
@NamedQueries({
        @NamedQuery(
                name = "sat.exam.findAll",
                query = "SELECT e FROM Exam e"
        )
})
public class Exam {
    private long id;
    private String examName;

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
    @Column(name = "exam_name")
    @JsonProperty
    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

}
