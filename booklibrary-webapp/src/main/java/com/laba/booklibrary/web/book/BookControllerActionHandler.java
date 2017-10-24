package com.laba.booklibrary.web.book;


import com.laba.booklibrary.service.books.BookService;
import com.laba.booklibrary.service.books.BookServiceImpl;
import com.laba.booklibrary.service.books.model.BookOnHoldTO;
import com.laba.booklibrary.service.books.model.BookTO;
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
    private static final String HAS_ROLE = "hasRole";
    private static final String LIBRARIAN = "librarian";
    private static final String START_PAGE = "/index?action=listBooks";
    private static final String USER_ID = "userId";
    private static final String ACTUAL_PAGE = "/index?action=listBooks&page=";
    private static final String READER = "reader";
    private static final String SEARCH_REQUEST = "searchRequest";


    private BookService bookService = new BookServiceImpl();

    void execute(String action, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        switch (action) {
            case "listBooks":
                listBooks(request, response, session);
                break;
            case "delete":
                deleteBook(request, response, session);
                break;
            case "edit":
                editBookPage(request, response, session);
                break;
            case "addBook":
                addBookPage(request, response, session);
                break;
            case "returnBook":
                returnBook(request, response, session);
                break;
            case "takeBook":
                takeBook(request, response, session);
                break;
            case "approveBook":
                approveBook(request, response, session);
                break;
            case "saveBook":
                saveBook(request, response, session);
                break;
            default:
                listBooks(request, response, session);
                break;
        }
    }

    private void addBookPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), LIBRARIAN)) {
            request.getRequestDispatcher("WEB-INF/jsp/book/bookForm.jsp").forward(request, response);
        } else {
            response.sendRedirect(START_PAGE);
        }
    }

    private void saveBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), LIBRARIAN)) {
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
                    response.sendRedirect(START_PAGE);
                }
            } else {
                bookService.addBook(bookTO);
                response.sendRedirect(START_PAGE);
            }
        } else {
            response.sendRedirect(START_PAGE);
        }
    }

    private void approveBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), LIBRARIAN) && StringUtils.isNotEmpty(request.getParameter("bookId")) && StringUtils.isNotEmpty(request.getParameter(USER_ID))) {
            long bookId = Long.parseLong(request.getParameter("bookId"));
            long userId = Long.parseLong(request.getParameter(USER_ID));
            bookService.approveBook(bookId, userId);
            if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                int page = Integer.parseInt(request.getParameter("page"));
                response.sendRedirect(ACTUAL_PAGE + page);
            } else {
                response.sendRedirect(START_PAGE);
            }
        } else {
            response.sendRedirect(START_PAGE);
        }
    }

    private void takeBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), READER)) {
            Long bookId = Long.parseLong(request.getParameter("id"));
            BookTO bookTO = bookService.getBookById(bookId);
            if (bookTO.getCount()>0){
                Long userId = (Long) session.getAttribute(USER_ID);
                String holdType = request.getParameter("holdType");
                bookService.takeBook(bookId, userId, holdType);
                if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                    int page = Integer.parseInt(request.getParameter("page"));
                    response.sendRedirect(ACTUAL_PAGE + page);
                } else {
                    response.sendRedirect(START_PAGE);
                }
            } else {
                response.sendRedirect(START_PAGE);
            }
        } else {
            response.sendRedirect(START_PAGE);
        }
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), READER)) {
            Long bookId = Long.parseLong(request.getParameter("id"));
            Long userId = Long.parseLong(request.getParameter(USER_ID));
            bookService.returnBook(bookId, userId);
            if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                int page = Integer.parseInt(request.getParameter("page"));
                response.sendRedirect(ACTUAL_PAGE + page);
            } else {
                response.sendRedirect(START_PAGE);
            }
        } else {
            response.sendRedirect(START_PAGE);
        }
    }

    private void editBookPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), LIBRARIAN)) {
            String id = request.getParameter("id");
            request.setAttribute("id", id);
            if (!StringUtils.isEmpty(id) && NumberUtils.isCreatable(id)) {
                Long bookId = Long.parseLong(id);
                BookTO bookTO = bookService.getBookById(bookId);
                if (bookTO.getTitle()!= null) {
                    request.setAttribute("bookTO", bookTO);
                    request.getRequestDispatcher("WEB-INF/jsp/book/bookForm.jsp").forward(request, response);
                } else {
                    response.sendRedirect(START_PAGE);
                }
            } else {
                response.sendRedirect(START_PAGE);
            }
        } else {
            response.sendRedirect(START_PAGE);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), LIBRARIAN) && StringUtils.isNotEmpty(request.getParameter("id"))) {
            long id = Long.parseLong(request.getParameter("id"));
            if (bookService.getBookById(id).getTitle() != null){
                if (!bookService.removeBook(id)) {
                    request.setAttribute("deleteError", "deleteError");
                }
                if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                    int page = Integer.parseInt(request.getParameter("page"));
                    request.getRequestDispatcher(ACTUAL_PAGE + page).forward(request, response);
                } else {
                    request.getRequestDispatcher(START_PAGE).forward(request, response);
                }
            }
            else {
                response.sendRedirect(START_PAGE);
            }
        } else {
            response.sendRedirect(START_PAGE);
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        List<BookTO> bookTOList;
        int recordsPerPage = 5;
        int page = 1;
        int bookCount;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (StringUtils.isNotEmpty(request.getParameter(SEARCH_REQUEST))) {
            request.setAttribute(SEARCH_REQUEST, request.getParameter(SEARCH_REQUEST));
            bookCount = bookService.getBookTOCountWithSearchRequest(request.getParameter(SEARCH_REQUEST));
            bookTOList = bookService.findBooks(request.getParameter(SEARCH_REQUEST), recordsPerPage, page);
        } else {
            bookCount = bookService.getBookTOCount();
            bookTOList = bookService.getBookTOList(recordsPerPage, page);
        }
        if (bookCount > recordsPerPage) {
            int numberOfPages = (int) Math.ceil((double) bookCount / recordsPerPage);
            request.setAttribute("numberOfPages", numberOfPages);
        }
        request.setAttribute("listLength", bookCount);
        request.setAttribute("currentPage", page);
        request.setAttribute("bookTOList", bookTOList);
        if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), READER)) {
            Long userId = (Long) session.getAttribute(USER_ID);
            List<BookOnHoldTO> booksOnHoldList = bookService.getBooksOnHoldList(userId);
            request.setAttribute("onHoldListLength", booksOnHoldList.size());
            request.setAttribute("booksOnHoldList", booksOnHoldList);
        } else if (StringUtils.equals((String) session.getAttribute(HAS_ROLE), LIBRARIAN)) {
            List<BookOnHoldTO> booksOnHoldList = bookService.getAllBooksOnHoldList();
            request.setAttribute("onHoldListLength", booksOnHoldList.size());
            request.setAttribute("booksOnHoldList", booksOnHoldList);
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
