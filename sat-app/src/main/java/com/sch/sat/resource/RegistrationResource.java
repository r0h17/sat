package com.sch.sat.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.sch.sat.api.Role;
import com.sch.sat.api.User;
import com.sch.sat.dao.RoleDao;
import com.sch.sat.dao.UserDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by rn250152 on 11/20/16.
 */
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    UserDao userDao;

    RoleDao rolesDao;

    public RegistrationResource(UserDao userDao, RoleDao rolesDao) {
        this.userDao = userDao;
        this.rolesDao = rolesDao;
    }

    @POST
    @Timed
    @UnitOfWork
    public Response login(User user) throws Exception {

        Optional<Role> role = this.rolesDao.findById(user.isTeacher() ? 1L : 2L);
        if(role.isPresent()) {
            user.setRole(role.get());
            user = this.userDao.create(user);
        }else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error Role").build();
        }
        return Response.status(Response.Status.OK).entity(user).build();

    }


}
