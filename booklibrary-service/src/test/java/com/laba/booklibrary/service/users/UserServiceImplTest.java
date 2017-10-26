package com.laba.booklibrary.service.users;

import com.laba.booklibrary.service.users.model.UserTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addUser() throws Exception {
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("username1");
        when(userDao.getUserTOById(1)).thenReturn(expectedUserTO);
        userService.addUser(expectedUserTO);
        UserTO actualUserTO = userService.getUserById(1);
        verify(userDao, times(1)).addUser(expectedUserTO);
        Assert.assertEquals(expectedUserTO, actualUserTO);
    }


    @Test
    public void checkUserIfExist() {
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("username1");
        when(userDao.checkUsername(expectedUserTO.getUsername())).thenReturn(true);
        boolean result = userService.checkUser(expectedUserTO.getUsername());
        verify(userDao, times(1)).checkUsername(expectedUserTO.getUsername());
        Assert.assertEquals(result, true);
    }

    @Test
    public void checkUserIfNotExist() {
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("username1");
        when(userDao.checkUsername(expectedUserTO.getUsername())).thenReturn(false);
        boolean result = userService.checkUser(expectedUserTO.getUsername());
        verify(userDao, times(1)).checkUsername(expectedUserTO.getUsername());
        Assert.assertEquals(result, false);
    }

    @Test
    public void validateUserIsValidated() {
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("username1");
        expectedUserTO.setPassword("password1");
        when(userDao.validateUser("username1", "password1")).thenReturn(true);
        boolean result = userService.validateUser(expectedUserTO.getUsername(), expectedUserTO.getPassword());
        verify(userDao, times(1)).validateUser(expectedUserTO.getUsername(), expectedUserTO.getPassword());
        Assert.assertEquals(result, true);
    }

    @Test
    public void validateUserIsNotValidated() {
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("username1");
        expectedUserTO.setPassword("password1");
        when(userDao.validateUser("username1", "password1")).thenReturn(false);
        boolean result = userService.validateUser(expectedUserTO.getUsername(), expectedUserTO.getPassword());
        verify(userDao, times(1)).validateUser(expectedUserTO.getUsername(), expectedUserTO.getPassword());
        Assert.assertEquals(result, false);
    }

    @Test
    public void getUserRole() {
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("username1");
        when(userDao.getUserRole(1)).thenReturn("librarian");
        String userRole = userService.getUserRole(1);
        verify(userDao, times(1)).getUserRole(1);
        Assert.assertEquals(userRole, "librarian");
    }

    @Test
    public void getUserById() {
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("username1");
        when(userDao.getUserTOById(1)).thenReturn(expectedUserTO);
        UserTO actualUserTO = userService.getUserById(1);
        Assert.assertEquals(expectedUserTO.getId(), actualUserTO.getId());
    }

    @Test
    public void getUserId() {
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("username1");
        when(userDao.getUserId(expectedUserTO.getUsername())).thenReturn(expectedUserTO.getId());
        long actualUserId = userService.getUserId("username1");
        Assert.assertEquals(expectedUserTO.getId(), actualUserId);
    }
}
