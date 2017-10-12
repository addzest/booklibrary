package com.laba.booklibrary.service.users;

import com.laba.booklibrary.service.connection.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * DAO access layer for users table
 */
class UserDaoImpl implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    /**
     * Method to add user to users table
     *
     * @param userTO - user instance
     */
    @Override
    public void addUser(UserTO userTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String addQuery = "INSERT INTO users (user_name, user_password, first_name, last_name, email) VALUES(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(addQuery);
            preparedStatement.setString(1,userTO.getUsername());
            preparedStatement.setString(2,userTO.getPassword());
            preparedStatement.setString(3,userTO.getFirstName());
            preparedStatement.setString(4,userTO.getLastName());
            preparedStatement.setString(5,userTO.getEmail());
            preparedStatement.execute();
        } catch(Exception e) {
            log.error("Add user exception",e);
        } finally {
            try {
                if(preparedStatement!=null)
                    preparedStatement.close();
            } catch (SQLException e) {
                log.error("Closing prepared statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean isValidated = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            String findQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND user_password = '" + password + "'";
            resultSet = statement.executeQuery(findQuery);
            if (resultSet.next()) {
                isValidated = true;
            }
        } catch(Exception e) {
            log.error("Validate user exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Closing connection exception",e);
            }
        }
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
        boolean isExist = false;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            String findQuery = "SELECT * FROM users WHERE user_name = '" + username + "'";
            resultSet = statement.executeQuery(findQuery);
            if (resultSet.next()) {
                isExist = true;
            }
        } catch(Exception e) {
            log.error("Check username exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
            return isExist;
        }
    }


    /**
     * Method to take user id from users table by name
     * @param username  - user name
     * @return userId - user id
     */

    @Override
    public long getUserId(String username) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        long userId = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            String findRoleQuery = "SELECT id FROM users WHERE user_name = '" + username + "'";
            resultSet = statement.executeQuery(findRoleQuery);
            if (resultSet.next()) {
                userId = resultSet.getLong("id");
            }
        } catch(Exception e) {
            log.error("Get user id exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String role = "reader";
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            String findRoleQuery = "SELECT role_name FROM user_roles WHERE id = (SELECT role_id FROM user_role_mapping WHERE user_id = '" + id + "')";
            resultSet = statement.executeQuery(findRoleQuery);
            if (resultSet.next()) {
                role = resultSet.getString("role_name");
            }
        } catch(Exception e) {
            log.error("Get user role exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);;
            }
        }
        return role;
    }

    /**
     * Method to set user role at user_roles table
     * @param id  - user id
     */
    @Override
    public void setUserRole(long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int role_id = 2; //"reader"=2
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String setRoleQuery = "INSERT INTO user_role_mapping (user_id, role_id) VALUES(?,?)";
            preparedStatement = connection.prepareStatement(setRoleQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.setInt(2,role_id);
            preparedStatement.execute();
        } catch(Exception e) {
            log.error("Set user role exception",e);
        } finally {
            try {
                if(preparedStatement!=null)
                    preparedStatement.close();
            } catch (SQLException e) {
                log.error("Closing prepared statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
    }
}
