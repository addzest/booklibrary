package com.laba.booklibrary.service.books;

import java.util.List;


interface BookDao {

    void addBook(BookTO bookTO);

    void removeBook(long id);

    void updateBook(BookTO bookTO);

    BookTO  getBookById(long id);
    List<BookTO> findBooks(String searchRequest);

    List<BookTO> getBookTOList();

    List<BookTO> getBooksOnHoldList(long userId);

    List<BookTO> getAllBooksOnHoldList();

    boolean bookOnHold (long id);
    void takeBook(long bookId, long userId, String holdType);
    void returnBook(long operationId);

    void approveBook(long operationId);
}
