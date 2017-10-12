package com.laba.booklibrary.service.books;

import com.laba.booklibrary.service.connection.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * DAO access layer for books and books_onhold tables
 */
class BookDaoImpl implements BookDao {
    private static final Logger log = Logger.getLogger(BookDaoImpl.class);

    /**
     * Method creates new book in books table
     * @param bookTO - book instance
     */
    @Override
    public void addBook(BookTO bookTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String addQuery = "INSERT INTO books (title, author, publish_year, count, description) VALUES(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(addQuery);
            preparedStatement.setString(1,bookTO.getTitle());
            preparedStatement.setString(2,bookTO.getAuthor());
            preparedStatement.setInt(3,bookTO.getPublishYear());
            preparedStatement.setInt(4,bookTO.getCount());
            preparedStatement.setString(5,bookTO.getDescription());
            preparedStatement.execute();
        } catch(Exception e) {
            log.error("Add book exception",e);
        } finally {
            try {
                if(preparedStatement!=null)
                    preparedStatement.close();
            } catch (SQLException e) {
                log.error("Closing prepared statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
    }

    /**
     * Method removes  book from books table by id
     * @param id - book id
     */
    @Override
    public void removeBook(long id) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String deleteQuery = "DELETE FROM books WHERE id= '"+id + "'";
            statement = connection.createStatement();
            statement.executeUpdate(deleteQuery);
        } catch(Exception e) {
            log.error("Remove book exception",e);
        } finally {
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
    }

    /**
     * Method updates  book in books table by id
     * @param bookTO - book instance
     */
    @Override
    public void updateBook(BookTO bookTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String updateQuery = "UPDATE books SET title = ?, author = ?, publish_year = ?, count = ?, description = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1,bookTO.getTitle());
            preparedStatement.setString(2,bookTO.getAuthor());
            preparedStatement.setInt(3,bookTO.getPublishYear());
            preparedStatement.setInt(4,bookTO.getCount());
            preparedStatement.setString(5,bookTO.getDescription());
            preparedStatement.setLong(6, bookTO.getId());
            preparedStatement.execute();
        } catch(Exception e) {
            log.error("Update book exception",e);
        } finally {
            try {
                if(preparedStatement!=null)
                    preparedStatement.close();
            } catch (SQLException e) {
                log.error("Closing prepared statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
    }

    /**
     * Method gets book in books table by id
     *
     * @param id - book id
     * @return bookTO - book instance
     */
    @Override
    public BookTO getBookById(long id) {
        BookTO bookTO = new BookTO();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String getByIdQuery = "SELECT title, author, publish_year, count, description FROM books WHERE id='"+id+"'";
            statement = connection.createStatement();
            resultSet =statement.executeQuery(getByIdQuery);
            while (resultSet.next()) {
                bookTO.setId(id);
                bookTO.setTitle(resultSet.getString("title"));
                bookTO.setAuthor(resultSet.getString("author"));
                bookTO.setPublishYear(resultSet.getInt("publish_year"));
                bookTO.setCount(resultSet.getInt("count"));
                bookTO.setDescription(resultSet.getString("description"));
            }
        } catch(Exception e) {
            log.error("Get book by id exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
        return bookTO;
    }

    /**
     * Method find books in books table containing searchRequest
     * @param searchRequest - row to find
     * @return list of books
     */
    @Override
    public List<BookTO> findBooks(String searchRequest) {
        List<BookTO> foundBooks = new ArrayList<BookTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            searchRequest = "%" + searchRequest + "%";
            String getByIdQuery = "SELECT id, title, author, publish_year, count, description FROM books WHERE lower(CONCAT(title,' ',author,' ',publish_year,' ',count,' ', description)) LIKE lower('"+searchRequest+"')";
            statement = connection.createStatement();
            resultSet =statement.executeQuery(getByIdQuery);
            while (resultSet.next()) {
                BookTO bookTO = new BookTO();
                bookTO.setId(resultSet.getLong("id"));
                bookTO.setTitle(resultSet.getString("title"));
                bookTO.setAuthor(resultSet.getString("author"));
                bookTO.setPublishYear(resultSet.getInt("publish_year"));
                bookTO.setCount(resultSet.getInt("count"));
                bookTO.setDescription(resultSet.getString("description"));
                foundBooks.add(bookTO);
            }
        } catch(Exception e) {
            log.error("Find book exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
        return foundBooks;
    }


    /**
     *  Method to get book list from table books
     * @return list of books
     */
    @Override
    public List<BookTO> getBookTOList() {
        List<BookTO> bookTOList= new ArrayList<BookTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String getBookTOListQuery = "SELECT id, title, author, publish_year, count, description FROM books ORDER BY publish_year LIMIT 5";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getBookTOListQuery);
            while (resultSet.next()) {
                BookTO bookTO = new BookTO();
                bookTO.setId(resultSet.getLong("id"));
                bookTO.setTitle(resultSet.getString("title"));
                bookTO.setAuthor(resultSet.getString("author"));
                bookTO.setPublishYear(resultSet.getInt("publish_year"));
                bookTO.setCount(resultSet.getInt("count"));
                bookTO.setDescription(resultSet.getString("description"));
                bookTOList.add(bookTO);
            }
        } catch(Exception e) {
            log.error("Get book list exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String takeBookQuery = "INSERT INTO books_onhold (book_id, user_id, hold_type, approved) VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(takeBookQuery);
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2,userId);
            preparedStatement.setString(3, holdType);
            preparedStatement.setBoolean(4, false); // false - not approved
            preparedStatement.execute();
        } catch(Exception e) {
            log.error("Take book exception",e);
        } finally {
            try {
                if(preparedStatement!=null)
                    preparedStatement.close();
            } catch (SQLException e) {
                log.error("Closing prepared statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
    }


    /**
     * Method removes book from books_onhold table by operation id
     * @param operationId - operation id
     */
    @Override
    public void returnBook(long operationId){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String returnBookQuery = "DELETE FROM books_onhold WHERE operation_id='" + operationId + "'";
            statement = connection.createStatement();
            statement.executeUpdate(returnBookQuery);
        } catch(Exception e) {
            log.error("Return book exception",e);
        } finally {
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
    }

    /**
     * Method sets approval of operation in books_onhold table by operation id
     * @param operationId - operation id
     */

    @Override
    public void approveBook(long operationId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String updateQuery = "UPDATE books_onhold SET books_onhold.approved = ? WHERE operation_id = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setBoolean(1, true); // true = approved
            preparedStatement.setLong(2, operationId);
            preparedStatement.execute();
        } catch(Exception e) {
            log.error("Approve book exception",e);;
        } finally {
            try {
                if(preparedStatement!=null)
                    preparedStatement.close();
            } catch (SQLException e) {
                log.error("Closing prepared statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
    }

    /**
     * Method checks if book is on hold in books_onhold table by id
     * @param id book id
     * @return <code>true</code> if the book is in book_onhold table;
     *                  <code>false</code> otherwise.
     */
    @Override
    public boolean bookOnHold(long id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean bookOnHold = true;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String checkBookOnHoldQuery = "SELECT book_id FROM books_onhold WHERE book_id = " + id;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(checkBookOnHoldQuery);
            if (resultSet.next()) {
                bookOnHold = true;
            } else {
                bookOnHold = false;
            }
        } catch(Exception e) {
            log.error("Approve book exception",e);;
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing prepared statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
            return  bookOnHold;
        }
    }

    /**
     * Method takes books on hold list by user id
     * @param userId - user id
     * @return List<BookTO> - list of books instances
     */
    @Override
    public List<BookTO> getBooksOnHoldList(long userId) {
        List<BookTO> booksOnHoldList= new ArrayList<BookTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String getBooksOnHoldListQuery = "SELECT books.id, books.title, books.author, books.publish_year, books.description, books_onhold.operation_id, books_onhold.hold_type, books_onhold.approved " +
                    "FROM books INNER JOIN books_onhold ON books.id=books_onhold.book_id WHERE books_onhold.user_id = '" + userId +"'";
            statement = connection.createStatement();
            resultSet =statement.executeQuery(getBooksOnHoldListQuery);
            while (resultSet.next()) {
                BookTO bookTO = new BookTO();
                bookTO.setId(resultSet.getLong("id"));
                bookTO.setTitle(resultSet.getString("title"));
                bookTO.setAuthor(resultSet.getString("author"));
                bookTO.setPublishYear(resultSet.getInt("publish_year"));
                bookTO.setDescription(resultSet.getString("description"));
                bookTO.setOperationId(resultSet.getLong("operation_id"));
                bookTO.setHoldType(resultSet.getString("hold_type"));
                bookTO.setApproved(resultSet.getBoolean("approved"));
                booksOnHoldList.add(bookTO);
            }
        } catch(Exception e) {
            log.error("Books on hold list exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
        return booksOnHoldList;
    }

    /**
     * Method takes  all books on hold from books_onhold table
     * no params
     *  @return List<BookTO> - list of books instances from books_onhold
     */
    @Override
    public List<BookTO> getAllBooksOnHoldList() {
        List<BookTO> booksOnHoldList= new ArrayList<BookTO>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String getAllBooksOnHoldListQuery = "SELECT books.id, books.title, books.author, books.publish_year, books.description, books_onhold.operation_id, books_onhold.user_id, books_onhold.hold_type, books_onhold.approved " +
                    "FROM books INNER JOIN books_onhold ON books.id=books_onhold.book_id";
            statement = connection.createStatement();
            resultSet =statement.executeQuery(getAllBooksOnHoldListQuery);
            while (resultSet.next()) {
                BookTO bookTO = new BookTO();
                bookTO.setId(resultSet.getLong("id"));
                bookTO.setTitle(resultSet.getString("title"));
                bookTO.setAuthor(resultSet.getString("author"));
                bookTO.setPublishYear(resultSet.getInt("publish_year"));
                bookTO.setDescription(resultSet.getString("description"));
                bookTO.setOperationId(resultSet.getLong("operation_id"));
                bookTO.setUserId(resultSet.getLong("user_id"));
                bookTO.setHoldType(resultSet.getString("hold_type"));
                bookTO.setApproved(resultSet.getBoolean("approved"));
                booksOnHoldList.add(bookTO);
            }
        } catch(Exception e) {
            log.error("All books on hold list exception",e);
        } finally {
            try {
                if (resultSet!= null)
                    resultSet.close();
            } catch (SQLException e) {
                log.error("Closing resultSet exception", e);
            }
            try {
                if(statement!=null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Closing statement exception",e);
            }
            try {
                if(connection!=null)
                    connection.close();
            } catch(SQLException e){
                log.error("Closing connection exception",e);
            }
        }
        return booksOnHoldList;
    }
}
