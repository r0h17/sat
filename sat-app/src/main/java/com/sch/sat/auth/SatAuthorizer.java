package com.sch.sat.auth;

import com.sch.sat.api.User;
import io.dropwizard.auth.Authorizer;

/**
 * Created by rn250152 on 11/20/16.
 */
public class SatAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User user, String role) {
        return user.getRole() != null && user.getRole().getRoleName().equalsIgnoreCase(role);
    }
}
