package com.laba.booklibrary.service.users.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_roles")
public class UserRoleTO {
    @Id
    private long id;
    @Column (name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "userRoleTO")
    private Set<UserRoleMappingTO> userRoleMappingTOs;

    @ManyToMany(mappedBy = "userRoles")
    private Set<UserTO> userTOs = new HashSet<UserTO>();


    public UserRoleTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "UserRoleTO{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
