package com.sch.sat.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by rn250152 on 11/20/16.
 */
@Entity
@Table(name = "users")
@NamedNativeQueries({
        @NamedNativeQuery(
                name    =   "getAllStudents",
                query   =   "SELECT id, username, password, firstName, lastName, role.id, role.roleName " +
                        "FROM user, role " +
                        "WHERE role.roleName = ?",
                resultClass=User.class
        )
})
public class User implements Principal{

    private long id;
    @Email(message = "Invalid email id")
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private List<Grades> gradesById;

    private boolean isTeacher;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    @JsonProperty
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "first_name")
    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Transient
    @JsonProperty
    public boolean isTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    @Override
    @Transient
    public String getName() {
        return this.username;
    }

    @OneToMany(mappedBy = "usersByUserId", fetch = FetchType.LAZY)
    @JsonIgnore
    public List<Grades> getGradesById() {
        return gradesById;
    }

    public void setGradesById(List<Grades> gradesById) {
        this.gradesById = gradesById;
    }

    /*public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }


    public boolean hasRole(UserRoles.Role name) {
        if(this.role == null || name == null)
            return false;

        return containsRole(this.role, name);
    }

    private boolean containsRole(UserRoles role, UserRoles.Role name) {
        if(role.getName() == name)
            return true;

        for(UserRoles r : role.getChildren()) {
            if(containsRole(r, name))
                return true;
        }

        return false;
    }*/
}
