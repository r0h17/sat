package com.sch.sat.dao;

import com.sch.sat.api.User;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.query.spi.ParameterMetadata;
import org.hibernate.internal.QueryImpl;
import org.hibernate.internal.SQLQueryImpl;
import org.hibernate.sql.JoinType;

import java.util.List;
import java.util.Optional;

/**
 * Created by rn250152 on 11/20/16.
 */
public class UserDao extends AbstractDAO<User> {
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @UnitOfWork
    public User create(User user) {
        return persist(user);
    }

    public Optional<User> findByUsername(String username) {
        Object userObject = criteria()
                .add(Restrictions.eq("username", username))
                .uniqueResult();
        if(userObject == null) return Optional.empty();
        return Optional.of((User) userObject);
    }

    public List<User> getAllStudents(String type){
        Criteria criteria = criteria();
        Criteria rolesCriteria = criteria.createCriteria("role");
        rolesCriteria.add(Restrictions.eq("roleName", type));
        List<User> students = criteria.list();

        return students;
    }

    public List<User> getAllStudentsWithMarksForTest(long testId){
        Criteria criteria = criteria();
        Criteria rolesCriteria = criteria.createCriteria("role");
        rolesCriteria.add(Restrictions.eq("roleName", "STUDENT"));

        /*Criteria criteria1 = criteria.createCriteria("gradesById", JoinType.LEFT_OUTER_JOIN)
                .createCriteria("test", JoinType.LEFT_OUTER_JOIN);
        criteria1.add(Restrictions.or(Restrictions.eq("id", testId), Restrictions.isNull("id")));*/

        return list(criteria);
    }

}