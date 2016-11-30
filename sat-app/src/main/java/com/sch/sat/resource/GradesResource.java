package com.sch.sat.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.sch.sat.api.Grades;
import com.sch.sat.api.PracticeTest;
import com.sch.sat.api.Role;
import com.sch.sat.api.User;
import com.sch.sat.core.UserGrades;
import com.sch.sat.dao.GradesDao;
import com.sch.sat.dao.PracticeTestDao;
import com.sch.sat.dao.UserDao;
import com.sch.sat.service.MessageService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import sun.swing.BakedArrayList;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by rn250152 on 11/22/16.
 */
@Path("/grades")
@Produces(MediaType.APPLICATION_JSON)
public class GradesResource {

    private final AtomicLong jodIdFactory = new AtomicLong(0L);

    final GradesDao gradesDao;

    final UserDao userDao;

    final PracticeTestDao practiceTestDao;

    final Channel channel;

    final MessageService emailService;

    ObjectMapper objectMapper = new ObjectMapper();

    public GradesResource(GradesDao gradesDao, UserDao userDao, PracticeTestDao practiceTestDao, MessageService emailService) {
        this.gradesDao = gradesDao;
        this.userDao = userDao;
        this.practiceTestDao = practiceTestDao;
        this.emailService = emailService;
        this.channel = null;
    }


    /*@Path("/{user_id}/")
    public Response saveGrade(List<Grades> gradesList){

    }*/

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getUserGrades(String body,
                                  @PathParam("id") long id) {
        return Response.status(Response.Status.OK)
                .entity(gradesDao.getAllUserGrades(id))
                .build();
    }


    @GET
    @RolesAllowed("TEACHER")
    @UnitOfWork
    public Response getGrades(@Auth User teacher,
                              @QueryParam("date") Date date,
                              @QueryParam("courseName") String courseName,
                              @QueryParam("exam") String exam,
                              @QueryParam("testId") Long testId
                            ){

        PracticeTest practiceTest = practiceTestDao.find(testId);
        Map<String, Double> allGradesForTest = gradesDao.getAllGradesForTest(practiceTest);
        List<User> allStudentsWithMarksForTest = userDao.getAllStudentsWithMarksForTest(testId);
        List<UserGrades> userGrades = new ArrayList<>();
        UserGrades userGrade;
        for (User student: allStudentsWithMarksForTest){
            userGrade = new UserGrades();
            userGrade.setUserName(student.getUsername());
            userGrade.setFirstName(student.getFirstName());
            userGrade.setLastName(student.getLastName());
            userGrade.setCourseName(practiceTest.getCourses().getCourseName());
            userGrade.setExamName(practiceTest.getCourses().getExam().getExamName());
            userGrade.setTestId(practiceTest.getId());
            userGrade.setDate(practiceTest.getDate());
            userGrade.setGrade(allGradesForTest.get(userGrade.getUserName()));
            /*List<Grades> gradesById = student.getGradesById();
            if(gradesById.size()>0){
                Grades grade = gradesById.get(0);
                userGrade.setGrade(grade.getGrade());
            }*/

            userGrades.add(userGrade);
        }

        return Response.ok().entity(userGrades).build();
    }

    @POST
    @RolesAllowed("TEACHER")
    @UnitOfWork
    public Response updateGrade(@Auth User teacher, List<UserGrades> userGrades){
        if(userGrades.size()==0) return Response.noContent().build();
        if(userGrades.size()==1) {
            UserGrades userGrade = userGrades.get(0);
            Grades grades = gradesDao.getGradeFor(userGrade);
            if (grades == null) {
                grades = new Grades();
                grades.setUsersByUserId(userDao.findByUsername(userGrade.getUserName()).get());
                grades.setTest(practiceTestDao.find(userGrade.getTestId()));
            }
            grades.setGrade(userGrade.getGrade());

            Grades savedValue = gradesDao.save(grades);
            if (savedValue != null) {

                emailService.sendEmail(userGrade);

                return Response.ok().entity(savedValue).build();
            } else {
                return Response.serverError().entity("Error while updating the Grade").build();
            }
        }else{
            final Long jobId = jodIdFactory.incrementAndGet();
            try {
                for (UserGrades userGrade: userGrades) {
                    channel.basicPublish("job", "index", new AMQP.BasicProperties(), objectMapper.writeValueAsBytes(userGrade));
                }
                return Response.accepted().entity("{message:Update data has been submitted}").build();
            } catch (IOException e) {
                e.printStackTrace();
                return Response.serverError().entity("Error while updating the Grades").build();
            }
        }
    }
}
