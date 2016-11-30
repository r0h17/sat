package com.sch.sat.dao;

import com.sch.sat.api.Exam;
import com.sch.sat.api.Grades;
import com.sch.sat.api.PracticeTest;
import com.sch.sat.api.User;
import com.sch.sat.core.UserGrades;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rn250152 on 11/22/16.
 */
public class GradesDao extends AbstractDAO<Grades> {
    public GradesDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Grades> getAllUserGrades(long id){
        Criteria criteria = criteria();
        Criteria userCriteria = criteria.createCriteria("usersByUserId");
        userCriteria.add(Restrictions.eq("id", id)).list();

        return criteria.list();
    }

    public List<Grades> getAllStudentGradesForDate(User user, Date date) {
        Criteria criteria = criteria();

        Criteria practiceTestCriteria = criteria.createCriteria("test");
        practiceTestCriteria.add(Restrictions.eq("date", date));

        Criteria userCriteria = practiceTestCriteria.createCriteria("createdByUser");
        userCriteria.add(Restrictions.eq("id",user.getId())).list();

        return criteria.list();
    }

    public Grades getGradeFor(UserGrades userGrade) {
        Criteria criteria = criteria();

        criteria.createCriteria("usersByUserId").add(Restrictions.eq("username",userGrade.getUserName()));
        criteria.createCriteria("test").add(Restrictions.eq("id",userGrade.getTestId()));

        return (Grades) criteria.uniqueResult();
    }

    public Grades save(Grades grades) {
        return persist(grades);
    }

    public Map<String, Double> getAllGradesForTest(PracticeTest practiceTest) {
        Map<String,Double> userGrades = new HashMap<>();
        Criteria criteria = criteria().createCriteria("test").add(Restrictions.eq("id",practiceTest.getId()));
        List<Grades> list = list(criteria);
        for (Grades grade: list){
            userGrades.put(grade.getUsersByUserId().getUsername(),grade.getGrade());
        }
        return userGrades;
    }
}
