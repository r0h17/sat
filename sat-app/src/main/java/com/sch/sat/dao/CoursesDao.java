package com.sch.sat.dao;

import com.sch.sat.api.Course;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;

/**
 * Created by rn250152 on 11/20/16.
 */
public class CoursesDao extends AbstractDAO<Course> {
    public CoursesDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Course> getAllCoursesForExamId(Long id){
        return criteria()
                .add(Restrictions.eq("exam.id", id))
                .list();


    }

    public Optional<Course> findById(Long courseId) {
        return Optional.ofNullable(get(courseId));
    }

    public List<Course> getAllCourses() {
        return list(criteria());
    }
}
