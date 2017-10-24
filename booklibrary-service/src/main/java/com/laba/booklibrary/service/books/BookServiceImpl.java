package com.laba.booklibrary.service.books;

import com.laba.booklibrary.service.books.model.BookOnHoldTO;
import com.laba.booklibrary.service.books.model.BookTO;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Service for working with books (CRUD, find etc.)
 */
public class BookServiceImpl implements BookService {

    private static final Logger log = Logger.getLogger(BookServiceImpl.class);
    private BookDao bookDao = new BookDaoImpl();

    /**
     * Method for adding book
     * @param bookTO - book object
     */
    @Override
    public void addBook(BookTO bookTO) {
        bookDao.addBook(bookTO);
        log.info("New book added " + bookTO);
    }

    /**
     * Method for removing book.
     * If book is in onhold list, preventing from deleting.
     * @param id - book id
     */
    @Override
    public boolean removeBook(long id) {
        if (bookDao.bookOnHold(id)) {
            log.info("Book on hold. Couldn't be deleted.");
            return false;
        } else {
            bookDao.removeBook(id);
            log.info("Deleted book with id " + id);
            return true;
            }
        }

    /**
     * Method for updating info about the book.
     * @param bookTO - book instance
     */
    @Override
    public void updateBook(BookTO bookTO) {
        bookDao.updateBook(bookTO);
        log.info("Updated " + bookTO);
    }

    /**
     *  Method for finding the book by id
     * @param id - book id
     * @return bookTO - book instance
     */
    @Override
    public BookTO getBookById(long id) {
        return bookDao.getBookById(id);
    }

    /**
     * Method for getting book list
     * @return book list
     */
    @Override
    public List<BookTO> getBookTOList(int recordsPerPage, int currentPage, String orderBy) {
        return bookDao.getBookTOList(recordsPerPage, currentPage, orderBy);
    }

    @Override
    public int getBookTOCount() {
        return bookDao.getBookTOCount();
    }

    @Override
    public int getBookTOCountWithSearchRequest(String searchRequest) {
        return bookDao.getBookTOCountWithSearchRequest(searchRequest);
    }

    /**
     * Method for finding book by search Request
     * @param searchRequest - request to find book
     * @return list of books, that containing searchRequest
     */
    @Override
    public List<BookTO> findBooks(String searchRequest, int recordsPerPage, int currentPage, String orderBy) {
        return bookDao.findBooks(searchRequest, recordsPerPage, currentPage, orderBy);
    }

    /**
     * Method to take the book from booklist by user. If count of books  = 0, it couldn't be taken.
     * @param bookId - book id
     * @param userId - user id
     * @param holdType - hold type(subscription or reading room)
     */
    @Override
    public void takeBook(long bookId, long userId, String holdType) {
        BookTO bookTO = bookDao.getBookById(bookId);
        if (bookTO.getCount() > 0) {
            bookTO.setCount(bookTO.getCount() - 1);
            bookDao.updateBook(bookTO);
            bookDao.takeBook(bookId, userId, holdType);
            log.info("Book " + bookTO + " taken by user " + userId);
        } else {
            log.info("User " + userId + " tried to take book " + bookTO + " with count = 0");
        }
    }

    /**
     * Method to return the book from onhold list to library list
     * @param bookId - book id
     * @param userId - id of operation (taking book)
     */
    @Override
    public void returnBook(long bookId, long userId) {
        BookTO bookTO = bookDao.getBookById(bookId);
        bookTO.setCount(bookTO.getCount() + 1);
        bookDao.updateBook(bookTO);
        bookDao.returnBook(bookId, userId);
        log.info("Book " + bookTO + " returned");
    }

    /**
     * Method to approve taking the book
     * @param bookId - book id
     * @param userId - id of operation (taking book)
     */
    @Override
    public void approveBook(long bookId, long userId) {
        bookDao.approveBook(bookId, userId);
        log.info("Operation " + bookId + userId + " approved");
    }

    /**
     * Method to get books on hold list by user
     * @param userId - user id
     * @return list of books, that where taken by user
     */
    @Override
    public List<BookOnHoldTO> getBooksOnHoldList(long userId) {
        return bookDao.getBooksOnHoldList(userId);
    }

    /**
     * Method to get all books on hold
     * @return list of all books on hold
     */
    public List<BookOnHoldTO> getAllBooksOnHoldList() {
        return bookDao.getAllBooksOnHoldList();
    }
}
