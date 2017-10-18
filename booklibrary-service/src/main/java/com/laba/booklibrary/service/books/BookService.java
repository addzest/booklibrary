package com.laba.booklibrary.service.books;

import com.laba.booklibrary.service.books.model.BookOnHoldTO;
import com.laba.booklibrary.service.books.model.BookTO;

import java.util.List;


public interface BookService {
    void addBook(BookTO bookTO);
    boolean removeBook(long id);
    void updateBook(BookTO bookTO);
    BookTO getBookById(long id);
    List<BookTO> findBooks(String searchRequest);
    List<BookTO> getBookTOList();

    List<BookOnHoldTO> getBooksOnHoldList(long userId);

    List<BookOnHoldTO> getAllBooksOnHoldList();
    void takeBook(long bookId, long userId, String holdType);

    void returnBook(long bookId, long userId);

    void approveBook(long bookId, long userId);
}
