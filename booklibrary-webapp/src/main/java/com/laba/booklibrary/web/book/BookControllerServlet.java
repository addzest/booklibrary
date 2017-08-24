package com.laba.booklibrary.web.book;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * controller servlet to provide operations with books
 * get - saving book (new or update)
 * post - delete, take, approve, etc.
 */

public class BookControllerServlet extends HttpServlet {

    private BookControllerActionHandler bookControllerActionHandler = new BookControllerActionHandler();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        bookControllerActionHandler.execute("saveBook", request, response, session);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (StringUtils.isEmpty(action)) {
            action = "listBooks";
        }
        request.setAttribute("action", action);
        HttpSession session = request.getSession();
        bookControllerActionHandler.execute(action, request, response, session);
    }
}
