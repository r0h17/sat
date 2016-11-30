package com.sch.sat.resource;

import com.sch.sat.api.User;
import com.sch.sat.dao.UserDao;
import io.dropwizard.hibernate.UnitOfWork;
import liquibase.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * Created by rn250152 on 11/20/16.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    final UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @GET
    @Path("/{username}")
    @UnitOfWork
    public Response getByUserName(@PathParam("username") String userName){
        Optional<User> user = userDao.findByUsername(userName);
        if(user.isPresent()) {
            return Response.status(Response.Status.OK)
                    .entity(user.get())
                    .build();
        }
        return Response.serverError().build();
    }

    @GET
    @UnitOfWork
    public Response getAll(@QueryParam("type") String type){
        List<User> userList = null;
        if(StringUtils.isNotEmpty(type)){
            if(type.equalsIgnoreCase("S"))
                userList = userDao.getAllStudents("STUDENT");
            else if(type.equalsIgnoreCase("T"))
                userList = userDao.getAllStudents("TEACHER");
        }else{
            //userDao.findByUsername();
        }
        if(userList==null || userList.isEmpty()){
            return Response.noContent().build();
        }else{
            return Response.ok().entity(userList).build();
        }
    }

}
