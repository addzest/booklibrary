package com.laba.booklibrary.service.users;

/**
 * Created by Trofim on 18.07.2017.
 */
public interface UserService {

    void addUser(UserTO userTO);

    boolean checkUser(String username);

    boolean validateUser(String username, String password);

    String getUserRole(long id);

    long getUserId(String username);


}
