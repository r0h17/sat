package com.sch.sat.dao;

import com.google.common.base.Optional;
import com.sch.sat.api.Role;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.SessionFactory;

/**
 * Created by rn250152 on 11/21/16.
 */
public class RoleDao extends AbstractDAO<Role> {
    public RoleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Role> findById(Long id) {
        return Optional.fromNullable(get(id));
    }
}
