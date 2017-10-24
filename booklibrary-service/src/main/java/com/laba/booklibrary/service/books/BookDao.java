package com.laba.booklibrary.service.books;

import com.laba.booklibrary.service.books.model.BookOnHoldTO;
import com.laba.booklibrary.service.books.model.BookTO;

import java.util.List;


interface BookDao {

    void addBook(BookTO bookTO);

    void removeBook(long id);

    void updateBook(BookTO bookTO);

    BookTO  getBookById(long id);

    List<BookTO> findBooks(String searchRequest, int recordsPerPage, int currentPage, String orderBy);

    int getBookTOCount();

    int getBookTOCountWithSearchRequest(String searchRequest);

    List<BookTO> getBookTOList(int recordsPerPage, int currentPage, String orderBy);

    List<BookOnHoldTO> getBooksOnHoldList(long userId);

    List<BookOnHoldTO> getAllBooksOnHoldList();

    boolean bookOnHold (long id);
    void takeBook(long bookId, long userId, String holdType);

    void returnBook(long bookId, long userId);

    void approveBook(long bookId, long userId);
}
