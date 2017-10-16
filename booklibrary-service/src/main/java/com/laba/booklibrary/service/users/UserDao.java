package com.laba.booklibrary.service.users;


import com.laba.booklibrary.service.users.model.UserTO;

interface UserDao {


    void addUser(UserTO userTO);

    boolean validateUser(String username, String password);

    boolean checkUsername(String username);

    long getUserId(String username);

    String getUserRole(long id);

    void setUserRole(long id);



}
