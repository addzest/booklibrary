package com.laba.booklibrary.service.users;


import com.laba.booklibrary.service.users.model.UserTO;

public interface UserDao {


    void addUser(UserTO userTO);

    boolean validateUser(String username, String password);

    boolean checkUsername(String username);

    long getUserId(String username);

    UserTO getUserTOById(long id);

    String getUserRole(long id);
}
