package com.sch.sat.api;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rn250152 on 11/21/16.
 */
@Entity
@Table(name = "roles")
public class Role {
    private long id;
    private String roleName;


    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
