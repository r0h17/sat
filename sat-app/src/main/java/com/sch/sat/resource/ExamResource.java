package com.sch.sat.resource;

import com.sch.sat.api.Exam;
import com.sch.sat.dao.ExamDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by rn250152 on 11/23/16.
 */
@Path("/exams")
@Produces(MediaType.APPLICATION_JSON)
public class ExamResource {

    final ExamDao examDao;

    public ExamResource(ExamDao examDao) {
        this.examDao = examDao;
    }

    @GET
    @UnitOfWork
    public Response getAllExams(){
        List<Exam> exams = examDao.getAll();
        return Response.ok().entity(exams).build();
    }

}
