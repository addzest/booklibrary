package com.laba.booklibrary.service.users;


import com.laba.booklibrary.service.users.model.UserTO;
import org.apache.log4j.Logger;

/**
 * Service for working with users
 */
public class UserServiceImpl implements UserService{
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private UserDao userDao = new UserDaoImpl();


    /**
     * Method to check if username is already exist
     *
     * @param username -  user name
     * @return <code>true</code> if the username is already exist;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean checkUser(String username) {
        boolean isExist = this.userDao.checkUsername(username);
        return isExist;
    }

    /**
     * Method to add user(after registration)
     * @param userTO - user instance
     */
    @Override
    public void addUser(UserTO userTO) {
        this.userDao.addUser(userTO);
        this.userDao.setUserRole(userDao.getUserId(userTO.getUsername()));
        log.info("New user added "+ userTO);
    }

    /**
     * Method to validate username and password of user during sign in
     * @param username - user name
     * @param password - user password
     * @return @return <code>true</code> if the username and password is right;
     *                  <code>false</code> otherwise.
     */
    @Override
    public boolean validateUser(String username, String password) {
        if (userDao.validateUser(username,password)){
            log.info("User validated " + username);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to take user role
     * @param id  - user id
     * @return user role
     */
    @Override
    public String getUserRole(long id) {
        return this.userDao.getUserRole(id);
    }

    /**
     * Method to get user id
     * @param username - user name
     * @return user id
     */
    @Override
    public long getUserId(String username) {
        return this.userDao.getUserId(username);
    }

}
