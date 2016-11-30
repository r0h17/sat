package com.sch.sat.dao;

import com.sch.sat.api.Exam;
import com.sch.sat.api.PracticeTest;
import com.sch.sat.api.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by rn250152 on 11/22/16.
 */
public class PracticeTestDao extends AbstractDAO<PracticeTest> {
    public PracticeTestDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public PracticeTest save(PracticeTest practiceTest){
        return persist(practiceTest);
    }

    public PracticeTest find(long id){
        return get(id);
    }
    public List<PracticeTest> getAllSchedules(User user){
        return criteria().createCriteria("createdByUser").add(Restrictions.eq("username",user.getUsername())).list();
    }
}
