package com.laba.booklibrary.service.books;

import com.laba.booklibrary.service.books.model.BookOnHoldIdTO;
import com.laba.booklibrary.service.books.model.BookOnHoldTO;
import com.laba.booklibrary.service.books.model.BookTO;
import com.laba.booklibrary.service.users.model.UserTO;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import javax.persistence.Query;
import java.util.List;

import static com.laba.booklibrary.service.connection.HibernateUtil.closeSession;
import static com.laba.booklibrary.service.connection.HibernateUtil.getSession;


/**
 * DAO access layer for books and books_onhold tables
 */
class BookDaoImpl implements BookDao {
    private static final boolean DEFAULT_APPROVE = false;
    private static final boolean APPROVED = true;
    /**
     * Method creates new book in books table
     * @param bookTO - book instance
     */
    @Override
    public void addBook(BookTO bookTO) {
        Session session = getSession();
        session.beginTransaction();
        session.save(bookTO);
        session.getTransaction().commit();
        closeSession();
    }

    /**
     * Method updates  book in books table by id
     * @param bookTO - book instance
     */
    @Override
    public void updateBook(BookTO bookTO) {
        Session session = getSession();
        session.beginTransaction();
        session.update(bookTO);
        session.getTransaction().commit();
        closeSession();
    }


    /**
     * Method removes  book from books table by id
     * @param id - book id
     */
    @Override
    public void removeBook(long id) {
        Session session = getSession();
        session.beginTransaction();
        BookTO bookTO = session.get(BookTO.class, id);
        session.remove(bookTO);
        session.getTransaction().commit();
        closeSession();
    }


    /**
     * Method gets book in books table by id
     *
     * @param id - book id
     * @return bookTO - book instance
     */
    @Override
    public BookTO getBookById(long id) {
        Session session = getSession();
        session.beginTransaction();
        BookTO bookTO = session.get(BookTO.class, id);
        closeSession();
        return bookTO;
    }

    /**
     * Method find books in books table containing searchRequest
     * @param searchRequest - row to find
     * @return list of found books
     */
    @Override
    public List<BookTO> findBooks(String searchRequest, int recordsPerPage, int currentPage, String orderBy) {
        Session session = getSession();
        session.beginTransaction();
        String searchRequestForQuery = "%" + searchRequest + "%";
        Query query = session.createQuery("from BookTO b where lower(concat(b.title,' ',b.author,' ',b.publishYear,' ',b.count,' ',b.description)) like lower(:searchRequest) order by :orderBy", BookTO.class);
        query.setParameter("searchRequest", searchRequestForQuery);
        query.setParameter("orderBy", orderBy);
        query.setFirstResult((currentPage - 1) * recordsPerPage);
        query.setMaxResults(recordsPerPage);
        List<BookTO> foundBooks = query.getResultList();
        closeSession();
        return foundBooks;
    }

    @Override
    public int getBookTOCount() {
        Session session = getSession();
        session.beginTransaction();
        Query getCount = session.createNativeQuery("select count(*) as count from books").addScalar("count", IntegerType.INSTANCE);
        int bookCount = (int) getCount.getSingleResult();
        closeSession();
        return bookCount;
    }

    @Override
    public int getBookTOCountWithSearchRequest(String searchRequest) {
        Session session = getSession();
        session.beginTransaction();
        String searchRequestForQuery = "%" + searchRequest + "%";
        Query getCountWithSearchRequest = session.createNativeQuery("select count(*) as count from books where lower(concat(title,' ',author,' ',publish_year,' ',count,' ',description)) like lower(:searchRequest)").addScalar("count", IntegerType.INSTANCE);
        getCountWithSearchRequest.setParameter("searchRequest", searchRequestForQuery);
        int bookCountWithSearchRequest = (int) getCountWithSearchRequest.getSingleResult();
        closeSession();
        return bookCountWithSearchRequest;
    }


    /**
     *  Method to get book list from table books
     * @return list of books
     */
    @Override
    public List<BookTO> getBookTOList(int recordsPerPage, int currentPage, String orderBy) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createNativeQuery("select * from books order by " + orderBy + " asc ", BookTO.class);
        query.setFirstResult((currentPage - 1) * recordsPerPage);
        query.setMaxResults(recordsPerPage);
        List<BookTO> bookTOList = query.getResultList();
        closeSession();
        return bookTOList;
    }


    /**
     * Method moves book in books_onhold table
     * @param bookId - book id
     * @param userId - user id, trying to take book
     * @param holdType - type of taking book
     */
    @Override
    public void takeBook(long bookId, long userId, String holdType){
        Session session = getSession();
        session.beginTransaction();
        BookOnHoldTO bookOnHoldTO = new BookOnHoldTO();
        bookOnHoldTO.setBookTO(session.get(BookTO.class, bookId));
        bookOnHoldTO.setUserTO(session.get(UserTO.class, userId));
        bookOnHoldTO.setHoldType(holdType);
        bookOnHoldTO.setApproved(DEFAULT_APPROVE);
        session.save(bookOnHoldTO);
        session.getTransaction().commit();
        closeSession();
    }


    /**
     * Method removes book from books_onhold table
     * @param bookId - book id
     * @param userId - user id
     */
    @Override
    public void returnBook(long bookId, long userId) {
        Session session = getSession();
        session.beginTransaction();
        BookOnHoldIdTO bookOnHoldIdTO = new BookOnHoldIdTO();
        bookOnHoldIdTO.setUserTO(session.get(UserTO.class, userId));
        bookOnHoldIdTO.setBookTO(session.get(BookTO.class, bookId));
        session.remove(session.get(BookOnHoldTO.class, bookOnHoldIdTO));
        session.getTransaction().commit();
        closeSession();
    }

    /**
     * Method sets approval of operation in books_onhold table by operation id
     * @param bookId - book id
     * @param userId - user id
     */

    @Override
    public void approveBook(long bookId, long userId) {
        Session session = getSession();
        session.beginTransaction();
        BookOnHoldIdTO bookOnHoldIdTO = new BookOnHoldIdTO();
        bookOnHoldIdTO.setUserTO(session.get(UserTO.class, userId));
        bookOnHoldIdTO.setBookTO(session.get(BookTO.class, bookId));
        BookOnHoldTO bookOnHoldTO = session.get(BookOnHoldTO.class, bookOnHoldIdTO);
        bookOnHoldTO.setApproved(APPROVED);
        session.update(bookOnHoldTO);
        session.getTransaction().commit();
        closeSession();
    }

    /**
     * Method checks if book is on hold in books_onhold table by id
     * @param id book id
     * @return <code>true</code> if the book is in book_onhold table;
     *                  <code>false</code> otherwise.
     */
    @Override
    public boolean bookOnHold(long id) {
        boolean bookOnHold = true;
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM BookOnHoldTO b WHERE b.pk.bookTO.id= :id");
        query.setParameter("id", id);
        if (query.getResultList().isEmpty()) {
            bookOnHold = false;
        }
        closeSession();
        return bookOnHold;
    }

    /**
     * Method takes books on hold list by user id
     * @param userId - user id
     * @return List<BookOnHoldTO> - list of books taken by user
     */
    @Override
    public List<BookOnHoldTO> getBooksOnHoldList(long userId) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("from BookOnHoldTO where pk.userTO.id= :userId", BookOnHoldTO.class);
        query.setParameter("userId", userId);
        List<BookOnHoldTO> booksOnHoldList = query.getResultList();
        closeSession();
        return booksOnHoldList;
    }

    /**
     * Method takes  all books on hold from books_onhold table
     * no params
     *  @return List<BookTO> - list of books instances from books_onhold
     */
    @Override
    public List<BookOnHoldTO> getAllBooksOnHoldList() {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("from BookOnHoldTO", BookOnHoldTO.class);
        List<BookOnHoldTO> booksOnHoldList = query.getResultList();
        closeSession();
        return booksOnHoldList;
    }
}
