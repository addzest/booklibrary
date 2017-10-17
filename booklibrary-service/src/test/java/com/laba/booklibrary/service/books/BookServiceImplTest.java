package com.laba.booklibrary.service.books;

import com.laba.booklibrary.service.books.model.BookTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @InjectMocks
    private BookService bookService = new BookServiceImpl();

    @Mock
    private BookDao bookDao;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void addBook() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        bookService.addBook(expectedBookTO);
        BookTO actualBook = bookDao.getBookById(1);
        verify(bookDao, times(1)).addBook(expectedBookTO);
        Assert.assertEquals(expectedBookTO, actualBook);
    }

    @Test
    public void removeBookOnHoldTrue() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        when(bookDao.bookOnHold(1)).thenReturn(true);
        boolean result = bookService.removeBook(1);
        verify(bookDao, times(1)).bookOnHold(1);
        verify(bookDao, times(0)).removeBook(1);
        Assert.assertEquals(result, false);
    }

    @Test
    public void removeBookOnHoldFalse() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        when(bookDao.bookOnHold(1)).thenReturn(false);
        boolean result = bookService.removeBook(1);
        verify(bookDao, times(1)).bookOnHold(1);
        verify(bookDao, times(1)).removeBook(1);
        Assert.assertEquals(result, true);
    }

    @Test
    public void updateBook() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title before update");
        expectedBookTO.setCount(0);
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        String titleBeforeUpdate = expectedBookTO.getTitle();
        BookTO actualBookTO = bookDao.getBookById(1);
        actualBookTO.setTitle("Book Title after update");
        bookService.updateBook(actualBookTO);
        String titleAfterUpdate = bookDao.getBookById(1).getTitle();
        Assert.assertNotEquals(titleBeforeUpdate, titleAfterUpdate);
    }

    @Test
    public void getBookById() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        BookTO actualBook = bookService.getBookById(1);
        Assert.assertEquals(expectedBookTO.getId(), actualBook.getId());
    }

    @Test
    public void getBookTOList() throws Exception {
        List<BookTO> expectedBookList = new ArrayList<>();
        BookTO expectedBookTO1 = new BookTO();
        expectedBookTO1.setId(1);
        expectedBookTO1.setTitle("Book Title1");
        expectedBookList.add(expectedBookTO1);
        BookTO expectedBookTO2 = new BookTO();
        expectedBookTO2.setId(2);
        expectedBookTO1.setTitle("Book Title2");
        expectedBookList.add(expectedBookTO2);
        when(bookDao.getBookTOList()).thenReturn(expectedBookList);
        List<BookTO> bookTOS = bookService.getBookTOList();
        Assert.assertEquals(2, bookTOS.size());
    }

    @Test
    public void findBooks() throws Exception {
        List<BookTO> expectedBookList = new ArrayList<>();
        BookTO expectedBookTO1 = new BookTO();
        expectedBookTO1.setId(1);
        expectedBookTO1.setTitle("Book Title1");
        expectedBookList.add(expectedBookTO1);
        String searchRequest = "Title1";
        when(bookDao.findBooks(searchRequest)).thenReturn(expectedBookList);
        List<BookTO> bookTOS = bookService.findBooks(searchRequest);
        Assert.assertEquals(1, bookTOS.size());
    }

    @Test
    public void takeBookCountGreaterZero() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        expectedBookTO.setCount(1);
        expectedBookTO.setUserId(1);
        expectedBookTO.setHoldType("subscription");
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        bookService.takeBook(1, 1, "subscription");
        verify(bookDao, times(1)).getBookById(1);
        verify(bookDao, times(1)).updateBook(expectedBookTO);
        verify(bookDao, times(1)).takeBook(1, 1, "subscription");
        Assert.assertEquals(0, expectedBookTO.getCount());
    }

    @Test
    public void takeBookCountNotGreaterZero() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        expectedBookTO.setCount(0);
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        bookService.takeBook(1, 1, "subscription");
        verify(bookDao, times(1)).getBookById(1);
        verify(bookDao, times(0)).updateBook(expectedBookTO);
        verify(bookDao, times(0)).takeBook(1, 1, "subscription");
        Assert.assertEquals(0, expectedBookTO.getCount());
    }

    @Test
    public void returnBook() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        expectedBookTO.setCount(1);
        expectedBookTO.setOperationId(1);
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        bookService.returnBook(1, 1);
        verify(bookDao, times(1)).getBookById(1);
        verify(bookDao, times(1)).updateBook(expectedBookTO);
        verify(bookDao, times(1)).returnBook(1);
        Assert.assertEquals(2, expectedBookTO.getCount());
    }

    @Test
    public void approveBook() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        expectedBookTO.setApproved(false);
        expectedBookTO.setOperationId(1);
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        bookService.approveBook(1);
        BookTO actualBookTO = bookDao.getBookById(1);
        actualBookTO.setApproved(true);
        Assert.assertEquals(true, expectedBookTO.isApproved());
    }

    @Test
    public void getBooksOnHoldList() throws Exception {
        List<BookTO> expectedBookList = new ArrayList<>();
        BookTO expectedBookTO1 = new BookTO();
        expectedBookTO1.setId(1);
        expectedBookTO1.setTitle("Book Title1");
        expectedBookTO1.setUserId(1);
        expectedBookList.add(expectedBookTO1);
        BookTO expectedBookTO2 = new BookTO();
        expectedBookTO2.setId(2);
        expectedBookTO1.setTitle("Book Title2");
        expectedBookTO2.setUserId(1);
        expectedBookList.add(expectedBookTO2);
        when(bookDao.getBooksOnHoldList(1)).thenReturn(expectedBookList);
        List<BookTO> bookTOS = bookService.getBooksOnHoldList(1);
        Assert.assertEquals(2, bookTOS.size());
    }

    @Test
    public void getAllBooksOnHoldList() throws Exception {
        List<BookTO> expectedBookList = new ArrayList<>();
        BookTO expectedBookTO1 = new BookTO();
        expectedBookTO1.setId(1);
        expectedBookTO1.setTitle("Book Title1");
        expectedBookList.add(expectedBookTO1);
        BookTO expectedBookTO2 = new BookTO();
        expectedBookTO2.setId(2);
        expectedBookTO1.setTitle("Book Title2");
        expectedBookList.add(expectedBookTO2);
        when(bookDao.getAllBooksOnHoldList()).thenReturn(expectedBookList);
        List<BookTO> bookTOS = bookService.getAllBooksOnHoldList();
        Assert.assertEquals(2, bookTOS.size());
    }

}