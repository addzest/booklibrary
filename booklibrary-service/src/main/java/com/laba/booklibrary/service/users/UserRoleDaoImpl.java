package com.laba.booklibrary.service.users;

import com.laba.booklibrary.service.connection.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * DAO access layer for user_role, user_role_mapping tables
 */
public class UserRoleDaoImpl implements UserRoleDao{
    private static final Logger log = Logger.getLogger(UserRoleDaoImpl.class);

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
        String role = "reader";
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            String findRoleQuery = "SELECT role_name FROM user_roles WHERE id = (SELECT role_id FROM user_role_mapping WHERE user_id = '" + id + "')";
            ResultSet resultSet = statement.executeQuery(findRoleQuery);
            if (resultSet.next()) {
                role = resultSet.getString("role_name");
            }
        } catch(Exception e) {
            log.error("Get user role exception",e);
        } finally {
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
