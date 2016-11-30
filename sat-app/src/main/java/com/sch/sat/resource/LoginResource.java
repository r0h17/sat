package com.sch.sat.resource;

import com.codahale.metrics.annotation.Timed;
import com.sch.sat.auth.SatAuthenticator;
import com.sch.sat.api.User;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by rn250152 on 11/20/16.
 */

@Path("/authenticate")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final SatAuthenticator authenticator;

    public LoginResource(SatAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @POST
    @Timed
    @UnitOfWork
    public Response login(@Valid User user) throws Exception {

        Optional<User> res = authenticator.authenticate(new BasicCredentials(user.getUsername(), user.getPassword()));
        if ( res.isPresent() ) {
            return Response.status(Response.Status.OK).entity(res.get()).build();
        } else {
            throw new WebApplicationException("Unable to log in with those credentials!");
        }
    }



}
