package com.laba.booklibrary.service.users;


import com.laba.booklibrary.service.users.model.UserRoleTO;
import com.laba.booklibrary.service.users.model.UserTO;
import org.hibernate.Session;
import javax.persistence.Query;

import static com.laba.booklibrary.service.connection.HibernateUtil.closeSession;
import static com.laba.booklibrary.service.connection.HibernateUtil.getSession;


/**
 * DAO access layer for users table
 */
class UserDaoImpl implements UserDao {
    private static final long DEFAULT_ROLE_ID = 2;// 2- reader
    private static final String USERNAME = "username";

    /**
     * Method to add user to users table
     * @param userTO - user instance
     */
    @Override
    public void addUser(UserTO userTO) {
        Session session = getSession();
        session.beginTransaction();
        UserRoleTO userRoleTO = session.get(UserRoleTO.class, DEFAULT_ROLE_ID);
        userRoleTO.getUserTOs().add(userTO);
        session.save(userRoleTO);
        session.getTransaction().commit();
        closeSession();
    }

    /**
     * Method to validate user from users table
     * @param username - user name
     * @param password - user password
     * @return <code>true</code> if the user is in users table;
     *                  <code>false</code> otherwise.
     */
    @Override
    public boolean validateUser(String username, String password) {
        boolean isValidated = false;
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("from UserTO where username = :username and password = :password");
        query.setParameter(USERNAME, username);
        query.setParameter("password", password);
        if (!query.getResultList().isEmpty()) {
            isValidated=true;
        }
        closeSession();
        return isValidated;
    }

    /**
     * Method to check username if it in users table
     * @param username  - user name
     * @return <code>true</code> if the username is in users table;
     *                  <code>false</code> otherwise.
     */

    @Override
    public boolean checkUsername(String username) {
        boolean isExist = true;
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("from UserTO where username = :username");
        query.setParameter(USERNAME, username);
        if (query.getResultList().isEmpty()) {
            isExist=false;
        }
        closeSession();
        return isExist;
    }


    /**
     * Method to take user id from users table by name
     * @param username  - user name
     * @return userId - user id
     */

    @Override
    public long getUserId(String username) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("from UserTO u where u.username = :username", UserTO.class);
        query.setParameter(USERNAME, username);
        UserTO userTO = (UserTO) query.getSingleResult();
        long userId = userTO.getId();
        closeSession();
        return userId;
    }

    /**
     * Method to get user role from user_roles table
     *
     * @param id - user id
     * @return role  - role the user have
     */
    @Override
    public String getUserRole(long id) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createNativeQuery("SELECT role_name FROM user_roles join user_role_mapping on user_roles.id = user_role_mapping.role_id where user_role_mapping.user_id = :id");
        query.setParameter("id",id);
        String role = (String) query.getSingleResult();
        closeSession();
        return role;
    }
}
