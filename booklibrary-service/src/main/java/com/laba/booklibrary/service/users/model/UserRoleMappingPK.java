package com.laba.booklibrary.service.users.model;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class UserRoleMappingPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserTO userTO;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private UserRoleTO userRoleTO;
}
