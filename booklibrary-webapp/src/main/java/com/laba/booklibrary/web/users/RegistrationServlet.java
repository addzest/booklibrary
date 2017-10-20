package com.laba.booklibrary.web.users;

import com.laba.booklibrary.service.users.UserService;
import com.laba.booklibrary.service.users.UserServiceImpl;
import com.laba.booklibrary.service.users.model.UserTO;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * registration servlet
 * doGet - forwarding to registration form
 * doPost - adding user, validation
 */
public class RegistrationServlet extends HttpServlet{
    private static final String HAS_ROLE = "hasRole";
    private static final String START_PAGE = "/index?action=listBooks";
    private static UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (StringUtils.isNotEmpty((CharSequence) session.getAttribute(HAS_ROLE))) {
            response.sendRedirect(START_PAGE);
        }else {
            request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (StringUtils.isNotEmpty((CharSequence) session.getAttribute(HAS_ROLE))) {
            response.sendRedirect(START_PAGE);
        } else {
            UserTO userTO = new UserTO();
            String username = request.getParameter("username");
            userTO.setUsername(username);
            String password = request.getParameter("password");
            userTO.setPassword(password);
            String firstName = request.getParameter("firstname");
            userTO.setFirstName(firstName);
            String lastName = request.getParameter("lastname");
            userTO.setLastName(lastName);
            String email = request.getParameter("email");
            userTO.setEmail(email);
            if (userService.checkUser(userTO.getUsername())) {
                request.setAttribute("userExistError", "isExist");
                request.setAttribute("userTO", userTO);
                request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
            } else {
                userService.addUser(userTO);
                session.setAttribute("name", username);
                session.setAttribute("password", password);
                session.setAttribute("userId", userService.getUserId(username));
                session.setAttribute(HAS_ROLE, userService.getUserRole(userService.getUserId(username)));
                response.sendRedirect(START_PAGE);
            }
        }
    }
}
