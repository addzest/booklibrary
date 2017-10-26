package com.laba.booklibrary.service.books;

import com.laba.booklibrary.service.books.model.BookOnHoldIdTO;
import com.laba.booklibrary.service.books.model.BookOnHoldTO;
import com.laba.booklibrary.service.books.model.BookTO;
import com.laba.booklibrary.service.users.UserDao;
import com.laba.booklibrary.service.users.model.UserTO;
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

    @Mock
    private UserDao userDao;

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
        when(bookDao.getBookTOList(5, 1, "title")).thenReturn(expectedBookList);
        List<BookTO> bookTOS = bookService.getBookTOList(5, 1, "title");
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
        when(bookDao.findBooks(searchRequest, 5, 1, "title")).thenReturn(expectedBookList);
        List<BookTO> bookTOS = bookService.findBooks(searchRequest, 5, 1, "title");
        Assert.assertEquals(1, bookTOS.size());
    }

    @Test
    public void takeBookCountGreaterZero() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        expectedBookTO.setCount(1);
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
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("user1");
        BookOnHoldIdTO bookOnHoldIdTO = new BookOnHoldIdTO();
        bookOnHoldIdTO.setBookTO(expectedBookTO);
        bookOnHoldIdTO.setUserTO(expectedUserTO);
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        when(userDao.getUserTOById(1)).thenReturn(expectedUserTO);
        bookService.returnBook(1, 1);
        verify(bookDao, times(1)).getBookById(1);
        verify(bookDao, times(1)).updateBook(expectedBookTO);
        verify(bookDao, times(1)).returnBook(bookOnHoldIdTO);
        Assert.assertEquals(2, expectedBookTO.getCount());
    }

    @Test
    public void approveBook() throws Exception {
        BookTO expectedBookTO = new BookTO();
        expectedBookTO.setId(1);
        expectedBookTO.setTitle("Book Title1");
        UserTO expectedUserTO = new UserTO();
        expectedUserTO.setId(1);
        expectedUserTO.setUsername("user1");
        BookOnHoldTO expectedBookOnHoldTO = new BookOnHoldTO();
        expectedBookOnHoldTO.setBookTO(expectedBookTO);
        expectedBookOnHoldTO.setUserTO(expectedUserTO);
        expectedBookOnHoldTO.setApproved(false);
        when(bookDao.getBookById(1)).thenReturn(expectedBookTO);
        when(userDao.getUserTOById(1)).thenReturn(expectedUserTO);
        bookService.approveBook(1, 1);
        expectedBookOnHoldTO.setApproved(true);
        verify(bookDao, times(1)).getBookById(1);
        verify(userDao, times(1)).getUserTOById(1);
        verify(bookDao, times(1)).approveBook(expectedBookOnHoldTO.getPk());
        Assert.assertEquals(true, expectedBookOnHoldTO.isApproved());
    }

    @Test
    public void getBooksOnHoldList() throws Exception {
        List<BookOnHoldTO> expectedBookOnHoldTOs = new ArrayList<>();
        BookTO expectedBookTO1 = new BookTO();
        expectedBookTO1.setId(1);
        expectedBookTO1.setTitle("Book Title1");
        UserTO expectedUserTO1 = new UserTO();
        expectedUserTO1.setId(1);
        expectedUserTO1.setUsername("user1");
        BookOnHoldTO expectedBookOnHoldTO1 = new BookOnHoldTO();
        expectedBookOnHoldTO1.setBookTO(expectedBookTO1);
        expectedBookOnHoldTO1.setUserTO(expectedUserTO1);
        expectedBookOnHoldTOs.add(expectedBookOnHoldTO1);
        BookTO expectedBookTO2 = new BookTO();
        expectedBookTO2.setId(2);
        expectedBookTO1.setTitle("Book Title2");
        BookOnHoldTO expectedBookOnHoldTO2 = new BookOnHoldTO();
        expectedBookOnHoldTO2.setBookTO(expectedBookTO2);
        expectedBookOnHoldTO2.setUserTO(expectedUserTO1);
        expectedBookOnHoldTOs.add(expectedBookOnHoldTO2);
        when(bookDao.getBooksOnHoldList(1)).thenReturn(expectedBookOnHoldTOs);
        List<BookOnHoldTO> bookOnHoldTOs = bookService.getBooksOnHoldList(1);
        Assert.assertEquals(2, bookOnHoldTOs.size());
    }

    @Test
    public void getAllBooksOnHoldList() throws Exception {
        List<BookOnHoldTO> expectedBookOnHoldToList = new ArrayList<>();
        BookTO expectedBookTO1 = new BookTO();
        expectedBookTO1.setId(1);
        expectedBookTO1.setTitle("Book Title1");
        UserTO expectedUserTO1 = new UserTO();
        expectedUserTO1.setId(1);
        expectedUserTO1.setUsername("user1");
        BookOnHoldTO expectedBookOnHoldTO1 = new BookOnHoldTO();
        expectedBookOnHoldTO1.setBookTO(expectedBookTO1);
        expectedBookOnHoldTO1.setUserTO(expectedUserTO1);
        expectedBookOnHoldToList.add(expectedBookOnHoldTO1);
        BookTO expectedBookTO2 = new BookTO();
        expectedBookTO1.setId(2);
        expectedBookTO1.setTitle("Book Title2");
        UserTO expectedUserTO2 = new UserTO();
        expectedUserTO1.setId(2);
        expectedUserTO1.setUsername("user2");
        BookOnHoldTO expectedBookOnHoldTO2 = new BookOnHoldTO();
        expectedBookOnHoldTO2.setBookTO(expectedBookTO2);
        expectedBookOnHoldTO2.setUserTO(expectedUserTO2);
        expectedBookOnHoldToList.add(expectedBookOnHoldTO2);
        when(bookDao.getAllBooksOnHoldList()).thenReturn(expectedBookOnHoldToList);
        List<BookOnHoldTO> bookOnHoldTOs = bookService.getAllBooksOnHoldList();
        Assert.assertEquals(2, bookOnHoldTOs.size());
    }

}