package com.laba.booklibrary.service.books;

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
     * @param bookTO
     */
    @Override
    public void addBook(BookTO bookTO) {
        bookDao.addBook(bookTO);
        log.info("New book added " + bookTO);
    }

    /**
     * Method for removing book. When count>0, decrease count by 1.
     * Whem count  = 0, checking if book is in onhold list, preventing from deleting
     * if book is not in onhold list,delete it.
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
    public List<BookTO> getBookTOList() {
        return bookDao.getBookTOList();
    }

    /**
     * Method for finding book by search Request
     * @param searchRequest - request to find book
     * @return list of books, that containing searchRequest
     */
    @Override
    public List<BookTO> findBooks(String searchRequest) {
        return bookDao.findBooks(searchRequest);
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
     * @param operation_id - id of operation (taking book)
     */
    @Override
    public void returnBook(long bookId, long operation_id) {
        BookTO bookTO = bookDao.getBookById(bookId);
        bookTO.setCount(bookTO.getCount() + 1);
        bookDao.updateBook(bookTO);
        bookDao.returnBook(operation_id);
        log.info("Book " + bookTO + " returned");
    }

    /**
     * Method to approve taking the book
     * @param operationId - id of operation (taking the book)
     */
    @Override
    public void approveBook(long operationId) {
        bookDao.approveBook(operationId);
        log.info("Operation " + operationId + " approved");
    }

    /**
     * Method to get books on hold list by user
     * @param userId - user id
     * @return list of books, that where taken by user
     */
    @Override
    public List<BookTO> getBooksOnHoldList(long userId) {
        return bookDao.getBooksOnHoldList(userId);
    }

    /**
     * Method to get all books on hold
     * @return list of all books on hold
     */
    public List<BookTO> getAllBooksOnHoldList() {
        return bookDao.getAllBooksOnHoldList();
    }
}
