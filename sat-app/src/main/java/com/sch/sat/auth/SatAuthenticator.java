package com.sch.sat.auth;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.sch.sat.api.User;
import com.sch.sat.dao.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by rn250152 on 11/20/16.
 */
public class SatAuthenticator implements Authenticator<BasicCredentials, User> {

    UserDao userDao;

    public SatAuthenticator(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of(
            "guest", ImmutableSet.of(),
            "good-guy", ImmutableSet.of("BASIC_GUY"),
            "chief-wizard", ImmutableSet.of("ADMIN", "BASIC_GUY")
    );

    /*@Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (VALID_USERS.containsKey(credentials.getUsername()) && "secret".equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername(), VALID_USERS.get(credentials.getUsername())));
        }
        return Optional.empty();
    }*/

    @UnitOfWork
    @Override
    public Optional<User> authenticate(final BasicCredentials basicCredentials) throws AuthenticationException {

        String username = basicCredentials.getUsername();
        String plaintextPassword = basicCredentials.getPassword();

        final Optional<User> user = userDao.findByUsername(username);
        if (user.isPresent()) {
            final User existingUser = user.get();
            checkState(existingUser.getPassword() != null, "Cannot authenticate: user with id: %s (username: %s) without password",
                    existingUser.getId(), existingUser.getUsername());

            if (plaintextPassword.equalsIgnoreCase(existingUser.getPassword())) {
                return user;
            }
        }
        return Optional.empty();
    }
}
