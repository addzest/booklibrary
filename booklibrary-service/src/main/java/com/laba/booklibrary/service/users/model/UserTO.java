package com.laba.booklibrary.service.users.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Blueprint for a user object. Contain all fields to work with a user.
 */
@Entity
@Table(name = "users")
public class UserTO {
    @Id
    private Long id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "user_password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "userTO")
    private Set<UserRoleMappingTO> userRoleMappingTOs;

    @ManyToMany
    @JoinTable(name = "user_roles_mapping",
            joinColumns =  { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn( name = "role_id") })
    private Set<UserRoleTO> userRoleTOs = new HashSet<UserRoleTO>();

    public UserTO() {
    }

    @Override
    public String toString() {
        return "UserTO{" +
                "username='" + username + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
