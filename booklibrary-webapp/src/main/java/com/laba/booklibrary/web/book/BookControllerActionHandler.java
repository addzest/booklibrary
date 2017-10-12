package com.laba.booklibrary.web.book;


import com.laba.booklibrary.service.books.BookService;
import com.laba.booklibrary.service.books.BookServiceImpl;
import com.laba.booklibrary.service.books.BookTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Action handler - to work with operations on books
 */

class BookControllerActionHandler {

    private BookService bookService = new BookServiceImpl();

    void execute(String action, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        switch (action) {
            case "listBooks": {
                listBooks(request, response, session);
                break;
            }
            case "delete": {
                deleteBook(request, response, session);
                break;
            }
            case "edit": {
                editBookPage(request, response, session);
                break;
            }
            case "addBook": {
                addBookPage(request, response, session);
                break;
            }
            case "returnBook": {
                returnBook(request, response, session);
                break;
            }
            case "takeBook": {
                takeBook(request, response, session);
                break;
            }
            case "approveBook": {
                approveBook(request, response, session);
                break;
            }
            case "saveBook": {
                saveBook(request, response, session);
                break;
            }
            default: {
                listBooks(request, response, session);
                break;
            }
        }
    }

    private void addBookPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "librarian")) {
            request.getRequestDispatcher("WEB-INF/jsp/book/bookForm.jsp").forward(request, response);
        } else {
            response.sendRedirect("index?action=listBooks");
        }
    }

    private void saveBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "librarian")) {
            BookTO bookTO = new BookTO();
            String title = request.getParameter("title");
            bookTO.setTitle(title);
            String author = request.getParameter("author");
            bookTO.setAuthor(author);
            int publishYear = Integer.parseInt(request.getParameter("publishyear"));
            bookTO.setPublishYear(publishYear);
            int count = Integer.parseInt(request.getParameter("count"));
            bookTO.setCount(count);
            String description = request.getParameter("description");
            bookTO.setDescription(description);
            String id = request.getParameter("id");
            if (!StringUtils.isEmpty(id) && NumberUtils.isCreatable(id)) {
                Long bookId = Long.parseLong(id);
                BookTO existingBookTO = bookService.getBookById(bookId);
                if (existingBookTO != null) {
                    bookTO.setId(bookId);
                    bookService.updateBook(bookTO);
                    response.sendRedirect("index?action=listBooks");
                }
            } else {
                bookService.addBook(bookTO);
                response.sendRedirect("index?action=listBooks");
            }
        } else {
            response.sendRedirect("index?action=listBooks");
        }
    }

    private void approveBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "librarian") && StringUtils.isNotEmpty(request.getParameter("operationId"))) {
            Long operationId = Long.parseLong(request.getParameter("operationId"));
            bookService.approveBook(operationId);
            if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                int page = Integer.parseInt(request.getParameter("page"));
                response.sendRedirect("index?action=listBooks&page=" + page);
            } else {
                response.sendRedirect("index?action=listBooks");
            }
        } else {
            response.sendRedirect("index?action=listBooks");
        }
    }

    private void takeBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "reader")) {
            Long bookId = Long.parseLong(request.getParameter("id"));
            BookTO bookTO = bookService.getBookById(bookId);
            if (bookTO.getCount()>0){
                Long userId = (Long) session.getAttribute("userId");
                String holdType = request.getParameter("holdType");
                bookService.takeBook(bookId, userId, holdType);
                if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                    int page = Integer.parseInt(request.getParameter("page"));
                    response.sendRedirect("index?action=listBooks&page=" + page);
                } else {
                    response.sendRedirect("index?action=listBooks");
                }
            } else {
                response.sendRedirect("index?action=listBooks");
            }
        } else {
            response.sendRedirect("index?action=listBooks");
        }
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "reader")) {
            Long bookId = Long.parseLong(request.getParameter("id"));
            Long operationId = Long.parseLong(request.getParameter("operationId"));
            bookService.returnBook(bookId, operationId);
            if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                int page = Integer.parseInt(request.getParameter("page"));
                response.sendRedirect("index?action=listBooks&page=" + page);
            } else {
                response.sendRedirect("index?action=listBooks");
            }
        } else {
            response.sendRedirect("index?action=listBooks");
        }
    }

    private void editBookPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "librarian")) {
            String id = request.getParameter("id");
            request.setAttribute("id", id);
            if (!StringUtils.isEmpty(id) && NumberUtils.isCreatable(id)) {
                Long bookId = Long.parseLong(id);
                BookTO bookTO = bookService.getBookById(bookId);
                if (bookTO.getTitle()!= null) {
                    request.setAttribute("bookTO", bookTO);
                    request.getRequestDispatcher("WEB-INF/jsp/book/bookForm.jsp").forward(request, response);
                } else {
                    response.sendRedirect("index?action=listBooks");
                }
            } else {
                response.sendRedirect("index?action=listBooks");
            }
        } else {
            response.sendRedirect("index?action=listBooks");
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "librarian") && StringUtils.isNotEmpty(request.getParameter("id"))) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (bookService.getBookById(id).getTitle() != null){
                if (!bookService.removeBook(id)) {
                    request.setAttribute("deleteError", "deleteError");
                }
                if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                    int page = Integer.parseInt(request.getParameter("page"));
                    request.getRequestDispatcher("index?action=listBooks&page=" + page).forward(request,response);
                } else {
                    request.getRequestDispatcher("index?action=listBooks").forward(request,response);
                }
            }
            else {
                response.sendRedirect("index?action=listBooks");
            }
        } else {
            response.sendRedirect("index?action=listBooks");
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        List<BookTO> bookTOList;
        if (StringUtils.isNotEmpty(request.getParameter("searchRequest"))) {
            request.setAttribute("searchRequest", request.getParameter("searchRequest"));
            bookTOList = bookService.findBooks(request.getParameter("searchRequest"));
        } else {
            bookTOList = bookService.getBookTOList();
        }
        int listLength = bookTOList.size();
        int recordsPerPage = 5;
        int page = 1;
        if (listLength > recordsPerPage) {
            int numberOfPages = (int) Math.ceil((double) listLength / recordsPerPage);
            request.setAttribute("numberOfPages", numberOfPages);
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            bookTOList = bookTOList.subList((page - 1) * recordsPerPage, Math.min(listLength, page * recordsPerPage));
        }
        request.setAttribute("listLength", listLength);
        request.setAttribute("currentPage", page);
        request.setAttribute("bookTOList", bookTOList);
        if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "reader")) {
            Long userId = (Long) session.getAttribute("userId");
            List<BookTO> booksOnHoldList = bookService.getBooksOnHoldList(userId);
            request.setAttribute("onHoldListLength", booksOnHoldList.size());
            request.setAttribute("booksOnHoldList", booksOnHoldList);
        } else if (session != null && StringUtils.equals((String) session.getAttribute("hasRole"), "librarian")) {
            List<BookTO> booksOnHoldList = bookService.getAllBooksOnHoldList();
            request.setAttribute("onHoldListLength", booksOnHoldList.size());
            request.setAttribute("booksOnHoldList", booksOnHoldList);
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
