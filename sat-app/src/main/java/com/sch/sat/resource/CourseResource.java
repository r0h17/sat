package com.sch.sat.resource;

import com.sch.sat.api.Course;
import com.sch.sat.dao.CoursesDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by rn250152 on 11/20/16.
 */
@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    final CoursesDao coursesDao;

    public CourseResource(CoursesDao coursesDao) {
        this.coursesDao = coursesDao;
    }

    @GET
    @UnitOfWork
    public Response getCoursesForExam(@QueryParam("examid") Long id){

        List<Course> allCoursesForExamId;

        if(id!=null)
            allCoursesForExamId = coursesDao.getAllCoursesForExamId(id);
        else
            allCoursesForExamId = coursesDao.getAllCourses();

        if(allCoursesForExamId==null){
            return Response.noContent().build();
        }
        else
            return Response.ok().entity(allCoursesForExamId).build();
    }
}
