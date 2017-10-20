package com.laba.booklibrary.service.users.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user_roles")
public class UserRoleTO implements Serializable {
    private long id;
    private String roleName;
    private Set<UserTO> userTOs = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role_mapping",
                joinColumns =  {@JoinColumn(name = "role_id")},
                inverseJoinColumns = {@JoinColumn(name = "user_id")})
    public Set<UserTO> getUserTOs(){
        return  userTOs;
    }

    public void setUserTOs(Set<UserTO> userTOs) {
        this.userTOs = userTOs;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column (name = "role_name")
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
