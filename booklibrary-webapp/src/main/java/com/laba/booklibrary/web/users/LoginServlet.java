package com.laba.booklibrary.web.users;

import com.laba.booklibrary.service.users.UserService;
import com.laba.booklibrary.service.users.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * login  servlet - sign in
 * doGet - going to login page,checking if already logged in
 * doPost  - validate user
 */

public class LoginServlet extends HttpServlet {
    private static final String HAS_ROLE = "hasRole";
    private static final String START_PAGE = "/index?action=listBooks";
    private static UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (StringUtils.isNotEmpty((CharSequence) session.getAttribute(HAS_ROLE))) {
            response.sendRedirect(START_PAGE);
        }else {
            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request,response);
        }
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (StringUtils.isNotEmpty((CharSequence) session.getAttribute(HAS_ROLE))) {
            response.sendRedirect(START_PAGE);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (userService.validateUser(username, password)) {
                String roleName = userService.getUserRole(userService.getUserId(username));
                session.setAttribute("name", username);
                session.setAttribute("password", password);
                session.setAttribute("userId", userService.getUserId(username));
                session.setAttribute(HAS_ROLE, roleName);
                response.sendRedirect(START_PAGE);
            } else {
                request.setAttribute("loginError", "loginError");
                request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
            }
        }
    }
}
