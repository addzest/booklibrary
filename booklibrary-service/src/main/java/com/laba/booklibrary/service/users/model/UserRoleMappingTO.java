package com.laba.booklibrary.service.users.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity @IdClass(UserRoleMappingPK.class)
@Table(name = "user_role_mapping")
public class UserRoleMappingTO {
    @Id
    private UserTO userTO;

    @Id
    private  UserRoleTO userRoleTO;

}
