package com.laba.booklibrary.service.users;

import com.laba.booklibrary.service.users.model.UserTO;


public interface UserService {

    void addUser(UserTO userTO);

    boolean checkUser(String username);

    boolean validateUser(String username, String password);

    String getUserRole(long id);

    long getUserId(String username);


}
