package com.laba.booklibrary.service.users;


interface UserDao {

    void addUser(UserTO userTO);

    boolean validateUser(String username, String password);

    boolean checkUsername(String username);

    long getUserId(String username);



}
