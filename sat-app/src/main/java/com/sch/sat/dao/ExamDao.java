package com.sch.sat.dao;

import com.sch.sat.api.Exam;
import com.sch.sat.api.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by rn250152 on 11/20/16.
 */
public class ExamDao extends AbstractDAO<Exam> {
    public ExamDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Exam> getAll(){

        return list(namedQuery("sat.exam.findAll"));

    }

    public Optional<Exam> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

}
