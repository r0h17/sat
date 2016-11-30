package com.sch.sat.resource;

import com.codahale.metrics.annotation.Timed;
import com.sch.sat.api.Course;
import com.sch.sat.api.Exam;
import com.sch.sat.api.PracticeTest;
import com.sch.sat.api.User;
import com.sch.sat.core.Schedule;
import com.sch.sat.dao.CoursesDao;
import com.sch.sat.dao.ExamDao;
import com.sch.sat.dao.PracticeTestDao;
import com.sch.sat.dao.UserDao;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by rn250152 on 11/22/16.
 */
@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleTestResource {

    final PracticeTestDao practiceTestDao;
    final UserDao userDao;
    final ExamDao examDao;
    final CoursesDao coursesDao;

    public ScheduleTestResource(PracticeTestDao practiceTestDao, UserDao userDao, ExamDao examDao, CoursesDao coursesDao) {
        this.practiceTestDao = practiceTestDao;
        this.userDao = userDao;
        this.examDao = examDao;
        this.coursesDao = coursesDao;
    }

    @POST
    @Timed
    @UnitOfWork
    public Response scheduleTest(@Auth User user, Schedule schedule) throws Exception {

        Optional<Course> course = coursesDao.findById(schedule.getCourseName());
        if(course.isPresent()) {
            PracticeTest test = new PracticeTest();
            test.setCreatedByUser(user);
            test.setDate(schedule.getExamDate());
            test.setCourses(course.get());

            PracticeTest save = practiceTestDao.save(test);
            return Response.status(Response.Status.OK).entity(test).build();
        }else
            return Response.serverError().entity("Error while getting user information!!").build();
    }
    @GET
    @Timed
    @UnitOfWork
    @PermitAll
    public Response getAllExamSchedules(@Auth User user){
        List<PracticeTest> allSchedules = practiceTestDao.getAllSchedules(user);
        return Response.ok().entity(allSchedules).build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getTest(String body,
                                  @PathParam("id") long id) {
        return Response.status(Response.Status.OK)
                .entity(practiceTestDao.find(id))
                .build();
    }
}
